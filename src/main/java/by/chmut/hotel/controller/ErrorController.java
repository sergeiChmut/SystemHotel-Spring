package by.chmut.hotel.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import static by.chmut.hotel.controller.constant.Constants.ERROR;


@Controller

public class ErrorController {


    @RequestMapping(value = "/error")

    public String showErrorPage() {

        return ERROR;

    }
}

