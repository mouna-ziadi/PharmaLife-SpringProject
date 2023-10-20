package tn.esprit.Controllers;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import tn.esprit.Entities.Donation;
import tn.esprit.Entities.Request;
import tn.esprit.Services.IDonationService;
import tn.esprit.Services.IRequestService;

import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin("*")
@AllArgsConstructor
@RequestMapping("/requests")
public class RequestRestController {
    IRequestService iRequestService;
    IDonationService idonationService;



    @DeleteMapping("cancelRequest/{id_request}")
    public void cancelRequest(@PathVariable("id_request") Integer IdRequest){
        iRequestService.cancelRequest(IdRequest);
    }

    @GetMapping("/getMyRequests/{id_association}")
    public List<Request> getRequestByAssociation (@PathVariable("id_association") Integer idAssociation)
    {
        return iRequestService.retrieveMyRequests(idAssociation);
    }


    @GetMapping("/retrieveAllRequests")
    public List<Request> retrieveAllRequests(){
        List<Request> listRequests = iRequestService.retrieveAllRequests();
        return listRequests;
    }

    @GetMapping("/retrieveAllRequestsInProgress")
    public List<Request> retrieveAllRequestsInProgress(){
        List<Request> listRequests = iRequestService.retrieveAllRequestsInProgress();
        return listRequests;
    }
    @GetMapping("/retrieveAllRequestsAccepted")
    public List<Request> retrieveAllRequestsAccepted(){
        List<Request> listRequests = iRequestService.retrieveAllRequestsAccepted();
        return listRequests;
    }
    @GetMapping("/retrieveAllRequestsRefused")
    public List<Request> retrieveAllRequestsRefused(){
        List<Request> listRequests = iRequestService.retrieveAllRequestsInRefused();
        return listRequests;
    }


    @PostMapping("/sendEmailToAssociation/{email}")
    @ResponseBody
    public void sendEmailToAssociation (@PathVariable String email) {
        iRequestService.sendMailToAssociation(email);
    }

    @GetMapping("/retrieveRequest/{id_request}")
    public Request retrieveRequest(@PathVariable("id_request")Integer IdRequest){
        return iRequestService.retrieveRequest(IdRequest);
    }

    @PutMapping("/updateRequest")
    public Request updateRequest(@RequestBody Request r){
        Request request = iRequestService.updateRequest(r);
        return request;
    }


    @PutMapping("/updateRequestDonation")
    public Request updateRequestDonation(@RequestBody Request r){
        Request request = iRequestService.updateRequestDonation(r);
        return request;
    }

    @GetMapping("/statisticsRequestStatus")
    Map<String, Double> RequestsByStatus(){
        return iRequestService.RequestByStatus();
    }

    @GetMapping("/statisticsRequestType")
    Map<String, Double> RequestsByType(){
        return iRequestService.RequestByType();
    }


    @PostMapping("/addRequest")
    public Request addRequest(@RequestBody Request r){
        Request request = iRequestService.addRequest(r);
        return request;
    }
    @PostMapping("/assignRequestToDonation/{id_donation}/{id_association}")
    public Request assignRequestToDonation(@RequestBody Request r, @PathVariable("id_donation")Integer IdDonation
            , @PathVariable("id_association")Integer IdAssociation){
        // Request request = iRequestService.addRequest(r);
        Donation d = idonationService.retrieveDonation(IdDonation);
        iRequestService.addRequest(r);
        d.setIdRequest(r.getIdRequest());

        Request request = iRequestService.assignRequestToDonation(r,IdDonation,IdAssociation);
        return request;
    }
    @PostMapping("/assignRequestToDonationInf3/{id_association}")
    public Request assignRequestToDonationInf3(@RequestBody Request r, @PathVariable("id_association")Integer IdAssociation){
        // Request request = iRequestService.addRequest(r);
        iRequestService.addRequest(r);
        Request request = iRequestService.assignRequestToDonationInf3(r,IdAssociation);
        return request;
    }

    @GetMapping("/findAllRequestsWhereIdDonationIsNull")
    public List<Request> findAllRequestsWhereIdDonationIsNull(){
        List<Request> listRequests = iRequestService.findAllRequestsWhereIdDonationIsNull();
        return listRequests;
    }

    @PutMapping("/assignRequestToDonationByAdmin/{id_donation}")
    public Request assignRequestToDonationByAdmin(@RequestBody Request r, @PathVariable("id_donation")Integer IdDonation){

        Request request = iRequestService.assignRequestToDonationByAdmin(r,IdDonation );
        return request;
    }

    @PutMapping("/assignDonationToRequestByAdmin/{id_request}")
    public Request assignDonationToRequestByAdmin(@RequestBody Donation d, @PathVariable("id_request")Integer IdRequest){
        Request request = iRequestService.assignDonationToRequestByAdmin(d,IdRequest );
        return request;
    }


}
