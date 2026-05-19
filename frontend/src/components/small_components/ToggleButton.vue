<template>
  <div class="toggle-container" :class="{ 'is-second': modelValue === valueTwo }">
    <span class="sliding-pill" aria-hidden="true" />

    <label class="toggle-option">
      <input
        type="radio"
        :name="name"
        :value="valueOne"
        :checked="modelValue === valueOne"
        @change="$emit('update:modelValue', valueOne)"
        class="hidden-radio"
      />
      <span class="toggle-button-label">{{ labelOne }}</span>
    </label>

    <label class="toggle-option">
      <input
        type="radio"
        :name="name"
        :value="valueTwo"
        :checked="modelValue === valueTwo"
        @change="$emit('update:modelValue', valueTwo)"
        class="hidden-radio"
      />
      <span class="toggle-button-label">{{ labelTwo }}</span>
    </label>
  </div>
</template>

<script setup>
defineProps({
  modelValue: { type: String, required: true },
  name:       { type: String, default: 'state-toggle' },
  labelOne:   { type: String, default: 'Vânzare' },
  labelTwo:   { type: String, default: 'Închiriere' },
  valueOne:   { type: String, default: 'sale' },
  valueTwo:   { type: String, default: 'rental' }
})

defineEmits(['update:modelValue'])
</script>

<style scoped>
.toggle-container {
  position: relative; /* anchor for the pill */
  display: inline-flex;
  background-color: #f1f5f9;
  padding: 4px;
  border-radius: 9999px;
  border: 1px solid #e2e8f0;
  user-select: none;
  width: fit-content;
}

/* ── Sliding pill ───────────────────────────────────────────── */
.sliding-pill {
  position: absolute;
  top: 4px;
  left: 4px;
  bottom: 4px;
  width: calc(50% - 4px);
  background-color: #ffffff;
  border-radius: 9999px;
  box-shadow: 0 2px 6px rgba(0, 0, 0, 0.08), 0 1px 2px rgba(0, 0, 0, 0.04);
  transition: transform 0.3s cubic-bezier(0.34, 1.56, 0.64, 1); /* ← spring */
  pointer-events: none;
}

.toggle-container.is-second .sliding-pill {
  transform: translateX(100%);
}

/* ── Labels ─────────────────────────────────────────────────── */
.toggle-option {
  flex: 1;                  /* fills exactly half the container */
  display: flex;
  justify-content: center;  /* centers label text over the pill */
  position: relative;
  z-index: 1;
  cursor: pointer;
}

.hidden-radio {
  position: absolute;
  opacity: 0;
  width: 0;
  height: 0;
}

.toggle-button-label {
  display: inline-block;
  min-width: 100px;
  text-align: center;
  padding: 8px 24px;
  font-size: 14px;
  font-weight: 600;
  color: #64748b;
  border-radius: 9999px;
  transition: color 0.3s cubic-bezier(0.34, 1.56, 0.64, 1); /* ← spring */
}

.toggle-option:hover .toggle-button-label {
  color: #334155;
}

/* Only animate the text colour now — the pill handles the background */
.hidden-radio:checked + .toggle-button-label {
  color: #2c3e50;
}

.hidden-radio:focus-visible + .toggle-button-label {
  outline: 2px solid #2c3e50;
  outline-offset: 2px;
}
</style>
