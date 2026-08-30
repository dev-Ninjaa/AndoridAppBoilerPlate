# Kotlin Boilerplate — Android Starter Kit

A clean, minimal Android starter project built with **Kotlin**, **Jetpack Compose**, and **Material 3**. Use this as a foundation for new Android apps.

---

## ✨ Features

- **Jetpack Compose** UI — no XML layouts
- **Material 3** theming with light / dark mode and dynamic color (Android 12+)
- **Edge-to-edge** display
- **ViewModel** + **StateFlow** for reactive counter state
- **Gradle Kotlin DSL** build configuration
- **GitHub Actions** CI — builds the app and runs tests on every push

---

## 🗂 Project Structure

```
app/src/main/java/com/example/boilerplate/
├── BoilerplateApplication.kt       # Application subclass
├── data/                           # Data layer (repositories, models — add here)
├── theme/
│   ├── Color.kt                    # Color palette
│   ├── Theme.kt                    # BoilerplateTheme composable
│   └── Type.kt                     # Typography scale
├── ui/
│   ├── components/
│   │   └── CounterButton.kt        # Reusable button composables
│   └── screens/
│       ├── CounterScreen.kt        # Counter screen composable
│       └── MainActivity.kt         # Single-activity entry point
└── viewmodel/
    └── CounterViewModel.kt         # Counter state & business logic
```

---

## 🏗 Tech Stack

| Layer | Technology |
|---|---|
| Language | Kotlin |
| UI | Jetpack Compose |
| Design System | Material 3 |
| State | ViewModel + StateFlow |
| Build | Gradle Kotlin DSL |
| Min SDK | 24 (Android 7.0) |
| Target SDK | 34 (Android 14) |

---

## 🔢 How the Counter Works

1. **`CounterViewModel`** owns a `MutableStateFlow<Int>` initialised to `0`.
2. It exposes a read-only `StateFlow<Int>` called `count` to the UI.
3. **`CounterScreen`** collects `count` with `collectAsStateWithLifecycle()` — Compose automatically recomposes when the value changes.
4. The `+`, `−`, and Reset buttons call `viewModel.increment()`, `viewModel.decrement()`, and `viewModel.reset()` respectively.
5. Because the state lives in the ViewModel, the counter survives screen rotations and other configuration changes.

---

## ⚙️ GitHub Actions CI

The workflow at [`.github/workflows/android-build.yml`](.github/workflows/android-build.yml) runs on every push and pull request:

1. Checks out the code.
2. Sets up **JDK 17** (Temurin).
3. Runs **unit tests** (`./gradlew test`).
4. Assembles the **debug APK** (`./gradlew assembleDebug`).
5. Uploads the APK as a **GitHub Actions artifact** (retained for 7 days).

---

## 🚀 Getting Started

### Prerequisites

- Android Studio Hedgehog (2023.1.1) or newer
- JDK 17+

### Run locally

```bash
git clone https://github.com/<your-username>/AndroidAppStarterKit.git
cd AndroidAppStarterKit
./gradlew assembleDebug
```

Open the project in Android Studio and click **Run ▶** to launch on an emulator or device.

### Run tests

```bash
./gradlew test
```

---

## 📦 Building from GitHub (no local setup)

Fork this repository on GitHub, then:

1. Open the repository in **GitHub Codespaces** (click *Code → Codespaces → New codespace*).
2. The Codespace will automatically install the required JDK.
3. Run `./gradlew assembleDebug` in the terminal.
4. Download the APK from the **Actions** tab after a CI run.

---

## 🛠 Extending This Boilerplate

| What to add | Where |
|---|---|
| New screens | `ui/screens/` |
| Shared UI components | `ui/components/` |
| Data models / repositories | `data/` |
| Additional ViewModels | `viewmodel/` |
| Navigation | Add Compose Navigation to `app/build.gradle.kts` and wire it in `MainActivity` |

---

## License

```
MIT License — free to use, modify, and distribute.
```
