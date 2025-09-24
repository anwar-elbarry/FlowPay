package métier;

import JDBC.AbonnementDAOJDBC;
import entity.Abonnement;
import entity.AbonnementAvecEngagement;
import entity.AbonnementSansEngagement;
import utilities.AbnStatut;
import utilities.DatabaseConnection;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Objects;
import java.util.UUID;
public class AbonnementService {

    public void creer(String nom_service, double montant_mensuel, LocalDate date_debut, LocalDate date_fin, AbnStatut statut,String type_abonnement,int duree_engagement_mois) throws SQLException {
        if (montant_mensuel <= 0){
            throw new IllegalArgumentException("le montant doit être positif");
        }
            UUID id = java.util.UUID.randomUUID();
            Abonnement ab ;
            if (Objects.equals(type_abonnement,"AVEC_ENGAGEMENT")){
                ab = new AbonnementAvecEngagement(id,nom_service,montant_mensuel,date_debut,date_fin,statut,duree_engagement_mois);
            }else {
                ab = new AbonnementSansEngagement(id,nom_service,montant_mensuel,date_debut,date_fin,statut);
            }
            AbonnementDAOJDBC abJDBC = new AbonnementDAOJDBC(DatabaseConnection.getConnection());
            abJDBC.create(ab,type_abonnement);
    }

    public void modifier(){}
    public void supprimer(){}
    public void resilier(){}
    public void genererEcheance(){}
}
