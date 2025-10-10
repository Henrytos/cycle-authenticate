CREATE TABLE IF NOT EXISTS users(
    user_id UUID PRIMARY KEY,
    username VARCHAR(50) NOT NULL,
    email VARCHAR(100) UNIQUE,
    password_hash VARCHAR(512) NOT NULL,
    date_of_brith TIMESTAMP
);