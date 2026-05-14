<script setup>
import { computed } from 'vue';

const props = defineProps({
  property: {
    type: Object,
    required: true
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
  <article class="property-row">
    <div class="image-section">
      <img src="../assets/images/random_house.jpg" alt="House photo" class="property-image" />
    </div>

    <div class="info-section">
      <div class="header-info">
        <h3 class="price">{{ formattedPrice }}</h3>
        <h4 class="title">{{ property.title }}</h4>
        <p class="location">{{ property.location }}</p>
      </div>

      <div class="specs-row">
        <div class="spec-item"><strong>{{ property.beds }}</strong> beds</div>
        <div class="spec-item"><strong>{{ property.baths }}</strong> baths</div>
        <div class="spec-item"><strong>2,100</strong> sqft</div> <!-- Added a dummy sqft to fill space -->
      </div>
    </div>

    <div class="action-section">
      <button class="view-btn">View Details</button>
    </div>
  </article>
</template>

<style scoped>
.property-row {
  display: flex;
  align-items: stretch;
  border: 1px solid var(--color-border);
  border-radius: 8px;
  background-color: white;
  margin-bottom: 1rem;
  overflow: hidden;
  transition: background-color 0.2s;
}

.property-row:hover {
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
  align-items: center;
  border-left: 1px solid var(--color-border);
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

@media (max-width: 768px) {
  .property-row {
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
