CREATE TABLE IF NOT EXISTS access_audit (
    id SERIAL PRIMARY KEY,
    username VARCHAR(255),
    source_ip VARCHAR(255),
    protocol VARCHAR(255),
    method VARCHAR(255),
    uri VARCHAR(255)
);