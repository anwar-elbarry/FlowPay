package DAO;

import entity.Abonnement;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface AbonnementDAO {
    Abonnement create(Abonnement a, String type_abonnement);

    Optional<Abonnement> findById(String id) throws SQLException;

    List<Abonnement> findAll() throws SQLException;

    Abonnement modifier(Abonnement a) throws SQLException;

    boolean supprimer(String id) throws SQLException;

    List<Abonnement> findByType(String type) throws SQLException;

    List<Abonnement> findActiveSubscriptions() throws SQLException;
}
