# Microservice: Gestion des Tournois

Ce microservice fait partie d'un projet distribué basé sur Spring Boot. Il est responsable de la **gestion des tournois**, incluant la création, la mise à jour, la suppression et l'affichage des tournois.

## 🔧 Fonctionnalités principales

- Ajouter, modifier, supprimer et lister les tournois ✅
- Architecture RESTful utilisant Spring Boot
- Intégré avec **Spring Cloud Netflix Eureka** pour le service discovery
- Persisté avec la base **H2** en mémoire
- Support de **WebSocket** pour communication en temps réel
- Intégration d’envoi d’email via **SMTP Gmail**
- Documentation API via **Swagger** (à ajouter si pas encore fait)

## ⚙️ Technologies utilisées

- Java 17
- Spring Boot 3.4.2
- Spring Cloud 2024.0.0
- Spring Data JPA
- Spring Mail
- Spring Websocket
- H2 Database
- Eureka Server
- Maven

## 🗂️ Structure du projet

```
.
├── src/main/java/tn/esprit/ms/mstournoi
│   ├── controllers
│   ├── entities
│   ├── repositories
│   ├── services
│   ├── MsTournoiApplication.java
├── resources
│   ├── application.properties
│   └── static / templates (le cas échéant)
├── pom.xml
```

## 🚀 Lancement local

### Prérequis

- Java 17+ installé
- Maven installé

### Étapes

```bash
# Cloner le projet
git clone <url-du-repo>
cd tournoii

# Lancer le microservice
./mvnw spring-boot:run
```

### Accès à l’application
- Service Tournoi : http://localhost:8087
- H2 Console : http://localhost:8087/h2-console
- Eureka Server : http://localhost:8087/eureka

## 📦 Configuration

Fichier `application.properties` :
```properties
spring.application.name=tournoii
server.port=8087

# Eureka
eureka.client.register-with-eureka=true
eureka.client.service-url.defaultZone=http://localhost:8087/eureka/

# H2 DB
datasource.url=jdbc:h2:mem:tournoi_db
spring.jpa.hibernate.ddl-auto=update
spring.h2.console.enabled=true


Et accédez à la documentation via `http://localhost:8087/swagger-ui.html`

## ✉️ Fonction d’envoi d’email
Les mails sont envoyés via l'API JavaMail configurée avec un compte Gmail. Activez l'authentification à deux facteurs et utilisez un mot de passe d'application sécurisé.

## 🔗 Eureka Discovery Server
Ce service agit à la fois comme client **et** serveur Eureka (self-registration). Vous pouvez ajouter d’autres microservices et les voir apparaître automatiquement dans Eureka.

---

## 📌 À faire


- [ ] Dockeriser l’application


