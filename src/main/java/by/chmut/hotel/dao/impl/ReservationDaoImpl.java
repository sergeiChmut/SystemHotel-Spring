package by.chmut.hotel.dao.impl;

import by.chmut.hotel.bean.Room;
import by.chmut.hotel.bean.dto.RoomDto;
import by.chmut.hotel.dao.ReservationDao;
import by.chmut.hotel.bean.Reservation;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Repository;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

@Repository
@Scope(proxyMode = ScopedProxyMode.INTERFACES)
public class ReservationDaoImpl extends BaseDao<Reservation> implements ReservationDao {

    public ReservationDaoImpl() {
        super();
        type = Reservation.class;
    }

    @Override
    public List<Reservation> getByUserId(Serializable userId) {
        List<Reservation> result = getEm().createQuery("from Reservation where user.id = :userid")
                .setParameter("userid",userId).getResultList();
        return result;
    }

    @Override
    public int deleteTemporaryReservation(int userId, Room room) {
        int rows = getEm().createQuery("delete from Reservation res where (res.payment = 0 and res.room = :room and res.user.id = :userId)")
                .setParameter("room", room).setParameter("userId",userId).executeUpdate();
        return rows;
    }

    @Override
    public void setPaidStatus(int userId, Room room) {
        getEm().createQuery("update Reservation res set res.payment = 1 where " +
                "(res.room.id = :roomId and res.user.id = :userId and res.checkIn = :checkIn and res.checkOut = :checkOut)")
                .setParameter("roomId",room.getId()).setParameter("userId", userId)
                .setParameter("checkIn", room.getCheckIn()).setParameter("checkOut", room.getCheckOut())
                .executeUpdate();
    }
}
