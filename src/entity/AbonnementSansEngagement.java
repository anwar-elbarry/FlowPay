package entity;

import utilities.AbnStatut;

import java.time.LocalDate;
import java.util.UUID;

public class AbonnementSansEngagement extends Abonnement{
    public AbonnementSansEngagement(UUID id, String nomService, double montantMensuel, LocalDate dateDebut, LocalDate dateFin, AbnStatut statut) {
        super(id, nomService, montantMensuel, dateDebut, dateFin, statut);
    }
}
