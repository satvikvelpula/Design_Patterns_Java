FROM maven:3.9-eclipse-temurin-17 AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src
# Uses the image arch classifier (linux-aarch64 on Apple Silicon / Jenkins arm).
RUN mvn -q clean package -DskipTests

FROM eclipse-temurin:17-jre
WORKDIR /app

# Native libs JavaFX needs to open a window on an X11 display (XQuartz for my mac, XMing for Windows applications)
RUN apt-get update && apt-get install -y --no-install-recommends \
    libgtk-3-0 \
    libgl1 \
    libx11-6 \
    libxtst6 \
    libxrender1 \
    libxi6 \
    libxext6 \
    libxrandr2 \
    libxcursor1 \
    libasound2t64 \
    && rm -rf /var/lib/apt/lists/*

COPY --from=build /app/target/OTP_JUnit_Assignment-1.0-SNAPSHOT.jar app.jar

ENV DB_URL=jdbc:mysql://host.docker.internal:3306/temperature_db?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC
ENV DB_USER=root
ENV DB_PASSWORD=root
ENV DISPLAY=host.docker.internal:0

ENTRYPOINT ["java", "-jar", "app.jar"]
