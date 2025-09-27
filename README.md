# FlowPay - Système de Gestion des Abonnements et Paiements

## 📋 Description du Projet

La gestion des abonnements est devenue une préoccupation quotidienne. Qu’il s’agisse d’abonnements personnels (streaming, musique, assurances, services mobiles) ou professionnels (logiciels, outils collaboratifs, services cloud), leur nombre ne cesse d’augmenter.

Cette diversité rend le suivi complexe : les dates d’échéance s’accumulent, certains paiements passent inaperçus, et le budget mensuel ou annuel devient difficile à anticiper.

Les utilisateurs comme les responsables financiers ont besoin d’un outil qui leur apporte plus de visibilité et de contrôle.

L’objectif de ce projet est de mettre en place une solution qui centralise la gestion des abonnements, permet de suivre les échéances, de détecter rapidement les paiements manqués et de générer des rapports synthétiques pour évaluer le coût réel et prévisionnel
## 🏗️ Architecture

L'application suit une architecture en couches classique :

```
┌─────────────────┐
│   Présentation  │  (Interface console)
├─────────────────┤
│ Logique Métier  │  (Services)
├─────────────────┤
│   Accès Données │  (DAO + JDBC)
├─────────────────┤
│     Entités     │  (Modèles de données)
└─────────────────┘
```

### Structure des Packages

- **`entity/`** : Classes de modèles de données
- **`métier/`** : Logique métier (services)
- **`DAO/`** : Interfaces d'accès aux données
- **`JDBC/`** : Implémentations JDBC des DAOs
- **`présentation/`** : Interface utilisateur console
- **`utilities/`** : Classes utilitaires

## 🗃️ Modèle de Données

### Entités Principales

#### Abonnement (Classe abstraite)
```java
- UUID id
- String nomService
- double montantMensuel
- LocalDate dateDebut
- LocalDate dateFin
- AbnStatut statut
```

#### Types d'Abonnements

1. **AbonnementAvecEngagement**
   - Hérite d'Abonnement
   - `int dureeEngagementMois` : durée d'engagement en mois

2. **AbonnementSansEngagement**
   - Hérite d'Abonnement
   - Pas de durée d'engagement

#### Paiement
```java
- UUID idPaiement
- UUID idAbonnement (clé étrangère)
- LocalDate dateEcheance
- LocalDate datePaiement
- String typePaiement
- PayStatut statut
```

### Enums

#### Statuts d'Abonnement (`AbnStatut`)
- `ACTIVE` : Abonnement actif
- `SUSPENDU` : Abonnement suspendu
- `RESILIE` : Abonnement résilié

#### Statuts de Paiement (`PayStatut`)
- `PAYE` : Paiement effectué
- `NON_PAYE` : Paiement en attente
- `EN_RETARD` : Paiement en retard

## ⚙️ Fonctionnalités

### 1. Gestion des Abonnements
- ✅ **Création** d'abonnements (avec/sans engagement)
- ✅ **Modification** du statut, montant, date de fin
- ✅ **Suppression** d'abonnements
- ✅ **Consultation** de la liste des abonnements
- ✅ **Génération automatique** des échéances

### 2. Gestion des Paiements
- ✅ **Enregistrement** de nouveaux paiements
- ✅ **Modification** des dates d'échéance, types et statuts
- ✅ **Suppression** de paiements
- ✅ **Consultation** des paiements par abonnement
- ✅ **Suivi** des paiements manqués
- ✅ **Calcul** des sommes payées

### 3. Rapports et Analyses
- ✅ **Analyse des paiements** (nombre, types, statuts)
- ✅ **Analyse des abonnements** (répartition, revenus)
- ✅ **Consultation** des paiements manqués avec montants
- ✅ **Affichage** des 5 derniers paiements
- ✅ **Calcul** du total payé par abonnement

## 🚀 Comment Utiliser

### Prérequis
- Java 8 ou supérieur
- PostgreSQL (base de données)
- Configuration de la connexion JDBC

### Configuration de la Base de Données

1. Créez une base de données PostgreSQL
2. Exécutez le script de création des tables :
```sql
-- Table des abonnements
CREATE TABLE abonnement (
    id UUID PRIMARY KEY,
    nom_service VARCHAR(255) NOT NULL,
    montant_mensuel DECIMAL(10,2) NOT NULL,
    date_debut DATE NOT NULL,
    date_fin DATE NOT NULL,
    statut VARCHAR(20) NOT NULL,
    type_abonnement VARCHAR(20) NOT NULL,
    duree_engagement_mois INTEGER DEFAULT 0
);

-- Table des paiements
CREATE TABLE paiement (
    id_paiement UUID PRIMARY KEY,
    id_abonnement UUID REFERENCES abonnement(id),
    date_echeance DATE NOT NULL,
    date_paiement DATE,
    type_paiement VARCHAR(50),
    statut VARCHAR(20) NOT NULL
);
```

