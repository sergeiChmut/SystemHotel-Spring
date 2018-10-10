package by.chmut.hotel.bean;

import lombok.*;
import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@NoArgsConstructor
@Getter
@Setter
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
    @ManyToOne(cascade= {CascadeType.REFRESH,CascadeType.REMOVE})
    @JoinColumn(name="contact_id")
    private Contacts contacts;
    @OneToMany(mappedBy="user",
            cascade= {CascadeType.REFRESH,CascadeType.REMOVE},fetch = FetchType.EAGER)
    private List<Reservation> reservations;

    @Override
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
        User user = (User) object;
        if (this.id != user.id) {
            return false;
        }
        if (this.login == null) {
            if (user.login != null) {
                return false;
            }
        } else if (!this.login.equals(user.login)) {
            return false;
        }
        if (this.password == null) {
            if (user.password != null) {
                return false;
            }
        } else if (!this.password.equals(user.password)) {
            return false;
        }
        if (this.name == null) {
            if (user.name != null) {
                return false;
            }
        } else if (!this.name.equals(user.name)) {
            return false;
        }
        if (this.lastName == null) {
            if (user.lastName != null) {
                return false;
            }
        } else if (!this.lastName.equals(user.lastName)) {
            return false;
        }
        if (this.role == null) {
            if (user.role != null) {
                return false;
            }
        } else if (!this.role.equals(user.role)) {
            return false;
        }
        if (this.contacts == null) {
            if (user.contacts != null) {
                return false;
            }
        } else if (!this.contacts.equals(user.contacts)) {
            return false;
        }
        if (this.reservations == null) {
            if (user.reservations != null) {
                return false;
            }
        } else if (!this.reservations.equals(user.reservations)) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int result = 1;
        result = result*37 + result*id;
        result = result*37 + (login == null ? 0 : login.hashCode() * result);
        result = result*37 + (password == null ? 0 : password.hashCode() * result);
        result = result*37 + (name == null ? 0 : name.hashCode() * result);
        result = result*37 + (lastName == null ? 0 : lastName.hashCode() * result);
        result = result*37 + (role == null ? 0 : role.hashCode() * result);
        result = result*37 + (contacts == null ? 0 : contacts.hashCode() * result);
        result = result*37 + (reservations == null ? 0 : reservations.hashCode() * result);
        return result;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", login='" + login + '\'' +
                ", password='" + password + '\'' +
                ", name='" + name + '\'' +
                ", lastName='" + lastName + '\'' +
                ", role='" + role + '\'' +
                ", contacts=" + contacts +
                '}';
    }
}

