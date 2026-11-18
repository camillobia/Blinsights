import { createRouter, createWebHistory } from 'vue-router'
import Home from '../views/Home.vue'
import Login from '../views/Login.vue'
import Upload from '../views/Upload.vue'
import Dashboard from '../views/Dashboard.vue'
import RelatorioView from '../views/Relatorio.vue'
import Cadastro from '../views/Cadastro.vue'
import AdminUsuarios from '../views/AdminUsuarios.vue'

const routes = [
  { path: '/', name: 'Home', component: Home },
  { path: '/login', name: 'Login', component: Login },
  { path: '/cadastro', name: 'Cadastro', component: Cadastro, meta: { soVisitante: true } },
  { path: '/admin/usuarios', name: 'AdminUsuarios', component: AdminUsuarios, meta: { requerLogin: true, requerAdmin: true } },
  { path: '/sidebar/upload', name: 'Upload', component: Upload },
  { path: '/dashboard', redirect: '/sidebar/dashboard' },
  { path: '/sidebar/dashboard', name: 'Dashboard', component: Dashboard },
  { path: '/sidebar/relatorio', name: 'Relatorio', component: RelatorioView }
]

const router = createRouter({
  history: createWebHistory(),
  routes
})

router.beforeEach((to) => {
  let sessao = null
  try {
    sessao = JSON.parse(localStorage.getItem('cti-sessao') || 'null')
  } catch {
    sessao = null
  }
  const autenticado = Boolean(sessao?.token)
  const admin = sessao?.usuario?.perfil === 'ADMIN'
  if (to.path.startsWith('/sidebar') && !autenticado) {
    return '/login'
  }
  if (to.meta.requerAdmin && !admin) {
    return '/sidebar/upload'
  }
  if (to.meta.soVisitante && autenticado) {
    return '/sidebar/upload'
  }
  if (to.path === '/login' && autenticado) {
    return '/sidebar/upload'
  }
})

export default router

