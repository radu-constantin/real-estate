<script setup>
import {computed} from 'vue';

const props = defineProps({
  listing: {type: Object, required: true}
});

const property = computed(() => props.listing.property);

const constructionYear = computed(() => {
  if (!property.value.dateOfConstruction) return '-';
  return new Date(property.value.dateOfConstruction).getFullYear();
});
</script>

<template>
  <div class="specs-card">
    <h4 class="card-title">Detalii proprietate</h4>
    <div class="specs-grid">
      <div class="spec-item">
        <span class="spec-label">Camere</span>
        <span class="spec-value">{{ property.numberOfRooms }}</span>
      </div>
      <div class="spec-item">
        <span class="spec-label">Suprafață utilă</span>
        <span class="spec-value">{{ property.floorArea }} m²</span>
      </div>
      <div class="spec-item">
        <span class="spec-label">Tip proprietate</span>
        <span class="spec-value">{{
            property.propertyType === 'house' ? 'Casă' : 'Apartament'
          }}</span>
      </div>
      <div class="spec-item">
        <span class="spec-label">Construit în</span>
        <span class="spec-value">{{ constructionYear }}</span>
      </div>
      <template v-if="property.propertyType === 'house'">
        <div class="spec-item">
          <span class="spec-label">Etaje</span>
          <span class="spec-value">{{ property.numberOfFloors }}</span>
        </div>
        <div class="spec-item">
          <span class="spec-label">Suprafață teren</span>
          <span class="spec-value">{{ property.plotArea }} m²</span>
        </div>
      </template>
      <template v-if="property.propertyType === 'apartment'">
        <div class="spec-item">
          <span class="spec-label">Etaj</span>
          <span class="spec-value">{{ property.floorNumber }}</span>
        </div>
      </template>
    </div>
    <div class="specs-grid">
      <div class="spec-item" style="grid-column: span 2;">
        <span class="spec-label">Descriere</span>
        <span class="spec-value">{{ listing.description }}</span>
      </div>
    </div>
  </div>
</template>

<style scoped>
.specs-card {
  border: 1px solid var(--color-border);
  border-radius: 8px;
  background: white;
  padding: 1.5rem;
}

.card-title {
  margin: 0 0 1.25rem;
  color: var(--color-text-main);
  font-size: 1rem;
  text-transform: uppercase;
  letter-spacing: 0.05em;
}

.specs-grid {
  display: grid;
  grid-template-columns: 1fr 1fr;
  gap: 1rem;
}

.spec-item {
  display: flex;
  flex-direction: column;
  gap: 0.2rem;
}

.spec-label {
  font-size: 0.8rem;
  color: var(--color-text-muted);
  text-transform: uppercase;
  letter-spacing: 0.04em;
}

.spec-value {
  font-size: 1rem;
  font-weight: 600;
  color: var(--color-text-main);
}
</style>
