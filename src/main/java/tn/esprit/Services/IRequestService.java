package tn.esprit.Services;

import tn.esprit.Entities.Association;
import tn.esprit.Entities.Donation;
import tn.esprit.Entities.Request;

import java.util.List;
import java.util.Map;

public interface IRequestService {

    Request addRequest (Request r);
    void cancelRequest (Integer idRequest);

    List<Request> retrieveMyRequests(Integer idAssociation);

    Request updateRequest (Request r);

    List<Request> retrieveAllRequests();



    public String sendMailToAssociation(String email);
    Request retrieveRequest(Integer idRequest);

    List<Request> retrieveAllRequestsInProgress();

    List<Request> retrieveAllRequestsAccepted();

    List<Request> retrieveAllRequestsInRefused();


    Request assignRequestToDonation(Request r, Integer idDonation,Integer idAssociation);

    Request updateRequestDonation(Request r);

    Map<String, Double> RequestByStatus();
    Map<String, Double> RequestByType();


    Request assignRequestToDonationInf3(Request r, Integer idAssociation);

    List<Request> findAllRequestsWhereIdDonationIsNull();

    Request assignRequestToDonationByAdmin(Request r, Integer idDonation);

    Request assignDonationToRequestByAdmin(Donation d, Integer idRequest);

}
