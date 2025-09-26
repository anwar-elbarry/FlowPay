package métier;

import JDBC.AbonnementDAOJDBC;
import entity.Abonnement;
import entity.AbonnementAvecEngagement;
import entity.AbonnementSansEngagement;

import utilities.AbnStatut;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Objects;
import java.util.UUID;

public class AbonnementService {
    private AbonnementDAOJDBC abonnementDAOJDBC;

    public AbonnementService(AbonnementDAOJDBC abonnementDAOJDBC) throws SQLException {
        this.abonnementDAOJDBC = abonnementDAOJDBC;
    }

    public void creer(String nom_service, double montant_mensuel, LocalDate date_debut, LocalDate date_fin, AbnStatut statut, String type_abonnement, int duree_engagement_mois) throws SQLException {
        if (montant_mensuel <= 0) {
            throw new IllegalArgumentException("le montant doit être positif");
        }
        UUID id = java.util.UUID.randomUUID();
        Abonnement ab;
        if (Objects.equals(type_abonnement, "AVEC_ENGAGEMENT")) {
            ab = new AbonnementAvecEngagement(id, nom_service, montant_mensuel, date_debut, date_fin, statut, duree_engagement_mois);
        } else {
            ab = new AbonnementSansEngagement(id, nom_service, montant_mensuel, date_debut, date_fin, statut);
        }
        ;
        this.abonnementDAOJDBC.create(ab, type_abonnement);
    }

    public Abonnement modifier(Abonnement abonnement) throws SQLException {
        return this.abonnementDAOJDBC.modifier(abonnement);
    }

    public boolean supprimer(String id) throws SQLException {
        return this.abonnementDAOJDBC.supprimer(id);
    }

    public String resilier(String id) throws SQLException {
        Abonnement abonnement = this.abonnementDAOJDBC.findById(id).get();
        // Vérification si l'abonnement est déjà résilié
        if (abonnement.getStatut() == AbnStatut.RESILIE) {
            return "L'abonnement avec l'ID " + id + " est déjà résilié.";
        }
        abonnement.setStatut(AbnStatut.RESILIE);
        this.abonnementDAOJDBC.modifier(abonnement);
        return "L'abonnement avec l'ID \" + id + \" a été résilié avec succès.";
    }

    public void genererEcheance() {
    }
}
