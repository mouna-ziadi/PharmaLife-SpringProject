package tn.esprit.Entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@Table( name = "Command")
public class Command implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="idCommand")
    private Integer idCommand;
    private String StatusCommand;
    private LocalDate DateCommand;
    private Date DateLivraison;
    private String ShippingAddressCommand;
    private  Integer idUser;
    private String Notes;
    private float Total ;
    @ElementCollection
    private List<Integer> productList;

    @ManyToOne
     @JsonIgnore
    User userCommand;


    //NoSQL


   private Integer  purchaseCommandId;


    private Integer  deliveryPersonId;

}
