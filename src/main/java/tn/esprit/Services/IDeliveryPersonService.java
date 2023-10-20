package tn.esprit.Services;

import tn.esprit.Entities.DeliveryPerson;

import java.util.List;

public interface IDeliveryPersonService {

    DeliveryPerson findByIdDP(Integer id);

    List<DeliveryPerson> getDeliveryPersons();

    DeliveryPerson addDeliveryPerson(DeliveryPerson dp);

    void deleteUserByIdDP(Integer id);

    void updateDeliveryPerson(DeliveryPerson dp);

    List<DeliveryPerson> findByNomPrenomDPContains(String nom);
}
