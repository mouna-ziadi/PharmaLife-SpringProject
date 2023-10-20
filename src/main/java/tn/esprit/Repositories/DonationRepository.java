package tn.esprit.Repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import tn.esprit.Entities.Donation;
import tn.esprit.Entities.User;

import java.util.List;
import java.util.Map;

public interface DonationRepository extends CrudRepository<Donation, Integer> {

    @Query("select d from Donation d inner join d.userDonation ee where ee.idUser = ?1")
    List<Donation> getDonationsByUserDonation (Integer idUser);

    @Query("SELECT r FROM Donation r WHERE r.userDonation.idUser =:user")
    User findDonationByUserDonation (@Param("user") int user);

    @Query("select d from Donation d where d.idRequest IS NULL")
    List<Donation> getDisabledDonations();

}
