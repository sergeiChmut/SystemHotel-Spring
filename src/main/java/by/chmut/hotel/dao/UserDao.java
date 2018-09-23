package by.chmut.hotel.dao;

import by.chmut.hotel.bean.User;

import javax.servlet.http.Cookie;

public interface UserDao extends Dao<User> {

    User getUser(String login);

}
