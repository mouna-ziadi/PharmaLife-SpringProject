package tn.esprit.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import tn.esprit.Entities.Category;
import tn.esprit.Entities.Product;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {
    List<Product> findByExpirationDateProductBefore(LocalDate beforeDate);
    List<Product> findByExpirationDateProductAfter(LocalDate afterDate);

    List<Product> findTop3ByOrderByCreationDateDesc();

    List<Product> findProductsByCategoryProduct(Category c);

    @Query("SELECT p  FROM Product p WHERE p.NameProduct=:name")
    Product findByNameProduct(@Param("name") String name);

}