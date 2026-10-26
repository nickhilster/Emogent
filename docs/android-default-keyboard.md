# Emogent Android Keyboard — default-first product and engineering brief

**Status:** Approved direction / implementation planned  
**Decision by:** Nik, 26 October 2026  
**Recorded by:** David  
**Purpose:** Turn Emogent into a **good, permanent Android keyboard first**. Make the independent personal expression protocol available from the keyboard once typing itself is trustworthy.

## 1. Decision and scope

> User direction (26 Oct 2026, paraphrased): Build a **standard, full Android keyboard** intended for users to select as their **permanent/default keyboard**. Keyboard quality comes first.

This supersedes the earlier proposal for a special-purpose, expression-only IME that users switch into temporarily. That earlier direction is retained here as historical context, **not the current product requirement**.

Emogent is a personal expression protocol for **one human and that human's own agents**, not a proposal for additions to universal Unicode emoji. The keyboard is an everyday distribution and interaction surface; it is not the source of truth for meaning or agent relationships. Future custom assets need not have Unicode code points.

The Linux emoji picker field note remains a distinct, personal ergonomics problem. Do not conflate it with this Android product.

## 2. Product promise / north star

**A keyboard worth keeping even when Emogent features are turned off.**

The user should be able to type, correct, select, navigate, and send ordinary text across everyday Android apps without wishing they still had Gboard or Samsung Keyboard. Expression is an added capability, not a tax on typing.

Scope initial release: Android; English QWERTY as initial complete baseline; layout and dictionary architecture designed for additional languages (prioritize Hindi and Marathi research rather than claiming support). Tablet/landscape, accessibility, multilingual typing, and gesture input should be explicitly tracked as product maturity requirements, not quietly omitted.

## 3. Delivery stages and gates

### Stage 0 — Repository and foundation audit (blocking architecture decision)
1. Inspect this repository and preserve its research/provenance records.
2. Inspect current Android IME platform guidance and at least: FlorisBoard (Apache-2.0) and HeliBoard (GPL-3.0). Consider AOSP LatinIME / other appropriate alternatives.
3. Evaluate: baseline typing quality, word suggestions/autocorrect, architecture extensibility, maintenance, builds on local tooling, upstream update burden, non-Latin roadmap, accessibility, license compatibility and attribution.
4. **Make a documented, justified selection;** do not assume a fork is legal, easy, or strategically desirable. FlorisBoard's README currently says word suggestions/spellcheck are not in current releases; HeliBoard has dictionaries and suggestions but its GPLv3 obligations matter. Check state again during implementation.
5. Decide Android application module location, package naming, supported API range, build and test setup. No binary blobs or closed-source glide libraries without rights review.

### Stage 1 — Installable, default-capable keyboard (functional skeleton)
- Native Android IME service via `InputMethodService`, correctly declared in manifest with input method metadata.
- Working onboarding that **guides the user** through enabling the IME in system settings, then selecting it as default using standard Android mechanisms. Never claim the app can silently change the default.
- Stable text insertion via `InputConnection`; cursor-aware backspace and selection handling; shift/caps lock; numbers, punctuation, symbols and long-press; space, enter and action-key semantics.
- Respect `EditorInfo.inputType` (normal text, email, URI, phone, numeric, password) and `imeOptions` (Done, Send, Search, Next). Secure/password fields must not be learned, cached, previewed or transmitted.
- Sensible layout sizing and touch targets, haptic settings, themes, keyboard switch control, one-handed and landscape consideration.
- Basic accessibility semantics and screen-reader operability.
- All text-entry behaviour works **entirely offline**.

### Stage 2 — Good-keyboard quality gate (must pass before calling default-ready)
- Low-latency reliable tap typing and repeatable long-press/backspace; undo/selection/cursor movement ergonomics.
- On-device dictionary suggestions, capitalization, autocorrection with undo, personal dictionary support and opt-in personalization.
- Languages/layout abstraction; English tested first, roadmap and test criteria for Hindi and Marathi, language switch UX.
- Punctuation/number layout quality; usable clipboard operations with privacy limits; dark/light high-contrast themes.
- Evaluate gesture/glide typing seriously. If not implementable safely/licensably immediately, flag as a visible parity gap; do **not** market as Gboard-equivalent.
- Handle device rotation, narrow screens, large font, accessibility text sizes, IME lifecycle, process recreation and app switching.
- Regression matrix: messaging, email, browsers, search, passwords, banking login form, OTP input, multiline editor, no-network conditions; physical Android device required prior to default-ready declaration.
- Measure startup/open latency, key-to-character latency, crash/ANR rate, correction quality, battery/CPU; record numbers and testing method rather than invent thresholds after the fact.
- Validate repeated day-long and multi-day actual use as the default keyboard, not just screenshots.

