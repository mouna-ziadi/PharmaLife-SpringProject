package tn.esprit.RegistrationAuth.Login;


import com.auth0.jwt.algorithms.Algorithm;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.bind.annotation.*;
import tn.esprit.Entities.User;
import tn.esprit.Repositories.UserRepository;
import tn.esprit.SMS.TwilioConfiguration;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping(path="/login")
@AllArgsConstructor
@CrossOrigin("*")
@Slf4j
public class LoginController {

    private final UserRepository userRepository;
    private AuthenticationManager authenticationManager;
    private PasswordEncoder passwordEncoder;
    public static final String FORM_URLENCODED_VALUE = "application/x-www-form-urlencoded";

    @PostMapping(consumes = FORM_URLENCODED_VALUE ,produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> authenticateUser( LoginForm loginRequest, HttpServletRequest request) {
        User user = userRepository.findByEmail(loginRequest.getUsername()).orElse(null);

        try {
            // find user by email
            if (user == null) {
                // user not found
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid email/password");
            }

            if (!user.isEnabled()) {
                // user not enabled
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Your account is not enabled. Please verify your account.");
            }

            if (user.isAccountNonLocked()) {
                // perform authentication
                authenticationManager.authenticate(
                        new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));


                // authentication successful
                if(user.getLoginAttempts()<3) {
                    userRepository.resetLoginAttempts(0,true,LocalDateTime.now(),user.getEmail());

                }


                // call successfulAuthentication method to generate tokens
                UserDetails userDetails = new org.springframework.security.core.userdetails.User(
                        user.getEmail(), user.getPassword(), user.getAuthorities());

                Algorithm algorithm = Algorithm.HMAC256("secret".getBytes());
                String access_token = JWT.create()
                        .withSubject(user.getUsername())
                        .withExpiresAt(new Date(System.currentTimeMillis() + 10 * 60 * 1000000))
                        .withIssuer(request.getRequestURI().toString())
                        .withClaim("idUser", user.getIdUser())
                        .withClaim("lastName", user.getLastName())
                        .withClaim("firstName",user.getFirstName())
                        .withClaim("phoneNumber",user.getPhoneNumber())
                        .withClaim("birthDate",user.getBirthDate().toString())
                        .withClaim("address",user.getAddress())
                        .withClaim("city",user.getCity())
                        .withClaim("roles", user.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()))
                        .sign(algorithm);

                // return tokens
                Map<String, String> tokens = new HashMap<>();
                tokens.put("access_token", access_token);
                return ResponseEntity.ok(tokens);

            } else {
                // user account is locked
                if (user.getLockTime() != null && user.getLockTime().plusHours(24).isBefore(LocalDateTime.now())) {
                    // lock time is more than 24 hours ago, unlock the account
                    userRepository.unlockUser(user.getEmail());
                    return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Your account was locked, but is now unlocked. Please try logging in again.");
                } else {
                    // lock time is less than 24 hours ago, return a forbidden status
                    long remainingTime = ChronoUnit.MINUTES.between(LocalDateTime.now(), user.getLockTime().plusHours(24));
                    return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Your account is locked for " + remainingTime + " hours. Please try again later.");
                }
            }
        } catch (AuthenticationException e) {
            // authentication failed
            if (user != null || passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
                if (!user.isEnabled()) {
                    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Please verify your account before logging in.");
                } else if (!user.isAccountNonLocked()) {
                    long remainingTime = ChronoUnit.MINUTES.between(LocalDateTime.now(), user.getLockTime().plusHours(24));
                    return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Your account has been locked for " + remainingTime + " hours. Please try again later.");
                } else {
                    int attempts = user.getLoginAttempts() + 1;
                    userRepository.updateLoginAttempts(attempts, loginRequest.getUsername());
                    if (attempts >= 3) {
                        userRepository.lockUser(LocalDateTime.now(), user.getEmail());
                        return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Your account has been locked for 24 hours. Please contact support for assistance.");
                    }
                }
            }
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid email/password");
        }
    }

}
