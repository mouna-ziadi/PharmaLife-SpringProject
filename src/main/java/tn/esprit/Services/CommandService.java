package tn.esprit.Services;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.esprit.Entities.Command;
import tn.esprit.Entities.User;
import tn.esprit.Repositories.CommandRepository;
import tn.esprit.Repositories.UserRepository;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import java.time.LocalDate;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;



@AllArgsConstructor
@Slf4j
@Service
public class CommandService implements ICommandService {
    private final CommandRepository commandRepository;

    UserRepository userRepository;

    @Autowired
    private JavaMailSender javaMailSender;
//    @Override
//    /* public Command addCommand(Command p) {
//        if (p.getShippingAddressCommand() == null || p.getShippingAddressCommand().isEmpty()) {
//            throw new IllegalArgumentException("ShippingAddress cannot be empty");
//        }
//        try {
//            return commandRepository.save(p);
//        } catch (Exception e) {
//            throw new RuntimeException("Failed to add ShippingAdress", e);
//        }
//    } */

    @Override
     public String addCommand(Command p) {
            commandRepository.save(p);
            this.sendSimpleMailForCommand("fakher.karouida@esprit.tn");
            return "add command";
        }

    @Override
    public Command updateCommand(Command d) {
        return commandRepository.save(d);
    }

    @Override
    public void deleteCommand(Integer idCommand) {
        commandRepository.deleteById(idCommand);
    }


    @Override
    public List<Command> retrieveMyCommand(Integer idUser) {
        User user = userRepository.findById(idUser).orElse(null) ;
        return commandRepository.getCommandByUserCommand(user.getIdUser());
    }


    @Override
    public List<Command> retrieveAllCommand() {
        return (List<Command>) commandRepository.findAll();
    }

    @Override
    public Command retrieveCommand(Integer idCommand) {
        return commandRepository.findById(Math.toIntExact(Long.valueOf(idCommand))).get();
    }


    @Override
    public HashMap<String, Integer> CommandByStatus() {
        HashMap<String, Integer> map=new HashMap<>();
        List<Command> listCommands= (List<Command>) commandRepository.findAll();
        for (Command d:listCommands) {
            String status = d.getStatusCommand();
            if(map.containsKey(status)){
                map.put(status,map.get(status)+1);
            }
            else {
                map.put(status,1);
            }
        }
        return map;
    }



    @Override
    public String addCommandByMail(Command d) {
        LocalDate dateActuelle = LocalDate.now();
        d.setDateCommand(dateActuelle);
        commandRepository.save(d);
        this.sendSimpleMailForCommand("fakher.karouida@esprit.tn");
        return "add command";
    }

    @Override
    public String sendSimpleMailForCommand(String email) {
        // Try block to check for exceptions
        try {

            // Creating a simple mail message
            SimpleMailMessage mailMessage = new SimpleMailMessage();

            // Setting up necessary details
            mailMessage.setFrom("pharmalife.cloudy@gmail.com");
            mailMessage.setTo(email);
            mailMessage.setText("Congratulation!!\u0020\nYour Command on PharmaLIfe was successfully added on " + LocalDate.now());
            mailMessage.setSubject("CONFIRMATION");

            // Sending the mail
            javaMailSender.send(mailMessage);
            return "Mail Sent Successfully...";
        }

        // Catch block to handle the exceptions
        catch (Exception e) {
            return "Error while Sending Mail";
        }
    }

    @Override
    public String assignCommandToProduct(Command r, Integer idProduct, Integer idUser) {
        User u = userRepository.findById(idUser).get();
        r.setUserCommand(u);
        r.setProductList(Collections.singletonList(idProduct));
        commandRepository.save(r);
        this.sendSimpleMailForCommand("fakher.karouida@esprit.tn");
        return "add command";
    }

    @Override
    public List<Command> retrieveMyCommands(Integer idUser) {
        User user = userRepository.findById(idUser).orElse(null) ;
        return commandRepository.getCommandsByUser(user.getIdUser());
    }


}



