<script setup>
import { RouterLink } from "vue-router";
import Button from './small_components/Button.vue';
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
