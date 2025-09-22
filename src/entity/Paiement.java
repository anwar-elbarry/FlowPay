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

}
