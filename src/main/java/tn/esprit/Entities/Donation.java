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
@Table( name = "Donation")
public class Donation implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="idDonation")
    private Integer idDonation;
    private String NameDonation;
    private String DescriptionDonation;
    private Integer QuantityDonation;
    private LocalDate DateDonation;
    private String ImageDonation;
    @Enumerated(EnumType.STRING)
    private DonationRequestType DonationType;

    @Enumerated(EnumType.STRING)
    private RequestDonationStatus statusDonation;

    //NoSQL
    private Integer idRequest;

    @ManyToOne
    // @JsonIgnore
    User userDonation;

    private Boolean archived;




}
