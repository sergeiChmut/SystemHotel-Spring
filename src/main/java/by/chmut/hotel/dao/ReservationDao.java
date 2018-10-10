package by.chmut.hotel.dao;


import by.chmut.hotel.bean.Reservation;
import by.chmut.hotel.bean.Room;
import java.io.Serializable;
import java.util.List;


public interface ReservationDao extends Dao<Reservation>  {

    List<Reservation> getByUserId (Serializable userId);

    int deleteTemporaryReservation(int userId, Room room);

    void setPaidStatus(int userId, Room room);
}

