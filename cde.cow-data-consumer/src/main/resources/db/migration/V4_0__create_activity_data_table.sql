CREATE TABLE IF NOT EXISTS activity_data (
    device_id VARCHAR(255) NOT NULL,
    day DATE NOT NULL,
    timestamp TIMESTAMPTZ NOT NULL,
    activity VARCHAR(255) NOT NULL
);

-- create hypertable
SELECT create_hypertable('activity_data', 'timestamp');

-- create indexes
CREATE INDEX ON activity_data (device_id DESC);
CREATE INDEX ON activity_data (device_id, day DESC);