package entity;

import utilities.PayStatut;

import java.time.LocalDate;
import java.util.UUID;

public class Paiement {
    private UUID idPaiement;
    private UUID idAbonnement;
    private LocalDate dateEcheance;
    private LocalDate datePaiement;
    private String typePaiement;
    private PayStatut statut;

    public Paiement(UUID idPaiement, LocalDate dateEcheance, UUID idAbonnement, LocalDate datePaiement, String typePaiement, PayStatut statut) {
        this.idPaiement = idPaiement;
        this.dateEcheance = dateEcheance;
        this.idAbonnement = idAbonnement;
        this.datePaiement = datePaiement;
        this.typePaiement = typePaiement;
        this.statut = statut;
    }

    public UUID getIdPaiement() {
        return idPaiement;
    }

    public void setIdPaiement(UUID idPaiement) {
        this.idPaiement = idPaiement;
    }

    public UUID getIdAbonnement() {
        return idAbonnement;
    }

    public void setIdAbonnement(UUID idAbonnement) {
        this.idAbonnement = idAbonnement;
    }

    public LocalDate getDateEcheance() {
        return dateEcheance;
    }

    public void setDateEcheance(LocalDate dateEcheance) {
        this.dateEcheance = dateEcheance;
    }

    public LocalDate getDatePaiement() {
        return datePaiement;
    }

    public void setDatePaiement(LocalDate datePaiement) {
        this.datePaiement = datePaiement;
    }

    public String getTypePaiement() {
        return typePaiement;
    }

    public void setTypePaiement(String typePaiement) {
        this.typePaiement = typePaiement;
    }

    public PayStatut getStatut() {
        return statut;
    }

    public void setStatut(PayStatut statut) {
        this.statut = statut;
    }

    @Override
    public String toString() {
        return "Paiement [idPaiement=" + idPaiement + ", idAbonnement=" + idAbonnement + ", dateEcheance=" + dateEcheance + ", datePaiement=" + datePaiement + ", typePaiement=" + typePaiement + ", statut=" + statut + "]";
    }
}
