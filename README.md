# 📱 TaskFlow - Application de Gestion de Tâches et Projets

Application mobile Android native pour la gestion complète de projets, tâches et employés avec suivi en temps réel et statistiques détaillées.

## 📋 Description

TaskFlow est une application de gestion de projets professionnelle développée en Java pour Android. Elle permet aux équipes de gérer efficacement leurs projets, d'assigner des tâches aux employés, de suivre la progression et d'analyser les performances via des statistiques visuelles.

## ✨ Fonctionnalités Principales

### 🎯 Gestion des Projets
- Création, modification et suppression de projets
- Suivi de la progression en temps réel avec barre de progression
- Affichage du nombre de tâches par projet
- Vue détaillée de chaque projet avec liste des tâches associées

### ✅ Gestion des Tâches
- Création de tâches avec titre, description et échéance
- Système de priorités (1-5)
- Statuts multiples : À faire, En cours, Terminée
- Assignation des tâches aux employés
- Catégorisation des tâches
- Système de commentaires pour chaque tâche
- Vue détaillée avec toutes les informations

### 👥 Gestion des Employés
- Ajout d'employés avec informations complètes (nom, prénom, email, matricule)
- Support des avatars personnalisés
- Vue détaillée avec liste des tâches assignées
- Compteur de tâches par employé

### 🏷️ Gestion des Catégories
- Création de catégories personnalisées par projet
- Attribution de couleurs pour identification visuelle
- Organisation des tâches par catégorie

### 💬 Système de Commentaires
- Ajout de commentaires sur les tâches
- Horodatage automatique
- Affichage chronologique des commentaires

### 📊 Tableau de Bord et Statistiques
- Vue d'ensemble des projets actifs
- Statistiques de progression
- Graphiques visuels avec MPAndroidChart
- Tâches récentes
- Indicateurs de performance

## 🛠️ Technologies Utilisées

### Architecture et Composants
- **Langage** : Java
- **SDK Minimum** : Android 7.0 (API 24)
- **SDK Cible** : Android 14 (API 35)
- **Architecture** : MVVM (Model-View-ViewModel)

### Bibliothèques Principales
- **Room Database** (2.6.1) - Base de données locale SQLite
- **LiveData & ViewModel** (2.7.0) - Gestion du cycle de vie et données réactives
- **Material Design Components** (1.11.0) - Interface utilisateur moderne
- **MPAndroidChart** (3.1.0) - Graphiques et statistiques
- **Glide** (4.16.0) - Chargement et mise en cache d'images

## 📦 Structure du Projet
app/src/main/java/com/taskflow/app/
├── database/
│ ├── dao/ # Data Access Objects
│ ├── entity/ # Entités de base de données
│ ├── model/ # Modèles de données complexes
│ └── AppDatabase.java # Configuration Room
├── repository/ # Couche d'accès aux données
├── ui/
│ ├── activity/ # Activités détaillées
│ ├── adapter/ # Adaptateurs RecyclerView
│ ├── bottomsheet/ # Bottom sheets
│ └── fragment/ # Fragments principaux
├── viewmodel/ # ViewModels MVVM
├── MainActivity.java # Activité principale
└── TaskFlowApplication.java
 🗄️ Modèle de Base de Données
    Tables Principales

 Project
- `id` (PK)
- `nom`
- `description`
`

#### Tache
- `id` (PK)
- `titre`
- `description`
- `date_echeance`
- `priorite` (1-5)
- `statut`
- `projet_id` (FK → Project)
- `assigne_a_id` (FK → Employe)
- `categorie_id` (FK → Categorie)

#### Employe
- `id` (PK)
- `nom`
- `prenom`
- `email` (unique)
- `num_matricule` (unique)
- `avatar`

#### Categorie
- `id` (PK)
- `nom`
- `couleur`
- `projet_id` (FK → Project)

#### Commentaire
- `id` (PK)
- `contenu`
- `date_creation`
- `tache_id` (FK → Tache)

### Relations
- Un projet peut avoir plusieurs tâches (1:N)
- Un projet peut avoir plusieurs catégories (1:N)
- Une tâche appartient à un projet (N:1)
- Une tâche peut être assignée à un employé (N:1)
- Une tâche peut avoir plusieurs commentaires (1:N)
- Une tâche peut appartenir à une catégorie (N:1)

## 🚀 Installation

### Prérequis
- Android Studio Arctic Fox ou supérieur
- JDK 8 ou supérieur
- Android SDK 24+
- Gradle 8.4+






