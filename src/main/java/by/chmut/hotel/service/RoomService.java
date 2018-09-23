package by.chmut.hotel.service;

import by.chmut.hotel.bean.Room;

import java.time.LocalDate;
import java.util.List;

public interface RoomService extends ServiceI<Room> {

    List<Room> getAllRoom();

    List<Room> getAvailableRoom(int bedType, LocalDate checkIn, LocalDate checkOut);

}
