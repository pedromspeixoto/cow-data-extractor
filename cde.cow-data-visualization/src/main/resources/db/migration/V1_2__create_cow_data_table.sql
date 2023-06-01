CREATE TABLE IF NOT EXISTS cow_data (
    cow_id VARCHAR(255) NOT NULL,
    device_id VARCHAR(255) NOT NULL,
    cow_name VARCHAR(255),
    cow_description TEXT,
    cow_picture BYTEA,
    birth_date DATE,
    weight NUMERIC
);

-- create indexes
CREATE INDEX ON cow_data (device_id DESC);
CREATE INDEX ON cow_data (cow_id DESC);