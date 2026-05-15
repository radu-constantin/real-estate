<script setup>
import { ref } from 'vue';
import axios from 'axios';

const props = defineProps({
  listingId: { type: Number, required: true }
});

const form = ref({ name: '', email: '', message: '' });
const submitted = ref(false);
const error = ref('');

const handleSubmit = async () => {
  error.value = '';
  try {
    await axios.post('/api/messages', {
      listingId: props.listingId,
      senderName: form.value.name,
      senderEmail: form.value.email,
      message: form.value.message,
    });
    submitted.value = true;
  } catch {
    error.value = 'A apărut o eroare. Încearcă din nou.';
  }
};
</script>

<template>
  <div class="contact-card">
    <h4 class="card-title">Contactează proprietarul</h4>
    <div v-if="submitted" class="success-message">
      <p>Mesajul tău a fost trimis cu succes!</p>
    </div>
    <form v-else @submit.prevent="handleSubmit" class="contact-form">
      <div class="form-group">
        <label>Nume</label>
        <input v-model="form.name" type="text" required placeholder="Numele tău" />
      </div>
      <div class="form-group">
        <label>Email</label>
        <input v-model="form.email" type="email" required placeholder="email@exemplu.com" />
      </div>
      <div class="form-group">
        <label>Mesaj</label>
        <textarea v-model="form.message" required rows="4" placeholder="Scrie mesajul tău..."></textarea>
      </div>
      <p v-if="error" class="error-message">{{ error }}</p>
      <button type="submit" class="submit-btn">Trimite mesaj</button>
    </form>
  </div>
</template>

<style scoped>
.contact-card {
  border: 1px solid var(--color-border);
  border-radius: 8px;
  background: white;
  padding: 1.5rem;
}

.card-title {
  margin: 0 0 1.25rem;
  color: var(--color-text-main);
  font-size: 1rem;
  text-transform: uppercase;
  letter-spacing: 0.05em;
}

.contact-form {
  display: flex;
  flex-direction: column;
  gap: 1rem;
}

.form-group {
  display: flex;
  flex-direction: column;
  gap: 0.3rem;
}

.form-group label {
  font-size: 0.85rem;
  color: var(--color-text-muted);
  font-weight: 500;
}

.form-group input,
.form-group textarea {
  padding: 0.6rem 0.75rem;
  border: 1px solid var(--color-border);
  border-radius: 4px;
  font-size: 0.95rem;
  color: var(--color-text-main);
  background: white;
  transition: border-color 0.2s;
}

.form-group input:focus,
.form-group textarea:focus {
  outline: none;
  border-color: var(--color-primary);
}

.form-group textarea {
  resize: vertical;
}

.submit-btn {
  background-color: var(--color-primary);
  color: white;
  border: none;
  padding: 0.7rem 1rem;
  border-radius: 4px;
  cursor: pointer;
  font-weight: bold;
  font-size: 0.95rem;
  transition: opacity 0.2s;
}

.submit-btn:hover {
  opacity: 0.9;
}

.success-message {
  background-color: #e8f5e9;
  color: #2e7d32;
  padding: 1rem;
  border-radius: 4px;
  text-align: center;
}

.error-message {
  color: #c62828;
  font-size: 0.85rem;
  margin: 0;
}
</style>
