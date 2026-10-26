# Emogent Keyboard prototype

This is the first native Android IME prototype. It prioritizes plain typing and uses no network permission or external typing service.

## Build and install

Requirements: JDK 17 or 21, Android SDK platform 35 and Android build tools. From the repository root:

```sh
cd android
./gradlew assembleDebug
adb install -r app/build/outputs/apk/debug/app-debug.apk
```

Open Emogent Keyboard from the launcher. Tap **Enable keyboard** and enable it in Android Settings. Tap **Choose current keyboard** and select Emogent. The app cannot silently change the active keyboard. To return to another keyboard, use the keyboard switch key in the bottom row or Android's system keyboard picker. The setup screen's text field is available for a first smoke test.

For a command-line Android emulator test, create/start an AVD with API 35, install the APK, then open a text-capable app and verify ordinary text, deletion, shift, symbol page and the editor action key. Also inspect email/URL, phone/numeric, multiline and password fields. Record device/API/app versions and any failures; an emulator is not physical-device or daily-use evidence.

## What works in this prototype

- Registered `InputMethodService`, system IME metadata and ordinary launcher activity.
- User-directed enable flow, standard IME picker, and an in-app test field.
- Touch QWERTY entry, shift/caps toggle, digits/symbol page, comma/period, space and delete-before-cursor.
- Basic capitalization hint, numeric/phone initial symbol page, email/URI period, and Go/Search/Send/Next/Done editor action mapping.
- Explicit system keyboard switch control.
- No suggestions, autocorrect, learning, clipboard capture, network permission, telemetry, or Emogent symbols.

## Evidence and limits (26 October 2026)

- Repository remote is `https://github.com/nickhilster/Emogent`; branch is `feature/android-ime-prototype`.
- Gradle 8.9 and JDK 21 launch successfully. `./gradlew assembleDebug` reaches Android Gradle Plugin SDK discovery, then fails because this host has no configured Android SDK (`SDK location not found`).
- No `adb`, `android.jar`, SDK platform or emulator was found on this host. APK build, install and real IME behavior are therefore **not verified**. Build/toolchain code has not been represented as a passing test.
- Next executable verification requires installing/configuring Android SDK platform 35/build tools, running `./gradlew assembleDebug`, then installing on emulator and a physical Android device.

This is a functional implementation attempt, not a default-ready keyboard. Key repeat, long-press alternates, cursor navigation, accessibility review, diverse screen sizes, suggestion/correction quality, non-English layouts and physical-device use remain open.
