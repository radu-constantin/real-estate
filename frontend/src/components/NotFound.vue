<template>
  <div class="page">
    <main class="main">
      <div class="card">
        <div class="icon-wrap" aria-hidden="true">
          <svg xmlns="http://www.w3.org/2000/svg" width="52" height="52" viewBox="0 0 24 24"
               fill="none" stroke="currentColor" stroke-width="1.4"
               stroke-linecap="round" stroke-linejoin="round">
            <path d="M3 9l9-7 9 7v11a2 2 0 0 1-2 2H5a2 2 0 0 1-2-2z" />
            <polyline points="9 22 9 12 15 12 15 22" />
            <line x1="12" y1="2" x2="12" y2="2.01" />
            <line x1="9" y1="9" x2="15" y2="9" stroke-width="1" opacity="0.4" />
          </svg>
        </div>

        <p class="error-label">Eroare</p>
        <h1 class="error-code">404</h1>
        <h2 class="error-title">Pagina nu a fost găsită</h2>
        <p class="error-desc">
          Pagina pe care o cauți nu există sau a fost mutată.<br />
          Verifică adresa URL sau întoarce-te la pagina principală.
        </p>

        <div v-if="notFoundPath" class="path-badge">
          {{ notFoundPath }}
        </div>

        <div class="actions">
          <RouterLink to="/" class="btn-primary">← Înapoi acasă</RouterLink>
        </div>
      </div>
    </main>
  </div>
</template>

<script setup>
import { computed } from 'vue'
import { useRoute } from 'vue-router'

const route = useRoute()

const notFoundPath = computed(() => {
  const path = route.fullPath
  return path && path !== '/' ? path : null
})
</script>

<style scoped>
.page {
  min-height: 100vh;
  font-family: -apple-system, BlinkMacSystemFont, 'Segoe UI', Roboto, 'Helvetica Neue', sans-serif;
}

.main {
  display: flex;
  align-items: center;
  justify-content: center;
  min-height: calc(100vh - 68px);
  padding: 48px 24px;
}

.card {
  background: #ffffff;
  border: 1px solid #e5e7eb;
  border-radius: 10px;
  padding: 52px 64px 56px;
  text-align: center;
  max-width: 500px;
  width: 100%;
}

.icon-wrap {
  color: #c4cdd7;
  display: flex;
  justify-content: center;
  margin-bottom: 24px;
}

.error-label {
  font-size: 11px;
  font-weight: 600;
  letter-spacing: 0.12em;
  text-transform: uppercase;
  color: #9ca3af;
  margin-bottom: 8px;
}

.error-code {
  font-size: 88px;
  font-weight: 800;
  line-height: 1;
  color: #1c2b3a;
  letter-spacing: -3px;
  margin-bottom: 18px;
}

.error-title {
  font-size: 20px;
  font-weight: 600;
  color: #111827;
  margin-bottom: 12px;
}

.error-desc {
  font-size: 14.5px;
  color: #6b7280;
  line-height: 1.65;
  margin-bottom: 28px;
}

.path-badge {
  display: inline-block;
  background: #f3f4f6;
  color: #6b7280;
  font-family: 'SF Mono', 'Fira Code', 'Consolas', monospace;
  font-size: 12.5px;
  padding: 6px 14px;
  border-radius: 4px;
  margin-bottom: 32px;
  max-width: 100%;
  overflow: hidden;
  text-overflow: ellipsis;
  white-space: nowrap;
}

.actions {
  display: flex;
  gap: 10px;
  justify-content: center;
  flex-wrap: wrap;
}

.btn-primary {
  background: #1c2b3a;
  color: #ffffff;
  padding: 10px 22px;
  border-radius: 6px;
  font-size: 14px;
  font-weight: 500;
  text-decoration: none;
  border: none;
  cursor: pointer;
  transition: background 0.15s;
  display: inline-flex;
  align-items: center;
}

.btn-primary:hover {
  background: #26384a;
}

@media (max-width: 600px) {
  .card {
    padding: 40px 28px 44px;
  }

  .error-code {
    font-size: 72px;
  }
}
</style>
