package by.chmut.hotel.dao.impl;

import by.chmut.hotel.dao.Dao;
import lombok.Getter;
import org.springframework.stereotype.Repository;


import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.io.Serializable;

@Repository
public class BaseDao<T> implements Dao<T> {

    Class<T> type;

    @PersistenceContext
    @Getter
    private EntityManager em;

    @Override
    public T add(T t) {
        em.persist(t);
        return t;
    }

    @Override
    public T get(Serializable id) {
        return em.find(type, id);
    }

    @Override
    public T update(T t) {
        em.merge(t);
        return t;
    }

    @Override
    public void delete(Serializable id) {
        T t = em.find(type, id);
        em.remove(t);
    }
}