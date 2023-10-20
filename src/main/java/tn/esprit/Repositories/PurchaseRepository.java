package tn.esprit.Repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import tn.esprit.Entities.Purchase;
import tn.esprit.Entities.User;

import java.util.List;

public interface PurchaseRepository extends CrudRepository<Purchase, Integer> {
    @Query("select d from Purchase d inner join d.userPurchase ee where ee.idUser = ?1")
    List<Purchase> getPurchasesByUserDonation (Integer idUser);

    @Query("SELECT r FROM Purchase r WHERE r.userPurchase.idUser =:user")
    User findPurchaseByUserPurchase (@Param("user") int user);

    List<Purchase> getPurchaseByUserPurchase(Integer idUser);
}