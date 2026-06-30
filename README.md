# 🌍 Exploramus

**Exploramus** is a discovery-focused application built with **Kotlin Multiplatform (KMP)**. It allows users to explore data, search for information, and manage personal favorites using a shared codebase for core logic and state management.

---

## 🚀 Architecture & Key Features

### ⚡️ DKMP Architecture
The project follows a reactive, state-driven (MVI-inspired) architecture that provides:
*   **Shared ViewModel**: Centralized `DKMPViewModel` that handles state for both platforms.
*   **Immutable State Flows**: Predictable UI behavior through consistent state updates.
*   **Platform Independence**: Navigation, state management, and data handling are 100% shared.

### 🛠️ Core Capabilities
*   **📱 Adaptive Design**: Custom-built adaptive UI layers for both **Android (Jetpack Compose)** and **iOS (SwiftUI)**, supporting phones, tablets, and foldable devices.
*   **🔗 Swift Interop**: Leverages **SKIE** to generate native-feeling Swift APIs for sealed classes, flows, and enums.
*   **🔡 Shared Resources**: Type-safe string resource handling with formatting arguments across platforms.
*   **🧪 Comprehensive Testing**:
    *   **Unit Tests**: Coverage for shared ViewModels, repositories, and business logic.
    *   **UI Tests**: Native automation for both platforms (Compose UI Tests & XCTest).

---

## 🏗️ Modular Structure

The project is architected into clean, decoupled modules to maximize maintainability:

- **`:shared`**: The "brain" of the app. Contains the `DKMPViewModel`, state management, and navigation routing.
- **`:data`**: A multi-layered data module featuring **SQLDelight** for local persistence, **Ktor** for networking, and repository patterns for data orchestration.
- **`:core`**: Pure Kotlin models and common utilities shared across all layers.
- **`:di`**: Global dependency injection powered by **Koin**.

---

## 📸 Visual Showcase

### 📱 Android Experience
| Home (Light) | Home (Dark) | Section | Details | Search | Settings |
|:---:|:---:|:---:|:---:|:---:|:---:|
| <img src="docs/screenshots/phone_home_light.png" width="160" /> | <img src="docs/screenshots/phone_home_dark.png" width="160" /> | <img src="docs/screenshots/phone_section.png" width="160" /> | <img src="docs/screenshots/phone_details.png" width="160" /> | <img src="docs/screenshots/phone_search.png" width="160" /> | <img src="docs/screenshots/phone_settings.png" width="160" /> |

### 🍎 iOS Experience
| Home (Light) | Home (Dark) | Section | Details | Search | Settings |
|:---:|:---:|:---:|:---:|:---:|:---:|
| <img src="docs/screenshots/iphone_home_light.png" width="160" /> | <img src="docs/screenshots/iphone_home_dark.png" width="160" /> | <img src="docs/screenshots/iphone_section.png" width="160" /> | <img src="docs/screenshots/iphone_details.png" width="160" /> | <img src="docs/screenshots/iphone_search.png" width="160" /> | <img src="docs/screenshots/iphone_settings.png" width="160" /> |

### 💻 Tablet Support
| Android Tablet | iPad |
|:---:|:---:|
| <img src="docs/screenshots/android_tablet.png" width="450" /> | <img src="docs/screenshots/ios_tablet.png" width="450" /> |

---

## 🛠️ Tech Stack & Adaptive UI

### Core Technologies
- **Networking**: [Ktor](https://ktor.io/) | **Database**: [SQLDelight](https://cashapp.github.io/sqldelight/) | **DI**: [Koin](https://insert-koin.io/)
- **Concurrency**: Coroutines & Flow | **Swift Interop**: [SKIE](https://skie.touchlab.co/) | **Images**: [Coil](https://coil-kt.github.io/coil/)

### 📐 Adaptive UI
Exploramus is fully form-factor aware:
- **Android**: Uses `Material3 Adaptive` components and custom `AdoptiveValue` utilities for Compact, Medium, and Expanded sizes.
- **iOS**: Custom adaptive logic in SwiftUI for tailored iPhone and iPad experiences.

---

Learn more about [Kotlin Multiplatform](https://www.jetbrains.com/help/kotlin-multiplatform-dev/get-started.html).
