<script setup>
import {RouterLink} from "vue-router";
import Button from './small_components/Button.vue';
import {useAuthStore} from '../stores/auth'
import router from "@/router/index.js";

const authStore = useAuthStore()

const logout = () => {
  authStore.logout()
  router.push('/auth')
}
</script>

<template>
  <div class="layout-container">
    <header class="navbar">
      <nav>
        <RouterLink to="/">
          <h1>IMOBILIARE</h1>
        </RouterLink>
        <ul>
          <li>
            <RouterLink to="/listings/create">
              <Button label="Adaugă anunț">
              </Button>
            </RouterLink>
          </li>
          <li>Anunțuri</li>
          <li>Profilul meu</li>
          <li>
            <RouterLink to="/auth">
              <Button label="Logout" ghost="true">
              </Button>
            </RouterLink>
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
