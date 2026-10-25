# In-class Assignment 1 — Localization Console (Temperature Converter)

## 1. Assignment Description

Implement `TemperatureConverter`, cover it with **JUnit 5** tests and **JaCoCo**, add **i18n** (English, Finnish, Swedish, Japanese), and automate build/test/coverage/Docker via **Jenkins** (+ Dockerfile).

### Deliverables
- `TemperatureConverter` (F↔C, extreme check)
- UTF-8 `MessagesBundle_*.properties` + localized **console** UI
- `TemperatureConverterTest` + JaCoCo report
- `Dockerfile` + `Jenkinsfile` (test → JaCoCo → Docker build/push)

---

## 2. Technologies & Tools Used

- Java 17, Maven
- JUnit Jupiter 5.10.2, JaCoCo 0.8.14
- Java `ResourceBundle` (UTF-8 `.properties`)
- Docker, Jenkins

*(Optional legacy GUI/DB classes remain in the module but are not required for this in-class task.)*

---

## 3. Design Approach & Implementation Method

- **Converter** — pure methods, easy to unit-test.
- **Localization** — at launch the console asks for language (1–4), loads `MessagesBundle` for `en_US` / `fi_FI` / `sv_SE` / `ja_JP`, then runs a menu (F→C, C→F, extreme check, exit).
- **CI/CD** — Jenkins runs `mvn clean test jacoco:report`, then builds/pushes the Docker image.

---

## 4. Testing & Quality Assurance

```bash
cd OTP_JUnit_Assignment
mvn clean test jacoco:report
```

| Test | Coverage focus | Expected |
|------|----------------|----------|
| `fahrenheitToCelsiusConvertsMultipleTemperatures` | F→C samples | Pass |
| `celsiusToFahrenheitConvertsMultipleTemperatures` | C→F samples | Pass |
| `isExtremeTemperatureBoundaryAndEdgeCases` | −40, 50, −41, 51 | Pass |

JaCoCo HTML: `target/site/jacoco/index.html`  
*(Screenshot this for Oma submission.)*

### Manual localization check
```bash
mvn -q exec:java -Dexec.mainClass=Main
# Choose 4) Japanese — convert a value — screenshot for Oma
```

---

## 5. How to Run

### Prerequisites
JDK 17+, Maven 3.9+

### Console (localized)
```bash
cd OTP_JUnit_Assignment
mvn -q compile exec:java -Dexec.mainClass=Main
# or after package:
mvn -q package -DskipTests
java -jar target/OTP_JUnit_Assignment-1.0-SNAPSHOT.jar
```

### Tests + coverage
```bash
mvn clean test jacoco:report
open target/site/jacoco/index.html   # macOS
```

### Docker
```bash
docker build -t satvikvelpula/temperature-converter:1.0 .
docker run -it --rm satvikvelpula/temperature-converter:1.0
```

### Jenkins
Use `Jenkinsfile` in this folder: Maven test + JaCoCo, then Docker build/push (`dockerhub-credentials`).

---

## Resource bundles (UTF-8)

- `src/main/resources/MessagesBundle_en_US.properties` (default)
- `src/main/resources/MessagesBundle_fi_FI.properties`
- `src/main/resources/MessagesBundle_sv_SE.properties`
- `src/main/resources/MessagesBundle_ja_JP.properties`
