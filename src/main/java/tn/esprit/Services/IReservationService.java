package tn.esprit.Services;

import tn.esprit.Entities.Reservation;

import java.util.List;

public interface IReservationService {


    Reservation addReservation (Reservation d);
    Reservation updateReservation (Reservation d);
    void deleteReservation (Integer idReservation);



    List<Reservation> retrieveAllReservations();
    Reservation retrieveReservation(Integer idReservation);

}
