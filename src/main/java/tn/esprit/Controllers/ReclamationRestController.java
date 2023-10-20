package tn.esprit.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import tn.esprit.Entities.Reclamation;
import tn.esprit.Services.IReclamationService;
import tn.esprit.Services.ReclamationService;

import java.time.LocalDate;
import java.util.List;
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/Reclamation")
public class ReclamationRestController {
    @Autowired
    ReclamationService reclamationService;
    @GetMapping("/all-reclamations")
    public List<Reclamation> retrieveAllReclamations(){
        return reclamationService.retrieveAllReclamations();
    }
    //back
    @GetMapping("/all-reclamationsNotArchived")
    public List<Reclamation> retrieveAllReclamationsNotArchived(){
        return reclamationService.retrieveAllReclamationsNotArchived();
    }
    @GetMapping("/all-reclamationsArchived")
    public List<Reclamation> retrieveAllReclamationsArchived(){
        return reclamationService.retrieveAllReclamationsArchived();
    }
    @GetMapping("/my-reclamations/{idUser}")
    public List<Reclamation> retrieveMyReclamations(@PathVariable("idUser")Integer idUser){
        return reclamationService.retrieveMyReclamations(idUser);
    }

    @GetMapping("/getrec/{userId}/{productId}")
    List<Reclamation> getReclamationsByUserAndProduct(@PathVariable("userId")int userId,@PathVariable("productId")int productId){
        return reclamationService.getReclamationsByUserAndProduct(userId,productId);
    }


    @PostMapping("/add-reclamation")
    public Reclamation addReclamation(@RequestBody Reclamation r){
        return reclamationService.addReclamation(r);
    }

    @GetMapping("/retrieve-reclamation/{idReclamation}")
    public Reclamation retrieveReclamation(@PathVariable("idReclamation") Integer idReclamation){
        return reclamationService.retrieveReclamation(idReclamation);
    }
    @PostMapping("/delete-reclamationFront/{idReclamation}")
    public Reclamation deleteReclamationFrontArchivedBack(@PathVariable("idReclamation")Integer idReclamation){
       return reclamationService.deleteReclamationFrontArchivedBack(idReclamation);
    }
    @PutMapping("/archive-reclamation")
    public Reclamation setArchivedReclamation(@RequestBody Reclamation r){
        return reclamationService.setArchivedReclamation(r);
    }


}
