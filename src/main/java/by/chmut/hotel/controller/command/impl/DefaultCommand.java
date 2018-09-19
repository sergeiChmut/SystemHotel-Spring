package by.chmut.hotel.controller.command.impl;

import by.chmut.hotel.service.RoomService;
import by.chmut.hotel.service.ServiceException;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;


@Controller
public class DefaultCommand {

    private static final Logger logger = Logger.getLogger(DefaultCommand.class);

    @Autowired
    private RoomService roomService;

    @GetMapping(value = "/home")

    public String mainPage(ModelMap model, HttpServletRequest req) {

        String url = "/home";

        try {
//            model.addAttribute("rooms",roomService.getAllRoom());
            req.getSession().setAttribute("rooms",roomService.getAllRoom());

        } catch (ServiceException e) {
            logger.error(e);

            url = "error";

            req.getSession().setAttribute("message", "main.error");
        }
        return url;
    }
}
