package tn.esprit.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import tn.esprit.Entities.Gift;
import tn.esprit.Entities.Product;
import tn.esprit.Entities.Reclamation;

import java.util.List;
import java.util.Set;

@Repository
public interface GiftRepository extends JpaRepository<Gift,Integer> {



    @Query("SELECT p FROM Product p JOIN p.gifts g WHERE g.idGift=:idGift")
    List<Product> productsByGift(@Param("idGift") Integer idGift);





}
