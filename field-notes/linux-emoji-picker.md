# Field note — the Linux emoji picker problem

**Status:** open · **Scope:** Nik's personal problem, tracked here because this repo is where the list lives. Explicitly *separate* from Emogent's mission (the agent–human expression bridge), per Nik's instruction of 25 Oct 2026.

## The problem, in Nik's words

> "the app isnt as convineint as the win+. key on windows for a emoji pop-up and I am curious to see if we can do something about it"

On Windows, **Win + .** summons an emoji panel at the cursor, anywhere, and inserts the glyph in one motion. On Nik's Linux desktop, the current tool is **jome** (a GTK emoji browser) — see `jome-emoji-picker-on-linux.png` in this folder, captured 25 Oct 2026. It works, but as a destination you visit and copy from, not a reflex you fire mid-sentence: extra window management, extra paste step, weaker summon/insert flow.

## Observations from the screenshot

- jome's footer identifies its dataset as **Emoji 11.0 (June 2018)**. That predates 🫂 PEOPLE HUGGING (Emoji 13.0, 2020) and everything after — so on this machine, the picker doesn't merely lack a reaction-bank hug; **the hug does not exist in the picker's universe at all.** The governed-vocabulary problem, at OS layer. (This is also why the field note belongs in this repo despite being out of mission scope: it's Strand D evidence — a curation/defaults failure in the wild.)
- The category browser is thorough (smileys, cat faces, gestures, roles…), so the gap is summoning ergonomics and dataset currency, not coverage of the older set.

## Candidate remedies (to evaluate on the machine)

1. **Keep jome, fix the summon.** Bind it to a global shortcut (GNOME Settings → Keyboard → Custom Shortcuts, or KDE Custom Shortcuts; `jome` as the command, **Super + .** to mirror the Windows habit). Removes the launch friction; the copy-then-paste step remains.
2. **Emote** (GTK picker built to mimic the Win+. flow): summon by hotkey, search, select — and it *types* the emoji at the cursor rather than leaving it on the clipboard. Closest known analogue; available via Flathub/Snap on most distros.
3. **IBus emoji chooser** — many GNOME/IBus setups already bind emoji to **Super + .** or **Ctrl + . / Ctrl + ;** natively; worth checking whether it's enabled before installing anything.
4. **Launcher-style pickers** — `rofi` with the emoji mode/plugin, or `wofi`/`fuzzel` emoji scripts: fastest for keyboard-first flow, inserts via `wtype`/`xdotool type` depending on X11/Wayland.
5. **Update the substrate regardless:** install the current `fonts-noto-color-emoji` so rendering covers recent codepoints system-wide, and check for a newer jome build — an Emoji 11.0 dataset in late 2026 is a stale-master problem, same family as a subtitle file that drops a word.

## Next step

Evaluate on Nik's actual desktop (distro/desktop environment unconfirmed at filing time). Remedy 1 is a five-minute test; remedy 2 is the likely end-state if the Win+. reflex is the bar. Findings to be appended here.
