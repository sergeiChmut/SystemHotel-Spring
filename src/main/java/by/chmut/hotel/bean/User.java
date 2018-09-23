package by.chmut.hotel.bean;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@NoArgsConstructor
@Data
@EqualsAndHashCode
@ToString
@Entity
@Table(name="Users")
public class User implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "login", unique = true, length = 20, nullable = false)
    private String login;
    private String password;
    private String name;
    private String lastName;
    private String role;
    @ManyToOne(cascade= {CascadeType.REMOVE,
            CascadeType.REFRESH})
    @JoinColumn(name="contact_id")
    private Contacts contacts;
    @OneToMany(mappedBy="user",
            cascade= {CascadeType.REMOVE, CascadeType.REFRESH})
    private List<Reservation> reservations;


}

