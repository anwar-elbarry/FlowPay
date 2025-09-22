package DAO;

import entity.Paiement;

import java.util.List;
import java.util.Optional;

public interface PaiementDAO {
    void create(Paiement p);
    Optional<Paiement> findById(String id);
    List<Paiement> findAll();
    void modifier(Paiement p);
    void supprimer(String id);
    List<Paiement> findByAbonnement(String idAbonnement);
    List<Paiement> findUnpaidByAbonnement(String idAbonnement);
    List<Paiement> findLastPayments(int Limit);
}
