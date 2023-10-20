package tn.esprit.Services;

import tn.esprit.Entities.Donation;
import tn.esprit.Entities.Purchase;
import tn.esprit.Entities.Request;
import tn.esprit.Entities.User;

import java.util.HashMap;
import java.util.List;

public interface IPurchaseService {

    Purchase addPurchase (Purchase d);
    Purchase updatePurchase (Purchase d);
    void deletePurchase (Integer idPurchase);


    List<Purchase> retrieveAllPurchases();
    Purchase retrievePurchase(Integer idPurchase);

    List<Purchase> retrieveMyPurchase(Integer idUser);

    //HashMap<String, Integer> DonationByStatus();





}