package tn.esprit.Entities;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "idComment")
@Entity
@Builder
@Table( name = "Comment")
public class Comment implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="idComment")
    private Long idComment;
    private String DescriptionComment;
    private LocalDate DateComment;


    @ManyToOne
    User userComment;

    @ManyToOne
    Article articleComment;




}
