package tn.esprit.Controllers;


import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.esprit.Entities.DeliveryPerson;
import tn.esprit.Services.IDeliveryPersonService;

import java.util.List;

@RestController
@Tag(name = "Delivery Person management")
@RequestMapping("/DeliveryPerson")
@AllArgsConstructor
@CrossOrigin("*")
public class DeliveryPersonRestController {

   IDeliveryPersonService deliveryPersonService;

    @PostMapping("add-DeliveryPerson")
    public DeliveryPerson addUser(@RequestBody DeliveryPerson dp) {
        DeliveryPerson deliveryPerson = deliveryPersonService.addDeliveryPerson(dp);
        return dp;
    }


    @GetMapping("/all-DeliveryPersons")
    @ResponseBody
    public ResponseEntity<List<DeliveryPerson>> getAllDeliveryPersons(){
        return ResponseEntity.ok().body(deliveryPersonService.getDeliveryPersons());
    }


    @GetMapping("/retrieve-DeliveryPerson/{idDeliveryPerson}")
    @ResponseBody
    public DeliveryPerson findByIdDeliveryPerson(@PathVariable("idDeliveryPerson") Integer idDeliveryPerson){
        return deliveryPersonService.findByIdDP(idDeliveryPerson);
    }


    @DeleteMapping("/delete-DeliveryPerson/{idDeliveryPerson}")
    @ResponseBody
    public void deleteUserById(@PathVariable("idDeliveryPerson") Integer idDeliveryPerson){
        deliveryPersonService.deleteUserByIdDP(idDeliveryPerson);
    }


    @PutMapping("/update-DeliveryPerson")
    @ResponseBody
    public void updateUser(@RequestBody DeliveryPerson dp) {
        deliveryPersonService.updateDeliveryPerson(dp);
    }

    @GetMapping("/findByNomPreDPContains/{nom}")
    @ResponseBody
    public List<DeliveryPerson> findByNomPreDPContains(@PathVariable("nom") String nom){
        return deliveryPersonService.findByNomPrenomDPContains(nom);
    }

}
