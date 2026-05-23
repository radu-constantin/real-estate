<script setup>
import { ref, computed, onMounted } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import axios from 'axios'
import api from '@/api/axios.js'

const route = useRoute()
const router = useRouter()

const loading = ref(true)
const submitting = ref(false)
const error = ref('')

const listingType = ref('')
const listingId = ref(null)
const propertyId = ref(null)
const propertyType = ref('')

const existingPhotos = ref([])
const newFiles = ref([])
const newPreviews = computed(() => newFiles.value.map(f => URL.createObjectURL(f)))
const onNewFilesSelected = (e) => { newFiles.value = [...e.target.files] }
const removeNewFile = (i) => { newFiles.value = newFiles.value.filter((_, idx) => idx !== i) }

const removeExistingPhoto = async (photoId) => {
  await api.delete(`/api/properties/${propertyId.value}/photos/${photoId}`)
  existingPhotos.value = existingPhotos.value.filter(p => p.id !== photoId)
}

const form = ref({
  description: '',
  askingPrice: '',
  monthlyRent: '',
  availableFrom: '',
  property: {
    address: '',
    numberOfRooms: '',
    floorArea: '',
    dateOfConstruction: '',
    numberOfFloors: '',
    plotArea: '',
    floorNumber: '',
  }
})

// LocalDate from Spring can come as array [y, m, d] or string "yyyy-MM-dd"
const toDateString = (val) => {
  if (!val) return ''
  if (Array.isArray(val)) {
    const [y, m, d] = val
    return `${y}-${String(m).padStart(2, '0')}-${String(d).padStart(2, '0')}`
  }
  return String(val).split('T')[0]
}

onMounted(async () => {
  try {
    const response = await axios.get(`/api/listings/${route.params.id}`)
    const listing = response.data
    const property = listing.property

    listingType.value = listing.listingType
    listingId.value = listing.id
    propertyId.value = property.id
    propertyType.value = property.propertyType

    form.value.description = listing.description || ''
    form.value.property.address = property.address || ''
    form.value.property.numberOfRooms = property.numberOfRooms ?? ''
    form.value.property.floorArea = property.floorArea ?? ''
    form.value.property.dateOfConstruction = toDateString(property.dateOfConstruction)

    if (property.propertyType === 'house') {
      form.value.property.numberOfFloors = property.numberOfFloors ?? ''
      form.value.property.plotArea = property.plotArea ?? ''
    } else {
      form.value.property.floorNumber = property.floorNumber ?? ''
    }

    if (listing.listingType === 'sale') {
      form.value.askingPrice = listing.askingPrice ?? ''
    } else {
      form.value.monthlyRent = listing.monthlyRent ?? ''
      form.value.availableFrom = toDateString(listing.availableFrom)
    }

    existingPhotos.value = property.photos || []
  } catch {
    error.value = 'Nu am putut încărca datele anunțului.'
  } finally {
    loading.value = false
  }
})

const handleSubmit = async () => {
  submitting.value = true
  error.value = ''

  try {
    const propertyPayload = {
      propertyType: propertyType.value,
      address: form.value.property.address,
      numberOfRooms: Number(form.value.property.numberOfRooms),
      floorArea: Number(form.value.property.floorArea),
      dateOfConstruction: form.value.property.dateOfConstruction,
    }

    if (propertyType.value === 'house') {
      propertyPayload.numberOfFloors = Number(form.value.property.numberOfFloors)
      propertyPayload.plotArea = Number(form.value.property.plotArea)
    } else {
      propertyPayload.floorNumber = Number(form.value.property.floorNumber)
    }

    await axios.put(`/api/properties/${propertyId.value}`, propertyPayload)

    if (listingType.value === 'sale') {
      await axios.put(`/api/sales/${listingId.value}`, {
        askingPrice: Number(form.value.askingPrice),
        description: form.value.description,
        status: 'active',
      })
    } else {
      await axios.put(`/api/rentals/${listingId.value}`, {
        monthlyRent: Number(form.value.monthlyRent),
        availableFrom: form.value.availableFrom,
        description: form.value.description,
        status: 'active',
      })
    }

    for (const file of newFiles.value) {
      const fd = new FormData()
      fd.append('file', file)
      const { data } = await api.post(`/api/properties/${propertyId.value}/photos`, fd)
      existingPhotos.value.push(data)
    }
    newFiles.value = []

    router.push('/profile')
  } catch {
    error.value = 'A apărut o eroare la salvarea modificărilor. Încearcă din nou.'
  } finally {
    submitting.value = false
  }
}
</script>

