package métier;

import JDBC.AbonnementDAOJDBC;
import entity.Abonnement;
import entity.AbonnementAvecEngagement;
import entity.AbonnementSansEngagement;

import entity.Paiement;
import utilities.AbnStatut;
import utilities.PayStatut;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

import static présentation.Main.paiementDAOJDBC;

public class AbonnementService {
    private AbonnementDAOJDBC abonnementDAOJDBC;

    public AbonnementService(AbonnementDAOJDBC abonnementDAOJDBC) throws SQLException {
        this.abonnementDAOJDBC = abonnementDAOJDBC;
    }

    public Abonnement creer(String nom_service, double montant_mensuel, LocalDate date_debut, LocalDate date_fin, AbnStatut statut, String type_abonnement, int duree_engagement_mois) throws SQLException {
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
        return this.abonnementDAOJDBC.create(ab, type_abonnement);
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

    public void genererEcheance() throws SQLException {
        List<Abonnement> activeSubscriptions = abonnementDAOJDBC.findAll().stream()
                .filter(abonnement -> abonnement.getStatut() == AbnStatut.ACTIVE)
                .collect(Collectors.toList());

        for (Abonnement abonnement : activeSubscriptions) {
            LocalDate nextBillingDate = abonnement.getDateFin().plusMonths(1);

            // Create new payment record
            Paiement nextPayment = new Paiement(
                    UUID.randomUUID(),
                    nextBillingDate,
                    abonnement.getId(),
                    null,
                    "bank",
                    PayStatut.NON_PAYE
            );

            paiementDAOJDBC.create(nextPayment);

            abonnement.setDateFin(nextBillingDate);
            abonnementDAOJDBC.modifier(abonnement);
        }
    }
}
