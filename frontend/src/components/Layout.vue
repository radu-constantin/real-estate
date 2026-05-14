<template>
  <div class="layout-container">
    <header class="navbar">
      <nav>
        <h1>IMOBILIARE</h1>
        <ul>
          <li>Acasă</li>
          <li>Anunțuri</li>
          <li>Profilul meu</li>
        </ul>
      </nav>
    </header>

    <main class="content">
      <slot />
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
