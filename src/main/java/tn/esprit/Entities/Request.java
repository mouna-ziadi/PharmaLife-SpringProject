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
@Table( name = "Request")
public class Request implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="idRequest")
    private Integer idRequest;
    private String NameRequest;

    private String DescriptionRequest;
    @Enumerated(EnumType.STRING)
    private DonationRequestType RequestType;

    private LocalDate DateRequest;
    @Enumerated(EnumType.STRING)
    private RequestDonationStatus statusRequest;

    @ManyToOne
    //@JsonIgnore
    Association association;

    //NoSQL
    private Integer idDonation;

    private Boolean archived;



}
