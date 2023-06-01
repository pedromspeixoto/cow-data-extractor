CREATE TABLE IF NOT EXISTS roles (
    role_id SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL
);

-- create indexes
CREATE INDEX ON roles (name DESC);