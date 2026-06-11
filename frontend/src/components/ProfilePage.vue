<script setup>
import { ref, onMounted, computed } from 'vue'
import { useRouter } from 'vue-router'
import api from '@/api/axios.js'
import { useAuthStore } from '../stores/auth'
import ListingItem from './ListingItem.vue'

const authStore = useAuthStore()
const router = useRouter()
const user = authStore.user
const listings = ref([])
const loading = ref(true)
const deleteModalVisible = ref(false)
const listingToDelete = ref(null)

const initials = computed(() => {
  return user?.username?.slice(0, 2).toUpperCase() || '??'
})

onMounted(async () => {
  try {
    const response = await api.get(`/listings?userId=${user.id}`)
    listings.value = response.data
  } finally {
    loading.value = false
  }
})

const logout = () => {
  authStore.logout()
  router.push('/auth')
}

const requestDelete = (id) => {
  listingToDelete.value = id
  deleteModalVisible.value = true
}

const cancelDelete = () => {
  deleteModalVisible.value = false
  listingToDelete.value = null
}

const confirmDelete = async () => {
  try {
    await api.delete(`/listings/${listingToDelete.value}`)
    listings.value = listings.value.filter(l => l.id !== listingToDelete.value)
  } finally {
    deleteModalVisible.value = false
    listingToDelete.value = null
  }
}
</script>

<template>
  <div class="profile-page">
    <div class="page-header">
      <h2>Profilul meu</h2>
    </div>

    <!-- User info card -->
    <div class="form-card profile-card">
      <div class="profile-identity">
        <div class="avatar">{{ initials }}</div>
        <div class="profile-info">
          <h3 class="username">{{ user?.username }}</h3>
          <span class="role-badge" :class="user?.role?.toLowerCase()">{{ user?.role }}</span>
        </div>
      </div>
      <button class="logout-btn" @click="logout">Deconectare</button>
    </div>

    <!-- Listings section -->
    <div class="listings-section">
      <div class="section-header">
        <h4 class="card-title">Anunțurile mele</h4>
        <button class="add-btn" @click="router.push('/listings/create')">+ Adaugă anunț</button>
      </div>

      <div v-if="loading" class="state-message">Se încarcă...</div>

      <template v-else>
        <div v-if="listings.length > 0">
          <p class="results-count">{{ listings.length }} {{ listings.length === 1 ? 'anunț' : 'anunțuri' }}</p>
          <ListingItem
            v-for="listing in listings"
            :key="listing.id"
            :listing="listing"
            :showEdit="true"
            :showDelete="true"
            @delete="requestDelete"
          />
        </div>
        <div v-else class="empty-state">
          <p>Nu ai publicat niciun anunț încă.</p>
          <button class="submit-btn" @click="router.push('/listings/create')">Publică primul anunț</button>
        </div>
      </template>
    </div>
  </div>

  <!-- Delete confirmation modal -->
  <Teleport to="body">
    <div v-if="deleteModalVisible" class="modal-backdrop" @click.self="cancelDelete">
      <div class="modal">
        <h3 class="modal-title">Confirmare ștergere</h3>
        <p class="modal-body">Ești sigur că vrei să ștergi acest anunț? Acțiunea nu poate fi anulată.</p>
        <div class="modal-actions">
          <button class="modal-cancel" @click="cancelDelete">Anulează</button>
          <button class="modal-confirm" @click="confirmDelete">Șterge</button>
        </div>
      </div>
    </div>
  </Teleport>
</template>

<style scoped>
.profile-page {
  max-width: 800px;
  margin: 0 auto;
}

.page-header {
  margin-bottom: 1.5rem;
}

.form-card {
  border: 1px solid var(--color-border);
  border-radius: 8px;
  background: white;
  padding: 1.5rem;
}

.card-title {
  margin: 0;
  color: var(--color-text-main);
  font-size: 1rem;
  text-transform: uppercase;
  letter-spacing: 0.05em;
}

.profile-card {
  display: flex;
  align-items: center;
  justify-content: space-between;
  margin-bottom: 1.5rem;
}

.profile-identity {
  display: flex;
  align-items: center;
  gap: 1rem;
}

