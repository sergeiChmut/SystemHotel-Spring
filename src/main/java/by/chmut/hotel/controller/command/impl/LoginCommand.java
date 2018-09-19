package by.chmut.hotel.controller.command.impl;

import by.chmut.hotel.bean.User;

import by.chmut.hotel.service.ServiceException;

import by.chmut.hotel.service.UserService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;


import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import static by.chmut.hotel.controller.command.impl.constant.Constants.COOKIE_AGE;
import static by.chmut.hotel.controller.command.validation.Validator.isPasswordValid;


@Controller

public class LoginCommand {

    @Autowired
    private UserService userService;

    private static final Logger logger = Logger.getLogger(LoginCommand.class);


    @RequestMapping(value = "/login", method = RequestMethod.POST)

    public String login(HttpServletRequest req, HttpServletResponse resp,
                           @RequestParam("login") String login,@RequestParam("password") String password,
                           @RequestParam(value = "remember", required = false) String remember) {

        if (login == null || password == null) {

            return "redirect:/home";
        }

        User user;

        HttpSession session = req.getSession();

        String errorMessage = "errorLog";

        String url = "/add_account";
        try {
            user = userService.getUser(login);

            if (isPasswordValid(user, password)) {

                session.setAttribute("user", user);

                errorMessage = "";

                url = req.getHeader("referer");//+req.getSession().getAttribute("prevPage");

                if (remember != null) {

                    activateRememberMe(user, resp);

                }

            }
        } catch (ServiceException e) {

            logger.error(e);

        }

        session.setAttribute("errorMsg", errorMessage);

        return "redirect:"+url;

    }

    private void activateRememberMe(User user, HttpServletResponse resp) {

        Cookie cookie = userService.addRememberMe(user);

        cookie.setMaxAge(COOKIE_AGE);

        resp.addCookie(cookie);

    }
}
