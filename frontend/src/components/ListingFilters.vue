<script setup>
import { ref, watch } from 'vue';

const emit = defineEmits(['filter-change']);

const searchQuery = ref('');
const maxPrice = ref(300000);
const minRooms = ref(0);

watch([searchQuery, maxPrice, minRooms], () => {
  emit('filter-change', {
    search: searchQuery.value,
    price: maxPrice.value,
    rooms: minRooms.value
  });
});
</script>

<template>
  <div class="filters-container">
    <div class="filter-group">
      <label for="search">Locație</label>
      <input
        id="search"
        v-model="searchQuery"
        type="text"
        placeholder="e.g. București"
      />
    </div>

    <div class="filter-group">
      <label for="price">Preț maxim: €{{ maxPrice.toLocaleString() }}</label>
      <input
        id="price"
        v-model.number="maxPrice"
        type="range"
        min="10000"
        max="1000000"
        step="5000"
      />
    </div>

    <div class="filter-group">
      <label for="rooms">Număr camere</label>
      <select id="rooms" v-model.number="minRooms">
        <option :value="0">Any</option>
        <option v-for="n in 5" :key="n" :value="n">{{ n }} +</option>
      </select>
    </div>
  </div>
</template>

<style scoped>
.filters-container {
  display: flex;
  gap: 2rem;
  background: white;
  padding: 1.5rem;
  border-radius: 8px;
  border: 1px solid var(--color-border);
  margin-bottom: 2rem;
  align-items: flex-end;
}

.filter-group {
  display: flex;
  flex-direction: column;
  gap: 0.5rem;
  flex: 1;
}

label {
  font-size: 0.85rem;
  font-weight: bold;
  color: var(--color-text-muted);
}

input[type="text"], select {
  padding: 0.6rem;
  border: 1px solid var(--color-border);
  border-radius: 4px;
}

input[type="range"] {
  cursor: pointer;
}
</style>
