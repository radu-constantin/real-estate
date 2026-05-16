<template>
  <button
    :class="['cta-btn', { 'cta-btn--loading': loading, 'cta-btn--ghost': ghost }]"
    :disabled="loading || disabled"
    @click="handleClick"
    v-bind="$attrs"
  >
    <span class="cta-btn__track" aria-hidden="true" />

    <span class="cta-btn__content">
      <span v-if="icon" class="cta-btn__icon-left">
        <slot name="icon">
          <svg
            xmlns="http://www.w3.org/2000/svg"
            width="16"
            height="16"
            viewBox="0 0 24 24"
            fill="none"
            stroke="currentColor"
            stroke-width="2.5"
            stroke-linecap="round"
            stroke-linejoin="round"
            aria-hidden="true"
          >
            <path :d="icon" />
          </svg>
        </slot>
      </span>

      <span class="cta-btn__label">
        <slot>{{ label }}</slot>
      </span>

      <span class="cta-btn__arrow" aria-hidden="true">
        <svg
          xmlns="http://www.w3.org/2000/svg"
          width="14"
          height="14"
          viewBox="0 0 24 24"
          fill="none"
          stroke="currentColor"
          stroke-width="2.5"
          stroke-linecap="round"
          stroke-linejoin="round"
        >
          <path d="M5 12h14M13 6l6 6-6 6" />
        </svg>
      </span>
    </span>

    <span v-if="loading" class="cta-btn__spinner" aria-label="Loading…">
      <svg
        xmlns="http://www.w3.org/2000/svg"
        width="16"
        height="16"
        viewBox="0 0 24 24"
        fill="none"
        stroke="currentColor"
        stroke-width="2.5"
        stroke-linecap="round"
        stroke-linejoin="round"
      >
        <path d="M21 12a9 9 0 1 1-6.219-8.56" />
      </svg>
    </span>
  </button>
</template>

<script setup>
import { defineProps, defineEmits } from 'vue'

const props = defineProps({
  label: {
    type: String,
    default: 'Get started',
  },
  icon: {
    type: String,
    default: null,
  },
  loading: {
    type: Boolean,
    default: false,
  },
  disabled: {
    type: Boolean,
    default: false,
  },
  ghost: {
    type: Boolean,
    default: false,
  },
})

const emit = defineEmits(['click'])

function handleClick(event) {
  if (props.loading || props.disabled) return
  emit('click', event)
}
</script>

<style scoped>
/* ── Design tokens ────────────────────────────────────────────── */
.cta-btn {
  --cta-font: 'DM Sans', 'Helvetica Neue', sans-serif;
  --cta-bg: #0f172a;
  --cta-bg-hover: #1e293b;
  --cta-accent: #38bdf8;
  --cta-text: #f8fafc;
  --cta-border: transparent;
  --cta-radius: 9999px;
  --cta-height: 40px;
  --cta-px: 20px;
  --cta-gap: 7px;
  --cta-fs: 0.875rem;
  --cta-fw: 550;
  --cta-letter: 0.01em;
  --cta-transition: 220ms cubic-bezier(0.4, 0, 0.2, 1);

  /* ghost variant */
  --cta-ghost-bg: transparent;
  --cta-ghost-border: #0f172a;
  --cta-ghost-text: #0f172a;
  --cta-ghost-bg-hover: #0f172a0d;
}

/* Dark scheme adjustments */
@media (prefers-color-scheme: dark) {
  .cta-btn {
    --cta-bg: #e2e8f0;
    --cta-bg-hover: #f8fafc;
    --cta-accent: #0ea5e9;
    --cta-text: #0f172a;
    --cta-ghost-border: #e2e8f0;
    --cta-ghost-text: #e2e8f0;
    --cta-ghost-bg-hover: #e2e8f014;
  }
}

