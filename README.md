# KQT — Taqweem (Hijri Date Converter)

An **Android app** (`com.kqg.taqweem`) that converts and picks **Hijri (Islamic) dates** using a custom Hijri date converter and date-picker dialog.

## ✨ Features

- 📅 Hijri ↔ Gregorian conversion
- 🗓️ Custom Hijri date picker dialog
- 🌙 Android native UI (Material)

## 🧱 Tech Stack

- Java, Android SDK (compileSdk 35, minSdk 24)
- AndroidX AppCompat, Material, ConstraintLayout

## 📁 Project Structure

```
app/
├── src/main/java/com/kqg/taqweem/
│   ├── MainActivity.java
│   ├── HijriDateConverter.java
│   └── HijriDatePickerDialog.java
└── src/main/res/   # layouts, drawables, values
```

## 🚀 Getting Started

### Prerequisites
- Android Studio / Gradle
- JDK 11

### Build
```bash
cd app  # project root is repo root
./gradlew assembleDebug
```
Or open the project in Android Studio and run.
