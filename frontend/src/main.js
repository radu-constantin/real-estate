import { createApp } from 'vue'
import { createPinia } from 'pinia'
import axios from "axios";

import App from './App.vue'
import router from './router/index.js'
import PrimeVue from 'primevue/config';
import './style.css'
import './assets/variables.css'

axios.defaults.baseURL = "http://localhost:8080"

const app = createApp(App)

app.use(createPinia())
app.use(router)
app.use(PrimeVue);

app.mount('#app')
