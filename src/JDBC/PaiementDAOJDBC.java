package JDBC;

import DAO.PaiementDAO;
import entity.Paiement;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class PaiementDAOJDBC implements PaiementDAO {
    private Connection connection;

    public PaiementDAOJDBC(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void create(Paiement p) {
        String sql = "INSERT INTO paiement (id_Paiement,id_abonnement, date_echeance, date_paiement, type_paiement,statut) VALUES (?, ?, ?, ?, ? ,?)";
        try (PreparedStatement pr = connection.prepareStatement(sql)) {
            pr.setObject(1, p.getIdPaiement());
            pr.setObject(2, p.getIdAbonnement().getId());
            pr.setDate(3, java.sql.Date.valueOf(p.getDateEcheance()));
            pr.setDate(4, java.sql.Date.valueOf(p.getDatePaiement()));
            pr.setString(5, p.getTypePaiement());
            pr.setString(6, p.getStatut().name());
            pr.executeUpdate();
        } catch (Exception e) {
            throw new RuntimeException("Erreur lors de la création du paiement : " + e);
        }
    }

    @Override
    public Optional<Paiement> findById(String id) {
        return Optional.empty();
    }

    @Override
    public List<Paiement> findAll() {
        return Collections.emptyList();
    }

    @Override
    public void modifier(Paiement p) {

    }

    @Override
    public void supprimer(String id) {

    }

    @Override
    public List<Paiement> findByAbonnement(String idAbonnement) {
        return Collections.emptyList();
    }

    @Override
    public List<Paiement> findUnpaidByAbonnement(String idAbonnement) {
        return Collections.emptyList();
    }

    @Override
    public List<Paiement> findLastPayments(int Limit) {
        return Collections.emptyList();
    }
}
