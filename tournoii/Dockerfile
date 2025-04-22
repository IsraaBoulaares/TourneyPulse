FROM openjdk:17-jdk-slim

EXPOSE 8087

COPY target/tournoii-0.0.1-SNAPSHOT.jar tournoii.jar

ENTRYPOINT ["java", "-jar", "tournoii.jar"]
