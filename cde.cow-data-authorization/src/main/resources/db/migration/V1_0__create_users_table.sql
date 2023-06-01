CREATE TABLE IF NOT EXISTS users (
    user_id VARCHAR(255) NOT NULL,
    username VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL
);

-- create indexes
CREATE INDEX ON users (username DESC);