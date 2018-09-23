package by.chmut.hotel.service;

import java.io.Serializable;

public interface ServiceI<T> {

    T add(T t);

    T update(T t);

    T get(Serializable id);

    void delete(Serializable id);

}
