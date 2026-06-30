# 🌍 Exploramus

**Exploramus** is a cutting-edge discovery platform built on the power of **Kotlin Multiplatform (KMP)**. It provides a seamless, high-performance experience for exploring the world, searching for data, and managing personal favorites—all while maintaining a single, robust codebase for core logic.

---

## 🚀 Key Features

*   **100% Shared Business Logic**: Navigation, state management, and data handling are entirely shared across platforms.
*   **📱 Native Adaptive Design**: Custom-built adaptive UI layers for both **Android (Jetpack Compose)** and **iOS (SwiftUI)**, ensuring a pixel-perfect experience on phones, tablets, and foldable devices.
*   **⚡️ DKMP Architecture**: A reactive, state-driven (MVI-inspired) architecture that provides immutable state flows and predictable UI behavior.
*   **🛠️ Robust Testing Suite**:
    *   **Unit Tests**: Comprehensive test coverage for shared ViewModels, repositories, and business logic.
    *   **UI Tests**: Native UI automation tests for both platforms (Compose UI Tests & XCTest).
*   **🔗 Seamless Swift Interop**: Leverages **SKIE** to generate high-quality, native-feeling Swift APIs for sealed classes, flows, and enums.
*   **🔡 Type-safe Shared Resources**: Shared string resource handling with type-safe formatting arguments, ensuring consistency across Android and iOS.

---

## 📸 Visual Showcase

### 📱 Android Experience
| Home (Light) | Home (Dark) | Section | Details | Search | Settings |
|:---:|:---:|:---:|:---:|:---:|:---:|
| <img src="docs/screenshots/phone_home_light.png" width="160" /> | <img src="docs/screenshots/phone_home_dark.png" width="160" /> | <img src="docs/screenshots/phone_section.png" width="160" /> | <img src="docs/screenshots/phone_details.png" width="160" /> | <img src="docs/screenshots/phone_search.png" width="160" /> | <img src="docs/screenshots/phone_settings.png" width="160" /> |

### 🍎 iOS Experience
| Home | Details |
|:---:|:---:|
| <img src="docs/screenshots/ios_phone.png" width="250" /> | <img src="docs/screenshots/ios_details.png" width="250" /> |

### 💻 Tablet Support
| Android Tablet | iPad |
|:---:|:---:|
| <img src="docs/screenshots/android_tablet.png" width="450" /> | <img src="docs/screenshots/ios_tablet.png" width="450" /> |

---

## 🏗️ Architecture & Tech Stack

### Multimodal & Modular Structure
The project is architected into clean, decoupled modules to maximize maintainability and scalability:

- **`:shared`**: The "brain" of the app. Contains the `DKMPViewModel`, state management, and navigation routing.
- **`:data`**: A multi-layered data module featuring **SQLDelight** for local persistence, **Ktor** for networking, and repository patterns for data orchestration.
- **`:core`**: Pure Kotlin models and common utilities shared across all layers.
- **`:di`**: Global dependency injection powered by **Koin**.

### Core Technologies
- **Networking**: [Ktor](https://ktor.io/)
- **Database**: [SQLDelight](https://cashapp.github.io/sqldelight/)
- **DI**: [Koin](https://insert-koin.io/)
- **Concurrency**: Kotlin Coroutines & Flow
- **Swift Interop**: [SKIE](https://skie.touchlab.co/)
- **Images**: [Coil](https://coil-kt.github.io/coil/)

---

## 📐 Adaptive UI
Exploramus isn't just cross-platform; it's **form-factor aware**. (See it in action in the [Visual Showcase](#-visual-showcase)).

- **Android**: Uses `Material3 Adaptive` components and custom `AdoptiveValue` utilities to handle Compact, Medium, and Expanded window sizes.
- **iOS**: Implements custom adaptive logic in SwiftUI to provide a tailored experience for iPhone and iPad users.

---

Learn more about [Kotlin Multiplatform](https://www.jetbrains.com/help/kotlin-multiplatform-dev/get-started.html).
