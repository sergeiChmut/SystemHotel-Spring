package by.chmut.hotel.service.impl;

import by.chmut.hotel.bean.Room;
import by.chmut.hotel.bean.User;
import by.chmut.hotel.bean.Reservation;
import by.chmut.hotel.dao.ReservationDao;
import by.chmut.hotel.service.BaseService;
import by.chmut.hotel.service.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


@Service
@Transactional
public class ReservationServiceImpl extends BaseService<Reservation> implements ReservationService {

    @Autowired
    private ReservationDao reservationDao;


    @Override
    public List<Room> getPaidRoomsIfUserHasThem(User user) {
        List<Reservation> lastReservations = reservationDao.getByUserId(user.getId());
        if (lastReservations.isEmpty()) {
            return Collections.emptyList();
        }
        List<Room> result = new ArrayList<>();
        for (Reservation reservation : lastReservations) {
            if (reservation.getPayment() == 1) {
                Room room = reservation.getRoom();
                room.setCheckIn(reservation.getCheckIn());
                room.setCheckOut(reservation.getCheckOut());
                result.add(room);
            }
        }
        return result;
    }

    @Override
    public int deleteTemporaryReservation(int userId, Room room) {
        int rows = reservationDao.deleteTemporaryReservation(userId, room);
        return rows;
    }

    @Override
    public void setPaidStatus(int userId, Room room) {
        reservationDao.setPaidStatus(userId,room);
    }
}
