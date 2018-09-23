package by.chmut.hotel.bean;


import lombok.*;

import javax.persistence.*;
@AllArgsConstructor
@NoArgsConstructor
@Data
@EqualsAndHashCode
@ToString
@Entity
@Table(name="Contacts")
public class Contacts {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String email;
    private String telephone;
    private String country;
    private String city;
    private String address;
    private String zip;
}
