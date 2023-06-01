CREATE TABLE IF NOT EXISTS gps_data (
    device_id VARCHAR(255) NOT NULL,
    day DATE NOT NULL,
    timestamp TIMESTAMPTZ NOT NULL,
    latitude NUMERIC NOT NULL,
    longitude NUMERIC NOT NULL
);

-- create hypertable
SELECT create_hypertable('gps_data', 'timestamp');

-- create indexes
CREATE INDEX ON gps_data (device_id DESC);
CREATE INDEX ON gps_data (device_id, day DESC);