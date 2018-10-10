package by.chmut.hotel.controller;

import by.chmut.hotel.bean.Room;
import by.chmut.hotel.service.RoomService;
import by.chmut.hotel.service.ServiceException;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;

import static by.chmut.hotel.controller.constant.Constants.*;

@Controller

public class SearchController {

    private static final Logger logger = Logger.getLogger(SearchController.class);

    @Autowired
    private RoomService roomService;

    private MessageSource messageSource;

    @Autowired
    public void setMessageSource(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @RequestMapping(value = "/search")
    public String searchRoom(Model model, HttpServletRequest req, Locale locale,
                             @RequestParam(value = "bedType", required = false) String bedType,
                             @RequestParam(value = "checkin", required = false) String checkIn,
                             @RequestParam(value = "checkout", required = false) String checkOut) {

        List<Room> rooms;

        String page = SEARCH;

        try {

            if (bedType == null || checkIn == null || checkOut == null) {

                rooms = roomService.getAllRoom();

            } else {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
                LocalDate checkInDate = LocalDate.parse(checkIn, formatter);
                LocalDate checkOutDate = LocalDate.parse(checkOut, formatter);
                int bedTypeInt = Integer.parseInt(bedType);
                req.getSession().setAttribute(CHECKIN, checkInDate);
                req.getSession().setAttribute(CHECKOUT, checkOutDate);

                rooms = roomService.getAvailableRoom(bedTypeInt,
                        checkInDate, checkOutDate);
            }

            req.getSession().setAttribute(ROOMS, rooms);

        } catch (ServiceException e) {

            logger.error(e);

            model.addAttribute(MESSAGE,
                    messageSource.getMessage(KEY_SEARCH_PAGE_ERROR, new Object[]{}, locale));

            page = ERROR;

        }

        return page;

    }

}
