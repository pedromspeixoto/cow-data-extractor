CREATE TABLE IF NOT EXISTS role_access_configuration (
    role_id BIGINT NOT NULL,
    method VARCHAR(255) NOT NULL,
    path VARCHAR(255) NOT NULL
);