# Spring_pokemon

# Pokémon API

Cette application est une **API REST** basée sur **Spring Boot** permettant d'interagir avec les données des Pokémon. Elle offre des fonctionnalités **CRUD** pour gérer les Pokémon, incluant la possibilité de **scraper** des données depuis une source externe, ainsi que de **rebondir** des requêtes vers une autre API distante.

L'application utilise une **authentification JWT** pour sécuriser les accès aux endpoints et une **gestion des rôles** pour définir les permissions des utilisateurs. En fonction de leurs rôles (Admin, User, Tester), les utilisateurs peuvent effectuer différentes actions sur les Pokémon.

## Fonctionnalités principales

- **CRUD sur les Pokémon** : Créer, Lire, Mettre à jour, Supprimer des Pokémon en fonction des rôles.
- **Scraping des Pokémon** : Les admins peuvent scraper des informations sur les Pokémon depuis une source externe.
- **Rebond de requêtes** : L'application peut rebondir des requêtes vers une API distante pour récupérer des données.
- **Authentification et gestion des rôles** : L'API utilise des **JWT Tokens** pour authentifier les utilisateurs et attribuer des rôles permettant de définir les accès.
  
## Prérequis

Avant de commencer, assurez-vous d'avoir les éléments suivants installés sur votre machine :

- **Java 17+**
- **Maven** ou **Gradle** (selon le gestionnaire de dépendances utilisé)
- **MySQL** ou tout autre système de gestion de base de données compatible
- **Postman** ou **Bruno** pour tester les API
- **JWT Token** pour l'authentification

## Installation

1. Cloner le projet dans un répertoire de votre choix.
```bash
git clone https://github.com/Desmornes/Spring_pokemon
cd Spring_pokemon
   
2. Configurer la base de données
Créez une base de données MySQL nommée my_spring_db (ou toute autre base de données de votre choix) et configurez-la dans le fichier application.properties.
Properties
Copier le code
spring.datasource.url=jdbc:mysql://localhost:3306/my_spring_db
spring.datasource.username=root
spring.datasource.password=ton-mot-de-passe
spring.jpa.hibernate.ddl-auto=update

3.  Lancer l'application
Lancez le projet avec Maven ou Gradle.
L'API sera disponible sur http://localhost:8085.

Sécurisation de l'API
L'application utilise Spring Security pour sécuriser les endpoints et gère l'authentification via JWT Tokens.

Les rôles définis sont les suivants :

ADMIN : Accès complet à toutes les fonctionnalités, y compris le scraping et le rebond des requêtes vers des APIs distantes.
USER : Accès limité aux opérations CRUD sur les Pokémon, mais sans possibilité de suppression ou modification (cela doit être effectué par un admin).
TESTER : Ce rôle peut être configuré pour avoir un accès limité aux fonctionnalités de test ou d'examen sans pouvoir modifier les données.
