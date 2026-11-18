import axios from 'axios'

const api = axios.create({
  baseURL: (import.meta.env.VITE_API_URL || 'http://localhost:8080/api').replace(/\/+$/, ''),
  timeout: 60000
})

// Substituído pela store de login (configurarApi) assim que o app inicia.
let pegarToken = () => null
let aoExpirar = () => {}

export function configurarApi({ getToken, onUnauthorized }) {
  pegarToken = getToken
  aoExpirar = onUnauthorized
}

function rotaPublica(url = '') {
  return url.includes('/auth/login') || url.includes('/auth/register')
}

api.interceptors.request.use((config) => {
  const token = pegarToken()
  // Login e cadastro nunca levam token: um token velho/expirado faria o Spring recusar com 401.
  if (token && !rotaPublica(config.url)) {
    config.headers.Authorization = `Bearer ${token}`
  }
  return config
})

api.interceptors.response.use(
  (resposta) => resposta,
  (erro) => {
    if (erro.response?.status === 401 && !rotaPublica(erro.config?.url)) {
      aoExpirar()
    }
    return Promise.reject(erro)
  }
)

function corpo(resposta) {
  return resposta.data
}

export const dashboardService = {
  obter: (loteId) =>
    api.get('/dashboard', { params: loteId ? { loteId } : {} }).then(corpo),
  lotes: () => api.get('/dashboard/lotes').then(corpo)
}

export const authService = {
  entrar: (email, senha) => api.post('/auth/login', { email, senha }).then(corpo),
  cadastrar: (dados) => api.post('/auth/register', dados).then(corpo),
  eu: () => api.get('/auth/me').then(corpo)
}

export const adminService = {
  listar: (status) => api.get('/admin/usuarios', { params: status ? { status } : {} }).then(corpo),
  alterarStatus: (id, status) => api.patch(`/admin/usuarios/${id}/status`, null, { params: { status } }).then(corpo),
  alterarPerfil: (id, perfil) => api.patch(`/admin/usuarios/${id}/perfil`, null, { params: { perfil } }).then(corpo),
  excluir: (id) => api.delete(`/admin/usuarios/${id}`).then(corpo)
}

export default api