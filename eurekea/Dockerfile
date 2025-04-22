FROM openjdk:17-jdk-slim

# Le port par défaut utilisé par Eureka
EXPOSE 8761

# Copier le jar généré (mets le vrai nom ici selon ton build Maven/Gradle)
COPY target/eurekea-0.0.1-SNAPSHOT.jar eurekea.jar

# Commande pour lancer l'application
ENTRYPOINT ["java", "-jar", "eurekea.jar"]
