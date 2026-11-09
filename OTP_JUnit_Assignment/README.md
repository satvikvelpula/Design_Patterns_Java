# In-class Assignment 6 — Database Localization (OTP2_Assignment_3)

JavaFX Temperature Converter with:
- UI labels from UTF-8 `MessagesBundle_*.properties` (en / fi / sv / ja / ar)
- **Unit names & descriptions from MariaDB** using the **translation table** approach
- Measurement tables for speed / distance / time
- JaCoCo, Jenkins, Docker, Kubernetes

## Chosen DB localization approach: Translation table

**Approach:** language-neutral `unit` table + `unit_translation(unit_id, lang, name, description)`.

### Schema (summary)

| Table | Role |
|-------|------|
| `unit` | Codes/categories/symbols only (C, F, K, kmh, …) — no localized text |
| `unit_translation` | One row per `(unit_id, lang)` with `name` + `description` (en, fi, sv, ja, ar) |
| `trip` / `trip_leg` | Measurement data (distance, time → speed) with FKs to `unit` |
| `conversion_record` | Saved temperature conversions (value, from/to unit, result, lang, timestamp) |

Queries use `LEFT JOIN unit_translation` for the selected language and `JOIN` English as **fallback** (`COALESCE`).

### Why this approach

1. **Normalization** — non-text fields (code, symbol, category) are stored once; adding Japanese/Arabic never duplicates measurement columns.
2. **Easy to extend** — a new language is new rows only (no `ALTER TABLE`, no new `unit_xx` tables).
3. **Matches production practice** — the brief notes translation tables are the usual industry choice; this assignment is small enough that the extra JOIN is fine.
4. **Clean fallback** — missing `lang` rows fall back to English without NULL-filled language columns.

Field/row/table approaches were rejected here: field columns grow the schema per language; row approach duplicates non-text data; per-language tables force runtime table-name switching and schema drift.

### Two localization layers

| Layer | Source | Examples |
|-------|--------|----------|
| Static UI | ResourceBundle `.properties` | Buttons, titles, column headers |
| Domain text | DB `unit_translation` | “Celsius”, “セルシウス”, “مئوية”, descriptions |

The language combo updates **both** at once (reload FXML bundle + re-query DAO with `LocaleCodes.fromLocale`).

---

## How to run

```bash
cd OTP_JUnit_Assignment
docker compose down -v    # required once to load new init.sql
docker compose up -d      # DAO tests need MariaDB running (otherwise they skip)
mvn javafx:run
mvn clean test jacoco:report jacoco:check
```

### JaCoCo / 80% core-logic target
JaCoCo **excludes GUI and legacy** classes (`Main`, `TemperatureViewController`, old `TempRecord*`, etc.).  
**Included core logic:** `TemperatureConverter`, `UnitDAO`, `ConversionRecordDAO`, `Unit`, `ConversionRecord`, `LocaleCodes`, `DBConnection`.

With MariaDB up, `jacoco:check` enforces **≥ 80% line coverage** on that core set. Open `target/site/jacoco/index.html` for the report.

Docker / Jenkins / minikube: see previous assignment notes; manifests in `Dockerfile`, `Jenkinsfile`, `k8s/temperature-converter.yaml`.

### Oma reminders
1. GitHub link (branch `OTP2_Assignment_3`)
2. Docker Hub URL
3. GUI screenshots (EN / JA / AR) via X server if required
4. Jenkins + JaCoCo + `kubectl get pods` screenshots
5. This README section (approach + justification)
