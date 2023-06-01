CREATE TABLE IF NOT EXISTS device_data (
    device_id VARCHAR(255) NOT NULL,
    device_type VARCHAR(255) NOT NULL,
    device_status varchar(255) NOT NULL,
    device_description TEXT,
    farm_id VARCHAR(255)
);

-- create indexes
CREATE INDEX ON device_data (device_id DESC);