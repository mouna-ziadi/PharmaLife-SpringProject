package tn.esprit.Entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
@Table( name = "Reclamation")
public class Reclamation implements Serializable{


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private int idReclamation;
    private String DescriptionReclamation;
    private LocalDate DateReclamation;
    //NoSQL
    private Integer idUser;
    private Integer idProduct;
    private Boolean archived;
    @ManyToOne(cascade = CascadeType.ALL)
    //@JsonIgnore
    private User userProduct;

    @ManyToOne(cascade = CascadeType.ALL)
    private Product product;


}
