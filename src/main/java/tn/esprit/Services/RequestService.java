package tn.esprit.Services;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import tn.esprit.Entities.Association;

import tn.esprit.Entities.Donation;
import tn.esprit.Entities.Request;
import tn.esprit.Entities.RequestDonationStatus;
import tn.esprit.Repositories.AssociationRepository;
import tn.esprit.Repositories.DonationRepository;
import tn.esprit.Repositories.RequestRepository;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@AllArgsConstructor
@Aspect
@Slf4j
public class RequestService implements IRequestService {
    @Autowired
    private JavaMailSender javaMailSender;

    RequestRepository requestRepository;
    AssociationRepository associationRepository;

    DonationRepository donationRepository;
    @Override
    public Request addRequest(Request r) {
        LocalDate dateActuelle = LocalDate.now();
        r.setStatusRequest(RequestDonationStatus.inProgress);
        r.setDateRequest(dateActuelle);
        return requestRepository.save(r);
    }


    @Override
    public void cancelRequest(Integer idRequest) {
        requestRepository.deleteById(idRequest);
    }

    @Override
    public List<Request> retrieveMyRequests(Integer idAssociation) {
        Association association = associationRepository.findById(idAssociation).orElse(null);
        return requestRepository.getRequestByAssociation(association.getIdAssociation());
    }

    @Override
    public Request updateRequest(Request r) {

        LocalDate dateActuelle = LocalDate.now();
        r.setDateRequest(dateActuelle);
        return requestRepository.save(r);
    }

    @Override
    public List<Request> retrieveAllRequests() {
        return (List<Request>) requestRepository.findAll();
    }

    @Override
    public String sendMailToAssociation(String email) {
        // Try block to check for exceptions
        try {

            // Creating a simple mail message
            SimpleMailMessage mailMessage = new SimpleMailMessage();

            // Setting up necessary details
            mailMessage.setFrom("cloudypi2023@gmail.com");
            mailMessage.setTo(email);
            mailMessage.setText("Congratulation!!\u0020\nYour Request on PharmaLIfe was successfully added on " + LocalDate.now()
                    +"\u0020\n with status : inProgress");
            mailMessage.setSubject("REQUEST");

            // Sending the mail
            javaMailSender.send(mailMessage);
            return "Mail Sent Successfully...";
        }

        // Catch block to handle the exceptions
        catch (Exception e) {
            return "Error while Sending Mail";
        }
    }

    @Override
    public Request retrieveRequest(Integer idRequest) {
        return requestRepository.findById(idRequest).get();
    }


    @Override
    public List<Request> retrieveAllRequestsInProgress() {
        return requestRepository.getRequestInProgress();
    }
    @Override
    public List<Request> retrieveAllRequestsAccepted() {
        return requestRepository.getRequestAccepted();
    }
    @Override
    public List<Request> retrieveAllRequestsInRefused() {
        return requestRepository.getRequestRefused();
    }


    @Override
    public Request updateRequestDonation(Request r) {
        return requestRepository.save(r);
        //return requestRepository.save(r);
    }

    @Override
    public Map<String, Double> RequestByStatus() {
        List<Request> requests = (List<Request>) requestRepository.findAll();
        Map<String, Integer> statusCounts = new HashMap<>();
        for (Request r : requests) {
            String status = r.getStatusRequest().toString();
            statusCounts.put(status, statusCounts.getOrDefault(status, 0) + 1);
        }
        int total = requests.size();
        Map<String, Double> statusPercentages = new HashMap<>();
        for (Map.Entry<String, Integer> entry : statusCounts.entrySet()) {
            String status = entry.getKey();
            int count = entry.getValue();
            double percentage = count * 100.0 / total;
            statusPercentages.put(status, percentage);
        }
        return statusPercentages;
    }

    @Override
    public Map<String, Double> RequestByType() {
        List<Request> requests = (List<Request>) requestRepository.findAll();
        Map<String, Integer> typeCounts = new HashMap<>();
        for (Request d : requests) {
            String type = d.getRequestType().toString();
            typeCounts.put(type, typeCounts.getOrDefault(type, 0) + 1);
        }
        int total = requests.size();
        Map<String, Double> typePercentages = new HashMap<>();
        for (Map.Entry<String, Integer> entry : typeCounts.entrySet()) {
            String type = entry.getKey();
            int count = entry.getValue();
            double percentage = count * 100.0 / total;
            typePercentages.put(type, percentage);
        }
        return typePercentages;
    }

    @Override
    public Request assignRequestToDonation(Request r, Integer idDonation, Integer idAssociation) {
        LocalDate dateActuelle = LocalDate.now();
        r.setStatusRequest(RequestDonationStatus.accepted);
        r.setDateRequest(dateActuelle);
        r.setIdDonation(idDonation);
        Donation d = donationRepository.findById(idDonation).get();
        d.setStatusDonation(RequestDonationStatus.accepted);
        donationRepository.save(d);
        r.setRequestType(d.getDonationType());
        r.setDescriptionRequest("Request added by client");
        r.setNameRequest("Request Front");
        Association a = associationRepository.findById(idAssociation).get();
        r.setAssociation(a);
        return requestRepository.save(r);
    }

    @Override
    public Request assignRequestToDonationInf3(Request r, Integer idAssociation) {
        LocalDate dateActuelle = LocalDate.now();
        r.setStatusRequest(RequestDonationStatus.inProgress);
        r.setDateRequest(dateActuelle);

        Association a = associationRepository.findById(idAssociation).get();
        r.setAssociation(a);
        return requestRepository.save(r);
    }


    @Override
    public List<Request> findAllRequestsWhereIdDonationIsNull() {

        return requestRepository.findAllRequestsWhereIdDonationIsNull();
    }

    @Override
    public Request assignRequestToDonationByAdmin(Request r, Integer idDonation) {
        //Request r = requestRepository.findById(idRequest).get();
        r.setStatusRequest(RequestDonationStatus.accepted);
        r.setIdDonation(idDonation);
        return requestRepository.save(r);
    }

    @Override
    public Request assignDonationToRequestByAdmin(Donation d, Integer idRequest) {

        Request r = requestRepository.findById(idRequest).get();
        d.setIdRequest(idRequest);
        d.setStatusDonation(RequestDonationStatus.accepted);
        donationRepository.save(d);
        r.setStatusRequest(RequestDonationStatus.accepted);
        r.setIdDonation(d.getIdDonation());
        return requestRepository.save(r);

    }





}