<template>
  <div v-if="loading" class="loading-state">Se încarcă...</div>
  <div v-else-if="error && !form.property.address" class="error-state">{{ error }}</div>

  <div v-else class="edit-page">
    <div class="page-header">
      <h2>Editează anunț</h2>
    </div>

    <form @submit.prevent="handleSubmit" class="edit-form">

      <!-- Listing type (read-only) -->
      <div class="form-card">
        <h4 class="card-title">Tip anunț</h4>
        <div class="type-display">
          <span class="type-badge">{{ listingType === 'sale' ? 'Vânzare' : 'Închiriere' }}</span>
          <span class="type-badge">{{ propertyType === 'apartment' ? 'Apartament' : 'Casă' }}</span>
        </div>
      </div>

      <!-- Property details -->
      <div class="form-card">
        <h4 class="card-title">Detalii proprietate</h4>
        <div class="fields-grid">
          <div class="form-group full-width">
            <label>Adresă</label>
            <input v-model="form.property.address" type="text" required placeholder="Str. Exemplu 10, Cluj-Napoca" />
          </div>

          <div class="form-group">
            <label>Număr camere</label>
            <input v-model="form.property.numberOfRooms" type="number" required min="1" />
          </div>

          <div class="form-group">
            <label>Suprafață utilă (m²)</label>
            <input v-model="form.property.floorArea" type="number" required min="1" />
          </div>

          <div class="form-group">
            <label>An construcție</label>
            <input v-model="form.property.dateOfConstruction" type="date" required />
          </div>

          <template v-if="propertyType === 'house'">
            <div class="form-group">
              <label>Număr etaje</label>
              <input v-model="form.property.numberOfFloors" type="number" required min="1" />
            </div>
            <div class="form-group">
              <label>Suprafață teren (m²)</label>
              <input v-model="form.property.plotArea" type="number" required min="1" />
            </div>
          </template>

          <template v-if="propertyType === 'apartment'">
            <div class="form-group">
              <label>Etaj</label>
              <input v-model="form.property.floorNumber" type="number" required min="0" />
            </div>
          </template>
        </div>
      </div>

      <!-- Listing details -->
      <div class="form-card">
        <h4 class="card-title">Detalii anunț</h4>
        <div class="fields-grid">
          <div class="form-group full-width">
            <label>Descriere</label>
            <textarea v-model="form.description" rows="4" placeholder="Descrie proprietatea..."></textarea>
          </div>

          <template v-if="listingType === 'sale'">
            <div class="form-group">
              <label>Preț cerut (EUR)</label>
              <input v-model="form.askingPrice" type="number" required min="0" />
            </div>
          </template>

          <template v-if="listingType === 'rental'">
            <div class="form-group">
              <label>Chirie lunară (EUR)</label>
              <input v-model="form.monthlyRent" type="number" required min="0" />
            </div>
            <div class="form-group">
              <label>Disponibil din</label>
              <input v-model="form.availableFrom" type="date" required />
            </div>
          </template>
        </div>
      </div>

      <!-- Photos -->
      <div class="form-card">
        <h4 class="card-title">Fotografii</h4>
        <div v-if="existingPhotos.length" class="photo-previews">
          <div v-for="photo in existingPhotos" :key="photo.id" class="preview-item">
            <img :src="photo.url" class="preview-img" alt="photo" />
            <button type="button" class="remove-photo-btn" @click="removeExistingPhoto(photo.id)">×</button>
          </div>
        </div>
        <p v-else class="no-photos-text">Nicio fotografie adăugată.</p>
        <label class="upload-label" style="margin-top: 1rem; display: inline-block;">
          <input type="file" multiple accept="image/*" @change="onNewFilesSelected" class="file-input" />
          + Adaugă fotografii
        </label>
        <div v-if="newPreviews.length" class="photo-previews" style="margin-top: 0.5rem;">
          <div v-for="(src, i) in newPreviews" :key="i" class="preview-item">
            <img :src="src" class="preview-img" alt="preview" />
            <button type="button" class="remove-photo-btn" @click="removeNewFile(i)">×</button>
          </div>
        </div>
      </div>

      <p v-if="error" class="error-message">{{ error }}</p>

      <div class="form-actions">
        <button type="button" class="cancel-btn" @click="router.back()">Anulează</button>
        <button type="submit" class="submit-btn" :disabled="submitting">
          {{ submitting ? 'Se salvează...' : 'Salvează modificările' }}
        </button>
      </div>

    </form>
  </div>
</template>

<style scoped>
.loading-state,
.error-state {
  text-align: center;
  padding: 3rem;
  color: var(--color-text-muted);
}

.edit-page {
  max-width: 720px;
  margin: 0 auto;
}

.page-header {
  margin-bottom: 1.5rem;
}

.edit-form {
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

.type-display {
  display: flex;
  gap: 0.75rem;
}

.type-badge {
  padding: 0.4rem 1rem;
  border: 1px solid var(--color-primary);
  border-radius: 4px;
  background: var(--color-primary);
  color: white;
  font-size: 0.95rem;
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

.file-input {
  display: none;
}

.upload-label {
  display: inline-block;
  padding: 0.5rem 1rem;
  border: 1px dashed var(--color-border);
  border-radius: 4px;
  cursor: pointer;
  font-size: 0.9rem;
  color: var(--color-text-muted);
  transition: border-color 0.2s;
}

.upload-label:hover {
  border-color: var(--color-primary);
  color: var(--color-primary);
}

.photo-previews {
  display: flex;
  flex-wrap: wrap;
  gap: 0.5rem;
}

.preview-item {
  position: relative;
  width: 100px;
  height: 80px;
}

.preview-img {
  width: 100%;
  height: 100%;
  object-fit: cover;
  border-radius: 4px;
  border: 1px solid var(--color-border);
}

.remove-photo-btn {
  position: absolute;
  top: -6px;
  right: -6px;
  width: 20px;
  height: 20px;
  border-radius: 50%;
  border: none;
  background: #c62828;
  color: white;
  font-size: 0.85rem;
  line-height: 1;
  cursor: pointer;
  display: flex;
  align-items: center;
  justify-content: center;
  padding: 0;
}

.no-photos-text {
  font-size: 0.9rem;
  color: var(--color-text-muted);
  margin: 0 0 0.5rem;
}
</style>
