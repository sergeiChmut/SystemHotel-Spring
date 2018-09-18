package by.chmut.hotel.controller.command.impl;

import by.chmut.hotel.service.RoomService;
import by.chmut.hotel.service.ServiceException;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;


import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static by.chmut.hotel.controller.command.impl.constant.Constants.MAIN_PAGE;
import static by.chmut.hotel.controller.command.impl.constant.Constants.PATH_FOR_ERROR_PAGE;

@Controller
public class DefaultCommand {

    private static final Logger logger = Logger.getLogger(DefaultCommand.class);

    @Autowired
    private RoomService roomService;

    @GetMapping(value = "/home")
    public String mainPage(ModelMap model, HttpServletRequest req) {
        try {
//            model.addAttribute("rooms",roomService.getAllRoom());
            req.getSession().setAttribute("rooms",roomService.getAllRoom());

        } catch (ServiceException e) {
            logger.error(e);

            req.getSession().setAttribute("pagePath", PATH_FOR_ERROR_PAGE);

            req.getSession().setAttribute("message", "main.error");
        }
        return "/home";
    }



    public void execute(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {

        try {
//            req.getSession().setAttribute("rooms", factory.getRoomService().getAllRoom());

        } catch (ServiceException e) {

            logger.error(e);

            req.getSession().setAttribute("pagePath", PATH_FOR_ERROR_PAGE);

            req.getSession().setAttribute("message", "main.error");
        }

        req.getRequestDispatcher(MAIN_PAGE).forward(req,resp);

    }
}
