package tn.esprit.Repositories;

import org.springframework.data.repository.CrudRepository;

import tn.esprit.Entities.Reservation;

public interface ReservationRepository  extends CrudRepository<Reservation, Integer> {
}
