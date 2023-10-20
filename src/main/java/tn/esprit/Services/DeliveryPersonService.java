package tn.esprit.Services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tn.esprit.Entities.DeliveryPerson;
import tn.esprit.Entities.User;
import tn.esprit.Repositories.DeliveryPersonRepository;
import tn.esprit.Repositories.UserRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DeliveryPersonService implements IDeliveryPersonService {

    private final DeliveryPersonRepository deliveryPersonRepository;

    @Override
    public DeliveryPerson findByIdDP(Integer id) {
        return deliveryPersonRepository.findByIdDeliveryP(id).orElse(null);
    }

    @Override
    public List<DeliveryPerson> getDeliveryPersons() {
        return deliveryPersonRepository.findAll();
    }

    @Override
    public DeliveryPerson addDeliveryPerson(DeliveryPerson dp) {
        deliveryPersonRepository.save(dp);
        return dp;
    }

    @Override
    public void deleteUserByIdDP(Integer id) {
        DeliveryPerson dp = deliveryPersonRepository.findByIdDeliveryP(id).orElse(null);
        deliveryPersonRepository.delete(dp);
    }

    @Override
    public void updateDeliveryPerson(DeliveryPerson dp) {
            deliveryPersonRepository.save(dp);
    }

    @Override
    public List<DeliveryPerson> findByNomPrenomDPContains(String nom) {
        return deliveryPersonRepository.findByNomPrenomDeliveryPContains(nom);
    }
}
