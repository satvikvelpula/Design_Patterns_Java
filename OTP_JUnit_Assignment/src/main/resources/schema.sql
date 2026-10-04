CREATE DATABASE IF NOT EXISTS temperature_db;
USE temperature_db;

CREATE TABLE IF NOT EXISTS temperature_unit (
    id INT AUTO_INCREMENT PRIMARY KEY,
    code VARCHAR(8)  NOT NULL UNIQUE,
    name VARCHAR(64) NOT NULL
);

CREATE TABLE IF NOT EXISTS temp_record (
    id INT AUTO_INCREMENT PRIMARY KEY,
    value DOUBLE NOT NULL,
    unit_id INT NOT NULL,
    recorded_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_temp_record_unit
    FOREIGN KEY (unit_id) REFERENCES temperature_unit (id)
);

INSERT INTO temperature_unit (code, name) VALUES
    ('C', 'Celsius'),
    ('F', 'Fahrenheit'),
    ('K', 'Kelvin')
ON DUPLICATE KEY UPDATE name = VALUES(name);
    