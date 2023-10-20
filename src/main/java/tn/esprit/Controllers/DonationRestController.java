package tn.esprit.Controllers;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import tn.esprit.Entities.Association;
import tn.esprit.Entities.Donation;
import tn.esprit.Entities.RequestDonationStatus;
import tn.esprit.Entities.User;
import tn.esprit.Repositories.UserRepository;
import tn.esprit.Services.IDonationService;
import tn.esprit.Services.IUserService;

import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin("*")
@AllArgsConstructor
@RequestMapping("/donations")

public class DonationRestController {
    IDonationService iDonationService;

    IUserService iUserService;
    @Autowired
    UserRepository userRepository;

    @PutMapping("/updateDonation")
    public Donation updateDonation(@RequestBody Donation d){
        Donation donation = iDonationService.updateDonation(d);
        return donation;
    }

    @DeleteMapping("deleteDonation/{id_donation}")
    public void deleteDonation(@PathVariable("id_donation") Integer IdDonation){
        iDonationService.deleteDonation(IdDonation);
    }

    @GetMapping("/getMyDonations/{idUser}")
    public List<Donation> getDonationsByUser (@PathVariable("idUser") Integer idUser)
    {
        return iDonationService.retrieveMyDonations(idUser);

    }


    @GetMapping("/retrieveAllDonations")
    public List<Donation> retrieveAllDonations(){
        List<Donation> listDonations = iDonationService.retrieveAllDonations();
        return listDonations;
    }

    @GetMapping("/retrieveDonation/{id_donation}")
    public Donation retrieveDonation(@PathVariable("id_donation")Integer IdDonation){
        return iDonationService.retrieveDonation(IdDonation);
    }

    @GetMapping("/statisticsDonationStatus")
    Map<String, Double> DonationsByStatus(){
        return iDonationService.DonationByStatus();
    }

    @GetMapping("/statisticsDonationType")
    Map<String, Double> DonationsByType(){
        return iDonationService.DonationByType();
    }



    @GetMapping("/statisticsDonationDate1")
    public Map<String, Map<String, Integer>> getDonationStatisticsByDate1() {
        return iDonationService.getDonationStatisticsByDate1();
    }


    @GetMapping("/getDonationsByAssociation/{idAssociation}")
    public List<Donation> getDonationsByAssociation (@PathVariable("idAssociation") Integer idAssociation)
    {
        return iDonationService.retrieveAllDonations();

    }

    @GetMapping("/getDonationsByRequest/{idRequest}")
    public List<Donation> getDonationsByRequest (@PathVariable("idRequest") Integer idRequest)
    {
        return iDonationService.getDisabledDonations();

    }


    @GetMapping("/getDisabledDonations")
    public List<Donation> getDisabledDonations ()
    {
        return iDonationService.getDisabledDonations();

    }
    @PostMapping("/addDonation")
    public Donation addDonation(@RequestBody Donation d){
        Donation donation = iDonationService.addDonation(d);
        return donation;
    }

    @PostMapping("/addDonationByMail/{id_user}")
    public String addDonationByMail(@RequestBody Donation d, @PathVariable("id_user") Integer IdUser){
        User u = userRepository.findByIdUser(IdUser).get();
        d.setUserDonation(u);
        return iDonationService.addDonationByMail(d);
    }




}
