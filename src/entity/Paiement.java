package entity;

import utilities.PayStatut;

import java.time.LocalDateTime;

public class Paiement {
    private String idPaiement;
    private Abonnement idAbonnement;
    private LocalDateTime dateEcheance;
    private LocalDateTime datePaiement;
    private String typePaiement;
    private PayStatut statut;

    public String getIdPaiement() {
        return idPaiement;
    }

    public void setIdPaiement(String idPaiement) {
        this.idPaiement = idPaiement;
    }

    public Abonnement getIdAbonnement() {
        return idAbonnement;
    }

    public void setIdAbonnement(Abonnement idAbonnement) {
        this.idAbonnement = idAbonnement;
    }

    public LocalDateTime getDateEcheance() {
        return dateEcheance;
    }

    public void setDateEcheance(LocalDateTime dateEcheance) {
        this.dateEcheance = dateEcheance;
    }

    public LocalDateTime getDatePaiement() {
        return datePaiement;
    }

    public void setDatePaiement(LocalDateTime datePaiement) {
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
}
