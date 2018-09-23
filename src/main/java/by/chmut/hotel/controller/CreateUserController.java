package by.chmut.hotel.controller;

import by.chmut.hotel.bean.User;
import by.chmut.hotel.controller.validation.encoder.Encoder;
import by.chmut.hotel.controller.domain.LoginData;
import by.chmut.hotel.service.ServiceException;
import by.chmut.hotel.service.UserService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@Controller
public class CreateUserController {

    @Autowired
    private UserService userService;

    private static final Logger logger = Logger.getLogger(LoginController.class);


    @RequestMapping(value = "/create_user", method = RequestMethod.POST)

    public String createUser(HttpServletRequest req, HttpServletResponse resp,
                        @ModelAttribute("loginData") LoginData loginData) {

        String message = "haveuser";

        String url = "add_account";

        try {
            User user = userService.addUser(loginData.getLogin(), Encoder.encode(loginData.getPassword()),
                    loginData.getFirstName(), loginData.getLastName(),
                    loginData.getEmail(), loginData.getPhone(), loginData.getCountry(), loginData.getCity(),
                    loginData.getAddress(), loginData.getZip());

            if (user != null) {

                req.getSession().setAttribute("user", user);

                message = "";

                url = "/reservation";
            }

        } catch (ServiceException e) {

            logger.error(e);

            message = "addUser.error";
        }

        req.getSession().setAttribute("errorMsg", message);

        return "redirect:"+url;

    }

}
