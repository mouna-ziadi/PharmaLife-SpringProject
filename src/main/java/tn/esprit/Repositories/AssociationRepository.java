package tn.esprit.Repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import tn.esprit.Entities.Association;
import tn.esprit.Entities.Donation;
import tn.esprit.Entities.Request;


import java.util.List;

public interface AssociationRepository extends CrudRepository<Association,Integer> {

    @Query("SELECT u FROM Association u WHERE u.EmailAssocation=?1")
    Association findByEmail(String email);

    @Query("select c from Request c where c.association.idAssociation>=?1")
    List<Request> getAssociationByRequestsAssociation(Integer idAssociation);


    @Query("SELECT DATEDIFF(CURRENT_DATE, a.DateAssociation) / 365 FROM Association a WHERE a.idAssociation = :id_association")
    Integer getAssociationByDateAssociation_Year(@Param("id_association") Integer id_association);


    @Query("select d from Association d inner join d.userAssociation ee where ee.idUser = ?1")
    List<Association> getAssociationsByUser(Integer idUser);
}
