package tn.esprit.Services;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.esprit.Entities.Event;
import tn.esprit.Entities.Reservation;
import tn.esprit.Entities.User;
import tn.esprit.Repositories.EventRepository;
import tn.esprit.Repositories.ReservationRepository;
import tn.esprit.Repositories.UserRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
@AllArgsConstructor
public class ReservationService implements IReservationService {

    @Autowired
    ReservationRepository reservationRepository;
    @Autowired
    EventRepository eventRepository;
    @Autowired
    UserRepository userRepository;

    @Override
    public Reservation addReservation(Reservation d) {

        Random rand = new Random();
        Optional<Event> currentEvent=eventRepository.findById(d.getIdEvent());
        Optional<User> currentUser=userRepository.findById(d.getIdUser());
        if(currentEvent.isPresent()&&currentUser.isPresent()){

            int alea = rand.nextInt(900) + 100; // generates a random number between 100 and 999
            d.setCodeReservation(alea);
            d.setEvent(currentEvent.get());
            d.setUserReservation(currentUser.get());
            d.setDateReservation(LocalDate.now());

        }
        return reservationRepository.save(d);
    }

    @Override
    public Reservation updateReservation(Reservation d) {
        return reservationRepository.save(d);
    }

    @Override
    public void deleteReservation(Integer idReservation) {
        reservationRepository.deleteById(idReservation);
    }



    @Override
    public List<Reservation> retrieveAllReservations() {
        return (List<Reservation>) reservationRepository.findAll();
    }



    @Override
    public Reservation retrieveReservation(Integer idReservation) {
        return reservationRepository.findById(idReservation).get();
    }



}
