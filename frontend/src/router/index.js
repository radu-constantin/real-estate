import { createRouter, createWebHistory } from 'vue-router'
import ListingPage from '@/components/ListingPage.vue'
import ListingDetailPage from '@/components/ListingDetailPage.vue'
import ListingCreatePage from '@/components/ListingCreatePage.vue'

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    { path: '/', component: ListingPage },
    { path: '/listings/create', component: ListingCreatePage },
    { path: '/listings/:id', component: ListingDetailPage },
  ],
})

export default router
