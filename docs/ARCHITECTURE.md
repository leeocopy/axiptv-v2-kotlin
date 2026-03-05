# Architecture Overview

## Monorepo
- **Backend**: Kotlin/Ktor service.
- **Android**: Compose-based Android TV app.

## Clean Architecture (Mobile)
- **Data Layer**: Retrofit services, Room DB, Repository implementations.
- **Domain Layer**: Use cases, Entities.
- **UI Layer**: ViewModels, Compose screens.

## Backend Architecture
- **API**: Ktor routes.
- **Service**: Business logic (Trial calculation, activation logic).
- **Persistence**: Exposed/jOOQ + Flyway.
