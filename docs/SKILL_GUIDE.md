# AXIPTV V2 — Skill Guide (Kotlin end-to-end)

## 1) Goal
Build a clean, production-ready Android TV IPTV app + Ktor backend.
No known traps, no silent inconsistencies, no unbounded caches.

## 2) Repo Structure (Monorepo)
- /backend   → Ktor API + DB migrations + tests
- /android   → Android TV app (Compose) + tests
- /docs      → specs, API contract, architecture, checklist
- /scripts   → SQL helpers (manual activation)
- /.github   → CI workflows

## 3) Backend Stack (Ktor)
- Kotlin, Gradle Kotlin DSL
- Ktor server
- Postgres (Neon)
- Flyway migrations
- Exposed OR jOOQ (choose one, keep it consistent)
- kotlinx.serialization for JSON

### Backend rules
- Implement: POST /api/device/status
- UPSERT device row:
  - if first time: set first_seen_at, trial_end_at = now + 7 days
  - always update last_seen_at
- allowed = is_active AND active_until>now OR now < trial_end_at
- Reason enum: ACTIVE, ACTIVE_EXPIRED, TRIAL_ACTIVE, TRIAL_EXPIRED
- Always return server_time and timestamps as ISO8601 UTC

### Security
- Validate input strictly (device_hash length, allowed chars)
- Use rate limit (simple) and request logging (no sensitive data)
- Do NOT expose DB secrets, never commit .env

## 4) Android TV App Stack
- Kotlin + Jetpack Compose
- MVVM + Hilt
- Clean Architecture (ui/domain/data)
- Retrofit for backend DeviceStatus
- OkHttp for Xtream calls
- Room for profiles
- DataStore for app state
- EncryptedSharedPreferences for secrets
- ExoPlayer for playback
- TV focus handling components (D-pad)

### Android rules
- Single source of truth for URL building:
  - LIVE/VOD/SERIES URL builder must be centralized (no mismatch between screens/player)
- Categories ALL must use "-1"
- Caches must be bounded (LRU/TTL). No unbounded mutableMap growth.
- Offline behavior:
  - Store last successful device status + lastCheckMs
  - If offline: use cached status and show days remaining
- Navigation:
  - Use ONE NavHost for root + nested graph only if necessary.
  - Keep routes and params documented in docs/ROUTES.md

## 5) Testing & Quality Gates
Backend:
- unit tests for status decision logic
- integration test against local Postgres (docker compose)

Android:
- unit tests for URL builder
- ViewModel state tests (UiState transitions)
- basic UI smoke test

## 6) Deliverables per Phase
Phase 1: backend skeleton + migrations + /api/device/status + docker compose
Phase 2: android skeleton + Splash + status call + storage
Phase 3: activation screens + QR + manual activation instructions
Phase 4: profiles + validation + browse skeleton
Phase 5: player + url builder + caching rules
Phase 6: polishing + CI + release build
