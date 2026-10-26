# Android keyboard foundation decision

**Status:** Prototype choice for review · 26 October 2026

## Decision

Start Emogent as an independently implemented Kotlin Android IME using the public `InputMethodService` and `InputConnection` APIs. Do not copy or fork keyboard source for the prototype. This keeps the product's licensing choices open while delivering a real installable architecture immediately. This is not a claim that this small key-entry prototype is already a competitive daily-driver engine.

## Candidate comparison

| Candidate | Typing, suggestions and correction | Maintenance / extensibility | License and implications | Decision |
|---|---|---|---|---|
| FlorisBoard | Mature Android keyboard UI and customization; its current README says word suggestions and spellcheck are not included in current releases. The separate FlorisBoard NLP project is an alpha Kotlin/C++ component and may be evaluated independently later. | Active Kotlin project, Android 8+; modular UI and extension direction. Integration still brings upstream update and dependency review. | Main app Apache-2.0. Any reused NLP component and dictionaries need their own license/provenance and build audit. | Best permissive reference/component candidate, but does not solve the immediate suggestion gap. Revisit as a standalone NLP/library contribution. |
| HeliBoard | Functional offline dictionary-backed suggestions/spellcheck; actual quality depends on imported dictionaries. Offers broad layout/customization capabilities. | Active fork of OpenBoard/AOSP, Kotlin, extensive feature set; upstream adaptation is not a small drop-in. | HeliBoard is GPL-3.0. Distribution of a derivative/combined work generally carries corresponding-source and GPL redistribution conditions. Repo also contains separately licensed assets/materials; file-level provenance matters. This is a material product decision, so no HeliBoard code is copied here. | Do not adopt without explicit product/legal acceptance of GPL obligations. Strong functional reference. |
| AOSP LatinIME | Full-featured Latin keyboard with suggestions, autocorrection and broad locale/layout work. | Maintained as part of Android platform source; platform build conventions, resources and integration create significant extraction/upstream burden for a standalone app. | AOSP source has file-level license headers (commonly Apache-2.0; inspect each copied file and notice). | Strong behavior reference; not selected as an app foundation because adapting platform modules is heavier than starting with public IME APIs. |
| Independent `InputMethodService` | Prototype implements direct text entry and field actions only; no dictionary suggestions, autocorrect or glide. | Small native Kotlin surface, explicit `EditorInfo` handling, easiest to understand and extend in stages. | No keyboard foundation source imported; Android SDK APIs. Dependencies currently none. | Selected for prototype. |

This is a scoped engineering choice, not a claim that independent typing code will outperform these projects. The next foundation checkpoint should prototype FlorisBoard NLP on a narrow English dataset and compare it against a carefully reviewed dictionary source before building autocorrection.

## Privacy and scope

The app has no `INTERNET` permission, telemetry SDK, external agent calls, dictionary download, or content logging. The service only commits key actions through the current `InputConnection`. Password variations are recognized, the IME window is marked secure while shown for those fields, and punctuation extras are removed; the prototype does not maintain composing text, learning, suggestions, clipboard history, or typed-content caches. This is defense in depth, not proof of credential safety: confirm password/OTP behavior on actual Android targets before treating it as validated.

## References checked

- [Android IME guide](https://developer.android.com/develop/ui/views/touch-and-input/creating-input-method): `InputMethodService`, manifest registration, `InputConnection` and user selection flow.
- [FlorisBoard repository](https://github.com/florisboard/florisboard): current README, Apache-2.0 license and current release feature statement.
- [FlorisBoard NLP repository](https://github.com/florisboard/nlp): separate NLP core; its README describes dictionary suggestions/spellcheck and an alpha project state.
- [HeliBoard repository](https://github.com/HeliBorg/HeliBoard): README describes offline use, dictionary-backed suggestions and GPLv3; repository includes additional separately licensed materials.
- [AOSP LatinIME source](https://android.googlesource.com/platform/packages/inputmethods/LatinIME/): platform source and ongoing Android branch history; any future reuse requires file-level license review.

## Reproducible build

From `android/`, run `./gradlew assembleDebug` with JDK 17 or 21 and Android SDK platform 35 plus build-tools installed. Gradle wrapper is 8.9; Android Gradle Plugin is 8.5.2. The local machine used for this first implementation does not have an Android SDK or emulator installed, so APK generation and device installation are unverified here. No binary APK is claimed.
