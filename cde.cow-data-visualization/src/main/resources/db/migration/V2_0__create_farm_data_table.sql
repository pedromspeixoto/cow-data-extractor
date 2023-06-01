CREATE TABLE IF NOT EXISTS farm_data (
    farm_id VARCHAR(255) NOT NULL,
    farm_description TEXT,
    farm_picture BYTEA,
    farm_status VARCHAR(255),
    farm_address VARCHAR(255),
    latitude NUMERIC,
    longitude NUMERIC,
    owner_name VARCHAR(255),
    owner_phone_number VARCHAR(255),
    owner_email VARCHAR(255)
);

-- create indexes
CREATE INDEX ON farm_data (farm_id DESC);