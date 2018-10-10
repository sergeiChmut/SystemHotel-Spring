package by.chmut.hotel.bean;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "Reservation")
public class Reservation implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @ManyToOne(cascade= {CascadeType.REFRESH,CascadeType.REMOVE})
    @JoinColumn(name="user_id")
    private User user;
    @ManyToOne(cascade= {CascadeType.REFRESH,CascadeType.REMOVE})
    @JoinColumn(name="room_id")
    private Room room;
    private LocalDate checkIn;
    private LocalDate checkOut;
    private LocalDateTime date;
    private int payment; //  0 - unpaid  |  1 - paid

    public boolean equals(Object object) {
        if (object == this) {
            return true;
        }
        if (object == null) {
            return false;
        }
        if (this.getClass() != object.getClass()) {
            return false;
        }
        Reservation reservation = (Reservation) object;
        if (this.id != reservation.id) {
            return false;
        }
        if (this.payment != reservation.payment) {
            return false;
        }
        if (this.user == null) {
            if (reservation.user != null) {
                return false;
            }
        } else if (!this.user.equals(reservation.user)) {
            return false;
        }
        if (this.room == null) {
            if (reservation.room != null) {
                return false;
            }
        } else if (!this.room.equals(reservation.room)) {
            return false;
        }
        if (this.checkIn == null) {
            if (reservation.checkIn != null) {
                return false;
            }
        } else if (!this.checkIn.equals(reservation.checkIn)) {
            return false;
        }
        if (this.checkOut == null) {
            if (reservation.checkOut != null) {
                return false;
            }
        } else if (!this.checkOut.equals(reservation.checkOut)) {
            return false;
        }
        if (this.date == null) {
            if (reservation.date != null) {
                return false;
            }
        } else if (!this.date.equals(reservation.date)) {
            return false;
        }
        return true;
    }

    public int hashCode() {
        int result = 1;
        result = result * 37 + id;
        result = result *37 + payment;
        result = result * 37 + (user == null ? 0 : user.hashCode() * result);
        result = result * 37 + (room == null ? 0 : room.hashCode() * result);
        result = result * 37 + (checkIn == null ? 0 : checkIn.hashCode() * result);
        result = result * 37 + (checkOut == null ? 0 : checkOut.hashCode() * result);
        result = result * 37 + (date == null ? 0 : date.hashCode() * result);
        return result;
    }

    @Override
    public String toString() {
        return "Reservation{" +
                "id=" + id +
                ", user=" + user +
                ", room=" + room +
                ", checkIn=" + checkIn +
                ", checkOut=" + checkOut +
                ", date=" + date +
                ", payment=" + payment +
                '}';
    }
}
