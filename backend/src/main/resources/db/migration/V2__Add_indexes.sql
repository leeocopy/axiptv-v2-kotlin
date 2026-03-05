CREATE INDEX IF NOT EXISTS idx_devices_trial_end_at ON devices(trial_end_at);
CREATE INDEX IF NOT EXISTS idx_devices_is_active ON devices(is_active);
CREATE INDEX IF NOT EXISTS idx_devices_active_until ON devices(active_until);
