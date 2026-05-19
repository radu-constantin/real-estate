<script setup>
import {ref} from 'vue';
import {useRouter} from 'vue-router';
import api from "@/api/axios.js";
import {useAuthStore} from "@/stores/auth.js";

const router = useRouter();
const authStore = useAuthStore();

const form = ref({
  listingType: 'sale',
  description: '',
  askingPrice: '',
  monthlyRent: '',
  availableFrom: '',
  property: {
    propertyType: 'apartment',
    address: '',
    numberOfRooms: '',
    floorArea: '',
    dateOfConstruction: '',
    numberOfFloors: '',
    plotArea: '',
    floorNum: '',
  },
});

const submitting = ref(false);
const error = ref('');

const handleSubmit = async () => {
  submitting.value = true;
  error.value = '';

  const userId = authStore.user?.id;

  try {
    const propertyPayload = {
      user: {id: userId},
      propertyType: form.value.property.propertyType,
      address: form.value.property.address,
      numberOfRooms: Number(form.value.property.numberOfRooms),
      floorArea: Number(form.value.property.floorArea),
      dateOfConstruction: form.value.property.dateOfConstruction,
    };

    if (form.value.property.propertyType === 'house') {
      propertyPayload.numberOfFloors = Number(form.value.property.numberOfFloors);
      propertyPayload.plotArea = Number(form.value.property.plotArea);
    } else {
      propertyPayload.floorNumber = Number(form.value.property.floorNum);
    }

    const propertyResponse = await api.post('/api/properties', propertyPayload);
    const propertyId = propertyResponse.data.id;

    if (form.value.listingType === 'sale') {
      await api.post('/api/sales', {
        propertyId,
        askingPrice: Number(form.value.askingPrice),
        description: form.value.description,
      });
    } else {
      await api.post('/api/rentals', {
        propertyId,
        monthlyRent: Number(form.value.monthlyRent),
        availableFrom: form.value.availableFrom,
        description: form.value.description,
      });
    }

    await router.push('/');
  } catch {
    error.value = 'A apărut o eroare la crearea anunțului. Încearcă din nou.';
  } finally {
    submitting.value = false;
  }
};
</script>

