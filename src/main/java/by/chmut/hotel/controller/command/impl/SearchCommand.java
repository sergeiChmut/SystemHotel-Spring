package by.chmut.hotel.controller.command.impl;

import by.chmut.hotel.bean.Room;
import by.chmut.hotel.service.RoomService;
import by.chmut.hotel.service.ServiceException;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.POST;

@Controller
public class SearchCommand {

    @Autowired
    private RoomService roomService;

    private static final Logger logger = Logger.getLogger(SearchCommand.class);


    @RequestMapping(value = "/searchR")
    public String showRoom(HttpServletRequest req) {

        List<Room> rooms = roomService.getAllRoom();

        req.getSession().setAttribute("rooms", rooms);

        return "/search";
    }


    @RequestMapping(value = "/search", method = POST)
    public String searchRoom(HttpServletRequest req, @RequestParam(value = "bedType" ,required = false) String bedType,
                             @RequestParam(value = "checkin",required = false) String checkIn,
                             @RequestParam(value = "checkout",required = false) String checkOut){

        List<Room> rooms = new ArrayList<>();

        String url = "/search";

        try {
            if (bedType == null || checkIn == null || checkOut == null) {

                rooms = roomService.getAllRoom();

            } else {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
                LocalDate checkInDate = LocalDate.parse(checkIn, formatter);
                LocalDate checkOutDate = LocalDate.parse(checkOut, formatter);
                int bedTypeInt = Integer.parseInt(bedType);
                req.getSession().setAttribute("checkIn", checkInDate);
                req.getSession().setAttribute("checkOut", checkOutDate);

                rooms = roomService.getAvailableRoom(bedTypeInt,
                        checkInDate, checkOutDate);
            }
        } catch (ServiceException e) {

            logger.error(e);

            req.getSession().setAttribute("message", "main.error");

            url = "/error";

        }

        req.getSession().setAttribute("rooms", rooms);

        return url;

    }

}
