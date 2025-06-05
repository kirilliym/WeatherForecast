CREATE INDEX IF NOT EXISTS idx_typo_wrong_hash ON typo USING hash (wrong);

CREATE INDEX IF NOT EXISTS idx_weather_place_date ON weather (place_id, date);

CREATE INDEX IF NOT EXISTS idx_prime_token_token_hash ON prime_token USING hash (token);
