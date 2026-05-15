<script setup>
import { computed } from 'vue';
import ListingImageCarousel from './ListingImageCarousel.vue';
import placeholderImage from '../assets/images/random_house.jpg';

const props = defineProps({
  listing: { type: Object, required: true }
});

const property = computed(() => props.listing.property);

const formattedPrice = computed(() => {
  const amount = props.listing.askingPrice || props.listing.monthlyRent;
  return new Intl.NumberFormat('en-US', {
    style: 'currency',
    currency: 'EUR',
    maximumFractionDigits: 0,
  }).format(amount);
});

const priceSuffix = computed(() => props.listing.listingType === 'rental' ? ' / lună' : '');
const listingTypeLabel = computed(() => props.listing.listingType === 'rental' ? 'Închiriere' : 'Vânzare');

const images = computed(() =>
  props.listing.photos?.length ? props.listing.photos.map(p => p.url) : [placeholderImage]
);
</script>

<template>
  <div class="detail-header">
    <div class="hero-image-container">
      <ListingImageCarousel :images="images" />
    </div>
    <div class="header-info">
      <div class="title-row">
        <div>
          <h2 class="price">{{ formattedPrice }}<span class="price-suffix">{{ priceSuffix }}</span></h2>
          <h3 class="address">{{ property.address }}</h3>
        </div>
        <span class="listing-badge">{{ listingTypeLabel }}</span>
      </div>
    </div>
  </div>
</template>

<style scoped>
.detail-header {
  border: 1px solid var(--color-border);
  border-radius: 8px;
  overflow: hidden;
  background: white;
  margin-bottom: 1.5rem;
}

.hero-image-container {
  height: 400px;
}

.header-info {
  padding: 1.5rem;
}

.title-row {
  display: flex;
  justify-content: space-between;
  align-items: flex-start;
}

.price {
  margin: 0;
  color: var(--color-primary);
  font-size: 2rem;
}

.price-suffix {
  font-size: 1rem;
  font-weight: normal;
  color: var(--color-text-muted);
}

.address {
  margin: 0.25rem 0 0;
  color: var(--color-text-main);
  font-weight: 600;
}

.listing-badge {
  background-color: var(--color-primary);
  color: white;
  padding: 0.3rem 0.8rem;
  border-radius: 4px;
  font-size: 0.85rem;
  font-weight: bold;
  white-space: nowrap;
}
</style>
