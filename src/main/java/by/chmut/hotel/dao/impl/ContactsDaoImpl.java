package by.chmut.hotel.dao.impl;

import by.chmut.hotel.bean.Contacts;
import by.chmut.hotel.dao.ContactsDao;
import org.springframework.stereotype.Repository;

@Repository
public class ContactsDaoImpl extends BaseDao<Contacts> implements ContactsDao {
    public ContactsDaoImpl() {
        super();
        type = Contacts.class;
    }
}
