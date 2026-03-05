# AXIPTV Android TV App

Professional IPTV player for Android TV.

## Stack
- **UI**: Jetpack Compose (TV-optimized)
- **Architecture**: MVVM + Clean Architecture
- **DI**: Hilt
- **Network**: Retrofit + OkHttp
- **Persistence**: DataStore, Room
- **Playback**: ExoPlayer

## Setup
1. Open the `/android` folder in Android Studio.
2. Ensure you have the latest stable Android Studio version.
3. Sync Gradle.
4. Set the backend URL in `NetworkModule.kt`.

## Current Features (Phase 2)
- **Splash Screen**: Checks device activation status.
- **Device Hashing**: Robust hash generation using `ANDROID_ID`.
- **Offline Mode**: Uses cached activation status if the backend is unreachable.
- **Activation Enforcement**: Navigates to activation screen if the trial or license has expired.
