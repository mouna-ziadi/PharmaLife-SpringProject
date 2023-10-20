package tn.esprit.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.Entities.DeliveryPerson;

import java.util.List;
import java.util.Optional;

public interface DeliveryPersonRepository extends JpaRepository<DeliveryPerson, Integer> {

    Optional<DeliveryPerson> findByIdDeliveryP(Integer id);

    List<DeliveryPerson> findByNomPrenomDeliveryPContains(String nom);
}
