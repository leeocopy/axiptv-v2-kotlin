# API Contract

## Device Status
`POST /api/device/status`

### Request Body
```json
{
  "device_hash": "string",
  "app_version": "string",
  "platform": "android_tv"
}
```

### Response Body
```json
{
  "allowed": true,
  "reason": "TRIAL_ACTIVE",
  "trial_end_at": "2026-03-12T10:00:00Z",
  "active_until": null,
  "server_time": "2026-03-05T10:00:00Z"
}
```

### Reason Enums
- `ACTIVE`
- `ACTIVE_EXPIRED`
- `TRIAL_ACTIVE`
- `TRIAL_EXPIRED`
