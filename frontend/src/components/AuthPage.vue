<template>
  <div class="auth-container">
    <div class="auth-card">
      <h2>{{ isLoginMode ? 'Login în Imobiliare' : 'Creează cont' }}</h2>

      <form @submit.prevent="handleSubmit">
        <div class="form-group">
          <label for="username">Username</label>
          <input
            type="text"
            id="username"
            v-model="username"
            required
            placeholder="Introdu numele de utilizator"
          />
        </div>

        <div class="form-group" v-if="!isLoginMode">
          <label for="email">Email</label>
          <input
            type="email"
            id="email"
            v-model="email"
            required
            placeholder="Introdu adresa de email"
          />
        </div>

        <div class="form-group">
          <label for="password">Password</label>
          <input
            type="password"
            id="password"
            v-model="password"
            required
            placeholder="Introdu parola"
          />
        </div>

        <button type="submit" :disabled="authStore.loading">
          {{ authStore.loading ? 'Procesare...' : (isLoginMode ? 'Logare' : 'Înregistrare') }}
        </button>
      </form>

      <p v-if="authStore.error" class="error-msg">{{ authStore.error }}</p>

      <div class="toggle-mode">
        <a href="#" @click.prevent="isLoginMode = !isLoginMode">
          {{ isLoginMode ? "Nu ai un cont? Înregistrează-te aici" : 'Ai deja un cont? Loghează-te aici' }}
        </a>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref } from 'vue'
import { useAuthStore } from '../stores/auth'
import { useRouter } from 'vue-router'

const authStore = useAuthStore()
const router = useRouter()

const username = ref('')
const email = ref('')
const password = ref('')
const isLoginMode = ref(true)

const handleSubmit = async () => {
  try {
    if (isLoginMode.value) {
      await authStore.login(username.value, password.value)
    } else {
      await authStore.register(username.value, email.value, password.value)
    }
    await router.push('/')
  } catch (err) {
    console.error('Authentication error:', err)
  }
}
</script>

<style scoped>
.auth-container {
  display: flex;
  justify-content: center;
  align-items: center;
  min-height: 80vh;
}
.auth-card {
  background: #fff;
  padding: 2rem;
  border-radius: 8px;
  box-shadow: 0 4px 12px rgba(0,0,0,0.1);
  width: 100%;
  max-width: 400px;
}
.form-group {
  margin-bottom: 1.25rem;
}
label {
  display: block;
  margin-bottom: 0.5rem;
  font-weight: bold;
}
input {
  width: 100%;
  padding: 0.75rem;
  border: 1px solid #ccc;
  border-radius: 4px;
  box-sizing: border-box;
}
button {
  width: 100%;
  padding: 0.75rem;
  background: #42b883;
  color: white;
  border: none;
  border-radius: 4px;
  font-size: 1rem;
  cursor: pointer;
}
button:disabled {
  background: #a0dcb3;
}
.error-msg {
  color: #e74c3c;
  margin-top: 1rem;
  text-align: center;
}
.toggle-mode {
  margin-top: 1.5rem;
  text-align: center;
}
a {
  color: #35495e;
  text-decoration: none;
}
</style>
