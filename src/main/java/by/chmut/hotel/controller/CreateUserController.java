package by.chmut.hotel.controller;

import by.chmut.hotel.bean.User;
import by.chmut.hotel.controller.domain.LoginData;
import by.chmut.hotel.service.ServiceException;
import by.chmut.hotel.service.impl.UserServiceImpl;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

import static by.chmut.hotel.controller.constant.Constants.DUPLICATE_MESSAGE;


@Controller
public class CreateUserController {

    @Autowired
    private UserServiceImpl userService;

    private static final Logger logger = Logger.getLogger(CreateUserController.class);


    @RequestMapping(value = "/create_user")

    public String createUser(HttpServletRequest req,
                             @ModelAttribute("loginData") LoginData loginData) {

        String message = "haveuser";

        String url = "error";

        try {
            User user = userService.newUser(loginData);

            if (user != null) {

                req.getSession().setAttribute("user", user);

                message = "";

                url = "/reservation";
            }
        } catch (ServiceException e) {

            logger.error(e);

            if (e.getMessage().equals(DUPLICATE_MESSAGE)) {
                message = "addUser.duplicate";
            } else {
                message = "addUser.error";
            }
        }
        req.getSession().setAttribute("message", message);

        return "redirect:" + url;

    }

}
