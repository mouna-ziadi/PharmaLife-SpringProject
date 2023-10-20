package tn.esprit.Services;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tn.esprit.Entities.Command;
import tn.esprit.Entities.Donation;
import tn.esprit.Entities.Purchase;
import tn.esprit.Entities.User;
import tn.esprit.Repositories.CommandRepository;
import tn.esprit.Repositories.PurchaseRepository;
import tn.esprit.Repositories.UserRepository;


import java.util.HashMap;
import java.util.List;



@AllArgsConstructor
@Slf4j
@Service

public class PurchaseService implements IPurchaseService {
    private final PurchaseRepository purchaseRepository;

    UserRepository userRepository;


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
    public Purchase addPurchase(Purchase p) {
        return purchaseRepository.save(p);
    }

    @Override
    public Purchase updatePurchase(Purchase d) {
        return purchaseRepository.save(d);
    }

    @Override
    public void deletePurchase(Integer idPurchase) {
        purchaseRepository.deleteById(idPurchase);
    }


    @Override
    public List<Purchase> retrieveMyPurchase(Integer idUser) {
        User user = userRepository.findById(idUser).orElse(null) ;
        return purchaseRepository.getPurchaseByUserPurchase(user.getIdUser());
    }


    @Override
    public List<Purchase> retrieveAllPurchases() {
        return (List<Purchase>) purchaseRepository.findAll();
    }

    @Override
    public Purchase retrievePurchase(Integer idPurchase) {
        return purchaseRepository.findById(Math.toIntExact(Long.valueOf(idPurchase))).get();
    }


  /*  @Override
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





*/

}



