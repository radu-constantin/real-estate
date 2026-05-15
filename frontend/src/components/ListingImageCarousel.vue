<script setup>
import { ref } from 'vue';

const props = defineProps({
  images: { type: Array, required: true }
});

const current = ref(0);

const prev = () => {
  current.value = (current.value - 1 + props.images.length) % props.images.length;
};

const next = () => {
  current.value = (current.value + 1) % props.images.length;
};
</script>

<template>
  <div class="carousel">
    <img :src="images[current]" alt="Property photo" class="carousel-image" />

    <template v-if="images.length > 1">
      <button class="arrow arrow-left" @click="prev" aria-label="Previous photo">&#8249;</button>
      <button class="arrow arrow-right" @click="next" aria-label="Next photo">&#8250;</button>

      <div class="dots">
        <button
          v-for="(_, i) in images"
          :key="i"
          class="dot"
          :class="{ active: i === current }"
          @click="current = i"
          :aria-label="`Photo ${i + 1}`"
        />
      </div>
    </template>
  </div>
</template>

<style scoped>
.carousel {
  position: relative;
  height: 100%;
  overflow: hidden;
}

.carousel-image {
  width: 100%;
  height: 100%;
  object-fit: cover;
  display: block;
}

.arrow {
  position: absolute;
  top: 50%;
  transform: translateY(-50%);
  background: rgba(0, 0, 0, 0.4);
  color: white;
  border: none;
  width: 40px;
  height: 40px;
  border-radius: 50%;
  font-size: 1.5rem;
  line-height: 1;
  cursor: pointer;
  display: flex;
  align-items: center;
  justify-content: center;
  transition: background 0.2s;
}

.arrow:hover {
  background: rgba(0, 0, 0, 0.65);
}

.arrow-left  { left: 0.75rem; }
.arrow-right { right: 0.75rem; }

.dots {
  position: absolute;
  bottom: 0.75rem;
  left: 50%;
  transform: translateX(-50%);
  display: flex;
  gap: 0.4rem;
}

.dot {
  width: 8px;
  height: 8px;
  border-radius: 50%;
  border: none;
  background: rgba(255, 255, 255, 0.55);
  cursor: pointer;
  padding: 0;
  transition: background 0.2s;
}

.dot.active {
  background: white;
}
</style>
