package by.chmut.hotel.controller;

import by.chmut.hotel.service.DtoService;
import by.chmut.hotel.service.ServiceException;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;


@Controller
public class AdminController {

    @Autowired
    private DtoService dtoService;

    private static final Logger logger = Logger.getLogger(AdminController.class);

    @RequestMapping(value = "/administration")

    public String admin(HttpServletRequest req) {

        req.getSession().removeAttribute("errorAdmin");

        req.getSession().removeAttribute("client");

        try {

            req.getSession().setAttribute("client", dtoService.getRoomWithCheckInOrDepartureForThisDay(LocalDate.now()));

        } catch (ServiceException e) {

            logger.error(e);

            req.getSession().setAttribute("errorAdmin", "errorAdmin");
        }

        return "/administration";
    }
}
