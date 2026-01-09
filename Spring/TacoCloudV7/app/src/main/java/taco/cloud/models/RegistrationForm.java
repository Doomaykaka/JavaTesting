package taco.cloud.models;

import org.springframework.security.crypto.password.PasswordEncoder;
import lombok.Data;

@Data
public class RegistrationForm {
    private String username;
    private String password;
    private String fullname;
    private String street;
    private String city;
    private String state;
    private String zip;
    private String phone;

    public UserData toUser(PasswordEncoder passwordEncoder) {
        UserData converted = new UserData();
        converted.setId(0);
        converted.setUsername(username);
        converted.setPassword(passwordEncoder.encode(password));
        converted.setFullname(fullname);
        converted.setStreet(street);
        converted.setCity(city);
        converted.setState(state);
        converted.setZip(zip);
        converted.setPhoneNumber(phone);

        return converted;
    }
}
