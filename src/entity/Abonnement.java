package entity;

import utilities.AbnStatut;

import java.time.LocalDate;
import java.util.UUID;

public abstract class Abonnement {
    protected UUID id;
    protected String nomService;
    protected double montantMensuel;
    protected LocalDate dateDebut;
    protected LocalDate dateFin;
    protected AbnStatut statut;

    public Abonnement(UUID id, String nomService, double montantMensuel, LocalDate dateDebut, LocalDate dateFin, AbnStatut statut) {
        this.id = id;
        this.nomService = nomService;
        this.montantMensuel = montantMensuel;
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
        this.statut = statut;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getNomService() {
        return nomService;
    }

    public void setNomService(String nomService) {
        this.nomService = nomService;
    }

    public double getMontantMensuel() {
        return montantMensuel;
    }

    public void setMontantMensuel(double montantMensuel) {
        this.montantMensuel = montantMensuel;
    }

    public LocalDate getDateFin() {
        return dateFin;
    }

    public void setDateFin(LocalDate dateFin) {
        this.dateFin = dateFin;
    }

    public LocalDate getDateDebut() {
        return dateDebut;
    }

    public void setDateDebut(LocalDate dateDebut) {
        this.dateDebut = dateDebut;
    }

    public AbnStatut getStatut() {
        return statut;
    }

    public void setStatut(AbnStatut statut) {
        this.statut = statut;
    }

    @Override
    public String toString() {
        return "Abonnement [id=" + id + ", nomService=" + nomService + ", montantMensuel=" + montantMensuel + ", dateDebut=" + dateDebut + ", dateFin=" + dateFin + ", statut=" + statut + "]";
    }
}
