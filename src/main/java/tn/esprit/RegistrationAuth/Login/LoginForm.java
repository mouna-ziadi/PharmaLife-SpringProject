package tn.esprit.RegistrationAuth.Login;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
public class LoginForm {

    @NotNull(message = "Username cannot be empty")
    private String username;

    @NotNull(message = "Password cannot be empty")
    @Length(min = 7, message = "Password should be at least 7 characters long")
    private String password;

}