package by.chmut.hotel.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import static by.chmut.hotel.controller.constant.Constants.HOME;


@Controller

public class WelcomeController {

    @GetMapping(value = "/")

    public String mainPage() {

        return "redirect:" + HOME;
    }

}
