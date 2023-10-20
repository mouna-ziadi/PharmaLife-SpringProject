package tn.esprit.Entities;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table( name = "Event")
public class Event implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="idEvent")
    private Integer idEvent;
    private String nameEvent;
    private String DescriptionEvent;
    private String locationEvent;
    private String ImageEvent;
    private LocalDate beginsAtEvent;
    private LocalDate endsAtEvent;

    //NoSQL
    private Integer idAssociation;


    @OneToMany(mappedBy="event")
    private Set<Reservation> ReservationsEvent;


}
