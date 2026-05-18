import { defineStore } from 'pinia'
import axios from 'axios'

const api = axios.create({
  baseURL: 'http://localhost:8080/api'
})

export const useAuthStore = defineStore('auth', {
  state: () => ({
    token: localStorage.getItem('token') || null,
    user: JSON.parse(localStorage.getItem('user')) || null,
    loading: false,
    error: null
  }),

  getters: {
    isAuthenticated: (state) => !!state.token,
    isAdmin: (state) => state.user?.role === 'ADMIN',
    currentUsername: (state) => state.user?.username || ''
  },

  actions: {
    async register(username, password) {
      this.loading = true
      this.error = null
      try {
        const response = await api.post('/auth/register', { username, password })

        const { token, id, username: resUsername, role } = response.data

        this.token = token
        this.user = { id, username: resUsername, role }

        localStorage.setItem('token', this.token)
        localStorage.setItem('user', JSON.stringify(this.user))

      } catch (err) {
        this.error = err.response?.data?.message || 'Registration failed'
        throw err
      } finally {
        this.loading = false
      }
    },

    async login(username, password) {
      this.loading = true
      this.error = null
      try {
        const response = await api.post('/auth/login', { username, password })

        const { token, id, username: resUsername, role } = response.data

        this.token = token
        this.user = { id, username: resUsername, role }

        localStorage.setItem('token', this.token)
        localStorage.setItem('user', JSON.stringify(this.user))

      } catch (err) {
        this.error = err.response?.data?.message || 'Invalid username or password'
        throw err
      } finally {
        this.loading = false
      }
    },

    logout() {
      this.token = null
      this.user = null
      localStorage.removeItem('token')
      localStorage.removeItem('user')
    }
  }
})
