package by.chmut.hotel.dao.impl;

import by.chmut.hotel.dao.RoomDao;
import by.chmut.hotel.bean.Room;
import org.springframework.stereotype.Repository;
import java.time.LocalDate;
import java.util.List;

import static by.chmut.hotel.controller.constant.Constants.QUANTITY_MINUTES_FOR_PAYMENT;

@Repository
public class RoomDaoImpl extends BaseDao<Room> implements RoomDao {

    public RoomDaoImpl() {
        super();
        type = Room.class;
    }

    @Override
    public List<Room> getAllRoom() {
        List<Room> result = getEm().createQuery("from Room").getResultList();
        return result;
    }

    @Override
    public List<Room> getAvailableRoom(int bedType, LocalDate checkIn, LocalDate checkOut) {

        List<Room> result = getEm().createQuery("select r from Room r where r.bedType = :bedType and r.id not in " +
                "(select ro.id from Room ro join ro.reservations res where (res.payment = 1 and " +
                "(res.checkOut> :checkIn) and (res.checkIn< :checkOut)))").setParameter("bedType",bedType)
                        .setParameter("checkIn",checkIn)
                        .setParameter("checkOut",checkOut).getResultList();

        return result;
    }


}
