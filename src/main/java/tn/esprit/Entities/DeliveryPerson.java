package tn.esprit.Entities;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sun.org.apache.xpath.internal.operations.Bool;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.bind.annotation.CrossOrigin;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@CrossOrigin
@Table( name = "DeliveryPerson")
public class DeliveryPerson implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="idDeliveryP")
    private Integer idDeliveryP;
    @Column(nullable=false)
    private String nomPrenomDeliveryP;
    @Column(nullable=false)
    private Integer CINDeliveryP;
    @Column(nullable=false)
    private Integer phoneNumberDeliveryP;
    @Column(nullable=false)

    private String addressDeliveryP;
    @Column(nullable=false)

    private String emailDeliveryP;
    @Column(nullable=false)

    @Temporal(TemporalType.DATE)
    private Date hireDateDeliveryP;
    @Column(nullable=false)

    @Enumerated(EnumType.STRING)
    private VehiculeType vehiculeType;
    @Column(nullable=false)

    private String deliveryArea;
    @Column(nullable=false)


    private Boolean availabilityDeliveryP;



    //NoSQL
    private Integer idUser;


}
