package by.chmut.hotel.controller.command.impl;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;


import javax.servlet.http.HttpServletRequest;

@Controller
public class SetRoomIdCommand {

    @RequestMapping(value = "/setRoomId")

    public void setId(HttpServletRequest req) {

        int roomId = Integer.parseInt(req.getParameter("roomId"));

        req.getSession().setAttribute("roomId",roomId);

    }
}
