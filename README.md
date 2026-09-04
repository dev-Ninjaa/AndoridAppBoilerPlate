# Aura 🌿✨

An aesthetic daily focus, intentions, and reflection companion app built with modern Kotlin and Jetpack Compose (Material 3).

---

## 🚀 Download & Install

### Method 1: Direct Download from Releases (Easiest!) 🎯

1. **Go to [Releases](../../releases)**
2. **Download** the latest `Aura-vX.X.X.apk` file
3. **Install** on your Android device (enable "Install from Unknown Sources" if prompted)
4. **Done!** Open Aura and enjoy ✨

### Method 2: Build from GitHub Actions

This repository includes CI/CD workflows that automatically build APKs:

#### For Quick Testing (Artifacts):
1. Go to the **[Actions](../../actions)** tab
2. Click the latest **Build and Deliver Android APK** run
3. Download **`Aura-Debug-APK`** from the Artifacts section
4. Unzip and install `app-debug.apk` on your device

#### Creating a New Release:
To create a new downloadable release:

```bash
# Create and push a version tag
git tag v1.0.0
git push origin v1.0.0
```

The workflow will automatically:
- Build the APK
- Create a GitHub Release
- Upload the APK with installation instructions

Or use the GitHub UI:
1. Go to **Releases** → **Draft a new release**
2. Create a new tag (e.g., `v1.0.0`)
3. The APK will be automatically built and attached

---

## 🎨 Aesthetic Highlights & Features

- **Zen & Warm Organic Aesthetics**: Hand-crafted M3 color schemes with soft gradients, soothing neutrals, and smooth spring animations.
- **Daily Reflection & Inspiration**: Inspiring daily quotes with an interactive shuffle effect.
- **Three Daily Intentions**: Focus on what matters today with an interactive intention tracker and progress meter.
- **Aesthetic Quick Scratchpad**: Capture thoughts, ideas, and affirmations with quick mood tags (`Calm`, `Focus`, `Idea`, `Energy`, `Gratitude`).
- **In-App Device & APK Hub**: View device architecture, build configuration, and testing guidance directly inside the app.
- **Edge-to-Edge Support**: Optimized for modern Android displays with safe area insets and high-contrast typography.
