import { computed, ref } from 'vue'
import { defineStore } from 'pinia'
import { authService, configurarApi } from '../services/api'

const CHAVE = 'cti-sessao'

function lerSessao() {
  try {
    return JSON.parse(localStorage.getItem(CHAVE)) || {}
  } catch {
    return {}
  }
}

export const useAuthStore = defineStore('auth', () => {
  const salva = lerSessao()
  const token = ref(salva.token || '')
  const usuario = ref(salva.usuario || null)

  const logado = computed(() => Boolean(token.value))
  const ehAdmin = computed(() => usuario.value?.perfil === 'ADMIN')

  function guardar() {
    localStorage.setItem(CHAVE, JSON.stringify({ token: token.value, usuario: usuario.value }))
  }

  async function entrar(email, senha) {
    const resposta = await authService.entrar(email, senha)
    token.value = resposta.token
    usuario.value = {
      id: resposta.id,
      nome: resposta.nome,
      email: resposta.email,
      perfil: resposta.perfil,
      status: resposta.status
    }
    guardar()
  }

  function sair() {
    token.value = ''
    usuario.value = null
    localStorage.removeItem(CHAVE)
  }

  configurarApi({
    getToken: () => token.value,
    onUnauthorized: sair
  })

  return { token, usuario, logado, ehAdmin, entrar, sair }
})
