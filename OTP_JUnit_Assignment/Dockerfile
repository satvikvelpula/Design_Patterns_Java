FROM maven:3.9-eclipse-temurin-17 AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn -q clean package -DskipTests

FROM eclipse-temurin:17-jre
WORKDIR /app
COPY --from=build /app/target/OTP_JUnit_Assignment-1.0-SNAPSHOT.jar app.jar

# Localization console (interactive stdin). Example:
#   docker build -t temperature-converter:1.0 .
#   docker run -it --rm temperature-converter:1.0
ENTRYPOINT ["java", "-jar", "app.jar"]
