<script setup>
import {ref, computed, onMounted} from 'vue';
import ListingFilters from './ListingFilters.vue';
import ListingList from './ListingList.vue';
import axios from "axios";

const listings = ref([])

onMounted(async () => {
  const response = await axios.get('/api/listings')
  listings.value = response.data
})

const activeFilters = ref({
  search: '',
  price: 2000000,
  rooms: 0
});

const handleFilterChange = (newFilters) => {
  activeFilters.value = newFilters;
};

const filteredListings = computed(() => {

  return listings.value.filter(listing => {
    const matchesSearch = activeFilters.value.search ? listing.property.address.toLowerCase().includes(activeFilters.value.search.toLowerCase()) : true
    const matchesPrice = listing.askingPrice <= activeFilters.value.price;
    const matchesRooms = listing.property.numberOfRooms >= activeFilters.value.rooms;
    console.log({
      propertyRooms: listing.property.numberOfRooms,
      filterRooms: activeFilters.value.rooms
    })
    return matchesSearch && matchesPrice && matchesRooms;
  });
});
</script>

<template>
  <div class="page-header">
    <h2>Proprietăți disponibile</h2>
    <p>{{ filteredListings.length }} rezultate găsite</p>
  </div>

  <ListingFilters @filter-change="handleFilterChange"/>

  <ListingList :listings="filteredListings"/>

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
