package tn.esprit.Services;

import tn.esprit.Entities.Reclamation;
import tn.esprit.Entities.User;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.List;

public interface IReclamationService {
    List<Reclamation> retrieveAllReclamationsArchived();
    List<Reclamation> retrieveAllReclamations();
    List<Reclamation> getReclamationsByUserAndProduct(int userId, int productId);
    Reclamation addReclamation(Reclamation r);

   // Reclamation updateReclamation (Reclamation r);

    Reclamation retrieveReclamation (Integer idReclamation);

    Reclamation deleteReclamationFrontArchivedBack(Integer idReclamation);

     Reclamation setArchivedReclamation(Reclamation r);

    //BACK
    List<Reclamation> retrieveAllReclamationsNotArchived();

     List<Reclamation> retrieveMyReclamations(Integer idUser);
}
