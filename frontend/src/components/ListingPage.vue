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
  rooms: 0,
  type: 'sale'
});

const handleFilterChange = (newFilters) => {
  activeFilters.value = newFilters;
  console.log(activeFilters.value)
};

const filteredListings = computed(() => {
  return listings.value.filter(listing => {
    const matchesSearch = activeFilters.value.search ? listing.property.address.toLowerCase().includes(activeFilters.value.search.toLowerCase()) : true
    const matchesPrice = listing.askingPrice <= activeFilters.value.price;
    const matchesRooms = listing.property.numberOfRooms >= activeFilters.value.rooms;
    const matchesType = listing.listingType === activeFilters.value.type
    return matchesSearch && matchesPrice && matchesRooms && matchesType;
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
</template>

<style scoped>
.page-header {
  margin-bottom: 1.5rem;
}
</style>
