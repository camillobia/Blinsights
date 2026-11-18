<template>
  <InnerShell>
    <main class="mx-auto max-w-6xl space-y-6 px-5 py-8">
      <header class="flex flex-col gap-4 sm:flex-row sm:items-end sm:justify-between">
        <div>
          <p class="text-sm font-semibold uppercase tracking-wide text-emerald-700">Administração</p>
          <h1 class="mt-1 text-3xl font-extrabold text-slate-900">Usuários</h1>
          <p class="mt-1 text-sm text-slate-500">
            Novas contas ficam pendentes até serem aprovadas aqui.
          </p>
        </div>

        <div class="flex flex-wrap gap-2" role="tablist" aria-label="Filtrar por status">
          <button
            v-for="aba in abas"
            :key="aba.valor"
            type="button"
            role="tab"
            :aria-selected="filtro === aba.valor"
            class="rounded-lg border px-3 py-2 text-sm font-semibold transition-colors"
            :class="filtro === aba.valor
              ? 'border-emerald-700 bg-emerald-700 text-white'
              : 'border-slate-300 bg-white text-slate-700 hover:bg-slate-50'"
            @click="filtro = aba.valor"
          >
            {{ aba.rotulo }}
            <span
              class="ml-1 rounded-full px-2 py-0.5 text-xs"
              :class="filtro === aba.valor ? 'bg-white/20' : 'bg-slate-100 text-slate-600'"
            >{{ contagem(aba.valor) }}</span>
          </button>
        </div>
      </header>

      <p v-if="erro" class="rounded-lg border border-red-200 bg-red-50 px-4 py-3 text-sm text-red-700">{{ erro }}</p>
      <p v-if="aviso" class="rounded-lg border border-emerald-200 bg-emerald-50 px-4 py-3 text-sm text-emerald-800">{{ aviso }}</p>

      <section class="overflow-hidden rounded-2xl border border-slate-200 bg-white">
        <div v-if="carregando" class="p-8 text-center text-sm text-slate-500">Carregando usuários…</div>

        <div v-else-if="!visiveis.length" class="p-8 text-center text-sm text-slate-500">
          {{ filtro === 'PENDENTE' ? 'Nenhum cadastro aguardando aprovação.' : 'Nenhum usuário neste filtro.' }}
        </div>

        <div v-else class="overflow-x-auto">
          <table class="w-full text-sm">
            <thead class="bg-slate-50 text-left text-slate-500">
              <tr>
                <th class="px-4 py-3 font-semibold">Nome</th>
                <th class="px-4 py-3 font-semibold">E-mail</th>
                <th class="px-4 py-3 font-semibold">Matrícula / Turma</th>
                <th class="px-4 py-3 font-semibold">Perfil</th>
                <th class="px-4 py-3 font-semibold">Status</th>
                <th class="px-4 py-3 font-semibold text-right">Ações</th>
              </tr>
            </thead>
            <tbody>
              <tr v-for="usuario in visiveis" :key="usuario.id" class="border-t border-slate-100">
                <td class="px-4 py-3 font-medium text-slate-900">
                  {{ usuario.nome }}
                  <span v-if="ehVoce(usuario)" class="ml-1 text-xs font-normal text-slate-400">(você)</span>
                </td>
                <td class="px-4 py-3 text-slate-600">{{ usuario.email }}</td>
                <td class="px-4 py-3 text-slate-600">
                  {{ usuario.matricula || '-' }}<span v-if="usuario.turma" class="text-slate-400"> · {{ usuario.turma }}</span>
                </td>
                <td class="px-4 py-3">
                  <span class="rounded-full px-2.5 py-1 text-xs font-semibold"
                    :class="usuario.perfil === 'ADMIN' ? 'bg-indigo-50 text-indigo-700' : 'bg-slate-100 text-slate-600'">
                    {{ usuario.perfil === 'ADMIN' ? 'Admin' : 'Usuário' }}
                  </span>
                </td>
                <td class="px-4 py-3">
                  <span class="rounded-full px-2.5 py-1 text-xs font-semibold" :class="estiloStatus(usuario.status)">
                    {{ rotuloStatus(usuario.status) }}
                  </span>
                </td>
                <td class="px-4 py-3">
                  <div v-if="!ehVoce(usuario) && confirmando === usuario.id" class="flex flex-wrap items-center justify-end gap-2">
                    <span class="text-xs text-slate-500">Excluir {{ usuario.nome }}? Não pode ser desfeito.</span>
                    <button :disabled="ocupado === usuario.id"
                      class="rounded-lg bg-red-600 px-3 py-1.5 text-xs font-semibold text-white hover:bg-red-700 disabled:opacity-50"
                      @click="excluir(usuario)">Confirmar exclusão</button>
                    <button :disabled="ocupado === usuario.id"
                      class="rounded-lg border border-slate-300 px-3 py-1.5 text-xs font-semibold text-slate-700 hover:bg-slate-50"
                      @click="confirmando = null">Cancelar</button>
                  </div>
                  <div v-else-if="!ehVoce(usuario)" class="flex flex-wrap justify-end gap-2">
                    <button v-if="usuario.status !== 'APROVADO'" :disabled="ocupado === usuario.id"
                      class="rounded-lg bg-emerald-700 px-3 py-1.5 text-xs font-semibold text-white hover:bg-emerald-800 disabled:opacity-50"
                      @click="alterarStatus(usuario, 'APROVADO')">Aprovar</button>
                    <button v-if="usuario.status !== 'BLOQUEADO'" :disabled="ocupado === usuario.id"
                      class="rounded-lg border border-red-200 px-3 py-1.5 text-xs font-semibold text-red-700 hover:bg-red-50 disabled:opacity-50"
                      @click="alterarStatus(usuario, 'BLOQUEADO')">Bloquear</button>
                    <button v-if="usuario.status === 'APROVADO'" :disabled="ocupado === usuario.id"
                      class="rounded-lg border border-slate-300 px-3 py-1.5 text-xs font-semibold text-slate-700 hover:bg-slate-50 disabled:opacity-50"
                      @click="alterarPerfil(usuario, usuario.perfil === 'ADMIN' ? 'USER' : 'ADMIN')">
                      {{ usuario.perfil === 'ADMIN' ? 'Remover admin' : 'Tornar admin' }}
                    </button>
                    <button :disabled="ocupado === usuario.id" :aria-label="`Excluir ${usuario.nome}`"
                      class="rounded-lg border border-red-200 px-3 py-1.5 text-xs font-semibold text-red-700 hover:bg-red-50 disabled:opacity-50"
                      @click="confirmando = usuario.id">Excluir</button>
                  </div>
                  <p v-else class="text-right text-xs text-slate-400">—</p>
                </td>
              </tr>
            </tbody>
          </table>
        </div>
      </section>
    </main>
  </InnerShell>
