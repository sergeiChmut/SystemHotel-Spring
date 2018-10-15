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
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.ArrayList;
import java.util.List;

import static by.chmut.hotel.controller.constant.Constants.DUPLICATE_MESSAGE;


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
    public User getUser(String login) {

        User user = userDao.getUser(login);

        return user;
    }

    @Override
    public User newUser(LoginData loginData) throws ServiceException {

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
                user.setContacts(contacts);
                user = userDao.add(user);
                return user;

            } catch (Exception e) {

                throw new ServiceException("Error with save user");

            }
        }
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws ServiceException  {

        by.chmut.hotel.bean.User user = userDao.getUser(username);

        List<GrantedAuthority> authorities = getAuthorities(user);

        org.springframework.security.core.userdetails.User.UserBuilder builder = null;

        if (user != null) {

            builder = org.springframework.security.core.userdetails.User.withUsername(username);
            builder.disabled(false);
            builder.password(user.getPassword());
            builder.authorities(authorities);
        } else {
            throw new ServiceException("User not found.");
        }
        return builder.build();
    }



    private List<GrantedAuthority> getAuthorities(by.chmut.hotel.bean.User user) {

        List<GrantedAuthority> authList = new ArrayList<>();

        authList.add(new SimpleGrantedAuthority(user.getRole()));

        return authList;
    }
}
