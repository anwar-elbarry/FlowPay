package JDBC;

import DAO.PaiementDAO;
import entity.Paiement;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class PaiementDAOJDBC implements PaiementDAO {

    @Override
    public void create(Paiement p) {

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
