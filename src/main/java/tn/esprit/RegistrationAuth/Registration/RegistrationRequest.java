package tn.esprit.RegistrationAuth.Registration;

import lombok.*;
import tn.esprit.Entities.Gender;
import tn.esprit.Entities.Role;

import java.time.LocalDate;

@Getter
@Setter
@ToString
@AllArgsConstructor
@EqualsAndHashCode
public class RegistrationRequest {
    private final String firstName;
    private final String lastName;
    private final String email;
    private final String password;
    private  String phoneNumber;
    private final String address;
    private final String city;
    private final Role role;
    private final Gender gender;
    private final LocalDate birthDate;


}
