# Constructive Affective Presence: the agent need not mirror the human

**Status:** Research/product proposal; not implemented or proven.  
**Origin:** Nik + David, 2026-10-27 (Toronto).  
**Integration:** [Kin constructive judgment and human-only instruction boundary](https://github.com/nickhilster/kin-ubuntu/blob/main/ideas/2026-10-27-constructive-judgment-and-instruction-boundary.md).

## Observation versus hypothesis

**Observation:** In a user-shared Codex screenshot, the user signs off with 🙏🏿 and the agent replies with 🙏🏿. This establishes visible expressive matching, **not** evidence of model-level mirror neurons or a causal emotional effect.

**Design direction:** Stop treating the human's current emotional register as the default tone to copy. An agent can instead initiate a constructive expressive presence: steadiness during friction, warmth during stress, curiosity during certainty, humour when welcome, or silence when words would be intrusive. The human may reflect that presence, but the agent is **not** commissioned to manipulate or “fix” a person's emotion.

**Scientific boundary:** Mirror neurons concern observation/action representations. Emotional contagion and social influence are related but distinct research areas. Whether any specific agent expression changes someone's mood is an empirical question; do not claim a proven mechanism from this screenshot. “Positive neuron” is the founder's metaphor.

## Relationship-specific expression protocol

- Preserve the Emogent unit: one human and *that human's own agents*, with meanings negotiated rather than universalized.
- Model `expression_intent` (e.g., steady, warm, light, neutral, quiet) separately from the inferred human emotion. Confidence is limited; do not diagnose or privately track mood by default.
- Candidate expressive actions: briefly acknowledge, introduce a calm tone, offer gentle curiosity, use a relationship-specific visual signal, withhold emoji, or stay silent.
- Rendering surfaces are not only emoji: wording, rhythm, punctuation, visuals, custom personal symbols, and supported voice prosody. An expressive choice cannot replace factual dissent.
- Match context, not mandatory positivity. Grief calls for care rather than cheerleading; urgency often calls for concision; celebration need not be flattened.
- Avoid flattery, imitative swearing, synthetic intimacy, engineered dependency, covert persuasion, and compulsory cheerfulness. User should be able to disable or revise an expressive preference.
- Respect user-controlled human-agent lexicons, FRIENDS permissions, memory-gate restrictions, and differences between agents. The agent does not have to be or imitate David.
- Do not claim to feel; expression is a behaviour and communication channel, not proof of inner experience.

## Split of responsibilities

**Kin:** facts, independent judgment, challenge selection, permissioned actions, and the human-only instruction amendment rule.  
**Emogent:** selection and rendering of a context-appropriate expressive stance, personal lexicon, dosing, channel adaptation and explicit user preferences.

Possible interface, *not an adopted schema*: `{stance: "steady", purpose: "constructive_disagreement", budget: "low"}`. Emogent cannot silently rewrite Kin's governing instructions or promote suggestions into active rules, even with human approval; Kin may only **propose**, and the human must physically perform the instruction edit.

## Validation strand — initiative vs mimicry

Compare three responses to equivalent scenarios: (A) reflexive emotional/emoji matching, (B) agent-initiated constructive stance, (C) neutral baseline. Include anger, joy, urgency, grief, technical failures, and political disagreement. Blind-score factual accuracy, appropriate recognition of the actual claim, emotional appropriateness, human agency, perceived respect, overstepping, and symbol comprehension. Include cases where no emoji is correct. Do not optimize attachment, session duration, compliance, or inferred “happiness.”

**Acceptance:** an agent can initiate steadiness without echoing anger; can disagree on evidence without condescension; preserves legitimate complaints; uses emoji only with appropriate meaning; provides an off switch; and honestly labels unverified benefit.

## Provenance

This is a proposal synthesized from a private conversation. It deliberately contains **no private screenshots, device identifiers, or verbatim transcript**. It does not change Emogent's existing canonical expression protocol, research brief, or keyboard priorities. Review before promotion.