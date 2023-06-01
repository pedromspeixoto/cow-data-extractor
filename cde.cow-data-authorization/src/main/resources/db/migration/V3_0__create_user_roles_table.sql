CREATE TABLE IF NOT EXISTS user_roles (
    user_id VARCHAR(255) NOT NULL,
    role_id BIGINT NOT NULL
);

-- create indexes
CREATE INDEX ON user_roles (user_id DESC);