### Stage 3 — Emogent expression as an additional layer (separate release gate)
- Access original, agent-generated image/animation symbols and relationship-scoped lexicon from a tab or integrated entry surface without degrading typing.
- Stable asset IDs, author/creator attribution, provenance, version history, optional per-relationship meaning, and human approvals.
- Prefer local-first library, minimal permissions; opt-in synchronization with the Playroom and one-human/many-agent scope.
- On compatible receiving apps, insert rich content using `InputConnection.commitContent()` and `EditorInfo.contentMimeTypes` via safe content URI grants. **Receiver cooperation is required**; there is no guarantee that arbitrary images render inline in all apps.
- Fallbacks: image share, clipboard, and explicit reference where appropriate. Never silently replace an image with unrelated Unicode emoji.
- Test compatibility empirically with WhatsApp, Google Messages, ChatGPT and other actual targets; log accepted / attached / rejected behaviours by app/version.

## 4. Security, permissions, privacy and trust

A permanently enabled keyboard is exceptionally sensitive software. Treat it as a high-trust input surface.

- Default: local processing, no keystroke telemetry, no advertising SDK, no remote logging, no background message upload.
- The ordinary typing engine must operate offline; a future optional symbol-sync companion must be consented to and logically separated.
- Never capture/retain passwords, payment fields, OTPs or confidential input; do not submit raw typed content to an LLM or an agent.
- No accessibility-service workaround for typing, no system privilege bypass, no covert collection.
- No clipboard surveillance: user-initiated clipboard functionality, minimal retention, visible clearing controls.
- Transparent privacy explanation and reproducible builds where feasible.
- Review dependencies, source license, imported dictionaries and fonts. If a GPL-derived keyboard is forked, plan the corresponding source-distribution/notice obligations before distributing it.
- Keep human authorization for agent-driven changes to symbol lexicons and for any outbound network integrations.

## 5. Architecture direction (subject to Stage 0 decision)

```text
Android IME surface (default keyboard)
    ├── Core typing engine (offline, reliable, tested)
    ├── Layout / language / correction engine
    ├── Settings & onboarding
    ├── Local personal state (carefully scoped)
    └── Emogent expression adapter (later, optional)
           ├── Human–agent lexicon and permissions
           ├── Asset IDs + provenance + version records
           ├── On-device renderer & picker
           └── Destination-aware rich-content transport/fallback
```

Avoid coupling the core keyboard lifecycle to an agent, network, Playroom server, or evolving symbol model.

## 6. Definition of done and evaluation

**Prototype acceptance:** user can install APK, enable keyboard, make it default through Android settings, type reliably into at least a browser, messenger, email composer, search input, numeric input and password field, and return to another IME if desired. Build/install/run evidence included.

**Default-ready acceptance:** Stage 2 features and real-device matrix pass; usability and performance measures collected; no plaintext keystroke collection; user deliberately chooses to keep Emogent Keyboard enabled for normal daily typing. **No claim of production readiness before this gate.**

**Emogent-ready acceptance (later):** at least one non-Unicode custom visual traverses from personal library through keyboard to one verified compatible destination, with provenance intact and honest fallbacks elsewhere.

## 7. Codex starting instructions

See the parent GitHub issue and child tickets. Codex should inspect existing repo conventions, synchronize a local clone under `/home/nikhil/dev/Emogent` (or use an already-existing canonical checkout), document the base-keyboard choice, then **implement a runnable initial Android IME**, tests and a reproducible install procedure on an isolated feature branch. The work does **not** finish after writing an architecture plan.

Do not modify existing source records to make them fit the new architecture. Publish test results with device/API information and distinguish simulated checks from physical-device checks.

## 8. References (starting points, recheck before implementation)

- Android IME: https://developer.android.com/develop/ui/views/touch-and-input/creating-input-method
- Rich content keyboard: https://developer.android.com/develop/ui/views/touch-and-input/image-keyboard
- Inline autofill: https://developer.android.com/identity/autofill/ime-autofill
- FlorisBoard: https://github.com/florisboard/florisboard
- HeliBoard: https://github.com/HeliBorg/HeliBoard

---
**Provenance:** The default-first keyboard direction is Nik's explicit 26 Oct 2026 instruction. Stage gates, threat model and candidate architectures are David's proposed implementation plan, pending implementation evidence. This document does not claim a functioning keyboard exists.
