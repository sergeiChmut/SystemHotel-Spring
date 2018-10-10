package by.chmut.hotel.service;

import by.chmut.hotel.bean.User;
import by.chmut.hotel.controller.domain.LoginData;
import org.springframework.security.core.userdetails.UserDetailsService;


public interface UserService extends ServiceI<User>, UserDetailsService {

    User getUser(String login);

    User newUser(LoginData loginData);
}
