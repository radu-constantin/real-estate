<script setup>
import { ref, computed, onMounted } from 'vue';
import { useRoute } from 'vue-router';
import api from "@/api/axios.js";
import { useAuthStore } from '@/stores/auth';
import ListingDetailHeader from './ListingDetailHeader.vue';
import ListingDetailSpecs from './ListingDetailSpecs.vue';
import ContactOwnerForm from './ContactOwnerForm.vue';

const route = useRoute();
const authStore = useAuthStore();
const listing = ref(null);
const loading = ref(true);
const error = ref('');

const isOwner = computed(() =>
  listing.value && authStore.user?.id === listing.value.property?.user?.id
);

onMounted(async () => {
  try {
    const response = await api.get(`/listings/${route.params.id}`);
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
      <ContactOwnerForm v-if="!isOwner" :listing-id="listing.id" />
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
