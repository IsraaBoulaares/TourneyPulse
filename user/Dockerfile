FROM openjdk:17-jdk-slim

EXPOSE 8087

COPY target/user-0.0.1-SNAPSHOT.jar user.jar

ENTRYPOINT ["java", "-jar", "user.jar"]
