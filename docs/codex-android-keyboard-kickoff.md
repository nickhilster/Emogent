# Codex Mission — Build Emogent Keyboard (Android)

**From:** Nik, Teambotics  
**To:** Codex  
**Date:** 26 October 2026  
**Target repository:** https://github.com/nickhilster/Emogent  
**Local workspace:** `/home/nikhil/dev`  
**First issues:** [#4](https://github.com/nickhilster/Emogent/issues/4), [#5](https://github.com/nickhilster/Emogent/issues/5); parent [#3](https://github.com/nickhilster/Emogent/issues/3).

## Mission

Build an **actual, installable Android keyboard** that can become the user's **permanent/default keyboard**. It must be a good everyday keyboard **before** Emogent expression features are added. Do NOT produce an emoji-only keyboard, a floating emoji picker, or merely a mockup.

The philosophy: **A keyboard worth keeping when every Emogent feature is disabled.** The special expression layer comes afterward.

## First: inspect, synchronize, preserve

1. Inspect the existing local `/home/nikhil/dev` directory. Find an existing Emogent checkout by checking its Git remote, not only its folder name. If none exists, clone `https://github.com/nickhilster/Emogent` into the appropriate location under that directory.
2. Check `git status`, current branch, remotes, uncommitted changes, and any repo/ancestor `AGENTS.md` or house conventions. Never overwrite local edits or reset the branch to make a clean workspace. Safely reconcile remote `main` with local state; if dirty or diverged, preserve the work and use a separate worktree/branch.
3. Read `README.md`, `docs/study-brief.md`, `docs/the-blindspot-is-the-data.md`, `field-notes/linux-emoji-picker.md`, and **`docs/android-default-keyboard.md`**. The research/provenance files are canonical records; don't rewrite their historical claims to match the new product.
4. Read all open keyboard issues, especially #3–#12, and avoid duplicating already present implementation.

## Build strategy — do not reinvent good typing needlessly

**Gate 0 (brief but real):** Compare current FlorisBoard, HeliBoard and AOSP LatinIME / other suitable engines. Check their licenses in source, current correction/suggestion quality, maintainability, gradle/runtime compat, keyboard usability, localization and extension paths. Note FlorisBoard may not yet ship suggestions. HeliBoard is GPLv3; adopting a GPL-derived base has product and redistribution implications. Do not bring in any proprietary glide binaries or copyrighted assets without appropriate rights.

Record the technical choice in `docs/android-keyboard-foundation-decision.md`. If a GPL-derived product would commit Teambotics to material copyleft obligations, flag that decision for Nik and do not silently lock it in. While that decision is pending, continue implementing an independently licensed IME skeleton, without cloning copyleft source into the product.

Then **write working Android code**, not just docs.

## Initial build scope

Use Kotlin and normal Android project conventions unless the foundation audit strongly justifies another stack.

Implement:
- Android application + `InputMethodService`, correctly registered manifest and `res/xml/method.xml` (or project equivalent).
- Installable/debuggable application, settings/activity to guide user through Android's explicit enable-and-select-default flows, plus an easy switch-to-other-IME control. The app cannot silently install itself as the default keyboard.
- Core QWERTY letter entry; shift/caps lock; delete/hold delete; space; enter; number/symbol pages; punctuation; long-press alternates; key repeat; sensible phone touch targets and layouts.
- Correct `InputConnection` operations and `EditorInfo.inputType/imeOptions` handling. Provide appropriate layouts/actions for email, URLs, numbers, phone, multiline, password fields and search/send/done/next.
- No keystroke logging, no analytics, no sending input to an LLM or agent, and no network required to type. Password/OTP fields must not feed suggestions, clipboard, learning, logs or preview caches.
- Working build and test instructions; unit/UI checks and emulator smoke tests where tooling exists.
- Clean module boundaries for later suggestions/autocorrect, languages, privacy/learning controls and Emogent's own image/symbol assets. Do not couple typing lifecycle to network or an agent.

As time allows after the working skeleton, advance suggestions/correction, editing ergonomics and accessibility under #6–#9; add tests with every increment. A good default keyboard ultimately needs predictive suggestions, autocorrect, easy cursor/selection control, multilingual readiness, reliability and responsiveness. Don't declare completion just because letters type.

## Not part of the first milestone

- No sticker-only release.
- No mandatory Playroom login, agent authorization, cloud inference, external APIs or image syncing.
- No custom non-Unicode Emogent asset UI until base typing works.
- No claims that images inserted by a keyboard render in every third-party app. Rich content needs the receiving app's support.

## Project workflow

1. Create a scoped feature branch, e.g. `feature/android-default-keyboard`, from a safe synced state.
2. Add/update `android/` (or justified equivalent), docs, tests, CI if sensible. Preserve existing materials.
3. Build with `./gradlew assembleDebug` or the actual project entry point. Report exact pass/fail and versions. If Android SDK/emulator/device isn't available, distinguish implementation and static checks from a verified build; do not invent a successful build.
4. Test IME selection and input against emulator or physical device when available; record API version and target apps. Capture screenshots/logs only if they contain **no personal typed content**.
5. Commit work to the feature branch and push if authorized/available. Open a draft PR against `main`, referencing the epic and child issues. Don't merge or publish an APK/store listing without Nik's review.
6. Update issue progress with evidence and any blockers.

## Report to Nik

Return: (a) local repo path and branch; (b) foundation selection, alternatives and license implications; (c) files changed; (d) build/test results with real evidence; (e) what's implemented vs still missing from daily-driver quality; (f) next concrete step; (g) commit and PR links if created.

**Success for this first execution:** a repo-aligned Android IME codebase with a real buildable typing prototype and a credible path to becoming someone's full-time keyboard. Prefer working code plus explicit gaps over a grand design with no running keyboard.

**Source of truth:** `docs/android-default-keyboard.md` and [epic #3](https://github.com/nickhilster/Emogent/issues/3). Product intent belongs to Nik. Preserve provenance. ✊🏿
