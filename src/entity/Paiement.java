package entity;

import utilities.PayStatut;

import java.time.LocalDate;

public class Paiement {
    private String idPaiement;
    private Abonnement idAbonnement;
    private LocalDate dateEcheance;
    private LocalDate datePaiement;
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
}
