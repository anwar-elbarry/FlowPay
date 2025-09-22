package entity;

import utilities.AbnStatut;

import java.time.LocalDateTime;

public abstract class Abonnement {
    protected String id;
    protected String nomService;
    protected double montantMensuel;
    protected LocalDateTime dateDebut;
    protected LocalDateTime dateFin;
    protected AbnStatut statut;

    public String getId() {
        return id;
    }

    public void setId(String id) {
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

    public LocalDateTime getDateFin() {
        return dateFin;
    }

    public void setDateFin(LocalDateTime dateFin) {
        this.dateFin = dateFin;
    }

    public LocalDateTime getDateDebut() {
        return dateDebut;
    }

    public void setDateDebut(LocalDateTime dateDebut) {
        this.dateDebut = dateDebut;
    }

    public AbnStatut getStatut() {
        return statut;
    }

    public void setStatut(AbnStatut statut) {
        this.statut = statut;
    }
}
