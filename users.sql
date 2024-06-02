CREATE TABLE users (
    user_id SERIAL PRIMARY KEY,
    daily_limit DECIMAL(15, 2) DEFAULT 10000.00,
    current_limit DECIMAL(15, 2) DEFAULT 10000.00
);