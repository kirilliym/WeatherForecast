DROP TABLE IF EXISTS weather;
DROP TABLE IF EXISTS place;
DROP TABLE IF EXISTS city;

CREATE TABLE IF NOT EXISTS city (
    id SERIAL PRIMARY KEY,
    request_cnt BIGINT,
    name VARCHAR(255) NOT NULL
);

CREATE TABLE IF NOT EXISTS place (
    id SERIAL PRIMARY KEY,
    city_id BIGINT REFERENCES city(id) ON DELETE CASCADE,
    name VARCHAR(255) NOT NULL,
    lat DOUBLE PRECISION,
    lon DOUBLE PRECISION
);

CREATE TABLE IF NOT EXISTS weather (
    id SERIAL PRIMARY KEY,
    place_id BIGINT REFERENCES place(id) ON DELETE CASCADE,
    temp_2_cel BIGINT,
    temp_feels_cel BIGINT,
    wind_speed_10 BIGINT,
    pres_surf BIGINT,
    vlaga_2f BIGINT,
    oblachnost_atmo BIGINT,
    updated TIMESTAMP,
    date DATE,
    time TIME
);
