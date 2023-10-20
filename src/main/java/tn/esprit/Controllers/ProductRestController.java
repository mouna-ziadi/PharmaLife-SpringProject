package tn.esprit.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import tn.esprit.Entities.Category;
import tn.esprit.Entities.Gift;
import tn.esprit.Entities.Product;
import tn.esprit.Services.IGiftService;
import tn.esprit.Services.IProductService;
import tn.esprit.Services.ProductService;

import java.util.HashMap;
import java.util.List;
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/Product")
public class ProductRestController {
    @Autowired
    ProductService productService;

    @GetMapping("all-products")
    public List<Product>retrieveAllProducts(){
        return productService.retrieveAllProducts();
    }

    @GetMapping("/all-productsFront")
    public List<Product> getAllProductsFront(){

        return productService.retrieveAllProductsFront();
    }
    @GetMapping("/all-productsExpired")
    public List<Product> retrieveAllProductsExpired(){

        return productService.retrieveAllProductsExpired() ;
    }
    @GetMapping("/all-productsNotExpired")
    public List<Product> retrieveAllProductsNotExpired(){

        return productService.retrieveAllProductsNotExpired() ;
    }

    @PostMapping("/add-product")
    public Product addProduct(@RequestBody Product p){
        return productService.addProduct(p);
    }

    @GetMapping("/top3Products")
    public List<Product> findTop3ByOrderByCreationDateAsc(){
        return productService.retrieveProductsByCreationDateDSC();
    }

    @GetMapping("/productsByNameCategory")
    public List<Product> findProductsByCategoryProduct(@RequestBody Category c){
        return productService.findProductsByCategoryProduct(c);
    }
    @GetMapping("/productByName/{name}")
    public List<Product>  existsByName(@RequestBody Category c){
        return productService.findProductsByCategoryProduct(c);
    }

    @GetMapping("/retrieve-product/{idProduct}")
    public Product retrieveProduct(@PathVariable("idProduct") Integer idProduct){
        return productService.retrieveProduct(idProduct);
    }

    @GetMapping("/exists/{name}")
    public Boolean getByName(@PathVariable("name") String name){
        return productService.existsByName(name);
    }
    @PutMapping("/update-product")
    public Product updateProduct(@RequestBody Product p){
        return productService.updateProduct(p);
    }
    @DeleteMapping("/delete-product/{idProduct}")
    public void deleteProduct(@PathVariable("idProduct") Integer idProduct){
        productService.deleteProduct(idProduct);
    }

    @GetMapping("/statisticsProductCategory")
    HashMap<String, Double> CategoriesByProducts(){
        return productService.CategoriesByProducts();
    }
    @GetMapping("/statisticsProductExpiration")
    HashMap<String, Float> ProductByExpirationDate(){
        return productService.ProductByExpirationDate();
    }

}
