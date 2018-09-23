package by.chmut.hotel.dao.impl;

import by.chmut.hotel.bean.dto.RoomDto;
import by.chmut.hotel.dao.Dto;
import lombok.Getter;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDate;
import java.util.List;

@Repository
public class RoomDtoImpl implements Dto {

    @PersistenceContext
    @Getter
    private EntityManager em;


    @Override
    public List<RoomDto> getAllRoomsWhereCheckInOrCheckOutEqualsDate(LocalDate date){
        List<RoomDto> result = em.createQuery("select r.roomNumber, r.bedType, res.checkIn, res.checkOut, u.name," +
                " u.lastname, c.telephone, c.city, r.price from Reservation res JOIN User u on res.user.id = u.id " +
                "JOIN Room r on res.room.id = r.id JOIN Contacts c on u.contacts.id = c.id " +
                "where res.checkIn = :date or res.checkOut = :date").setParameter("date", date)
                .getResultList();
        return result;
    }
}
