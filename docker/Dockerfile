FROM openjdk:17-jdk-slim
COPY ../auth-service-bootstrap/target/auth-service-bootstrap-1.0-SNAPSHOT.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/app.jar"]
