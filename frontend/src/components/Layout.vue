<script setup>
import { ref, watch, onMounted, onUnmounted } from 'vue';
import { RouterLink, useRoute } from 'vue-router';
import { useAuthStore } from '@/stores/auth';
import api from '@/api/axios';
import Button from './small_components/Button.vue';

const authStore = useAuthStore();
const route = useRoute();
const unreadCount = ref(0);
let pollInterval = null;

const fetchUnreadCount = async () => {
  if (!authStore.isAuthenticated) return;
  try {
    const res = await api.get('/inquiries/unread-count');
    unreadCount.value = res.data.count;
  } catch {
    // silent fail — badge just won't update
  }
};

// Refresh badge on every navigation so it's always up-to-date without waiting for the poll
watch(() => route.path, () => fetchUnreadCount());

onMounted(() => {
  fetchUnreadCount();
  pollInterval = setInterval(fetchUnreadCount, 30000);
});

onUnmounted(() => clearInterval(pollInterval));
</script>

<template>
  <div class="layout-container">
    <header class="navbar">
      <nav>
        <RouterLink to="/">
          <h1>IMOBILIARE</h1>
        </RouterLink>
        <ul v-if="authStore.isAuthenticated">
          <li>
            <RouterLink to="/listings/create">
              <Button label="Adaugă anunț">
              </Button>
            </RouterLink>
          </li>
          <li>
            <RouterLink to="/inquiries" class="nav-link">
              Mesaje
              <span v-if="unreadCount > 0" class="badge">{{ unreadCount }}</span>
            </RouterLink>
          </li>
          <li>
            <RouterLink to="/profile" class="nav-link">Profilul meu</RouterLink>
          </li>
        </ul>
      </nav>
    </header>

    <main class="content">
      <slot/>
    </main>

    <footer class="footer">
      <p>&copy; 2026 The Web Devs. All rights reserved.</p>
    </footer>
  </div>
</template>

<style scoped>
.layout-container {
  display: flex;
  flex-direction: column;
  min-height: 100vh;
  color: var(--color-text-main);
}

.navbar {
  background-color: var(--color-primary);
  color: var(--color-primary-contrast);
  color: white;
  padding: 1rem;
}

nav {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

nav ul {
  list-style: none;
  display: flex;
  gap: 1rem;
}

ul li {
  display: flex;
  flex-direction: column;
  justify-content: center;
}

.nav-link {
  color: white;
  text-decoration: none;
  font-size: 0.95rem;
}

.nav-link:hover {
  text-decoration: underline;
}

.badge {
  background-color: #c62828;
  color: white;
  border-radius: 50%;
  font-size: 0.7rem;
  font-weight: bold;
  padding: 0.1rem 0.4rem;
  margin-left: 4px;
  vertical-align: middle;
}

.content {
  flex: 1;
  padding: 2rem;
}

.footer {
  background-color: var(--color-bg-light);
  color: var(--color-text-muted);
  padding: 1rem;
  text-align: center;
  border-top: 1px solid #ddd;
}
</style>
