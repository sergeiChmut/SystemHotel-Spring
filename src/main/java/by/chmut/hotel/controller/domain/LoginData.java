package by.chmut.hotel.controller.domain;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class LoginData {
	
	private String login;
	private String password;
	private String firstName;
	private String lastName;
	private String email;
	private String phone;
	private String country;
	private String city;
	private String address;
	private String zip;


}
