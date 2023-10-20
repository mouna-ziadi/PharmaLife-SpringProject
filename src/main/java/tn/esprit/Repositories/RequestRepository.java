package tn.esprit.Repositories;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import tn.esprit.Entities.Association;
import tn.esprit.Entities.Donation;
import tn.esprit.Entities.Request;

import java.util.List;

public interface RequestRepository extends CrudRepository<Request,Integer> {
    @Query("select r from Request r inner join r.association ee where ee.idAssociation = ?1")
    List<Request> getRequestByAssociation (Integer idAssociation);

    @Query("select r from Request r where r.statusRequest = 'inProgress'")
    List<Request> getRequestInProgress ();

    @Query("select r from Request r where r.statusRequest = 'refused'")
    List<Request> getRequestRefused();

    @Query("select r from Request r where r.statusRequest = 'accepted'")
    List<Request> getRequestAccepted ();

    @Query("SELECT r FROM Request r WHERE r.idDonation IS NULL")
    List<Request> findAllRequestsWhereIdDonationIsNull();



}
