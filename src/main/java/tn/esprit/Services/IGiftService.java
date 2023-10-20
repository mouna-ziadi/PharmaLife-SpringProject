package tn.esprit.Services;

import tn.esprit.Entities.Gift;
import tn.esprit.Entities.Product;

import java.util.List;

public interface IGiftService {
    List<Gift> retrieveAllGifts();
    List<Gift> myGifts(Integer idUser);

    public Gift addGift(Gift g);

    Gift updateGift (Gift g);

    Gift retrieveGift (Integer idGift);

    void deleteGift( Integer idGift);
     Gift addProductToGift(Integer giftId, Product product);
     Gift deleteProductFromGift(Integer giftId, Product product);
    List<Product> getProductsByGift(Integer idGift);
    // List<Product> getProductsByGiftScheduled(Integer idGift);

    Boolean getProductsByGiftAndCheckDuplicate(Integer giftId, Integer productId);
}
