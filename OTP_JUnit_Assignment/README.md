# In-class Assignment 6 — JavaFX GUI Localization (+ DB, Docker, Jenkins, K8s)

Branch: **`OTP2_Assignment_2`**  
Module: **`OTP_JUnit_Assignment`**

## Status (what is done)

| PDF requirement | Status | Where |
|-----------------|--------|--------|
| JavaFX GUI for temperature convert | **Done** | `Main.java` + `temperature.fxml` + `TemperatureViewController.java` |
| Language: EN / FI / SV / JA / **AR** | **Done** | `MessagesBundle_*.properties` (incl. `ar_SA`), combo in GUI |
| i18n via **FXML** + `.properties` (UTF-8) | **Done** | `%keys` in FXML + `ResourceBundle` |
| DB ≥ 2 related tables + GUI uses DB | **Done** (from A7 / prior) | `temperature_unit` ↔ `temp_record` (`schema.sql`), save/refresh in GUI |
| Converter + JUnit + JaCoCo | **Done** | `TemperatureConverter` + tests + JaCoCo plugin |
| Jenkins build/test + Docker Hub push | **Done** (existing) | `Jenkinsfile` |
| Dockerfile for GUI (X11) | **Done** | `Dockerfile` (GTK + `DISPLAY`) |
| Kubernetes YAML + minikube | **Done** (manifest) | `k8s/temperature-converter.yaml` |
| Screenshots / Hub URL / GitHub link | **Your remaining work** | See below |

> Note: PDF text mentions “speed, distance, and time” tables — that looks like a template leftover. This project keeps the **temperature** domain with two related tables (same idea as OTP_JUnit_Assignment_7).

---

## How to continue (checklist)

### 1. Database
```bash
cd OTP_JUnit_Assignment
docker compose up -d
```

### 2. Run the localized JavaFX GUI locally
```bash
mvn -q javafx:run
```
- Switch language in the combo (try **Arabic** for RTL + non-default locale).
- Convert a value, **Save to DB**, **Refresh history**.

### 3. Tests + coverage (screenshot for yourself / course)
```bash
mvn clean test jacoco:report
open target/site/jacoco/index.html
```

### 4. Docker image + X server screenshot (Oma)
**macOS (XQuartz):**
```bash
# XQuartz → Preferences → Security → “Allow connections from network clients”
# then restart XQuartz
xhost +localhost
docker build -t satvikvelpula/temperature-converter:1.0 .
docker run --rm -e DISPLAY=host.docker.internal:0 satvikvelpula/temperature-converter:1.0
```
**Windows:** use Xming/VcXsrv; set `DISPLAY` to your host IP `:0.0`.

Screenshot the GUI window from the X server for Oma.

### 5. Push image (if not already)
```bash
docker login
docker push satvikvelpula/temperature-converter:1.0
```
Oma needs: `https://hub.docker.com/r/satvikvelpula/temperature-converter`

### 6. Minikube + YAML
```bash
minikube start
# optional: load local image into minikube
minikube image load satvikvelpula/temperature-converter:1.0

kubectl apply -f k8s/temperature-converter.yaml
kubectl get pods -n temperature-demo
kubectl logs -n temperature-demo -l app=temperature-converter

kubectl delete -f k8s/temperature-converter.yaml
```
Pod may exit if there is no display — still shows you applied the week-2-style manifest.

### 7. GitHub
Commit/push branch `OTP2_Assignment_2` when ready; submit the repo URL on Oma.

---

## Oma deliverables

1. GitHub repository link  
2. Docker Hub image URL  
3. Screenshot(s) of the GUI via Xming/XQuartz (show a non-English language if possible)

---

## Useful commands summary

| Goal | Command |
|------|---------|
| DB | `docker compose up -d` |
| GUI | `mvn javafx:run` |
| Test/coverage | `mvn clean test jacoco:report` |
| Image | `docker build -t satvikvelpula/temperature-converter:1.0 .` |
| K8s | `kubectl apply -f k8s/temperature-converter.yaml` |
