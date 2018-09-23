package by.chmut.hotel.service.impl;

import by.chmut.hotel.bean.User;
import by.chmut.hotel.dao.impl.UserDaoImpl;
import by.chmut.hotel.service.BaseService;
import by.chmut.hotel.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional
public class UserServiceImpl  extends BaseService<User> implements UserService {

    @Autowired
    private UserDaoImpl userDao;

    @Override
    public User getUser(String login) {
        User user = userDao.getUser(login);
        return user;
    }

    @Override
    public User addUser(String login, String password, String name, String lastName, String email, String phone, String country, String city, String address, String zip) {
        return null;
    }
}
