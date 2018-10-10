package by.chmut.hotel.service;

import by.chmut.hotel.bean.Reservation;
import by.chmut.hotel.bean.Room;
import by.chmut.hotel.bean.User;

import java.util.List;

public interface ReservationService extends ServiceI<Reservation>{

    List<Room> getPaidRoomsIfUserHasThem(User user);

    int deleteTemporaryReservation(int userId, Room room);

    void setPaidStatus(int userId, Room room);
}
