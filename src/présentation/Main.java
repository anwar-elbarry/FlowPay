package présentation;

import JDBC.AbonnementDAOJDBC;
import JDBC.PaiementDAOJDBC;
import entity.Abonnement;

import entity.Paiement;
import métier.AbonnementService;
import métier.PaiementService;
import utilities.AbnStatut;
import utilities.DatabaseConnection;
import utilities.DateValidation;
import utilities.PayStatut;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.*;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    static Scanner scanner = new Scanner(System.in);
    static AbonnementDAOJDBC abonnementDAOJDBC;
    static AbonnementService abonnementService;
    static PaiementDAOJDBC paiementDAOJDBC;
    static PaiementService paiementService;

    static {
        try {
            abonnementDAOJDBC = new AbonnementDAOJDBC(DatabaseConnection.getConnection());
            abonnementService = new AbonnementService(abonnementDAOJDBC);
            paiementDAOJDBC = new PaiementDAOJDBC(DatabaseConnection.getConnection());
            paiementService = new PaiementService(paiementDAOJDBC);
        } catch (SQLException e) {
            throw new RuntimeException("Error initializing DAO or Service", e);
        }
    }

    public static void main(String[] args) throws Exception {
        int choix = -1;
        while (choix != 0) {
            menu();
            if (scanner.hasNextInt()) {
                choix = scanner.nextInt();
                scanner.nextLine(); // consume newline
            } else {
                System.out.println("Veuillez entrer un nombre valide !");
                scanner.nextLine(); // consume invalid input
                continue;
            }
            switch (choix) {
                case 1:
                    creerAbonnement();
                    break;
                case 2:
                    listerAbonnements();
                    modifierAbonnement();
                    break;
                case 3:
                    listerAbonnements();
                    supprimerAbonnement();
                    break;
                case 4:
                    listerAbonnements();
                    break;
                case 5:
                    listerAbonnements();
                    afficherPaiements();
                    break;
                case 6:
                    enregistrerPaiement();
                    break;
                case 7:
                    listerAbonnements();
                    afficherPaiements();
                    modifierPaiement();
                    break;
                case 8:
                    supprimerPaiement();
                    break;
                case 9:
                    consulterPaiementsManques();
                    break;
                case 10:
                    afficherSommePayee();
                    break;
                case 11:
                    afficherDerniersPaiements();
                    break;
                case 12:
                    genererRapports();
                    break;
                case 0:
                    System.out.println("Au revoir !");
                    break;
                default:
                    System.out.println("Option invalide !");
            }
        }
        scanner.close();

    }

    private static void menu() {
        System.out.println("\n=== MENU GESTION DES ABONNEMENTS ===");
        System.out.println("1. Créer un abonnement (avec/sans engagement)");
        System.out.println("2. Modifier un abonnement");
        System.out.println("3. Supprimer un abonnement");
        System.out.println("4. Consulter la liste des abonnements");
        System.out.println("5. Afficher les paiements d'un abonnement");
        System.out.println("6. Enregistrer un paiement");
        System.out.println("7. Modifier un paiement");
        System.out.println("8. Supprimer un paiement");
        System.out.println("9. Consulter les paiements manqués avec montant total impayé");
        System.out.println("10. Afficher la somme payée d'un abonnement");
        System.out.println("11. Afficher les 5 derniers paiements");
        System.out.println("12. Générer des rapports financiers");
        System.out.println("0. Quitter");
        System.out.print("Choix: ");
    }

    private static void creerAbonnement() throws SQLException {
        System.out.print("Nom du service: ");
        String nom = scanner.nextLine();
        System.out.print("Montant mensuel: ");
        double montant = scanner.nextDouble();
        scanner.nextLine();
        LocalDate dateDebut = DateValidation.askDate("Date Debut:(YYYY-MM-DD)");
        LocalDate dateFin = DateValidation.askDate("Date Fin:(YYYY-MM-DD)");
        int compareDates = DateValidation.compareDates(dateFin, dateDebut);
        while (compareDates < 0 || compareDates == 0) {
            System.out.print("Date de fin doit etre superieur a date de debut !\n");
            dateFin = DateValidation.askDate("Date Fin:(YYYY-MM-DD)");
            compareDates = DateValidation.compareDates(dateDebut, dateFin);
        }
        System.out.print("Avec engagement oui(1)/ non(2): ");
        int engagement = scanner.nextInt();
        String type_abonnement;
        int duree_Engagement_Mois;
        while ((engagement != 1) && (engagement != 2)) {
            System.out.print("Option invalide !Avec engagement oui(1)/ non(2): ");
            engagement = scanner.nextInt();
        }
        if (engagement == 1) {
            type_abonnement = "AVEC_ENGAGEMENT";
            System.out.print("Duree Engagement Mois :");
            duree_Engagement_Mois = scanner.nextInt();
        } else {
            type_abonnement = "SANS_ENGAGEMENT";
            duree_Engagement_Mois = 0;
        }
        Abonnement abonnement = abonnementService.creer(nom, montant, dateDebut, dateFin, AbnStatut.ACTIVE, type_abonnement, duree_Engagement_Mois);
        Paiement p = new Paiement(UUID.randomUUID(), dateFin, abonnement.getId(), null, "bank", PayStatut.NON_PAYE);
        paiementService.enregistrer(p);
        System.out.println("Abonnement créé avec succée ");
    }


    private static void modifierAbonnement() throws SQLException {
        try {
            System.out.print("ID de l'abonnement a modifier: ");
            String id = scanner.nextLine();
            Optional<Abonnement> abonnement = abonnementDAOJDBC.findById(id);
            if (!abonnement.isPresent()) {
                System.out.println("Abonnement non trouvé !");
                return;
            }
            Abonnement abonnementModif = abonnement.get();

            // Afficher le menu des modifications possibles
            System.out.println("\nQue souhaitez-vous modifier ?");
            System.out.println("1. Statut de l'abonnement");
            System.out.println("2. Montant mensuel");
            System.out.println("3. Date de fin");
            System.out.println("4. Annuler");
            System.out.print("Votre choix (1-4) : ");

            int choix = 0;

            try {
                choix = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("❌ Veuillez entrer un nombre valide.");
                return;
            }
            switch (choix) {
                case 1:
                    System.out.println("\nNouveau statut (ACTIVE, SUSPENDU, RESILIE) : ");
                    try {
                        AbnStatut nouveauStatut = AbnStatut.valueOf(scanner.nextLine().toUpperCase());
                        abonnementModif.setStatut(nouveauStatut);
                        abonnementDAOJDBC.modifier(abonnementModif);
                        System.out.println("✅ Statut mis à jour avec succès !");
                    } catch (IllegalArgumentException e) {
                        System.out.println("❌ Statut invalide. Les valeurs possibles sont : ACTIVE, SUSPENDU, RESILIE");
                    }
                    break;

                case 2:
                    System.out.print("Nouveau montant mensuel : ");
                    try {
                        double nouveauMontant = Double.parseDouble(scanner.nextLine());
                        if (nouveauMontant <= 0) {
                            System.out.println("❌ Le montant doit être supérieur à 0");
                            return;
                        }
                        abonnementModif.setMontantMensuel(nouveauMontant);
                        abonnementDAOJDBC.modifier(abonnementModif);
                        System.out.println("✅ Montant mensuel mis à jour avec succès !");
                    } catch (NumberFormatException e) {
                        System.out.println("❌ Veuillez entrer un montant valide.");
                    }
                    break;

                case 3:
                    LocalDate nouvelleDateFin = DateValidation.askDate("Nouvelle date de fin (YYYY-MM-DD) : ");
                    if (nouvelleDateFin.isBefore(abonnementModif.getDateDebut())) {
                        System.out.println("❌ La date de fin doit être postérieure à la date de début (" + abonnementModif.getDateDebut() + ")");
                        return;
                    }
                    abonnementModif.setDateFin(nouvelleDateFin);
                    abonnementDAOJDBC.modifier(abonnementModif);
                    System.out.println("✅ Date de fin mise à jour avec succès !");
                    break;

                case 4:
                    System.out.println("Opération annulée.");
                    break;

                default:
                    System.out.println("❌ Option non valide.");
            }
        } catch (SQLException e) {
            System.out.println("❌ Erreur lors de la modification de l'abonnement.");
        }
    }

    private static void supprimerAbonnement() throws SQLException {
        System.out.print("ID de l'abonnement a supprimer: ");
        String id = scanner.nextLine();
        boolean isDeleted = abonnementDAOJDBC.supprimer(id);
        if (isDeleted) {
            System.out.println("Abonnement supprimé avec succès.");
        } else {
            System.out.println("Abonnement non trouvé.");
        }
    }

    private static void listerAbonnements() throws SQLException {
        System.out.println("============= Liste des abonnements ===========");
        abonnementDAOJDBC.findAll().forEach(System.out::println);
        System.out.println("===============================================");
    }

    private static void afficherPaiements() throws SQLException {
        System.out.print("ID de l'abonnement: ");
        String idAbonnement = scanner.nextLine();
        List<Paiement> listPaiement = paiementDAOJDBC.findByAbonnement(idAbonnement);
        if (listPaiement.isEmpty()) {
            System.out.println("Aucun paiement trouvé pour cet abonnement.");
        } else {
            System.out.println("============= Liste des paiements ===========");
            listPaiement.forEach(System.out::println);
            System.out.println("==============================================");
        }
    }

    private static void enregistrerPaiement() {
        System.out.print("ID de l'abonnement: ");
        String idAbonnement = scanner.nextLine();
        System.out.println("Date d'echeance (YYYY-MM-DD): ");
        LocalDate dateEcheance = DateValidation.askDate("Date d'echeance (YYYY-MM-DD): ");
        System.out.println("Type de paiement: ");
        String typePaiement = scanner.nextLine();
        UUID id = UUID.randomUUID();
        PayStatut statut = PayStatut.NON_PAYE;
        Paiement p = new Paiement(id, dateEcheance, UUID.fromString(idAbonnement), LocalDate.now(), typePaiement, statut);
        paiementDAOJDBC.create(p);
        System.out.println("Paiement enregistré avec succès.");
    }

    private static void modifierPaiement() throws SQLException {
        System.out.print("ID du paiement à modifier: ");
        String id = scanner.nextLine();
        Optional<Paiement> p = paiementDAOJDBC.findById(id);
        if (!p.isPresent()) {
            System.out.println("❌ Paiement introuvable !");
            return;
        }

        // Afficher le menu
        System.out.println("============= Menu des modifications ===========");
        System.out.println("1. Modifier la date d'échéance");
        System.out.println("2. Modifier le type de paiement");
        System.out.println("3. Modifier le statut");
        System.out.println("4. Annuler");
        System.out.println("==============================================");
        System.out.print("Votre choix (1-4) : ");

        int choix;
        while (true) {
            try {
                choix = Integer.parseInt(scanner.nextLine());
                if (choix >= 1 && choix <= 4) {
                    break;
                } else {
                    System.out.println("❌ Veuillez entrer un nombre entre 1 et 4.");
                    System.out.print("Votre choix (1-4) : ");
                }
            } catch (NumberFormatException e) {
                System.out.println("❌ Entrée invalide, veuillez entrer un nombre.");
                System.out.print("Votre choix (1-4) : ");
            }
        }

        switch (choix) {
            case 1:
                LocalDate nouvelleDateEcheance = DateValidation.askDate("Nouvelle date d'échéance (YYYY-MM-DD): ");
                p.get().setDateEcheance(nouvelleDateEcheance);
                paiementDAOJDBC.modifier(p.get());
                break;
            case 2:
                System.out.print("Nouveau type de paiement: ");
                String nouveauTypePaiement = scanner.nextLine();
                p.get().setTypePaiement(nouveauTypePaiement);
                paiementDAOJDBC.modifier(p.get());
                break;
            case 3:
                System.out.print("Nouveau statut: ");
                String nouveauStatut = scanner.nextLine();
                try {
                    p.get().setStatut(PayStatut.valueOf(nouveauStatut.toUpperCase()));
                    paiementDAOJDBC.modifier(p.get());
                } catch (IllegalArgumentException e) {
                    System.out.println("❌ Statut invalide. Valeurs possibles : " + Arrays.toString(PayStatut.values()));
                }
                break;
            case 4:
                System.out.println("Opération annulée.");
                break;
        }
    }

    private static void supprimerPaiement() throws SQLException {

    }

    private static void consulterPaiementsManques() throws SQLException {

    }

    private static void afficherSommePayee() {

    }

    private static void afficherDerniersPaiements() throws SQLException {
    }

    private static void genererRapports() {
        System.out.println("Générer rapports financiers...");
        // Logic for financial reports
    }
}