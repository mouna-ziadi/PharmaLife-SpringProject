package tn.esprit.Services;

import tn.esprit.Entities.Association;
import tn.esprit.Entities.Donation;
import tn.esprit.Entities.Request;

import java.util.HashMap;
import java.util.List;

public interface IAssociationService {

    public String addAssociationByMail (Association a);
    Association addAssociation (Association a);
    Association updateAssociation (Association a);
    void deleteAssociation (Integer idAssociation);


    List<Association> retrieveAllAssociations();

    public String sendSimpleMail(String email);

    Association retrieveAssociation(Integer idAssociation);


    HashMap<String, Integer> nombreAnneeParAssociation();



    List<Association> getAssociationsPlusDeDeuxAns();

    void calculerNombreAnnees();

    List<Association> retrieveMyAssociations(Integer idUser);



}