/* ── Base ─────────────────────────────────────────────────────── */
.cta-btn {
  position: relative;
  display: inline-flex;
  align-items: center;
  justify-content: center;
  height: var(--cta-height);
  padding: 0 var(--cta-px);
  background: var(--cta-bg);
  color: var(--cta-text);
  border: 1.5px solid var(--cta-border);
  border-radius: var(--cta-radius);
  font-family: var(--cta-font);
  font-size: var(--cta-fs);
  font-weight: var(--cta-fw);
  letter-spacing: var(--cta-letter);
  white-space: nowrap;
  cursor: pointer;
  overflow: hidden;
  outline: none;
  user-select: none;
  -webkit-tap-highlight-color: transparent;
  transition:
    background var(--cta-transition),
    color var(--cta-transition),
    border-color var(--cta-transition),
    transform 120ms ease,
    box-shadow var(--cta-transition);
}

/* ── Shimmer track ────────────────────────────────────────────── */
.cta-btn__track {
  position: absolute;
  inset: 0;
  border-radius: inherit;
  background: linear-gradient(
    90deg,
    transparent 0%,
    rgba(255, 255, 255, 0.08) 50%,
    transparent 100%
  );
  background-size: 200% 100%;
  opacity: 0;
  transition: opacity var(--cta-transition);
}

.cta-btn:hover .cta-btn__track {
  opacity: 1;
  animation: cta-shimmer 1.4s linear infinite;
}

@keyframes cta-shimmer {
  from { background-position: 200% 0; }
  to   { background-position: -200% 0; }
}

/* ── Content row ──────────────────────────────────────────────── */
.cta-btn__content {
  position: relative;
  display: inline-flex;
  align-items: center;
  gap: var(--cta-gap);
}

/* ── Arrow nudge on hover ─────────────────────────────────────── */
.cta-btn__arrow {
  display: inline-flex;
  align-items: center;
  transform: translateX(0);
  transition: transform var(--cta-transition);
}

.cta-btn:hover:not(:disabled) .cta-btn__arrow {
  transform: translateX(3px);
}

/* ── Accent dot on label ──────────────────────────────────────── */
.cta-btn__label {
  position: relative;
}

.cta-btn__label::after {
  content: '';
  position: absolute;
  bottom: -1px;
  left: 0;
  width: 0;
  height: 1.5px;
  background: var(--cta-accent);
  border-radius: 9999px;
  transition: width var(--cta-transition);
}

.cta-btn:hover:not(:disabled) .cta-btn__label::after {
  width: 100%;
}

/* ── Left icon ────────────────────────────────────────────────── */
.cta-btn__icon-left {
  display: inline-flex;
  align-items: center;
  opacity: 0.75;
}

/* ── States ───────────────────────────────────────────────────── */
.cta-btn:hover:not(:disabled) {
  background: var(--cta-bg-hover);
}

.cta-btn:focus-visible {
  box-shadow: 0 0 0 3px rgba(56, 189, 248, 0.45);
}

.cta-btn:active:not(:disabled) {
  transform: scale(0.96);
}

.cta-btn:disabled {
  opacity: 0.45;
  cursor: not-allowed;
}

/* ── Ghost variant ────────────────────────────────────────────── */
.cta-btn--ghost {
  background: var(--cta-ghost-bg) !important;
  border-color: var(--cta-ghost-border);
  color: var(--cta-ghost-text);
}

.cta-btn--ghost .cta-btn__track {
  display: none;
}

.cta-btn--ghost:hover:not(:disabled) {
  background: var(--cta-ghost-bg-hover) !important;
}

/* ── Loading state ────────────────────────────────────────────── */
.cta-btn--loading .cta-btn__content {
  opacity: 0;
}

.cta-btn__spinner {
  position: absolute;
  inset: 0;
  display: flex;
  align-items: center;
  justify-content: center;
  animation: cta-spin 0.75s linear infinite;
}

@keyframes cta-spin {
  to { transform: rotate(360deg); }
}
</style>
