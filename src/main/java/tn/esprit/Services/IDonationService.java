package tn.esprit.Services;

import tn.esprit.Entities.Donation;

import java.util.List;
import java.util.Map;

public interface IDonationService {

    Donation addDonation (Donation d);
    Donation updateDonation (Donation d);
    void deleteDonation (Integer idDonation);
    List<Donation> retrieveMyDonations(Integer idUser);


    String sendSimpleMailForDonation(String email);

    List<Donation> retrieveAllDonations();
    Donation retrieveDonation(Integer idDonation);

    Map<String, Double> DonationByStatus();

    Map<String, Double> DonationByType();



    Map<String, Integer> getDonationStatisticsByDate();

    public Map<String, Map<String, Integer>> getDonationStatisticsByDate1();

    List<Donation> getDisabledDonations();

    String addDonationByMail(Donation d);
}
