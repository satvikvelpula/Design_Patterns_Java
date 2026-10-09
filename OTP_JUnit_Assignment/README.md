# OTP Temperature Converter — Individual Assignment README

## 1. Assignment Description

### Problem statement
Build a Java temperature-conversion application with unit tests, persistent storage, a desktop GUI, and a CI/CD path (Maven tests, JaCoCo coverage, Docker, Jenkins).

### Key requirements
- Convert between **Celsius (C)**, **Fahrenheit (F)**, and **Kelvin (K)** using a dedicated converter class.
- Detect **extreme** Celsius temperatures (below −40 °C or above 50 °C).
- Persist conversion-related data in a relational database (`temperature_unit`, `temp_record`).
- Provide a **JavaFX** UI to convert values, save inputs, and view recent records.
- Verify behavior with **JUnit 5**; report coverage with **JaCoCo**.
- Package and run via **Maven** / **Docker**; automate build–test–coverage–image push with **Jenkins**.

### Deliverables addressed
| Deliverable | Location / artifact |
|-------------|---------------------|
| Converter logic | `src/main/java/TemperatureConverter.java` |
| Domain + JDBC layer | `TemperatureUnit`, `TempRecord`, DAOs, `DBConnection` |
| DB schema + seed units | `src/main/resources/schema.sql` |
| JavaFX GUI | `Main.java` (+ `Launcher.java` for shaded JAR) |
| Automated tests | `src/test/java/*Test.java` |
| Coverage report | `mvn test` → `target/site/jacoco/` |
| Containerization | `Dockerfile`, `docker-compose.yml` |
| CI pipeline | `Jenkinsfile` |

---

## 2. Technologies & Tools Used

| Category | Technology |
|----------|------------|
| Language | Java 17 |
| Build | Apache Maven 3.9+ |
| GUI | JavaFX Controls 22.0.2 |
| Database | MariaDB 11 (MySQL-compatible JDBC) |
| JDBC driver | `mysql-connector-j` 8.3.0 |
| Unit testing | JUnit Jupiter 5.10.2 |
| Coverage | JaCoCo 0.8.14 |
| Containers | Docker, Docker Compose |
| CI/CD | Jenkins (Maven agent image, Docker build/push) |
| IDE (dev) | IntelliJ IDEA |

---

## 3. Design Approach & Implementation Method

### Solution design
The app is split into clear layers:

1. **Domain / logic** — `TemperatureConverter` holds pure conversion and extreme-temperature rules (easy to unit-test without a DB or GUI).
2. **Persistence** — `schema.sql` defines units and records; `TemperatureUnitDAO` / `TempRecordDAO` use JDBC prepared statements; `DBConnection` reads `DB_URL`, `DB_USER`, `DB_PASSWORD` from the environment (defaults: local MariaDB as `root`/`root`).
3. **Presentation** — `Main` (JavaFX) loads units into combo boxes, converts via Celsius as an intermediate scale, saves the entered value + unit, and shows the last 20 records in a `TableView`.
4. **Packaging / ops** — `Launcher` is the shaded-JAR entry point (avoids JavaFX module-path issues). `docker-compose` starts MariaDB and applies the schema; `Dockerfile` builds the fat JAR and runs it with GTK/X11 libs for a display. `Jenkinsfile` runs `mvn clean test jacoco:report`, then builds and pushes `satvikvelpula/temperature-converter:1.0`.

### Key decisions
- **Celsius as pivot** in the GUI keeps multi-unit conversion consistent with the converter API.
- **DAO + env-based JDBC** separates UI from SQL and supports both local Compose and Docker (`host.docker.internal`).
- **DB-dependent tests use `Assumptions.assumeTrue`** so CI can still pass converter/model tests when MariaDB is unavailable; with Compose up, DAO tests run fully.
- **JavaFX 22** so Linux aarch64 classifiers work on Apple Silicon / arm Jenkins agents.
- **Shade plugin** produces a runnable JAR with `Launcher` as `Main-Class`.

---

## 4. Testing & Quality Assurance Steps

### Automated tests (JUnit 5)
Run from `OTP_JUnit_Assignment`:

```bash
mvn clean test jacoco:report
```

| Test class | Scenarios | Typical outcome |
|------------|-----------|-----------------|
| `TemperatureConverterTest` | F↔C, K→C, extreme bounds (−40 / 50 inclusive = not extreme) | 4 tests, pass |
| `TemperatureUnitTest` | Field storage / `toString` | Pass |
| `TempRecordTest` | Value + unit id storage | Pass |
| `DBConnectionTest` | Default URL/user; live connection if DB up | Pass (or skip live connect if DB down) |
| `TemperatureUnitDAOTest` | Seeded C/F/K; `findByCode("C")` | Pass when MariaDB is up |
| `TempRecordDAOTest` | Insert + `findRecent` | Pass when MariaDB is up |

Latest local Surefire run (with DB available): **11 tests, 0 failures, 0 errors**.

JaCoCo HTML report: `target/site/jacoco/index.html`  
(`TemperatureConverter` is fully covered by unit tests; GUI `Main` is exercised manually.)

### Manual verification
1. `docker compose up -d` — MariaDB healthy; units C/F/K seeded.
2. `mvn javafx:run` — window opens; units load into combo boxes.
3. Convert e.g. `32 F → C` → result ≈ `0.00 C`.
4. **Save input to DB** → row appears after **Refresh history**.
5. Invalid input / missing unit → error `Alert` (no crash).
6. Optional: build image and run with `DISPLAY` set (XQuartz / X11) to confirm containerized GUI.

Screenshot reference (if present): `src/main/images/Test_Case_Res.png`.

---

## 5. How to Run

### Prerequisites
- JDK **17+**
- **Maven 3.9+**
- **Docker** / Docker Compose (for the database)
- For GUI in Docker: an X11 display (e.g. XQuartz on macOS) with TCP clients allowed

### Database
```bash
cd OTP_JUnit_Assignment
docker compose up -d
# Wait until healthy; schema + seed run on first start
```

Defaults: `localhost:3306`, database `temperature_db`, user/password `root`/`root`.

### Tests & coverage
```bash
cd OTP_JUnit_Assignment
mvn clean test jacoco:report
```

### Run GUI (local)
```bash
cd OTP_JUnit_Assignment
mvn javafx:run
```

Or package and run the shaded JAR (JavaFX on the module path / shaded deps as built):

```bash
mvn clean package
java -jar target/OTP_JUnit_Assignment-1.0-SNAPSHOT.jar
```

### Docker image (GUI needs display)
```bash
docker build -t satvikvelpula/temperature-converter:1.0 .
# Example on macOS with XQuartz:
#   export DISPLAY=host.docker.internal:0
docker run --rm -e DISPLAY=host.docker.internal:0 satvikvelpula/temperature-converter:1.0
```

Ensure Compose DB is reachable from the container (`DB_URL` defaults to `host.docker.internal:3306`).

### Jenkins
Point the job at this repository/branch and use the root `Jenkinsfile` under `OTP_JUnit_Assignment` (build from repo root with `dir('OTP_JUnit_Assignment')` as written). Provide credential ID `dockerhub-credentials` for the push stage.
