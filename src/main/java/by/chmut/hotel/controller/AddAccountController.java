package by.chmut.hotel.controller;

import by.chmut.hotel.controller.domain.LoginData;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import javax.servlet.http.HttpServletRequest;

@Controller
public class AddAccountController {

    @RequestMapping("/add_account")

    public String showForm(HttpServletRequest req, Model model) {

        if (req.getSession().getAttribute("user") == null) {

            LoginData loginData = new LoginData();

            model.addAttribute("loginData", loginData);

            req.getSession().setAttribute("errorMsg", "");

            return "/add_account";
        }

        return "/reservation";

    }

}
