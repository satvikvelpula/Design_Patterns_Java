FROM maven:3.9-eclipse-temurin-17 AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src
# Force a published JavaFX classifier so the image builds on linux/arm and linux/amd64 CI
RUN mvn -q clean package -DskipTests -Djavafx.platform=linux

FROM eclipse-temurin:17-jre
WORKDIR /app
COPY --from=build /app/target/OTP_JUnit_Assignment-1.0-SNAPSHOT.jar app.jar
ENV DB_URL=jdbc:mysql://host.docker.internal:3306/temperature_db?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC
ENV DB_USER=root
ENV DB_PASSWORD=root
ENTRYPOINT ["java", "-jar", "app.jar"]
