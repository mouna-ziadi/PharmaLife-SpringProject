package tn.esprit.Controllers;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import tn.esprit.Entities.Reservation;
import tn.esprit.Services.IReservationService;


import java.util.List;

@RestController
@CrossOrigin("*")
@AllArgsConstructor
@RequestMapping("/reservations")

public class ReservationRestController {
    IReservationService iReservationService;

    @PostMapping("/addReservation")
    public Reservation addReservation(@RequestBody Reservation d){
        Reservation reservation = iReservationService.addReservation(d);
        return reservation;
    }

    @PutMapping("/updateReservation")
    public Reservation updateReservation(@RequestBody Reservation d){
        Reservation reservation = iReservationService.updateReservation(d);
        return reservation;
    }

    @DeleteMapping("deleteReservation/{id_reservation}")
    public void deleteReservation(@PathVariable("id_reservation") Integer IdReservation){
        iReservationService.deleteReservation(IdReservation);
    }




    @GetMapping("/retrieveAllReservations")
    public List<Reservation> retrieveAllReservations(){
        List<Reservation> listReservations = iReservationService.retrieveAllReservations();
        return listReservations;
    }



}
