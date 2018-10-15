package by.chmut.hotel.dao.impl;

import by.chmut.hotel.bean.Reservation;
import by.chmut.hotel.bean.dto.RoomDto;
import by.chmut.hotel.dao.Dto;
import lombok.Getter;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Repository
public class RoomDtoImpl implements Dto {

    @PersistenceContext
    @Getter
    private EntityManager em;


    @Override
    public List<RoomDto> getAllRoomsWhereCheckInOrCheckOutEqualsDate(LocalDate date){
        List<Reservation> res = em.createQuery("from Reservation res where res.payment = 1 and (res.checkIn = :date or res.checkOut = :date)").setParameter("date", date)
                .getResultList();
        List<RoomDto> result = new ArrayList<>();
        for (Reservation reservation:res) {
            RoomDto current = new RoomDto();
            current.setRoomNumber(reservation.getRoom().getRoomNumber());
            current.setBedType(reservation.getRoom().getBedType());
            current.setPrice(reservation.getRoom().getPrice());
            current.setCheckIn(reservation.getCheckIn());
            current.setCheckOut(reservation.getCheckOut());
            current.setName(reservation.getUser().getName());
            current.setLastname(reservation.getUser().getLastName());
            current.setTelephone(reservation.getUser().getContacts().getTelephone());
            current.setCity(reservation.getUser().getContacts().getCity());
            result.add(current);
        }
        return result;
    }
}
