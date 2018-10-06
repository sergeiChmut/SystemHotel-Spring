package by.chmut.hotel.service;

import by.chmut.hotel.bean.User;
import by.chmut.hotel.controller.domain.LoginData;


public interface UserService extends ServiceI<User>  {

    User getUserAndValidate(String login, String password);

    User getUser(String login);

    User newUser(LoginData loginData);
}
