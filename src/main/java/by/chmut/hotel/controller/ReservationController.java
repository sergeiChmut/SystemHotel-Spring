package by.chmut.hotel.controller;

import by.chmut.hotel.bean.Reservation;
import by.chmut.hotel.bean.Room;
import by.chmut.hotel.bean.User;
import by.chmut.hotel.service.ReservationService;
import by.chmut.hotel.service.RoomService;
import by.chmut.hotel.service.ServiceException;
import by.chmut.hotel.service.UserService;
import org.apache.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import static by.chmut.hotel.controller.constant.Constants.*;

@Controller

public class ReservationController {

    private static final Logger logger = Logger.getLogger(ReservationController.class);

    private static final long NULL = 0L;

    @Autowired
    private UserService userService;
    @Autowired
    private ReservationService reservationService;
    @Autowired
    private RoomService roomService;

    private MessageSource messageSource;

    @Autowired
    public void setMessageSource(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @GetMapping(value = "/reservation")
    public String makeReservation(Model model, HttpServletRequest req, Authentication authentication, Locale locale) {

        HttpSession session = req.getSession();

        long totalSum = getTotalSum(session);

        List<Room> temporaryRooms = getRooms(session);

        Integer roomId = (Integer) session.getAttribute(ROOM_ID);

        Integer temporaryNumber = (Integer) session.getAttribute(TEMPORARY_NUMBER);

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        User user = userService.getUser(userDetails.getUsername());

        // search - if user user has paid reservation - set to session for view
        setPaidRoomsInSession(req, user);

        String page = RESERVATION;

        try {
            // Add room
            if (roomId != null) {
                Room room = getRoomById(req, roomId);
                temporaryRooms.add(room);
                saveReservation(user, room);
                totalSum += room.getPrice();
                session.removeAttribute(ROOM_ID);
                session.setAttribute(TEMPORARY_ROOMS, temporaryRooms);
                session.setAttribute(TOTAL_SUM, totalSum);
            }
            // Remove room
            if (temporaryNumber != null) {
                Room room = getRoomByIdFromTemp(temporaryRooms, temporaryNumber);
                temporaryRooms.remove(room);
                reservationService.deleteTemporaryReservation(user.getId(), room);
                totalSum -= room.getPrice();
                session.removeAttribute(TEMPORARY_NUMBER);
                session.setAttribute(TEMPORARY_ROOMS, temporaryRooms);
                session.setAttribute(TOTAL_SUM, totalSum);
            }

            // set Empty message
            if (temporaryRooms.isEmpty()) {
                req.setAttribute(EMPTY_MESSAGE, KEY_RESERVATION_EMPTY_LIST);
            }
        } catch (ServiceException e) {
            logger.error(e);
            model.addAttribute(MESSAGE,
                    messageSource.getMessage(KEY_RESERVATION_PAGE_ERROR, new Object[]{}, locale));

            page = ERROR;
        }

        return page;
    }

    private void saveReservation(User user, Room room) {
        Reservation reservation = new Reservation();
        reservation.setUser(user);
        reservation.setRoom(room);
        reservation.setCheckIn(room.getCheckIn());
        reservation.setCheckOut(room.getCheckOut());
        reservation.setDate(LocalDateTime.now());
        reservation.setPayment(UNPAID);
        reservationService.add(reservation);
    }


    private void setPaidRoomsInSession(HttpServletRequest req, User user) {
        try {
            req.getSession().setAttribute(PAID_ROOMS, reservationService.getPaidRoomsIfUserHasThem(user));
        } catch (ServiceException e) {
            logger.error(e);
        }
    }


    private Room getRoomById(HttpServletRequest req, int roomId) throws ServiceException {
        Room result;
        result = roomService.get(roomId);
        result.setTemporaryNumber((int) (Math.random() * 1000000) + (int) (Math.random() * 1000));
        result.setCheckIn((LocalDate) req.getSession().getAttribute(CHECKIN));
        result.setCheckOut((LocalDate) req.getSession().getAttribute(CHECKOUT));
        return result;
    }

    private Room getRoomByIdFromTemp(List<Room> temporaryRooms, int temporaryNumber) {
        Room result = new Room();
        for (Room room : temporaryRooms) {
            if (room.getTemporaryNumber() == temporaryNumber) {
                result = room;
            }
        }
        return result;
    }


    private long getTotalSum(HttpSession session) {

        if (session.getAttribute(TOTAL_SUM) != null) {

            return (long) session.getAttribute(TOTAL_SUM);
        }
        session.setAttribute(TOTAL_SUM, NULL);

        return NULL;
    }

    private List<Room> getRooms(HttpSession session) {

        List<Room> result = (List<Room>) session.getAttribute(TEMPORARY_ROOMS);

        if (result != null) {
            return result;
        }

        result = new ArrayList<>();

        session.setAttribute(TEMPORARY_ROOMS, new ArrayList<Room>());

        return result;
    }

}

