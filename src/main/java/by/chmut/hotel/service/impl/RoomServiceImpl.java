package by.chmut.hotel.service.impl;

import by.chmut.hotel.bean.Room;
import by.chmut.hotel.dao.impl.RoomDaoImpl;
import by.chmut.hotel.service.BaseService;
import by.chmut.hotel.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDate;
import java.util.List;

@Service
@Transactional
public class RoomServiceImpl  extends BaseService<Room> implements RoomService {

    @Autowired
    private RoomDaoImpl roomDao;

    @Override
    public List<Room> getAllRoom() {
        List<Room> result = roomDao.getAllRoom();
        return result;
    }

    @Override
    public List<Room> getAvailableRoom(int bedType, LocalDate checkIn, LocalDate checkOut) {
        List<Room> result = roomDao.getAvailableRoom(bedType,checkIn,checkOut);
        return result;
    }
}
