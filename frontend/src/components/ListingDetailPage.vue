<script setup>
import { ref, onMounted } from 'vue';
import { useRoute } from 'vue-router';
import axios from 'axios';
import ListingDetailHeader from './ListingDetailHeader.vue';
import ListingDetailSpecs from './ListingDetailSpecs.vue';
import ContactOwnerForm from './ContactOwnerForm.vue';

const route = useRoute();
const listing = ref(null);
const loading = ref(true);
const error = ref('');

onMounted(async () => {
  try {
    const response = await axios.get(`/api/listings/${route.params.id}`);
    listing.value = response.data;
  } catch {
    error.value = 'Nu am putut încărca detaliile anunțului.';
  } finally {
    loading.value = false;
  }
});
</script>

<template>
  <div v-if="loading" class="loading-state">Se încarcă...</div>
  <div v-else-if="error" class="error-state">{{ error }}</div>
  <div v-else class="detail-page">
    <ListingDetailHeader :listing="listing" />
    <div class="detail-body">
      <ListingDetailSpecs :listing="listing" />
      <ContactOwnerForm :listing-id="listing.id" />
    </div>
  </div>
</template>

<style scoped>
.loading-state,
.error-state {
  text-align: center;
  padding: 3rem;
  color: var(--color-text-muted);
}

.detail-body {
  display: grid;
  grid-template-columns: 1fr 1fr;
  gap: 1.5rem;
}

@media (max-width: 768px) {
  .detail-body {
    grid-template-columns: 1fr;
  }
}
</style>
