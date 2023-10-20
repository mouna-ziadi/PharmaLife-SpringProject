package tn.esprit.Entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table( name = "Reservation")
public class Reservation implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="idReservation")
    private Integer idReservation;
    private LocalDate dateReservation;


    private Integer codeReservation ;

        //nosql
    private Integer idEvent;
    private Integer idUser;


    @ManyToOne

   User userReservation;

  @ManyToOne
  @JsonIgnore
  Event event;


}
