# P6-Full-Stack-reseau-dev

## Description

Ce projet est une application full-stack développée avec Angular pour le frontend et Spring Boot pour le backend.  
Il s'agit d'un réseau social pour développeurs permettant de s'inscrire, se connecter, publier des articles et interagir avec d'autres utilisateurs.

---

## 🚀 Technologies utilisées

### Frontend

- **Angular 17**
- **Angular Material** pour le style

### Backend

- **Java 17** avec **Spring Boot**
- **Spring Security** pour l'authentification et l'autorisation (JWT)
- **Spring Data JPA** pour l'accès aux données
- **Hibernate** pour l'ORM
- **MySQL** comme base de données
- **Lombok** pour réduire le code boilerplate

---

## 📦 Installation

### 1️⃣ Cloner le projet

git clone https://github.com/AlbanFraincart/Developpez-une-application-fullstack-projet-6.git
cd P6-Full-Stack-reseau-dev

### Frontend

cd front
npm install
ng serve

### Backend

Créer un fichier .env à la racine du backend

DB_URL=jdbc:mysql://localhost:3306/votre_base?useSSL=false&serverTimezone=UTC
DB_USERNAME=root
DB_PASSWORD=root
JWT_SECRET=votre_cle_secrete
JWT_EXPIRATION_MS=86400000

cd back
mvn clean install
