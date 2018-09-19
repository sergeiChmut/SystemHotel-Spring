package by.chmut.hotel.controller.command.impl;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import static by.chmut.hotel.controller.command.impl.constant.Constants.REMEMBER_ME_COOKIE;

@Controller
public class LogoutCommand {


    @GetMapping(value = "/logout")

    public String mainPage(HttpServletRequest req, HttpServletResponse resp) {

        req.getSession().removeAttribute("user");

        removeRememberMe(req, resp);

        return "redirect:"+req.getHeader("referer");
    }


    private void removeRememberMe(HttpServletRequest req, HttpServletResponse resp) {
        Cookie[] cookies = req.getCookies();
        if (cookies == null) {
            return;
        }
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals(REMEMBER_ME_COOKIE)) {
                cookie.setMaxAge(0);
                cookie.setValue(null);
                resp.addCookie(cookie);
            }
        }
    }
}
