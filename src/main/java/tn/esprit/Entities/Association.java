package tn.esprit.Entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.annotation.PostConstruct;
import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.Period;
import java.util.Set;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table( name = "Association")
public class Association implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="idAssociation")
    private int idAssociation;
    private String AssociationName;
    private String AdressAssociation;
    private String EmailAssocation;
    private String LogoAssocation;
    private String DescriptionAssocation;
    private String PhoneNumberAssocation;
    private String CountryAssocation;

    private LocalDate DateAssociation;
    private int nbAnneeAssociation;
    @OneToMany(mappedBy="association")
    @JsonIgnore
    private Set<Request> RequestsAssociation;

    @ManyToOne
    @JsonIgnore
    User userAssociation;

    private Boolean archived;


}
