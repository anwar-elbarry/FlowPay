package JDBC;

import DAO.PaiementDAO;
import entity.Paiement;
import utilities.PayStatut;

import java.net.ConnectException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

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
            pr.setObject(2, p.getIdAbonnement());
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
    public Optional<Paiement> findById(String id) throws SQLException {
        String sql = "SELECT * FROM paiement WHERE id_paiement = ?";
        try (PreparedStatement pr = connection.prepareStatement(sql)) {
            pr.setString(1, id);
            try (ResultSet result = pr.executeQuery()) {
                if (result.next()) {
                    return Optional.of(new Paiement(
                            (UUID) result.getObject("id_paiement"),
                            result.getDate("date_echeance").toLocalDate(),
                            (UUID) result.getObject("id_abonnement"),
                            result.getDate("date_paiement").toLocalDate(),
                            result.getString("type_paiement"),
                            PayStatut.valueOf(result.getString("statut"))
                    ));
                }
            }

        } catch (SQLException e) {
            throw new SQLException(e.getMessage());
        }
        return Optional.empty();
    }

    @Override
    public List<Paiement> findAll() throws SQLException {
        List<Paiement> paiementsList = new ArrayList<>();
        String sql = "SELECT * FROM paiement";
        try (PreparedStatement pr = connection.prepareStatement(sql)) {
            ResultSet result = pr.executeQuery();
            while (result.next()) {
                Paiement p = new Paiement(
                        (UUID) result.getObject("id_paiement"),
                        result.getDate("date_echeance").toLocalDate(),
                        (UUID) result.getObject("id_abonnement"),
                        result.getDate("date_paiement").toLocalDate(),
                        result.getString("type_paiement"),
                        PayStatut.valueOf(result.getString("statut"))
                );
                paiementsList.add(p);
            }
        } catch (SQLException e) {
            throw new SQLException("error while fetching all paiements :" + e.getMessage());
        }
        return paiementsList;
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
