CREATE TABLE IF NOT EXISTS prime_token (
    id SERIAL PRIMARY KEY,
    token VARCHAR(255) NOT NULL,
    remain_operations INT NOT NULL
);
