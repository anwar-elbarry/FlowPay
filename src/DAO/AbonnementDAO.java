package DAO;

import entity.Abonnement;

import java.util.List;
import java.util.Optional;

public interface AbonnementDAO {
     void create(Abonnement a,String type_abonnement);
     Optional<Abonnement> findById(String id);
     List<Abonnement> findAll();
     void modifier(Abonnement a);
     void supprimer(String id);
     List<Abonnement> findByType(String type);
     List<Abonnement> findActiveSubscriptions();
}
