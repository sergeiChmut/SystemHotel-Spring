package by.chmut.hotel.bean;


import lombok.*;
import javax.persistence.*;

@NoArgsConstructor
@Getter
@Setter
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
        Contacts contacts = (Contacts) object;
        if (this.id != contacts.id) {
            return false;
        }
        if (this.email == null) {
            if (contacts.email != null) {
                return false;
            }
        } else if (!this.email.equals(contacts.email)) {
            return false;
        }
        if (this.telephone == null) {
            if (contacts.telephone != null) {
                return false;
            }
        } else if (!this.telephone.equals(contacts.telephone)) {
            return false;
        }
        if (this.country == null) {
            if (contacts.country != null) {
                return false;
            }
        } else if (!this.country.equals(contacts.country)) {
            return false;
        }
        if (this.city == null) {
            if (contacts.city != null) {
                return false;
            }
        } else if (!this.city.equals(contacts.city)) {
            return false;
        }
        if (this.address == null) {
            if (contacts.address != null) {
                return false;
            }
        } else if (!this.address.equals(contacts.address)) {
            return false;
        }
        if (this.zip == null) {
            if (contacts.zip != null) {
                return false;
            }
        } else if (!this.zip.equals(contacts.zip)) {
            return false;
        }
        return true;
    }
    public int hashCode() {
        int result = 1;
        result = result*37 + result*id;
        result = result*37 + (email == null ? 0 : email.hashCode()*result);
        result = result*37 + (telephone == null ? 0 : telephone.hashCode()*result);
        result = result*37 + (country == null ? 0 : country.hashCode()*result);
        result = result*37 + (city == null ? 0 : city.hashCode()*result);
        result = result*37 + (address == null ? 0 : address.hashCode()*result);
        result = result*37 + (zip == null ? 0 : zip.hashCode()*result);
        return result;
    }

    @Override
    public String toString() {
        return "Contacts{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", telephone='" + telephone + '\'' +
                ", country='" + country + '\'' +
                ", city='" + city + '\'' +
                ", address='" + address + '\'' +
                ", zip='" + zip + '\'' +
                '}';
    }
}
