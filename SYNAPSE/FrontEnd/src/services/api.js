import axios from 'axios'

const api = axios.create({
  baseURL: import.meta.env.VITE_API_URL || 'http://localhost:8080'
})

api.interceptors.request.use((config) => {
  const token = localStorage.getItem('authToken')
  if (token) {
    config.headers.Authorization = `Bearer ${token}`
  }
  return config
})

function corpo(resposta) {
  return resposta.data
}

export const dashboardService = {
  obter: (loteId) =>
    api.get('/api/dashboard', { params: loteId ? { loteId } : {} }).then(corpo),
  lotes: () => api.get('/api/dashboard/lotes').then(corpo)
}

export default api