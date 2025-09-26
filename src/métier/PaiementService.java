package métier;

import JDBC.PaiementDAOJDBC;
import entity.Paiement;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

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

    public void genererRapport() {
    }
}
