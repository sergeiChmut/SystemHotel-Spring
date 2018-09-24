package by.chmut.hotel.service;

import by.chmut.hotel.dao.Dao;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;

@NoArgsConstructor
@Service
@Transactional
public class BaseService<T> implements ServiceI<T> {

    @Autowired
    private Dao<T> baseDao;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public T add(T t) {
        baseDao.add(t);
        return t;
    }

    @Override
    public T update(T t) {
        return baseDao.update(t);
    }

    @Override
    public T get(Serializable id) {
        return baseDao.get(id);
    }

    @Override
    public void delete(Serializable id) {
        baseDao.delete(id);
    }
}