### Lancement de l'Application

```bash
cd src
javac -cp ".:postgresql.jar" présentation/Main.java
java -cp ".:postgresql.jar" présentation.Main
```

### Menu Principal

L'application propose un menu interactif avec 13 options :

1. **Créer un abonnement** (avec/sans engagement)
2. **Modifier un abonnement**
3. **Supprimer un abonnement**
4. **Consulter la liste des abonnements**
5. **Afficher les paiements d'un abonnement**
6. **Enregistrer un paiement**
7. **Modifier un paiement**
8. **Supprimer un paiement**
9. **Consulter les paiements manqués** avec montant total impayé
10. **Afficher la somme payée** d'un abonnement
11. **Afficher les 5 derniers paiements**
12. **Générer des rapports financiers**
13. **Générer les échéances de paiement**

## 🛠️ Technologies Utilisées

- **Langage** : Java 8+
- **Base de données** : PostgreSQL
- **Accès aux données** : JDBC
- **Architecture** : Layered Architecture (Couches)
- **Interface** : Console (CLI)

## 📁 Structure du Projet

```
FlowPay/
├── src/
│   ├── DAO/
│   │   ├── AbonnementDAO.java
│   │   └── PaiementDAO.java
│   ├── JDBC/
│   │   ├── AbonnementDAOJDBC.java
│   │   └── PaiementDAOJDBC.java
│   ├── entity/
│   │   ├── Abonnement.java
│   │   ├── AbonnementAvecEngagement.java
│   │   ├── AbonnementSansEngagement.java
│   │   └── Paiement.java
│   ├── métier/
│   │   ├── AbonnementService.java
│   │   └── PaiementService.java
│   ├── présentation/
│   │   └── Main.java
│   └── utilities/
│       ├── AbnStatut.java
│       ├── PayStatut.java
│       └── DateValidation.java
├── .idea/
├── README.md
└── FlowPay.iml
```

## 🔧 Classes Utilitaires

### DateValidation
- Validation et parsing des dates
- Format supporté : YYYY-MM-DD
- Gestion des erreurs de format

### DatabaseConnection
- Gestion de la connexion à la base de données
- Configuration centralisée des paramètres de connexion

## 📊 Exemples d'Utilisation

### Création d'un Abonnement avec Engagement
```java
// Création d'un abonnement Internet avec 12 mois d'engagement
Abonnement abonnement = abonnementService.creer(
    "Internet Fibre",    // nom du service
    49.99,              // montant mensuel
    LocalDate.now(),    // date de début
    LocalDate.now().plusYears(1), // date de fin
    AbnStatut.ACTIVE,   // statut
    "AVEC_ENGAGEMENT",  // type
    12                  // durée d'engagement
);
```

### Enregistrement d'un Paiement
```java
Paiement paiement = new Paiement(
    UUID.randomUUID(),                    // ID unique
    LocalDate.now().plusMonths(1),       // date d'échéance
    abonnement.getId(),                  // ID de l'abonnement
    null,                                // date de paiement (null si non payé)
    "Virement bancaire",                 // type de paiement
    PayStatut.NON_PAYE                   // statut
);
```
## Links

👉 [Voir le diagramme de class](https://lucid.app/lucidchart/5a2d4306-7d26-4731-99de-65c6ff125b22/edit?viewport_loc=-13644%2C-6609%2C20458%2C9341%2C0_0&invitationId=inv_57ef5155-2e3a-45d5-bb93-8b3dde35838f)


## 🤝 Contribution

Pour contribuer au projet :

1. Fork le projet
2. Créer une branche pour votre fonctionnalité (`git checkout -b feature/AmazingFeature`)
3. Commit vos changements (`git commit -m 'Add some AmazingFeature'`)
4. Push sur la branche (`git push origin feature/AmazingFeature`)
5. Ouvrir une Pull Request

## 📝 Auteur

**Développé par :** [Anouar El barry]
**Version :** 1.0.0
**Date :** 2025-09-27

---

*Projet développé dans le cadre d'un apprentissage Java avec architecture en couches et gestion de base de données .*
