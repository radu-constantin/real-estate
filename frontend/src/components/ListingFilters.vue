<script setup>
import { ref, watch } from 'vue';

const emit = defineEmits(['filter-change']);

const searchQuery = ref('');
const maxPrice = ref(1000000);
const minBeds = ref(0);

watch([searchQuery, maxPrice, minBeds], () => {
  emit('filter-change', {
    search: searchQuery.value,
    price: maxPrice.value,
    beds: minBeds.value
  });
});
</script>

<template>
  <div class="filters-container">
    <div class="filter-group">
      <label for="search">Search Location</label>
      <input
        id="search"
        v-model="searchQuery"
        type="text"
        placeholder="e.g. Beverly Hills"
      />
    </div>

    <div class="filter-group">
      <label for="price">Max Price: ${{ maxPrice.toLocaleString() }}</label>
      <input
        id="price"
        v-model.number="maxPrice"
        type="range"
        min="100000"
        max="2000000"
        step="50000"
      />
    </div>

    <div class="filter-group">
      <label for="beds">Min Bedrooms</label>
      <select id="beds" v-model.number="minBeds">
        <option :value="0">Any</option>
        <option v-for="n in 5" :key="n" :value="n">{{ n }}+ Beds</option>
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