</template>

<script setup>
import { computed, onMounted, ref } from 'vue'
import InnerShell from '../components/InnerShell.vue'
import { adminService } from '../services/api'
import { useAuthStore } from '../store/Authstore'

const auth = useAuthStore()
const usuarios = ref([])
const filtro = ref('PENDENTE')
const erro = ref('')
const aviso = ref('')
const carregando = ref(false)
const ocupado = ref(null)
const confirmando = ref(null)

const abas = [
  { valor: 'PENDENTE', rotulo: 'Pendentes' },
  { valor: 'APROVADO', rotulo: 'Aprovados' },
  { valor: 'BLOQUEADO', rotulo: 'Bloqueados' },
  { valor: '', rotulo: 'Todos' }
]

const statusDe = (u) => u.status || 'PENDENTE'
const visiveis = computed(() => usuarios.value
  .filter((u) => !filtro.value || statusDe(u) === filtro.value)
  .sort((a, b) => (a.nome || '').localeCompare(b.nome || '', 'pt-BR')))
const contagem = (valor) => valor ? usuarios.value.filter((u) => statusDe(u) === valor).length : usuarios.value.length
const ehVoce = (u) => u.email?.toLowerCase() === auth.usuario?.email?.toLowerCase()

const rotuloStatus = (s) => ({ APROVADO: 'Aprovado', BLOQUEADO: 'Bloqueado' }[s] || 'Pendente')
const estiloStatus = (s) => ({
  APROVADO: 'bg-emerald-50 text-emerald-700',
  BLOQUEADO: 'bg-red-50 text-red-700'
}[s] || 'bg-amber-50 text-amber-700')

const mensagemErro = (falha, padrao) => falha?.response?.data?.erro || padrao

async function carregar() {
  erro.value = ''
  carregando.value = true
  try {
    usuarios.value = await adminService.listar()
  } catch (falha) {
    erro.value = mensagemErro(falha, 'Não foi possível carregar os usuários.')
  } finally {
    carregando.value = false
  }
}

async function executar(usuario, acao, mensagemSucesso) {
  erro.value = ''
  aviso.value = ''
  ocupado.value = usuario.id
  try {
    await acao()
    aviso.value = mensagemSucesso
    await carregar()
  } catch (falha) {
    erro.value = mensagemErro(falha, 'Não foi possível alterar o usuário.')
  } finally {
    ocupado.value = null
  }
}

const alterarStatus = (u, status) => executar(u,
  () => adminService.alterarStatus(u.id, status),
  status === 'APROVADO' ? `${u.nome} foi aprovado(a) e já pode entrar.` : `${u.nome} foi bloqueado(a).`)

const alterarPerfil = (u, perfil) => executar(u,
  () => adminService.alterarPerfil(u.id, perfil),
  perfil === 'ADMIN' ? `${u.nome} agora é administrador(a).` : `${u.nome} deixou de ser administrador(a).`)

const excluir = (u) => executar(u,
  () => adminService.excluir(u.id),
  `${u.nome} foi excluído(a).`)
  .finally(() => { confirmando.value = null })

onMounted(carregar)
</script>
