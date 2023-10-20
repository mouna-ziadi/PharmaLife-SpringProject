package tn.esprit.Controllers;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import tn.esprit.Entities.Purchase;
import tn.esprit.Services.IPurchaseService;

import java.util.HashMap;
import java.util.List;

@RestController
@CrossOrigin("*")
@AllArgsConstructor
@RequestMapping("/purchases")

public class PurchaseRestController {
    IPurchaseService iPurchaseService;

    @PostMapping("/addPurchase")
    public Purchase addPurchase(@RequestBody Purchase d){
        Purchase purchase = iPurchaseService.addPurchase(d);
        return purchase;
    }

    @PutMapping("/updatePurchase")
    public Purchase updatePurchase(@RequestBody Purchase d){
        Purchase purchase = iPurchaseService.updatePurchase(d);
        return purchase;
    }

    @DeleteMapping("/deletePurchase/{idPurchase}")
    public void deletePurchase(@PathVariable("idPurchase") Integer idPurchase){
        iPurchaseService.deletePurchase(idPurchase);
    }

    @GetMapping("/getMyPurchase/{idUser}")
    public List<Purchase> getPurchasesByUser (@PathVariable("idUser") Integer idUser)
    {
        return iPurchaseService.retrieveMyPurchase(idUser);

    }


    @GetMapping("/retrieveAllPurchases")
    public List<Purchase> retrieveAllPurchases(){
        List<Purchase> listPurchases = iPurchaseService.retrieveAllPurchases();
        return listPurchases;
    }

    @GetMapping("/retrievePurchase/{idPurchase}")
    public Purchase retrievePurchase(@PathVariable("idPurchase")Integer idPurchase){
        return iPurchaseService.retrievePurchase(idPurchase);
    }

  /* @GetMapping("/statisticsDonationStatus")
    HashMap<String, Integer> DonationsByStatus(){
        return iPurchaseService.DonationByStatus();
    } */

}