<template>
  <div class="create-page">
    <div class="page-header">
      <h2>Adaugă anunț</h2>
    </div>

    <form @submit.prevent="handleSubmit" class="create-form">

      <div class="form-card">
        <h4 class="card-title">Tip anunț</h4>
        <div class="type-selector">
          <button
            type="button"
            class="type-btn"
            :class="{ active: form.listingType === 'sale' }"
            @click="form.listingType = 'sale'"
          >
            Vânzare
          </button>
          <button
            type="button"
            class="type-btn"
            :class="{ active: form.listingType === 'rental' }"
            @click="form.listingType = 'rental'"
          >
            Închiriere
          </button>
        </div>
      </div>

      <div class="form-card">
        <h4 class="card-title">Detalii proprietate</h4>

        <div class="type-selector" style="margin-bottom: 1.5rem;">
          <button
            type="button"
            class="type-btn"
            :class="{ active: form.property.propertyType === 'apartment' }"
            @click="form.property.propertyType = 'apartment'"
          >
            Apartament
          </button>
          <button
            type="button"
            class="type-btn"
            :class="{ active: form.property.propertyType === 'house' }"
            @click="form.property.propertyType = 'house'"
          >
            Casă
          </button>
        </div>

        <div class="fields-grid">
          <div class="form-group full-width">
            <label>Adresă</label>
            <input v-model="form.property.address" type="text" required
                   placeholder="Str. Exemplu 10, Cluj-Napoca"/>
          </div>

          <div class="form-group">
            <label>Număr camere</label>
            <input v-model="form.property.numberOfRooms" type="number" required min="1"
                   placeholder="3"/>
          </div>

          <div class="form-group">
            <label>Suprafață utilă (m²)</label>
            <input v-model="form.property.floorArea" type="number" required min="1"
                   placeholder="85"/>
          </div>

          <div class="form-group">
            <label>An construcție</label>
            <input v-model="form.property.dateOfConstruction" type="date" required/>
          </div>

          <template v-if="form.property.propertyType === 'house'">
            <div class="form-group">
              <label>Număr etaje</label>
              <input v-model="form.property.numberOfFloors" type="number" required min="1"
                     placeholder="2"/>
            </div>
            <div class="form-group">
              <label>Suprafață teren (m²)</label>
              <input v-model="form.property.plotArea" type="number" required min="1"
                     placeholder="300"/>
            </div>
          </template>

          <template v-if="form.property.propertyType === 'apartment'">
            <div class="form-group">
              <label>Etaj</label>
              <input v-model="form.property.floorNum" type="number" required min="0"
                     placeholder="3"/>
            </div>
          </template>
        </div>
      </div>

      <div class="form-card">
        <h4 class="card-title">Detalii anunț</h4>
        <div class="fields-grid">
          <div class="form-group full-width">
            <label>Descriere</label>
            <textarea v-model="form.description" rows="4"
                      placeholder="Descrie proprietatea..."></textarea>
          </div>

          <template v-if="form.listingType === 'sale'">
            <div class="form-group">
              <label>Preț cerut (EUR)</label>
              <input v-model="form.askingPrice" type="number" required min="0"
                     placeholder="250000"/>
            </div>
          </template>

          <template v-if="form.listingType === 'rental'">
            <div class="form-group">
              <label>Chirie lunară (EUR)</label>
              <input v-model="form.monthlyRent" type="number" required min="0" placeholder="500"/>
            </div>
            <div class="form-group">
              <label>Disponibil din</label>
              <input v-model="form.availableFrom" type="date" required/>
            </div>
          </template>
        </div>
      </div>

      <p v-if="error" class="error-message">{{ error }}</p>

      <div class="form-actions">
        <button type="button" class="cancel-btn" @click="router.back()">Anulează</button>
        <button type="submit" class="submit-btn" :disabled="submitting">
          {{ submitting ? 'Se publică...' : 'Publică anunț' }}
        </button>
      </div>

    </form>
  </div>
</template>

<style scoped>
.create-page {
  max-width: 720px;
  margin: 0 auto;
}

.page-header {
  margin-bottom: 1.5rem;
}

.create-form {
  display: flex;
  flex-direction: column;
  gap: 1.5rem;
}

.form-card {
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

.type-selector {
  display: flex;
  gap: 0.75rem;
}

.type-btn {
  padding: 0.5rem 1.25rem;
  border: 1px solid var(--color-border);
  border-radius: 4px;
  background: white;
  color: var(--color-text-main);
  cursor: pointer;
  font-size: 0.95rem;
  transition: background 0.15s, color 0.15s, border-color 0.15s;
}

.type-btn.active {
  background: var(--color-primary);
  color: white;
  border-color: var(--color-primary);
}

.fields-grid {
  display: grid;
  grid-template-columns: 1fr 1fr;
  gap: 1rem;
}

.form-group {
  display: flex;
  flex-direction: column;
  gap: 0.3rem;
}

.form-group.full-width {
  grid-column: span 2;
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

.error-message {
  color: #c62828;
  font-size: 0.9rem;
  margin: 0;
}

.form-actions {
  display: flex;
  justify-content: flex-end;
  gap: 0.75rem;
}

.cancel-btn {
  padding: 0.7rem 1.25rem;
  border: 1px solid var(--color-border);
  border-radius: 4px;
  background: white;
  color: var(--color-text-main);
  cursor: pointer;
  font-size: 0.95rem;
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

.submit-btn:hover:not(:disabled) {
  opacity: 0.9;
}

.submit-btn:disabled {
  opacity: 0.6;
  cursor: not-allowed;
}

@media (max-width: 600px) {
  .fields-grid {
    grid-template-columns: 1fr;
  }

  .form-group.full-width {
    grid-column: span 1;
  }
}
</style>
