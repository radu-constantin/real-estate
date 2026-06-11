<script setup>
import { ref, computed, onMounted } from 'vue';
import ListingFilters from './ListingFilters.vue';
import ListingList from './ListingList.vue';
import api from "@/api/axios.js";
import ListingSort from './ListingSort.vue'

const activeSort = ref({ sortBy: 'id', sortDir: 'asc' })
const listings = ref([])
const currentPage = ref(0)
const totalPages = ref(0)
const totalElements = ref(0)
const PAGE_SIZE = 10

const activeFilters = ref({
  search: '',
  price: 2000000,
  rooms: 0,
  type: 'sale'
});

const fetchListings = async () => {
  const params = {
    page: currentPage.value,
    size: PAGE_SIZE,
    listingType: activeFilters.value.type,
    maxPrice: activeFilters.value.price,
    sortBy: activeSort.value.sortBy,
    sortDir: activeSort.value.sortDir,
  }
  if (activeFilters.value.search) params.address = activeFilters.value.search
  if (activeFilters.value.rooms > 0) params.minRooms = activeFilters.value.rooms

  const response = await api.get('/listings', { params })
  listings.value = response.data.content
  totalPages.value = response.data.totalPages
  totalElements.value = response.data.totalElements
}

const handleSortChange = (newSort) => {
  activeSort.value = newSort
  currentPage.value = 0
  fetchListings()
}

const handleFilterChange = (newFilters) => {
  activeFilters.value = newFilters
  currentPage.value = 0
  fetchListings()
}

const goToPage = (page) => {
  if (page >= 0 && page < totalPages.value) {
    currentPage.value = page
    fetchListings()
  }
}

const visiblePages = computed(() => {
  const total = totalPages.value
  const current = currentPage.value

  if (total <= 7) return Array.from({ length: total }, (_, i) => i)

  const pages = [0]
  const start = Math.max(1, current - 2)
  const end = Math.min(total - 2, current + 2)

  if (start > 1) pages.push('...')
  for (let i = start; i <= end; i++) pages.push(i)
  if (end < total - 2) pages.push('...')
  pages.push(total - 1)

  return pages
})

onMounted(fetchListings)
</script>

<template>
  <div class="page-header">
    <h2>Proprietăți disponibile</h2>
    <p>{{ totalElements }} rezultate găsite</p>
  </div>

  <ListingFilters @filter-change="handleFilterChange"/>
  <ListingSort @sort-change="handleSortChange"/>
  <ListingList :listings="listings"/>

  <div class="pagination" v-if="totalPages > 1">
    <button @click="goToPage(currentPage - 1)" :disabled="currentPage === 0">‹</button>
    <template v-for="(page, index) in visiblePages" :key="index">
      <span v-if="page === '...'" class="ellipsis">...</span>
      <button
        v-else
        @click="goToPage(page)"
        :class="{ active: currentPage === page }"
      >
        {{ page + 1 }}
      </button>
    </template>
    <button @click="goToPage(currentPage + 1)" :disabled="currentPage === totalPages - 1">›</button>
  </div>
</template>

<style scoped>
.page-header {
  margin-bottom: 1.5rem;
}

.pagination {
  display: flex;
  gap: 0.5rem;
  justify-content: center;
  margin-top: 2rem;
}

.pagination button {
  padding: 0.4rem 0.75rem;
  border: 1px solid #ccc;
  background: transparent;
  cursor: pointer;
  border-radius: 4px;
}

.pagination button.active {
  background: #1a3c5e;
  color: white;
  border-color: transparent;
}

.pagination button:disabled {
  opacity: 0.4;
  cursor: not-allowed;
}

.ellipsis {
  padding: 0.4rem 0.5rem;
  color: #666;
}
</style>
