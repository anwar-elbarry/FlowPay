package JDBC;

import DAO.AbonnementDAO;
import entity.Abonnement;
import entity.AbonnementAvecEngagement;
import entity.AbonnementSansEngagement;
import utilities.AbnStatut;
import utilities.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class AbonnementDAOJDBC implements AbonnementDAO {
    private Connection connection;

    public AbonnementDAOJDBC(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void create(Abonnement a, String type_abonnement) {

        String sql = "INSERT INTO abonnement (id,nom_service, montant_mensuel, date_debut, date_fin, statut, type_abonnement, duree_engagement_mois) VALUES (?,?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement pr = connection.prepareStatement(sql)) {
            pr.setObject(1, a.getId());
            pr.setString(2, a.getNomService());
            pr.setDouble(3, a.getMontantMensuel());
            pr.setDate(4, java.sql.Date.valueOf(a.getDateDebut()));
            pr.setDate(5, java.sql.Date.valueOf(a.getDateFin()));
            pr.setString(6, a.getStatut().name());
            pr.setString(7, type_abonnement);
            pr.setInt(8, Objects.equals(type_abonnement, "AVEC_ENGAGEMENT") ? ((AbonnementAvecEngagement) a).getDureeEngagementMois() : 0);
            pr.executeUpdate();
        } catch (Exception e) {
            throw new RuntimeException("Erreur lors de la création de l'abonnement : " + e);
        }
    }

    @Override
    public Optional<Abonnement> findById(String id) throws SQLException {
        String sql = "SELECT * FROM Abonnement WHERE id = ?";
        PreparedStatement pr = connection.prepareStatement(sql);
        {
            pr.setObject(1, UUID.fromString(id));
            try (ResultSet result = pr.executeQuery()) {
                if (result.next()) {
                    Abonnement ab;
                    if (Objects.equals(result.getString("type_abonnement"), "AVEC_ENGAGEMENT")) {
                        ab = new AbonnementAvecEngagement(
                                (UUID) result.getObject("id"),
                                result.getString("nom_service"),
                                result.getDouble("montant_mensuel"),
                                result.getDate("date_debut").toLocalDate(),
                                result.getDate("date_fin").toLocalDate(),
                                AbnStatut.valueOf(result.getString("statut")),
                                result.getInt("duree_engagement_mois")
                        );
                    } else {
                        ab = new AbonnementSansEngagement(
                                (UUID) result.getObject("id"),
                                result.getString("nom_service"),
                                result.getDouble("montant_mensuel"),
                                result.getDate("date_debut").toLocalDate(),
                                result.getDate("date_fin").toLocalDate(),
                                AbnStatut.valueOf(result.getString("statut"))
                        );
                    }
                    return Optional.of(ab);
                }
            } catch (SQLException e) {
                throw new SQLException(e.getMessage());
            }
        }
        return Optional.empty();
    }

    @Override
    public List<Abonnement> findAll() throws SQLException {
        List<Abonnement> abonnementsList = new ArrayList<>();
        String sql = "SELECT * FROM abonnement";
        PreparedStatement pr = connection.prepareStatement(sql);
        try (ResultSet result = pr.executeQuery()) {
            Abonnement ab;
            while (result.next()) {
                if (result.getString("type_abonnement").equals("AVEC_ENGAGEMENT")) {
                    ab = new AbonnementAvecEngagement(
                            (UUID) result.getObject("id"),
                            result.getString("nom_service"),
                            result.getDouble("montant_mensuel"),
                            result.getDate("date_debut").toLocalDate(),
                            result.getDate("date_fin").toLocalDate(),
                            AbnStatut.valueOf(result.getString("statut")),
                            result.getInt("duree_engagement_mois")
                    );
                } else {
                    ab = new AbonnementSansEngagement(
                            (UUID) result.getObject("id"),
                            result.getString("nom_service"),
                            result.getDouble("montant_mensuel"),
                            result.getDate("date_debut").toLocalDate(),
                            result.getDate("date_fin").toLocalDate(),
                            AbnStatut.valueOf(result.getString("statut"))
                    );
                }
                abonnementsList.add(ab);
            }
            return abonnementsList;
        } catch (SQLException e) {
            throw new SQLException(e.getMessage());
        }
    }

    @Override
    public void modifier(Abonnement a) {

    }

    @Override
    public void supprimer(String id) {

    }

    @Override
    public List<Abonnement> findByType(String type) {
        return Collections.emptyList();
    }

    @Override
    public List<Abonnement> findActiveSubscriptions() {
        return Collections.emptyList();
    }
}
