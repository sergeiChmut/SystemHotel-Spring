package by.chmut.hotel.bean;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

@AllArgsConstructor
@Data
@NoArgsConstructor
@EqualsAndHashCode
@ToString
@Entity
@Table(name = "Reservation")
public class Reservation implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @ManyToOne(cascade= {CascadeType.REMOVE,
            CascadeType.REFRESH})
    @JoinColumn(name="user_id")
    private User user;
    @ManyToOne(cascade= {CascadeType.REMOVE,
            CascadeType.REFRESH})
    @JoinColumn(name="room_id")
    private Room room;
    private LocalDate checkIn;
    private LocalDate checkOut;
    private LocalDateTime date; // date in format YYYY MM DD HH MM
    private int payment; //  0 - unpaid  |  1 - paid

}
