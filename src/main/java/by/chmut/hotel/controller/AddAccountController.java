package by.chmut.hotel.controller;

import by.chmut.hotel.controller.domain.LoginData;
import by.chmut.hotel.service.ServiceException;
import by.chmut.hotel.service.UserService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Locale;

import static by.chmut.hotel.controller.constant.Constants.*;


@Controller
@RequestMapping("/add_account")

public class AddAccountController {

    private static final Logger logger = Logger.getLogger(AddAccountController.class);

    @Autowired
    private UserService userService;

    private MessageSource messageSource;

    @Autowired
    public void setMessageSource(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @RequestMapping("/form")

    public String showForm(Authentication authentication, Model model) {

        if (authentication == null || !authentication.isAuthenticated()) {

            LoginData loginData = new LoginData();

            model.addAttribute(LOGINDATA, loginData);

            return NEW_ACCOUNT;
        }

        return "redirect:" + SEARCH;
    }


    @RequestMapping(value = "/create_user")
    @Transactional
    public String createUser(Model model, @ModelAttribute(LOGINDATA) LoginData loginData, Locale locale) {

        String message;

        try {

            userService.newUser(loginData);

            return "redirect:" + LOGIN;

        } catch (ServiceException e) {

            logger.error(e);

            if (e.getMessage().equals(DUPLICATE_MESSAGE)) {
                message = KEY_NEW_USER_ERROR_DUPLICATE ;
            } else {
                message = KEY_NEW_USER_ERROR;
            }

            model.addAttribute(MESSAGE, messageSource.getMessage(message, new Object[]{}, locale));

        }

        return ERROR;
    }


    @RequestMapping("/error")

    public String loginFail(Model model, Locale locale) {

        logger.info(LOGIN_FAILED_MESSAGE);

        model.addAttribute(MESSAGE, messageSource.getMessage(KEY_LOGIN_ERROR, new Object[]{}, locale));

        LoginData loginData = new LoginData();

        model.addAttribute(LOGINDATA, loginData);

        return NEW_ACCOUNT;

    }
}
