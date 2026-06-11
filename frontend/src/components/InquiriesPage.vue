<script setup>
import { ref, onMounted } from 'vue';
import { RouterLink } from 'vue-router';
import api from '@/api/axios';

const inquiries = ref([]);
const loading = ref(true);

onMounted(async () => {
  try {
    const res = await api.get('/inquiries/received');
    inquiries.value = res.data;
  } finally {
    loading.value = false;
  }
});

const markAsRead = async (inquiry) => {
  try {
    await api.put(`/inquiries/${inquiry.id}/read`);
    inquiry.read = true;
  } catch {
  }
};

const formatDate = (dateStr) => {
  if (!dateStr) return '';
  return new Date(dateStr).toLocaleDateString('ro-RO', {
    day: '2-digit', month: '2-digit', year: 'numeric',
    hour: '2-digit', minute: '2-digit'
  });
};
</script>

<template>
  <div class="inquiries-page">
    <h2 class="page-title">Mesaje primite</h2>

    <div v-if="loading" class="state-msg">Se încarcă...</div>

    <div v-else-if="inquiries.length === 0" class="state-msg">
      Nu ai primit niciun mesaj încă.
    </div>

    <div v-else class="inquiry-list">
      <div
        v-for="inquiry in inquiries"
        :key="inquiry.id"
        class="inquiry-card"
        :class="{ unread: !inquiry.read }"
      >
        <div class="inquiry-header">
          <div class="inquiry-meta">
            <span class="sender">{{ inquiry.senderUsername }}</span>
            <span class="listing-address">{{ inquiry.listingAddress }}</span>
            <span class="sent-at">{{ formatDate(inquiry.sentAt) }}</span>
          </div>
          <div class="inquiry-actions">
            <button
              v-if="!inquiry.read"
              class="btn-read"
              @click="markAsRead(inquiry)"
            >
              Marchează ca citit
            </button>
            <span v-else class="read-badge">Citit</span>
          </div>
        </div>

        <p class="inquiry-message">{{ inquiry.message }}</p>

        <RouterLink :to="`/listings/${inquiry.listingId}/edit`" class="edit-link">
          Anunț
        </RouterLink>
      </div>
    </div>
  </div>
</template>

<style scoped>
.inquiries-page {
  max-width: 800px;
  margin: 0 auto;
}

.page-title {
  margin: 0 0 1.5rem;
  color: var(--color-text-main);
  font-size: 1.4rem;
}

.state-msg {
  text-align: center;
  padding: 3rem;
  color: var(--color-text-muted);
}

.inquiry-list {
  display: flex;
  flex-direction: column;
  gap: 1rem;
}

.inquiry-card {
  border: 1px solid var(--color-border);
  border-radius: 8px;
  background: white;
  padding: 1.25rem;
}

.inquiry-card.unread {
  border-left: 3px solid var(--color-primary);
}

.inquiry-header {
  display: flex;
  justify-content: space-between;
  align-items: flex-start;
  gap: 1rem;
  margin-bottom: 0.75rem;
}

.inquiry-meta {
  display: flex;
  flex-direction: column;
  gap: 0.2rem;
}

.sender {
  font-weight: 600;
  color: var(--color-text-main);
}

.listing-address {
  font-size: 0.85rem;
  color: var(--color-text-muted);
}

.sent-at {
  font-size: 0.8rem;
  color: var(--color-text-muted);
}

.inquiry-actions {
  flex-shrink: 0;
}

.btn-read {
  background: none;
  border: 1px solid var(--color-border);
  border-radius: 4px;
  padding: 0.3rem 0.7rem;
  font-size: 0.8rem;
  cursor: pointer;
  color: var(--color-text-muted);
  transition: border-color 0.2s;
}

.btn-read:hover {
  border-color: var(--color-primary);
  color: var(--color-primary);
}

.read-badge {
  font-size: 0.8rem;
  color: #2e7d32;
}

.inquiry-message {
  color: var(--color-text-main);
  margin: 0 0 1rem;
  line-height: 1.5;
}

.edit-link {
  display: inline-block;
  font-size: 0.85rem;
  color: var(--color-primary);
  text-decoration: none;
  border: 1px solid var(--color-primary);
  border-radius: 4px;
  padding: 0.3rem 0.75rem;
  transition: background 0.15s, color 0.15s;
}

.edit-link:hover {
  background-color: var(--color-primary);
  color: white;
}
</style>
