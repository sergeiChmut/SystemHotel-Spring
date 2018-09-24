package by.chmut.hotel.dao.impl;

import by.chmut.hotel.dao.UserDao;
import by.chmut.hotel.bean.User;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserDaoImpl extends BaseDao<User> implements UserDao {

    public UserDaoImpl() {
        super();
        type = User.class;
    }

    @Override
    public User getUser(String login) {

        List<User> results = getEm().createQuery("from User WHERE login = :login")
                .setParameter("login", login).getResultList();

        return results.isEmpty() ? null : results.get(0);
    }

}
