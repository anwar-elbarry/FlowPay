package DAO;

import entity.Paiement;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface PaiementDAO {
    void create(Paiement p);

    Optional<Paiement> findById(String id) throws SQLException;

    List<Paiement> findAll() throws SQLException;

    void modifier(Paiement p) throws SQLException;

    boolean supprimer(String id) throws SQLException;

    List<Paiement> findByAbonnement(String idAbonnement) throws SQLException;

    List<Paiement> findUnpaidByAbonnement(String idAbonnement) throws SQLException;

    List<Paiement> findUnpaid() throws SQLException;

    List<Paiement> findLastPayments(int Limit) throws SQLException;
}
