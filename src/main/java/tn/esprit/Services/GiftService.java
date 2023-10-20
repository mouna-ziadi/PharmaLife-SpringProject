package tn.esprit.Services;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.esprit.Entities.Gift;
import tn.esprit.Entities.Product;
import tn.esprit.Entities.User;
import tn.esprit.Repositories.GiftRepository;
import tn.esprit.Repositories.ProductRepository;
import tn.esprit.Repositories.UserRepository;

import javax.persistence.EntityNotFoundException;
import javax.persistence.OrderBy;
import java.time.LocalDate;
import java.util.*;

@Service
@AllArgsConstructor
public class GiftService implements IGiftService{
    @Autowired
    GiftRepository giftRepository;
    @Autowired
    ProductRepository productRepository;
    @Autowired
    UserRepository userRepository;
    @Override
    public List<Gift> retrieveAllGifts() {
        return giftRepository.findAll();
    }
    @Override
    public List<Gift> myGifts(Integer idUser){
        List<Gift> giftList =giftRepository.findAll();
        List<Gift> mygifts=new ArrayList<>();
        for (Gift g:giftList
             ) {
            if(g.getUserGift().getIdUser()==idUser){
                mygifts.add(g);
            }
        }
        return mygifts;
    }

    @Override
    public Gift addGift(Gift g) {
        Optional<User> userOptional = userRepository.findById(g.getIdUser());
        List<Product> prodList= new ArrayList<>();

        if(userOptional.isPresent()){
            User usergift=userOptional.get();
            g.setProductsGift(g.getProductsGift());
            g.setDescription("Gift for people who are in need");
            giftRepository.save(g);
            return g;
        }else{
            throw new NoSuchElementException("User not found.");
        }

    }

    @Override
    public Gift updateGift(Gift g) {
        giftRepository.save(g);
        return g;
    }

    @Override
    public Gift retrieveGift(Integer idGift) {
        return giftRepository.findById(idGift).get();
    }

    @Override
    public void deleteGift(Integer idGift) {
        List<Product> productList=giftRepository.productsByGift(idGift);
        for (Product p:productList
             ) {
            p.setQuantityProduct(p.getQuantityProduct()+1);
            productRepository.save(p);
        }
        giftRepository.deleteById(idGift);

    }
    @Override
    public Gift addProductToGift(Integer giftId, Product product) {

        Gift gift = giftRepository.findById(giftId).orElseThrow(() -> new EntityNotFoundException("Gift not found"));
        Optional<User> userOptional = userRepository.findById(gift.getIdUser());
        List<Product>  prodlist=giftRepository.productsByGift(giftId);

        if(userOptional.isPresent()){
            gift.setUserGift(userOptional.get());
        }
        gift.setDescription("Gift description");
        gift.getProductsGift().add(product);
        for (Product pr:prodlist
             ) {

                product.setQuantityProduct(product.getQuantityProduct()-1);
                productRepository.save(product);


        }

        return giftRepository.save(gift);
    }
    @Override
    public Gift deleteProductFromGift(Integer giftId, Product product) {

        Gift gift = giftRepository.findById(giftId).orElseThrow(() -> new EntityNotFoundException("Gift not found"));
        Optional<User> userOptional = userRepository.findById(gift.getIdUser());
        List<Product>  prodlist=giftRepository.productsByGift(giftId);

        if(userOptional.isPresent()){
            gift.setUserGift(userOptional.get());
        }

        gift.getProductsGift().removeIf(pr -> pr.getIdProduct() == product.getIdProduct());
        product.setQuantityProduct(product.getQuantityProduct()+1);
        productRepository.save(product);


        return giftRepository.save(gift);
    }
    @Override
    public List<Product> getProductsByGift(Integer idGift){
        List<Product> prodgift=giftRepository.productsByGift(idGift);
        for (Product p:prodgift
             ) {
            if(p.getExpirationDateProduct().compareTo(LocalDate.now())<0) {
                prodgift.remove(p);
            }
        }
        return prodgift;
    }
    @Override
    public Boolean getProductsByGiftAndCheckDuplicate(Integer giftId, Integer productId) {
        Gift gift = giftRepository.findById(giftId).orElseThrow(() -> new EntityNotFoundException("Gift not found"));
        List<Product> productList = gift.getProductsGift();
        for (Product product : productList) {
            if (product.getIdProduct()==productId) {
                return true;
            }

        }
        return false;
    }

}
