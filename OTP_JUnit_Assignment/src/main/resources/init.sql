-- Assignment 6: Database Localization (translation-table approach)
-- Charset: utf8mb4 for Japanese / Arabic

CREATE DATABASE IF NOT EXISTS temperature_db
  CHARACTER SET utf8mb4
  COLLATE utf8mb4_unicode_ci;

USE temperature_db;

SET NAMES utf8mb4;

-- ---------------------------------------------------------------------------
-- Language-neutral unit catalogue (temperature + speed / distance / time)
-- ---------------------------------------------------------------------------
CREATE TABLE IF NOT EXISTS unit (
    id       INT AUTO_INCREMENT PRIMARY KEY,
    code     VARCHAR(16)  NOT NULL UNIQUE,
    category VARCHAR(32)  NOT NULL,
    symbol   VARCHAR(16)  NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ---------------------------------------------------------------------------
-- Localization: one translation row per (unit, language)
-- ---------------------------------------------------------------------------
CREATE TABLE IF NOT EXISTS unit_translation (
    unit_id     INT          NOT NULL,
    lang        CHAR(2)      NOT NULL,
    name        VARCHAR(128) NOT NULL,
    description VARCHAR(255) NOT NULL,
    PRIMARY KEY (unit_id, lang),
    CONSTRAINT fk_unit_translation_unit
        FOREIGN KEY (unit_id) REFERENCES unit (id)
        ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ---------------------------------------------------------------------------
-- Measurement tables (speed = distance / time)
-- ---------------------------------------------------------------------------
CREATE TABLE IF NOT EXISTS trip (
    id         INT AUTO_INCREMENT PRIMARY KEY,
    label      VARCHAR(64) NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE IF NOT EXISTS trip_leg (
    id                INT AUTO_INCREMENT PRIMARY KEY,
    trip_id           INT NOT NULL,
    distance_value    DOUBLE NOT NULL,
    distance_unit_id  INT NOT NULL,
    time_value        DOUBLE NOT NULL,
    time_unit_id      INT NOT NULL,
    speed_value       DOUBLE NOT NULL,
    speed_unit_id     INT NOT NULL,
    recorded_at       TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_trip_leg_trip FOREIGN KEY (trip_id) REFERENCES trip (id),
    CONSTRAINT fk_trip_leg_distance FOREIGN KEY (distance_unit_id) REFERENCES unit (id),
    CONSTRAINT fk_trip_leg_time FOREIGN KEY (time_unit_id) REFERENCES unit (id),
    CONSTRAINT fk_trip_leg_speed FOREIGN KEY (speed_unit_id) REFERENCES unit (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ---------------------------------------------------------------------------
-- Temperature conversion history (GUI last-5 list)
-- ---------------------------------------------------------------------------
CREATE TABLE IF NOT EXISTS conversion_record (
    id            INT AUTO_INCREMENT PRIMARY KEY,
    input_value   DOUBLE NOT NULL,
    from_unit_id  INT NOT NULL,
    to_unit_id    INT NOT NULL,
    result_value  DOUBLE NOT NULL,
    lang          CHAR(2) NOT NULL,
    recorded_at   TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_conv_from FOREIGN KEY (from_unit_id) REFERENCES unit (id),
    CONSTRAINT fk_conv_to   FOREIGN KEY (to_unit_id) REFERENCES unit (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ---------------------------------------------------------------------------
-- Seed units
-- ---------------------------------------------------------------------------
INSERT INTO unit (code, category, symbol) VALUES
    ('C',   'temperature', '°C'),
    ('F',   'temperature', '°F'),
    ('K',   'temperature', 'K'),
    ('kmh', 'speed',       'km/h'),
    ('mph', 'speed',       'mph'),
    ('km',  'distance',    'km'),
    ('mi',  'distance',    'mi'),
    ('h',   'time',        'h'),
    ('s',   'time',        's')
ON DUPLICATE KEY UPDATE symbol = VALUES(symbol);

-- Helper: resolve unit id by code for inserts below
-- Translations: en, fi, sv, ja, ar

INSERT INTO unit_translation (unit_id, lang, name, description)
SELECT u.id, t.lang, t.name, t.description
FROM unit u
JOIN (
    SELECT 'C' AS code, 'en' AS lang, 'Celsius' AS name, 'Metric temperature scale' AS description
    UNION ALL SELECT 'C', 'fi', 'Celsius', 'Metrijärjestelmän lämpötila-asteikko'
    UNION ALL SELECT 'C', 'sv', 'Celsius', 'Metrisk temperaturskala'
    UNION ALL SELECT 'C', 'ja', 'セルシウス', 'メートル法の温度目盛り'
    UNION ALL SELECT 'C', 'ar', 'مئوية', 'مقياس حرارة متري'

    UNION ALL SELECT 'F', 'en', 'Fahrenheit', 'Imperial temperature scale'
    UNION ALL SELECT 'F', 'fi', 'Fahrenheit', 'Brittiläinen lämpötila-asteikko'
    UNION ALL SELECT 'F', 'sv', 'Fahrenheit', 'Imperial temperaturskala'
    UNION ALL SELECT 'F', 'ja', '華氏', 'ヤード・ポンド法の温度目盛り'
    UNION ALL SELECT 'F', 'ar', 'فهرنهايت', 'مقياس حرارة إمبراطوري'

    UNION ALL SELECT 'K', 'en', 'Kelvin', 'Absolute thermodynamic scale'
    UNION ALL SELECT 'K', 'fi', 'Kelvin', 'Absoluuttinen lämpötila-asteikko'
    UNION ALL SELECT 'K', 'sv', 'Kelvin', 'Absolut termodynamisk skala'
    UNION ALL SELECT 'K', 'ja', 'ケルビン', '絶対温度目盛り'
    UNION ALL SELECT 'K', 'ar', 'كلفن', 'مقياس ديناميكي حراري مطلق'

    UNION ALL SELECT 'kmh', 'en', 'Kilometre per hour', 'Metric speed'
    UNION ALL SELECT 'kmh', 'fi', 'Kilometri tunnissa', 'Metrijärjestelmän nopeus'
    UNION ALL SELECT 'kmh', 'sv', 'Kilometer per timme', 'Metrisk hastighet'
    UNION ALL SELECT 'kmh', 'ja', 'キロメートル毎時', 'メートル法の速度'
    UNION ALL SELECT 'kmh', 'ar', 'كيلومتر في الساعة', 'سرعة مترية'

    UNION ALL SELECT 'mph', 'en', 'Mile per hour', 'Imperial speed'
    UNION ALL SELECT 'mph', 'fi', 'Maili tunnissa', 'Brittiläinen nopeus'
    UNION ALL SELECT 'mph', 'sv', 'Engelsk mil per timme', 'Imperial hastighet'
    UNION ALL SELECT 'mph', 'ja', 'マイル毎時', 'ヤード・ポンド法の速度'
    UNION ALL SELECT 'mph', 'ar', 'ميل في الساعة', 'سرعة إمبراطورية'

    UNION ALL SELECT 'km', 'en', 'Kilometre', 'Metric distance'
    UNION ALL SELECT 'km', 'fi', 'Kilometri', 'Metrijärjestelmän pituus'
    UNION ALL SELECT 'km', 'sv', 'Kilometer', 'Metrisk längd'
    UNION ALL SELECT 'km', 'ja', 'キロメートル', 'メートル法の距離'
    UNION ALL SELECT 'km', 'ar', 'كيلومتر', 'مسافة مترية'

    UNION ALL SELECT 'mi', 'en', 'Mile', 'Imperial distance'
    UNION ALL SELECT 'mi', 'fi', 'Maili', 'Brittiläinen pituus'
    UNION ALL SELECT 'mi', 'sv', 'Engelsk mil', 'Imperial längd'
    UNION ALL SELECT 'mi', 'ja', 'マイル', 'ヤード・ポンド法の距離'
    UNION ALL SELECT 'mi', 'ar', 'ميل', 'مسافة إمبراطورية'

    UNION ALL SELECT 'h', 'en', 'Hour', 'Time in hours'
    UNION ALL SELECT 'h', 'fi', 'Tunti', 'Aika tunteina'
    UNION ALL SELECT 'h', 'sv', 'Timme', 'Tid i timmar'
    UNION ALL SELECT 'h', 'ja', '時間', '時間単位'
    UNION ALL SELECT 'h', 'ar', 'ساعة', 'الوقت بالساعات'

    UNION ALL SELECT 's', 'en', 'Second', 'Time in seconds'
    UNION ALL SELECT 's', 'fi', 'Sekunti', 'Aika sekunteina'
    UNION ALL SELECT 's', 'sv', 'Sekund', 'Tid i sekunder'
    UNION ALL SELECT 's', 'ja', '秒', '秒単位'
    UNION ALL SELECT 's', 'ar', 'ثانية', 'الوقت بالثواني'
) AS t ON t.code = u.code
ON DUPLICATE KEY UPDATE
    name = VALUES(name),
    description = VALUES(description);

-- Sample trip (measurement demo data)
INSERT INTO trip (label) VALUES ('Demo commute')
ON DUPLICATE KEY UPDATE label = VALUES(label);

INSERT INTO trip_leg (trip_id, distance_value, distance_unit_id, time_value, time_unit_id, speed_value, speed_unit_id)
SELECT tr.id, 30.0, d.id, 0.5, ti.id, 60.0, s.id
FROM trip tr
JOIN unit d  ON d.code = 'km'
JOIN unit ti ON ti.code = 'h'
JOIN unit s  ON s.code = 'kmh'
WHERE tr.label = 'Demo commute'
  AND NOT EXISTS (SELECT 1 FROM trip_leg WHERE trip_id = tr.id);
