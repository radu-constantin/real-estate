<script setup>
import {ref, computed, onMounted} from 'vue';
import ListingFilters from './ListingFilters.vue';
import ListingList from './ListingList.vue';
import axios from "axios";

const properties = ref([])

onMounted(async () => {
  const response = await axios.get('/api/listings')
  properties.value = response.data
})

// const allProperties = ref([
//   { id: 1, title: 'Modern Villa', price: 850000, location: 'Los Angeles, CA', beds: 3, baths: 2, imageUrl: '/path/to/img.jpg' },
//   { id: 2, title: 'Cozy Cottage', price: 450000, location: 'Austin, TX', beds: 2, baths: 1, imageUrl: '/path/to/img.jpg' },
// ]);

const activeFilters = ref({
  search: '',
  price: 2000000,
  beds: 0
});

const handleFilterChange = (newFilters) => {
  activeFilters.value = newFilters;
};

const filteredListings = computed(() => {
  return properties.value;
  // return properties.value.filter(p => {
  //   const matchesSearch = p.location.toLowerCase().includes(activeFilters.value.search.toLowerCase()) ||
  //     p.title.toLowerCase().includes(activeFilters.value.search.toLowerCase());
  //   const matchesPrice = p.price <= activeFilters.value.price;
  //   const matchesBeds = p.beds >= activeFilters.value.beds;
  //
  //   return matchesSearch && matchesPrice && matchesBeds;
  // });
});
</script>

<template>
    <div class="page-header">
      <h2>Proprietăți disponibile</h2>
      <p>{{ filteredListings.length }} rezultate găsite</p>
    </div>

    <ListingFilters @filter-change="handleFilterChange" />

    <ListingList :listings="filteredListings" />

    <div v-if="filteredListings.length === 0" class="empty-state">
      <p>Nu sunt proprietăți conform criteriilor de căutare. Ajustează filtrele!</p>
    </div>
</template>

<style scoped>
.page-header {
  margin-bottom: 1.5rem;
}
.empty-state {
  text-align: center;
  padding: 3rem;
  color: var(--color-text-muted);
}
</style>
