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
}
