FROM eclipse-temurin:21-jdk-jammy
WORKDIR /app
COPY target/Irctc-backend-0.0.1-SNAPSHOT.jar /app/myapp.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "myapp.jar"]
