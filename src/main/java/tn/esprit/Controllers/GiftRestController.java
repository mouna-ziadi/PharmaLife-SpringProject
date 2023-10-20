package tn.esprit.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import tn.esprit.Entities.Category;
import tn.esprit.Entities.Gift;
import tn.esprit.Entities.Product;
import tn.esprit.Services.GiftService;
import tn.esprit.Services.ICategoryService;
import tn.esprit.Services.IGiftService;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("/Gift")

public class GiftRestController {
    @Autowired
    GiftService giftService;

    @GetMapping("/all-gifts")
    public List<Gift> getAllGifts(){
        return giftService.retrieveAllGifts();
    }

    @PostMapping("/add-gift")
    public Gift addGift(@RequestBody Gift g){
        return giftService.addGift(g);
    }
    @PostMapping("/{giftId}/products")
    public Gift addProductToGift(@PathVariable Integer giftId, @RequestBody Product product) {
        return giftService.addProductToGift(giftId, product);
    }
    @PostMapping("/{giftId}/product-delete")
    public Gift deleteProductFromGift(@PathVariable Integer giftId, @RequestBody Product product) {
        return giftService.deleteProductFromGift(giftId, product);
    }

    @GetMapping("/retrieve-gift/{idGift}")
    public Gift retrieveGift(@PathVariable("idGift") Integer idGift){
        return giftService.retrieveGift(idGift);
    }

    @PutMapping("/update-gift")
    public Gift updateGift(@RequestBody Gift g){
        return giftService.updateGift(g);
    }
    @DeleteMapping("/delete-gift/{idGift}")
    public void deleteGift(@PathVariable("idGift") Integer idGift){
       giftService.deleteGift(idGift);
    }

    @GetMapping("/productsForGift/{idGift}")
    public List<Product> getProductsByGift(@PathVariable("idGift") Integer idGift){
        return giftService.getProductsByGift(idGift);

    }
    @GetMapping("/myGifts/{idUser}")
    public List<Gift> myGifts(@PathVariable("idUser") Integer idUser){
        return giftService.myGifts(idUser);
    }

    @GetMapping("/checkDuplication/{giftId}/{productId}")
    Boolean getProductsByGiftAndCheckDuplicate(@PathVariable("giftId") Integer giftId,@PathVariable("productId") Integer productId){
        return giftService.getProductsByGiftAndCheckDuplicate(giftId,productId);
    }

}
