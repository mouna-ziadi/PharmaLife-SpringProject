package tn.esprit.RegistrationAuth.Registration;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import tn.esprit.Entities.User;
import tn.esprit.RegistrationAuth.MessageResponse;
import tn.esprit.Repositories.UserRepository;
import tn.esprit.SMS.TwilioConfiguration;
import tn.esprit.Services.IUserService;
import tn.esprit.Services.UserService;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping(path="/registration")
@AllArgsConstructor
@CrossOrigin("*")
public class RegistrationController {

    private RegistrationService registrationService;
    private final UserRepository userRepository;
    IUserService userService;

    private final TwilioConfiguration twilioConfiguration;

    @PostMapping
    public ResponseEntity<?> register(@RequestBody RegistrationRequest request) {
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Email is already in use!"));
        }
        registrationService.register(request);
        // Snd sms when registration
         Twilio.init(twilioConfiguration.getAccountSid(), twilioConfiguration.getAuthToken());
       /* Message message = Message.creator(
                        new PhoneNumber(request.getPhoneNumber()),
                        new PhoneNumber(twilioConfiguration.getTrialNumber()),
                        "Thank You For Registering On Our Platform PharmaLife \uD83D\uDE03 ! Please Check You Email To Verify Your Account ")
                .create();*/
        return ResponseEntity.ok(new MessageResponse("User registered successfully!"));

    }


    @GetMapping(path = "confirm")
    public String confirm(@RequestParam("token") String token) {
        ///Twilio sms when confirm account
       /* User u = userService.findByToken(token);
        Twilio.init(twilioConfiguration.getAccountSid(), twilioConfiguration.getAuthToken());
        if (u != null){
        Message message = Message.creator(
                        new PhoneNumber(u.getPhoneNumber()),
                        new PhoneNumber(twilioConfiguration.getTrialNumber()),
                        "Your Account Is Verified \uD83D\uDE03 ! Now You Will Benefit From Our Services")
                .create();}*/
        return registrationService.confirmToken(token);

    }

    @GetMapping("/forgetPassword/{email}")
    public Map<String, String> forgetpassword(@PathVariable("email") String email) {
        Map<String, String> temp = new HashMap<String, String>();
        temp.put("token", registrationService.forgetpassword(email));
        return temp;
    }

    @GetMapping("/resetPassword/{token}/{email}/{password}")
    public Map<String, String> reset(@PathVariable("token") String token, @PathVariable("email") String email, @PathVariable("password") String password) {
        Map<String, String> temp = new HashMap<String, String>();
        temp.put("token", registrationService.resetPassword(token, email, password));
        return temp;

    }





}








