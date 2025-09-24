package JDBC;

import DAO.AbonnementDAO;
import entity.Abonnement;
import entity.AbonnementAvecEngagement;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class AbonnementDAOJDBC implements AbonnementDAO {
    private Connection connection;

    public AbonnementDAOJDBC (Connection connection){
        this.connection = connection;
    }

    @Override
    public void create(Abonnement a,String type_abonnement) {

        String sql = "INSERT INTO abonnement (id,nom_service, montant_mensuel, date_debut, date_fin, statut, type_abonnement, duree_engagement_mois) VALUES (?,?, ?, ?, ?, ?, ?, ?)";
        try(PreparedStatement pr = connection.prepareStatement(sql)){
        pr.setObject(1,a.getId());
        pr.setString(2,a.getNomService());
        pr.setDouble(3,a.getMontantMensuel());
        pr.setDate(4,java.sql.Date.valueOf(a.getDateDebut()));
        pr.setDate(5,java.sql.Date.valueOf(a.getDateFin()));
        pr.setString(6,a.getStatut().name());
        pr.setString(7,type_abonnement);
        pr.setInt(8, Objects.equals(type_abonnement, "AVEC_ENGAGEMENT") ? ((AbonnementAvecEngagement) a).getDureeEngagementMois() : 0);
        pr.executeUpdate();
        }catch (Exception e){
            throw new RuntimeException("Erreur lors de la création de l'abonnement : "+ e);
        }
    }

    @Override
    public Optional<Abonnement> findById(String id) {
        return Optional.empty();
    }

    @Override
    public List<Abonnement> findAll() {
        return Collections.emptyList();
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
