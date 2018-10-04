package by.chmut.hotel.controller;

import by.chmut.hotel.bean.Room;
import by.chmut.hotel.bean.User;
import by.chmut.hotel.service.ReservationService;
import by.chmut.hotel.service.RoomService;
import by.chmut.hotel.service.ServiceException;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Controller

public class ReservationController {

    @Autowired
    private ReservationService reservationService;
    @Autowired
    private RoomService roomService;

    private static final Logger logger = Logger.getLogger(ReservationController.class);

    @GetMapping(value = "/reservation")
    public String makeReservation(HttpServletRequest req) {
        HttpSession session = req.getSession();
        session.removeAttribute("error");
        long totalSum = getTotalSum(session);
        List<Room> temporaryRooms = getRooms(session);
        Integer roomId = (Integer) session.getAttribute("roomId");
        Integer temporaryNumber = (Integer) session.getAttribute("tempNum");
        User user = (User) session.getAttribute("user");

        // search - if user user has paid reservation - set to session for view
        setPaidRoomsInSession(req, user);

        try {
            // Add room
            if (roomId != null) {
                Room room = getRoomById(req, roomId);
                temporaryRooms.add(room);
//                reservationService.save(new Reservation(user.getId(), room.getId(), room.getCheckIn(), room.getCheckOut()));
                totalSum += room.getPrice();
                session.removeAttribute("roomId");
                session.setAttribute("tempRooms", temporaryRooms);
                session.setAttribute("totalSum", totalSum);
            }
            // Remove room
            if (temporaryNumber != null) {
                Room room = getRoomByIdFromTemp(temporaryRooms, temporaryNumber);
                temporaryRooms.remove(room);
                reservationService.deleteTemporaryReservation(user.getId(), room);
                totalSum -= room.getPrice();
                session.removeAttribute("tempNum");
                session.setAttribute("tempRooms", temporaryRooms);
                session.setAttribute("totalSum", totalSum);
            }
        } catch (ServiceException e) {
            logger.error(e);
            req.getSession().setAttribute("error", "errorReservation");
        }


        // set Empty message
        if (temporaryRooms.isEmpty()) {
            req.setAttribute("emptyMsg", "reserv.emptyList");
        }

        return "/reservation";
    }

    private void setPaidRoomsInSession(HttpServletRequest req, User user) {
        try {
            req.getSession().setAttribute("paidRooms", reservationService.getPaidRoomsIfUserHasThem(user));
        } catch (ServiceException e) {
            logger.error(e);
        }
    }


    private Room getRoomById(HttpServletRequest req, int roomId) throws ServiceException {
        Room result;
        result = roomService.get(roomId);
        result.setTemporaryNumber((int) (Math.random() * 1000000) + (int) (Math.random() * 1000));
        result.setCheckIn((LocalDate) req.getSession().getAttribute("checkIn"));
        result.setCheckOut((LocalDate) req.getSession().getAttribute("checkOut"));
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

        if (session.getAttribute("totalSum") != null) {

            return (long) session.getAttribute("totalSum");
        }
        session.setAttribute("totalSum", 0L);

        return 0L;
    }

    private List<Room> getRooms(HttpSession session) {

        List<Room> result = (List<Room>) session.getAttribute("tempRooms");

        if (result != null) {
            return result;
        }

        result = new ArrayList<>();

        session.setAttribute("tempRooms", new ArrayList<Room>());

        return result;
    }

}

