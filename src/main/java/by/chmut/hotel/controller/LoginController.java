package by.chmut.hotel.controller;

import by.chmut.hotel.bean.User;

import by.chmut.hotel.service.ServiceException;

import by.chmut.hotel.service.impl.UserServiceImpl;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Controller

public class LoginController {

    @Autowired
    private UserServiceImpl userService;

    private static final Logger logger = Logger.getLogger(LoginController.class);


    @RequestMapping(value = "/login", method = RequestMethod.POST)

    public String login(HttpServletRequest req, HttpServletResponse resp,
                           @RequestParam("login") String login,@RequestParam("password") String password,
                           @RequestParam(value = "remember", required = false) String remember) {

        if (login == null || password == null) {

            return "redirect:/home";
        }

        User user;

        HttpSession session = req.getSession();

        String message = "errorLog";

        String url = "/error";

        try {

            user = userService.getUserAndValidate(login, password);

            if (user != null) {

                session.setAttribute("user", user);

                message = "";

                url = req.getHeader("referer");

                if (remember != null) {

                    activateRememberMe(user, resp);

                }

            }
        } catch (ServiceException e) {

            logger.error(e);

        }

        session.setAttribute("message", message);

        return "redirect:"+url;

    }

    private void activateRememberMe(User user, HttpServletResponse resp) {

//        Cookie cookie = userService.addRememberMe(user);
//
//        cookie.setMaxAge(COOKIE_AGE);
//
//        resp.addCookie(cookie);

    }
}
