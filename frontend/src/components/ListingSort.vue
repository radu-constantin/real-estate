<script setup>
import { ref } from 'vue'

const emit = defineEmits(['sort-change'])

const sortBy = ref('id')
const sortDir = ref('asc')

const handleSortByChange = () => {
  emit('sort-change', { sortBy: sortBy.value, sortDir: sortDir.value })
}

const toggleDir = () => {
  sortDir.value = sortDir.value === 'asc' ? 'desc' : 'asc'
  emit('sort-change', { sortBy: sortBy.value, sortDir: sortDir.value })
}
</script>

<template>
  <div class="sort-bar">
    <span class="sort-label">Sortează după:</span>
    <select v-model="sortBy" @change="handleSortByChange" class="sort-select">
      <option value="id">Implicit</option>
      <option value="price">Preț</option>
      <option value="numberOfRooms">Camere</option>
    </select>
    <button @click="toggleDir" class="sort-dir-btn">
      {{ sortDir === 'asc' ? '↑ Crescător' : '↓ Descrescător' }}
    </button>
  </div>
</template>

<style scoped>
.sort-bar {
  display: flex;
  align-items: center;
  gap: 0.75rem;
  margin-bottom: 1rem;
}

.sort-label {
  font-size: 0.9rem;
  color: var(--color-text-muted);
  white-space: nowrap;
}

.sort-select {
  padding: 0.4rem 0.75rem;
  border: 1px solid var(--color-border);
  border-radius: 4px;
  background: white;
  font-size: 0.9rem;
  color: var(--color-text-main);
  cursor: pointer;
  height: 35px;
}

.sort-dir-btn {
  padding: 0.4rem 0.75rem;
  border: 1px solid var(--color-border);
  border-radius: 4px;
  background: white;
  font-size: 0.9rem;
  color: var(--color-text-main);
  cursor: pointer;
  transition: background 0.15s, border-color 0.15s;
  height: 35px;
}

.sort-dir-btn:hover {
  background: var(--color-bg-light);
  border-color: #aaa;
}
</style>
