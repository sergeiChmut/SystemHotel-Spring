package by.chmut.hotel.service.impl;


import by.chmut.hotel.bean.Contacts;
import by.chmut.hotel.service.BaseService;
import by.chmut.hotel.service.ContactsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional
public class ContactsServiceImpl extends BaseService<Contacts> implements ContactsService {
}
