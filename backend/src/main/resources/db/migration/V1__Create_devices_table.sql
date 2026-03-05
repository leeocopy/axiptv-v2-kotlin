CREATE TABLE IF NOT EXISTS devices (
    id BIGSERIAL PRIMARY KEY,
    device_hash TEXT UNIQUE NOT NULL,
    first_seen_at TIMESTAMP WITH TIME ZONE NOT NULL,
    last_seen_at TIMESTAMP WITH TIME ZONE NOT NULL,
    trial_end_at TIMESTAMP WITH TIME ZONE NOT NULL,
    is_active BOOLEAN NOT NULL DEFAULT FALSE,
    active_until TIMESTAMP WITH TIME ZONE
);

CREATE INDEX idx_devices_hash ON devices(device_hash);
