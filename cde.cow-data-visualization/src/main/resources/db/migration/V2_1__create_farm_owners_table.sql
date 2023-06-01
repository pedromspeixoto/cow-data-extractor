CREATE TABLE IF NOT EXISTS farm_owners (
    farm_id VARCHAR(255) NOT NULL,
    owner_id VARCHAR(255) NOT NULL,
    role VARCHAR(255) DEFAULT 'ADMIN' NOT NULL
);

-- create indexes
CREATE INDEX ON farm_owners (farm_id, owner_id DESC);