<script setup>
import { computed } from 'vue';

const props = defineProps({
  property: {
    type: Object,
    required: true,
    // Expected shape: { id, title, price, location, beds, baths, imageUrl }
  }
});

const formattedPrice = computed(() => {
  return new Intl.NumberFormat('en-US', {
    style: 'currency',
    currency: 'USD',
    maximumFractionDigits: 0,
  }).format(props.property.price);
});
</script>

<template>
  <article class="property-card">
    <div class="image-wrapper">
      <img src="../assets/images/random_house.jpg" alt="House photo" class="property-image" />
    </div>

    <div class="property-details">
      <h3 class="price">{{ formattedPrice }}</h3>
      <h4 class="title">{{ property.address }}</h4>
      <p class="location">{{ property.numberOfRooms }}</p>

      <div class="specs">
        <span><strong>{{ property.floorArea }}</strong> bds</span>
        <span class="divider">|</span>
<!--        <span><strong>{{ property.baths }}</strong> ba</span>-->
      </div>
    </div>
  </article>
</template>

<style scoped>
.property-card {
  max-width: 350px;
  width: 100%;

  border: 1px solid var(--color-border);
  border-radius: 8px;
  overflow: hidden;
  background-color: white;
  transition: transform 0.2s ease, box-shadow 0.2s ease;
  cursor: pointer;
}

.image-wrapper {
  height: 200px;
  overflow: hidden;
}

.property-card:hover {
  transform: translateY(-4px);
  box-shadow: 0 4px 12px rgba(0,0,0,0.1);
}

.image-wrapper {
  aspect-ratio: 16 / 9;
  overflow: hidden;
}

.property-image {
  width: 100%;
  height: 100%;
  object-fit: cover;
}

.property-details {
  padding: 1rem;
}

.price {
  margin: 0;
  font-size: 1.25rem;
  color: var(--color-text-main);
}

.title {
  margin: 0.25rem 0;
  font-size: 1rem;
  font-weight: 500;
}

.location {
  font-size: 0.875rem;
  color: var(--color-text-muted);
  margin-bottom: 1rem;
}

.specs {
  font-size: 0.875rem;
  border-top: 1px solid var(--color-border);
  padding-top: 0.75rem;
  display: flex;
  gap: 0.5rem;
}

.divider {
  color: var(--color-border);
}
</style>
