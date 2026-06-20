# Exploramus

Exploramus is a discovery-focused application built with **Kotlin Multiplatform (KMP)**. It allows users to explore various entities (such as countries), search for information, manage favorites, and customize settings.

## Architecture: DKMP

The project follows the **DKMP (Declarative Kotlin Multiplatform)** architecture pattern, which emphasizes a shared, state-driven business logic layer and thin, declarative UI layers for each platform.

### Key Components

*   **Declarative UI**: Uses **Jetpack Compose** for Android and **SwiftUI** for iOS.
*   **Kotlin Multiplatform**: All business logic, state management, and navigation are shared across platforms in the `:shared` module.
*   **State-driven (MVI)**: The UI observes immutable state flows provided by a shared `DKMPViewModel`.
*   **StateManager**: A centralized component in the shared module that manages screen states, navigation backstacks, and lifecycle-aware coroutine scopes.
*   **Dependency Injection**: Powered by **Koin** for both shared and platform-specific dependencies.

---

## Project Structure

The project is organized into several modules to ensure a clean separation of concerns:

### UI Layers
*   **`composeApp`**: The Android application module, built entirely with Jetpack Compose.
*   **`iosApp`**: The native iOS application, built with SwiftUI and consuming the shared KMP framework.

### Shared Logic
*   **`:shared`**: The core multiplatform module. It contains:
    - **`DKMPViewModel`**: The primary interface between UI and logic.
    - **Navigation**: Shared routing and backstack management.
    - **Screen States**: Definitions for the data shown on each screen.
*   **`:core`**:
    - `:core:models`: Common data classes used across the project.
    - `:core:common`: Utilities and logging.
*   **`:data`**:
    - `:data:repository`: The main entry point for data access, coordinating between local and remote sources.
    - `:data:network`: API clients and remote data fetching.
    - `:data:localdb`: Local persistence (e.g., SQLDelight).
    - `:data:assets`: Handling of bundled assets and JSON data.
*   **`:di`**: Global dependency injection configuration.

---

## Getting Started

### Build and Run Android Application
To build and run the Android app, use the run configuration in Android Studio or run:
```shell
./gradlew :composeApp:assembleDebug
```

### Build and Run iOS Application
1. Open the `iosApp` directory in Xcode.
2. Select a simulator or physical device.
3. Build and Run (**Cmd + R**).

Alternatively, you can run the iOS configuration directly from Android Studio if the KMP plugin is configured.

---

Learn more about [Kotlin Multiplatform](https://www.jetbrains.com/help/kotlin-multiplatform-dev/get-started.html).
