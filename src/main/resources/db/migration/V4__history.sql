CREATE TABLE IF NOT EXISTS history (
    id SERIAL PRIMARY KEY,
    request VARCHAR(255) NOT NULL,
    request_date DATE NOT NULL,
    place_id BIGINT REFERENCES place(id) ON DELETE CASCADE,
    date DATE NOT NULL,
    time TIME NOT NULL
);