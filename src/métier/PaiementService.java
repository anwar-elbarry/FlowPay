package métier;

import JDBC.PaiementDAOJDBC;
import entity.Paiement;
import utilities.PayStatut;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

public class PaiementService {
    private PaiementDAOJDBC paiementDAOJDBC;

    public PaiementService(PaiementDAOJDBC paiementDAOJDBC) {
        this.paiementDAOJDBC = paiementDAOJDBC;
    }

    public void enregistrer(Paiement p) {
        this.paiementDAOJDBC.create(p);
    }

    public void modifier(Paiement p) throws SQLException {
        try {
            this.paiementDAOJDBC.modifier(p);
        } catch (SQLException e) {
            throw new SQLException(e.getMessage());
        }
    }

    public void supprimer(String id) throws SQLException {
        try {
            this.paiementDAOJDBC.supprimer(id);
        } catch (SQLException e) {
            throw new SQLException(e.getMessage());
        }
    }

    public List<Paiement> detecterImpaye() throws SQLException {
        return this.paiementDAOJDBC.findUnpaid();
    }

    public void paiementAnalys() throws SQLException {
        try {
            List<Paiement> unpaidPaiement = paiementDAOJDBC.findUnpaid();
            List<Paiement> paidPaiement = paiementDAOJDBC.findAll().stream().filter(p -> p.getStatut().equals(PayStatut.PAYE)).collect(Collectors.toList());
            List<Paiement> enRetardPaiement = paiementDAOJDBC.findAll().stream().filter(p -> p.getStatut().equals(PayStatut.EN_RETARD)).collect(Collectors.toList());
            System.out.println("Nombre de paiements impayés : " + unpaidPaiement.size());
            System.out.println("Nombre de paiements payés : " + paidPaiement.size());
            System.out.println("Nombre de paiements en retard : " + enRetardPaiement.size());
        } catch (SQLException e) {
            System.out.println("Error while analyzing payments : " + e.getMessage());
        }
    }
}
