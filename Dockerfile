FROM openjdk:17-jdk-alpine
ARG JAR_FILE=target/*.jar
COPY ./target/demo-spring-ang-0.0.1-SNAPSHOT.jar
LABEL authors="Computer"

ENTRYPOINT ["java", "-jar","/app-jar"]