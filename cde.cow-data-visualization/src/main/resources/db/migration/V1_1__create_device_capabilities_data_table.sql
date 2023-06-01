CREATE TABLE IF NOT EXISTS device_capabilities (
    capabilities_id SERIAL PRIMARY KEY,
    device_id VARCHAR(255) NOT NULL,
    accelerometer BOOLEAN NOT NULL DEFAULT FALSE,
    gyroscope BOOLEAN NOT NULL DEFAULT FALSE,
    gps BOOLEAN NOT NULL DEFAULT FALSE,
    temperature BOOLEAN NOT NULL DEFAULT FALSE,
    humidity BOOLEAN NOT NULL DEFAULT FALSE
);

-- create indexes
CREATE INDEX ON device_data (device_id DESC);