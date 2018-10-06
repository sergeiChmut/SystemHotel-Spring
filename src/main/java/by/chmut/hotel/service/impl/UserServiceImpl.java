package by.chmut.hotel.service.impl;

import by.chmut.hotel.bean.Contacts;
import by.chmut.hotel.bean.User;
import by.chmut.hotel.controller.domain.LoginData;
import by.chmut.hotel.dao.ContactsDao;
import by.chmut.hotel.dao.UserDao;
import by.chmut.hotel.service.BaseService;
import by.chmut.hotel.service.ServiceException;
import by.chmut.hotel.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static by.chmut.hotel.controller.constant.Constants.DUPLICATE_MESSAGE;
import static by.chmut.hotel.controller.constant.Constants.ROLE_USER;
import static by.chmut.hotel.service.validation.Validator.isPasswordValid;

@Service
@Transactional
public class UserServiceImpl  extends BaseService<User> implements UserService {

    @Autowired
    private UserDao userDao;
    @Autowired
    private ContactsDao contactsDao;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Override
    public User getUserAndValidate(String login, String password) {

        User user = userDao.getUser(login);

        if (isPasswordValid(user, password)) {
            return user;
        }

        return null;
    }

    @Override
    public User getUser(String login) {

        User user = userDao.getUser(login);

        return user;
    }

    @Override
    public User newUser(LoginData loginData) {

        User user = userDao.getUser(loginData.getLogin());

        if (user != null) {

            throw new ServiceException(DUPLICATE_MESSAGE);

        } else {

            try {
                Contacts contacts = new Contacts();
                contacts.setEmail(loginData.getEmail());
                contacts.setTelephone(loginData.getPhone());
                contacts.setCountry(loginData.getCountry());
                contacts.setCity(loginData.getCity());
                contacts.setAddress(loginData.getAddress());
                contacts.setZip(loginData.getZip());
                contacts = contactsDao.add(contacts);
                user = new User();
                user.setLogin(loginData.getLogin());
                user.setPassword(passwordEncoder.encode(loginData.getPassword()));
                user.setName(loginData.getFirstName());
                user.setLastName(loginData.getLastName());
                user.setRole("ROLE_USER");
                user = userDao.add(user);
                user.setContacts(contacts);
                return user;

            } catch (Exception e) {

                throw new ServiceException("Error with save user");

            }
        }
    }
}
