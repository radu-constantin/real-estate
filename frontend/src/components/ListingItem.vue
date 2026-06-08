<script setup>
import {computed} from 'vue';
import {useRouter} from 'vue-router';
import {capitalize} from "lodash";

const props = defineProps({
  listing: {
    type: Object,
    required: true
  },
  showEdit: {
    type: Boolean,
    default: false
  },
  showDelete: {
    type: Boolean,
    default: false
  }
});

const emit = defineEmits(['delete']);

const router = useRouter();

const goToDetail = () => router.push(`/listings/${props.listing.id}`);

const property = props.listing.property;

const propertyPhotos = property?.photos;

const hasPhotos = computed(() => propertyPhotos && propertyPhotos.length > 0);

const placeholderImage = 'https://placehold.co/600x400/png';

const formattedPrice = computed(() => {
  return new Intl.NumberFormat('en-US', {
    style: 'currency',
    currency: 'EUR',
    maximumFractionDigits: 0,
  }).format(props.listing?.askingPrice || props.listing?.monthlyRent);
});
</script>

<template>
  <article class="listing-row">
    <div class="image-section">
      <img :src="hasPhotos ? propertyPhotos[0].url : placeholderImage" alt="House photo"
           class="listing-image"/>
    </div>

    <div class="info-section">
      <div class="header-info">
        <div class="property-details">
          <h3 class="price">{{ formattedPrice }}</h3>
          <h4 class="title">{{ property.address }}</h4>
        </div>
        <div class="type-status">
          <h3 class="type">{{ capitalize(property.propertyType) }}</h3>
          <span v-if="listing.status" class="status-badge" :class="listing.status">{{ listing.status }}</span>
        </div>
      </div>

      <div class="specs-row">
        <div class="spec-item"><strong>{{ property.numberOfRooms }}</strong> camere</div>
        <div class="spec-item"><strong>{{ property.floorArea }}</strong> m<sup>2</sup></div>

        <div class="spec-item" v-if="property.propertyType === 'house'">
          <strong>{{ property.numberOfFloors }}</strong> etaje
        </div>
        <div class="spec-item" v-if="property.propertyType === 'house'"><strong>{{
            property.plotArea
          }}</strong> m<sup>2</sup> teren
        </div>

        <div class="spec-item" v-if="property.propertyType === 'apartment'"><strong>etajul
          {{ property.floorNumber }}</strong></div>

      </div>
    </div>

    <div class="action-section">
      <button class="view-btn" @click="goToDetail">Vezi detalii</button>
      <button v-if="showEdit" class="edit-btn" @click="router.push(`/listings/${listing.id}/edit`)">
        Editează
      </button>
      <button v-if="showDelete" class="delete-btn" @click="emit('delete', listing.id)">Șterge
      </button>
    </div>
  </article>
</template>

<style scoped>
.listing-row {
  display: flex;
  align-items: stretch;
  border: 1px solid var(--color-border);
  border-radius: 8px;
  background-color: white;
  margin-bottom: 1rem;
  overflow: hidden;
  transition: background-color 0.2s;
}

.listing-row:hover {
  background-color: #fcfcfc;
}

.image-section {
  width: 250px;
  min-width: 250px;
  height: 160px;
}

.image-section img {
  width: 100%;
  height: 100%;
  object-fit: cover;
}

.info-section {
  flex: 1;
  padding: 1.25rem;
  display: flex;
  flex-direction: column;
  justify-content: space-between;
}

.header-info {
  display: flex;
  flex-direction: row;
  justify-content: space-between;
  align-items: flex-start;
}

.header-info .price {
  margin: 0;
  color: var(--color-primary);
  font-size: 1.4rem;
}

.header-info .title {
  margin: 0.1rem 0;
  font-weight: 600;
}

.header-info .location {
  color: var(--color-text-muted);
  font-size: 0.9rem;
}

.specs-row {
  display: flex;
  gap: 2rem;
  padding-top: 1rem;
  border-top: 1px solid var(--color-border);
}

.spec-item {
  font-size: 0.9rem;
}

.action-section {
  padding: 1.25rem;
  display: flex;
  flex-direction: column;
  align-items: stretch;
  justify-content: center;
  border-left: 1px solid var(--color-border);
  min-width: 120px;
}

.view-btn {
  background-color: var(--color-primary);
  color: white;
  border: none;
  padding: 0.6rem 1rem;
  border-radius: 4px;
  cursor: pointer;
  font-weight: bold;
}

.edit-btn {
  margin-top: 0.5rem;
  background-color: white;
  color: var(--color-primary);
  border: 1px solid var(--color-primary);
  padding: 0.6rem 1rem;
  border-radius: 4px;
  cursor: pointer;
  font-weight: bold;
  transition: background 0.15s, color 0.15s;
}

.edit-btn:hover {
  background-color: var(--color-primary);
  color: white;
}

.delete-btn {
  margin-top: 0.5rem;
  background-color: white;
  color: #c62828;
  border: 1px solid #c62828;
  padding: 0.6rem 1rem;
  border-radius: 4px;
  cursor: pointer;
  font-weight: bold;
  transition: background 0.15s, color 0.15s;
}

.delete-btn:hover {
  background-color: #c62828;
  color: white;
}

.type-status {
  display: flex;
  flex-direction: column;
  align-items: flex-end;
  gap: 0.4rem;
}

.status-badge {
  padding: 0.2rem 0.6rem;
  border-radius: 4px;
  font-size: 0.75rem;
  font-weight: 600;
  text-transform: uppercase;
}

.status-badge.active {
  background: #e8f5e9;
  color: #2e7d32;
}

.status-badge.inactive {
  background: #f5f5f5;
  color: #757575;
}

.status-badge.sold {
  background: #fce4ec;
  color: #c62828;
}

@media (max-width: 768px) {
  .listing-row {
    flex-direction: column;
  }

  .image-section {
    width: 100%;
  }

  .action-section {
    border-left: none;
    border-top: 1px solid var(--color-border);
  }
}
</style>
