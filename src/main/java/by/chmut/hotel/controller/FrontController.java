package by.chmut.hotel.controller;

import by.chmut.hotel.controller.command.CommandType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;



@Controller

public class FrontController {

    @GetMapping(value = "/")
    public String mainPage() {

        return "redirect:/home";
    }

    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        CommandType commandType = RequestHandler.get(request);

        commandType.getCommand().execute(request,response);

    }

}
