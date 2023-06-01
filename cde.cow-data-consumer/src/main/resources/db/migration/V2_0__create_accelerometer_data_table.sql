CREATE TABLE IF NOT EXISTS accelerometer_data (
    device_id VARCHAR(255) NOT NULL,
    day DATE NOT NULL,
    timestamp TIMESTAMPTZ NOT NULL,
    x NUMERIC NOT NULL,
    y NUMERIC NOT NULL,
    z NUMERIC NOT NULL
);

-- create hypertable
SELECT create_hypertable('accelerometer_data', 'timestamp');

-- create indexes
CREATE INDEX ON accelerometer_data (device_id DESC);
CREATE INDEX ON accelerometer_data (device_id, day DESC);