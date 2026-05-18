import { createRouter, createWebHistory } from 'vue-router'
import ListingPage from '@/components/ListingPage.vue'
import ListingDetailPage from '@/components/ListingDetailPage.vue'
import ListingCreatePage from '@/components/ListingCreatePage.vue'
import ProfilePage from '@/components/ProfilePage.vue'
import ListingEditPage from '@/components/ListingEditPage.vue'
import AuthPage from "@/components/AuthPage.vue"
import { useAuthStore } from "@/stores/auth.js"

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/auth',
      component: AuthPage,
      meta: { guestOnly: true }
    },
    { path: '/', component: ListingPage, meta: { requiresAuth: true } },
    { path: '/listings/create', component: ListingCreatePage, meta: { requiresAuth: true } },
    { path: '/listings/:id', component: ListingDetailPage, meta: { requiresAuth: true } },
    { path: '/profile', component: ProfilePage, meta: { requiresAuth: true } },
    { path: '/listings/:id/edit', component: ListingEditPage, meta: { requiresAuth: true } },
    {
      path: '/:pathMatch(.*)*',
      redirect: '/auth'
    }
  ],
})

router.beforeEach((to, from) => {
  const authStore = useAuthStore()

  if (to.meta.requiresAuth && !authStore.isAuthenticated) {
    return '/auth'
  }

  if (to.meta.guestOnly && authStore.isAuthenticated) {
    return '/'
  }
})

export default router
