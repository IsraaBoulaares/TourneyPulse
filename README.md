

# 🏆 TourneyPulse

🧩 **Architecture de Microservices avec Spring Boot, Eureka et API Gateway**

**TourneyPulse** est une plateforme modulaire pour la gestion des tournois sportifs. Elle suit une architecture microservices basée sur **Spring Boot**, **Spring Cloud Eureka** (pour la découverte des services) et **Spring Cloud Gateway** (pour le routage intelligent des requêtes).

Le système se compose de plusieurs microservices spécialisés, chacun dédié à une fonctionnalité bien précise :

## 🔗 Microservices inclus

- 🎯 **Eureka** – Serveur de découverte des services
- 🚪 **Gateway** – Passerelle API pour centraliser les requêtes
- 🏟️ **StadiumManagement** – Gestion des stades
- 📝 **GestionReservation** – Réservation des matchs et stades
- 👥 **GestionEquipe** – Gestion des équipes
- 🎉 **Evenement** – Gestion des événements
- 🏆 **Tournoii** – Organisation et gestion des tournois

## 🔧 Technologies utilisées

- Java 17
- Spring Boot 3.2.4
- Spring Cloud 
- Spring Cloud Gateway
- Eureka Server & Eureka Client
- Maven

## 📁 Structure du projet

```
.
├── eurekea/               --> Serveur de découverte Eureka
├── Gateway/               --> Passerelle API Gateway
├── SatduimManagment/      --> Microservice de gestion des stades
├── GestionReservation/    --> Microservice de gestion des réservations
├── GestionEquipe/         --> Microservice de gestion des équipes
├── evenement/             --> Microservice de gestion des événements
└── tournoii/              --> Microservice de gestion des tournois
```

## ▶️ Lancement du projet

1. **Démarrer Eureka** (port `8761`) :
   ```bash
   cd eurekea
   mvn spring-boot:run
   ```

2. **Démarrer Gateway** (port `8093`) :
   ```bash
   cd ../Gateway
   mvn spring-boot:run
   ```

3. **Démarrer les microservices** :
   (à faire pour chaque service)
   ```bash
   cd ../<nom-du-service>
   mvn spring-boot:run
   ```

> Exemple de fonctionnement :  
> `http://localhost:8093/equipe` ou `http://localhost:8093/reservations`

## ✅ Fonctionnalités couvertes

- Découverte automatique des services avec **Eureka**
- Routage centralisé via **API Gateway**
- Architecture évolutive, modulaire et maintenable
- Intégration de plusieurs services métiers autour du sport
- Préparation pour authentification, monitoring, et scalabilité

## 🧪 Fonctionnalités à venir

- Interface utilisateur (Angular)
- Authentification / Autorisation (JWT)
- Tableau de bord d’administration
- Dockerisation complète

## 🤝 Contribution

Les contributions sont les bienvenues !  
N'hésitez pas à forker le projet, ouvrir des issues ou proposer des améliorations.

## 📄 Licence

Ce projet est open-source sous licence MIT.
