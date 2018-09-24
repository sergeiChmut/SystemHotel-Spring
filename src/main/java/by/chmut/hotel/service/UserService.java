package by.chmut.hotel.service;

import by.chmut.hotel.bean.User;


public interface UserService extends ServiceI<User>  {

    User getUserAndValidate(String login, String password);
}
