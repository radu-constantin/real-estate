<script setup>
import ListingItem from './ListingItem.vue';

const props = defineProps({
  listings: {
    type: Array,
    required: true
  }
});
</script>

<template>
  <section class="property-list-container">
    <div v-if="listings.length > 0" class="list-stack">
      <ListingItem
        v-for="item in listings"
        :key="item.id"
        :listing="item"
      />
    </div>

    <div v-else class="no-results">
      <p>No listings found matching your criteria.</p>
    </div>
  </section>
</template>

<style scoped>
.property-list-container {
  width: 100%;
}

.list-stack {
  display: flex;
  flex-direction: column;
  gap: 1.5rem; /* Space between each property row */
}

.no-results {
  padding: 4rem;
  text-align: center;
  background-color: var(--color-bg-light);
  border-radius: 8px;
  border: 2px dashed var(--color-border);
  color: var(--color-text-muted);
}
</style>
