CREATE TABLE IF NOT EXISTS gyroscope_data (
    device_id VARCHAR(255) NOT NULL,
    day DATE NOT NULL,
    timestamp TIMESTAMPTZ NOT NULL,
    alpha NUMERIC NOT NULL,
    beta NUMERIC NOT NULL,
    gamma NUMERIC NOT NULL
);

-- create hypertable
SELECT create_hypertable('gyroscope_data', 'timestamp');

-- create indexes
CREATE INDEX ON gyroscope_data (device_id DESC);
CREATE INDEX ON gyroscope_data (device_id, day DESC);