.avatar {
  width: 56px;
  height: 56px;
  border-radius: 50%;
  background: var(--color-primary);
  color: white;
  display: flex;
  align-items: center;
  justify-content: center;
  font-size: 1.25rem;
  font-weight: 700;
  flex-shrink: 0;
}

.profile-info {
  display: flex;
  flex-direction: column;
  gap: 0.35rem;
}

.username {
  margin: 0;
  font-size: 1.2rem;
  color: var(--color-text-main);
}

.role-badge {
  display: inline-block;
  padding: 0.2rem 0.6rem;
  border-radius: 4px;
  font-size: 0.75rem;
  font-weight: 600;
  text-transform: uppercase;
  letter-spacing: 0.05em;
  width: fit-content;
}

.role-badge.user {
  background: #e3f2fd;
  color: #1565c0;
}

.role-badge.admin {
  background: #fce4ec;
  color: #c62828;
}

.logout-btn {
  padding: 0.6rem 1.25rem;
  border: 1px solid var(--color-border);
  border-radius: 4px;
  background: white;
  color: var(--color-text-main);
  cursor: pointer;
  font-size: 0.95rem;
  transition: background 0.15s, border-color 0.15s;
}

.logout-btn:hover {
  background: var(--color-bg-light);
  border-color: #aaa;
}

.listings-section {
  display: flex;
  flex-direction: column;
  gap: 1rem;
}

.section-header {
  display: flex;
  align-items: center;
  justify-content: space-between;
}

.results-count {
  margin: 0 0 0.75rem;
  color: var(--color-text-muted);
  font-size: 0.9rem;
}

.add-btn {
  padding: 0.5rem 1rem;
  background: var(--color-primary);
  color: white;
  border: none;
  border-radius: 4px;
  cursor: pointer;
  font-size: 0.9rem;
  font-weight: 600;
  transition: opacity 0.2s;
}

.add-btn:hover {
  opacity: 0.85;
}

.state-message {
  text-align: center;
  padding: 3rem;
  color: var(--color-text-muted);
}

.empty-state {
  text-align: center;
  padding: 3rem;
  color: var(--color-text-muted);
  border: 1px dashed var(--color-border);
  border-radius: 8px;
  display: flex;
  flex-direction: column;
  align-items: center;
  gap: 1rem;
}

.submit-btn {
  padding: 0.7rem 1.5rem;
  background: var(--color-primary);
  color: white;
  border: none;
  border-radius: 4px;
  cursor: pointer;
  font-weight: bold;
  font-size: 0.95rem;
  transition: opacity 0.2s;
}

.submit-btn:hover {
  opacity: 0.9;
}

@media (max-width: 600px) {
  .profile-card {
    flex-direction: column;
    align-items: flex-start;
    gap: 1rem;
  }
}

.modal-backdrop {
  position: fixed;
  inset: 0;
  background: rgba(0, 0, 0, 0.45);
  display: flex;
  align-items: center;
  justify-content: center;
  z-index: 1000;
}

.modal {
  background: white;
  border-radius: 8px;
  padding: 2rem;
  max-width: 420px;
  width: 90%;
  box-shadow: 0 8px 32px rgba(0, 0, 0, 0.18);
}

.modal-title {
  margin: 0 0 0.75rem;
  font-size: 1.1rem;
  color: var(--color-text-main);
}

.modal-body {
  margin: 0 0 1.5rem;
  color: var(--color-text-muted);
  font-size: 0.95rem;
  line-height: 1.5;
}

.modal-actions {
  display: flex;
  justify-content: flex-end;
  gap: 0.75rem;
}

.modal-cancel {
  padding: 0.6rem 1.25rem;
  border: 1px solid var(--color-border);
  border-radius: 4px;
  background: white;
  color: var(--color-text-main);
  cursor: pointer;
  font-size: 0.95rem;
  transition: background 0.15s;
}

.modal-cancel:hover {
  background: var(--color-bg-light);
}

.modal-confirm {
  padding: 0.6rem 1.25rem;
  border: none;
  border-radius: 4px;
  background: #c62828;
  color: white;
  cursor: pointer;
  font-size: 0.95rem;
  font-weight: 600;
  transition: opacity 0.15s;
}

.modal-confirm:hover {
  opacity: 0.85;
}
</style>
