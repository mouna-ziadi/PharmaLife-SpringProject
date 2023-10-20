package tn.esprit.Services;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.esprit.Entities.Category;
import tn.esprit.Entities.Product;
import tn.esprit.Repositories.CategoryRepository;
import tn.esprit.Repositories.ProductRepository;

import java.time.LocalDate;
import java.util.*;

@Service
@AllArgsConstructor
public class ProductService implements IProductService {
    @Autowired
    ProductRepository productRepository;
    @Autowired
    CategoryRepository categoryRepository;

    @Override
    public List<Product> retrieveAllProducts() {
        List<Product> productList = productRepository.findAll();
        List<Product> newlist = new ArrayList<>();
        for (Product p : productList
        ) {
            if (p.getExpirationDateProduct().compareTo(LocalDate.now()) < 0 || (p.getCategoryProduct().isArchived())) {
                p.setExpired(1);
                newlist.add(p);
            } else {
                p.setExpired(0);
                newlist.add(p);
            }
        }
        return newlist;
    }

    @Override
    public List<Product> retrieveProductsByCreationDateDSC() {
        return productRepository.findTop3ByOrderByCreationDateDesc();
    }

    @Override
    public List<Product> retrieveAllProductsExpired() {
        List<Product> allprod = productRepository.findAll();
        List<Product> prodExpired = new ArrayList<>();
        for (Product p : allprod
        ) {
            if (p.getExpirationDateProduct().compareTo(LocalDate.now()) < 0) {
                p.setExpired(1);
                prodExpired.add(p);
            }
        }
        return prodExpired;
    }

    @Override
    public List<Product> retrieveAllProductsNotExpired() {
        List<Product> allprod = productRepository.findAll();
        List<Product> prodNotExpired = new ArrayList<>();
        for (Product p : allprod
        ) {
            if ((p.getExpirationDateProduct().compareTo(LocalDate.now()) > 0) && (!p.getCategoryProduct().isArchived()) && (p.getQuantityProduct() != 0)) {
                p.setExpired(0);
                prodNotExpired.add(p);
            }
        }
        return prodNotExpired;
    }

    @Override
    public boolean existsByName(String name) {
        Product p = productRepository.findByNameProduct(name);
        if (p == null) {
            return false;
        } else
            return true;
    }

    @Override
    public List<Product> retrieveAllProductsFront() {
        List<Product> allProducts = productRepository.findAll();
        List<Product> finalProducts = new ArrayList<>();
        for (Product oneProduct : allProducts) {
            if (oneProduct.getQuantityProduct() != 0 && oneProduct.getExpirationDateProduct().compareTo(LocalDate.now()) > 0) {
                finalProducts.add(oneProduct);
                productRepository.save(oneProduct);
            }
        }
        return finalProducts;
    }

    @Override
    public List<Product> findProductsByCategoryProduct(Category c) {
        return productRepository.findProductsByCategoryProduct(c);

    }


    @Override
    public Product addProduct(Product p) {
        Optional<Category> currentCategory = categoryRepository.findById(p.getIdCategory());
        if (currentCategory.isPresent()) {
            Category c=currentCategory.get();
            p.setCategoryProduct(c);
            Random rand = new Random();
            int alea = rand.nextInt(900) + 100; // generates a random number between 100 and 999
            p.setReferenceProduct("#REF" + alea);
            p.setExpired(0);

            if (p.getExpirationDateProduct().compareTo(LocalDate.now()) < 0) {
                throw new RuntimeException("product expired, cannot be added");
            }
            p.setImageProduct(p.getNameProduct() + ".jpg");
            p.setCreationDate(LocalDate.now());

            productRepository.save(p);
            // categoryRepository.save(p.getCategoryProduct());
        }
            return p;
        }




    @Override
    public Product updateProduct(Product p) {
        productRepository.save(p);
        return p;
    }

    @Override
    public Product retrieveProduct(Integer idProduct) {
        return productRepository.findById(idProduct).orElse(null);
    }

    @Override
    public void deleteProduct(Integer idProduct) {
        productRepository.deleteById(idProduct);

    }
    @Override
    public HashMap<String, Double> CategoriesByProducts() {

        HashMap<String, Float> map=new HashMap<>();
        List<Product> listProducts=productRepository.findAll();
        Integer nbp=listProducts.size();
        for (Product p:listProducts) {
            String categoryName=p.getCategoryProduct().getNameCategory();
                map.put(categoryName,map.getOrDefault(categoryName,0F)+1);

        }
        HashMap<String, Double> mapPourcentages=new HashMap<>();
        for(Map.Entry<String,Float> entry:map.entrySet()){
            String category=entry.getKey();
            Float count=entry.getValue();
            double per=count*100.0/nbp;
            mapPourcentages.put(category,per);
        }
        return mapPourcentages;
    }

    @Override
    public HashMap<String, Float> ProductByExpirationDate() {
        HashMap<String, Float> map = new HashMap<>();
        List<Product> productList = productRepository.findAll();
        Integer sizelist=productList.size();
        Float expiredCount = 0F;
        Float notExpiredCount = 0F;
        for (Product p : productList) {
            if (p.getExpirationDateProduct().isBefore(LocalDate.now())) {
                expiredCount++;
            } else if (p.getExpirationDateProduct().isAfter(LocalDate.now())) {
                {
                    notExpiredCount++;
                }
            }

        }
        map.put("Expired", (expiredCount/sizelist)*100);
        map.put("Available", (notExpiredCount/sizelist)*100);
        return map;
    }
}
