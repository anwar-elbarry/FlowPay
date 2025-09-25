package entity;

import utilities.AbnStatut;

import java.time.LocalDate;
import java.util.UUID;

public class AbonnementAvecEngagement extends Abonnement {
    private int dureeEngagementMois;

    public AbonnementAvecEngagement(UUID id, String nomService, double montantMensuel, LocalDate dateDebut, LocalDate dateFin, AbnStatut statut, int duree_engagement_mois) {
        super(id, nomService, montantMensuel, dateDebut, dateFin, statut);
        this.dureeEngagementMois = duree_engagement_mois;
    }

    public int getDureeEngagementMois() {
        return dureeEngagementMois;
    }

    @Override
    public String toString() {
        return "AbonnementAvecEngagement [dureeEngagementMois=" + dureeEngagementMois + ", id=" + id + ", nomService=" + nomService + ", montantMensuel=" + montantMensuel + ", dateDebut=" + dateDebut + ", dateFin=" + dateFin + ", statut=" + statut + "]";
    }
}
