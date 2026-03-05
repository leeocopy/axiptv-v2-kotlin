# AXIPTV Backend

Ktor service for device activation and status management.

## Tech Stack
- **Framework**: Ktor 2.3.7 (Netty)
- **DB**: Postgres (Neon for prod, Docker for dev)
- **ORM**: Exposed
- **Migrations**: Flyway
- **Language**: Kotlin 1.9.22

## Local Development

### 1. Start Postgres
```bash
docker-compose up -d
```

### 2. Environment Variables
Create a `.env` (not committed) or set:
- `JDBC_URL`: `jdbc:postgresql://localhost:5432/axiptv`
- `DB_USER`: `user`
- `DB_PASSWORD`: `password`
- `PORT`: `8080`

### 3. Run
```bash
./gradlew run
```

## API Endpoints

### Health
`GET /health`

### Device Status
`POST /api/device/status`
Request:
```json
{ "device_hash": "SOME_UNIQUE_HASH" }
```

Response:
```json
{
  "allowed": true,
  "reason": "TRIAL_ACTIVE",
  "trial_end_at": "...",
  "active_until": null,
  "server_time": "..."
}
```

## Testing
```bash
./gradlew test
```
