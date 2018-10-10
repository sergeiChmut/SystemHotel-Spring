package by.chmut.hotel.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;


import javax.servlet.http.HttpServletRequest;

import static by.chmut.hotel.controller.constant.Constants.ROOM_ID;
import static by.chmut.hotel.controller.constant.Constants.TEMPORARY_NUMBER;


@Controller
public class AjaxRequestController {

    @RequestMapping(value = "/setRoomId")

    public void setId(HttpServletRequest req) {

        int roomId = Integer.parseInt(req.getParameter(ROOM_ID));

        req.getSession().setAttribute(ROOM_ID,roomId);

    }


    @RequestMapping(value = "/setUniqueNumber")

    public void setUniqueNumber(HttpServletRequest req) {

        int tempNumber = Integer.parseInt(req.getParameter(TEMPORARY_NUMBER));

        req.getSession().setAttribute(TEMPORARY_NUMBER,tempNumber);

    }
}
