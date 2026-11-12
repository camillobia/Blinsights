<template>
  <InnerShell>
    <main class="mx-auto max-w-7xl space-y-6 px-5 py-8">
      <header class="flex flex-col gap-4 sm:flex-row sm:items-end sm:justify-between">
        <div>
          <p class="text-sm font-semibold uppercase tracking-wide text-emerald-700">Análise de dados</p>
          <h1 class="mt-1 text-3xl font-extrabold text-slate-900">Dashboard</h1>
          <p class="mt-1 text-sm text-slate-500">
            Estatísticas calculadas pelo serviço Python a partir do lote selecionado.
          </p>
        </div>
        <div class="flex flex-wrap items-end gap-3">
          <label class="text-sm font-medium text-slate-700">
            Lote processado
            <select
              v-model="loteSelecionado"
              class="mt-1 block min-w-64 rounded-lg border border-slate-300 bg-white px-3 py-2"
              :disabled="carregando"
              @change="carregar(loteSelecionado)"
            >
              <option value="">Lote mais recente</option>
              <option v-for="lote in lotes" :key="lote.id" :value="String(lote.id)">
                #{{ lote.id }} · {{ lote.arquivoNome || lote.arquivo_nome }} ({{ lote.linhasValidas ?? lote.linhas_validas }} válidas)
              </option>
            </select>
          </label>
          <button
            class="rounded-lg border border-slate-300 px-4 py-2 text-sm font-semibold text-slate-700 hover:bg-slate-50"
            @click="sair"
          >
            Sair
          </button>
        </div>
      </header>

      <div v-if="erro" role="alert" class="rounded-xl border border-red-200 bg-red-50 p-4 text-red-800">
        {{ erro }}
        <button class="ml-3 font-semibold underline" @click="carregar(loteSelecionado)">Tentar novamente</button>
      </div>
      <div v-else-if="carregando" class="rounded-xl bg-white p-8 text-center text-slate-500 shadow-sm">
        Carregando estatísticas…
      </div>
      <div v-else-if="estatisticas" class="space-y-6">
        <p v-if="lote" class="text-sm text-slate-500">
          Arquivo: {{ lote.arquivoNome }} · {{ lote.linhasValidas }} de {{ lote.totalLinhas }} linhas válidas
        </p>

        <section class="grid gap-4 sm:grid-cols-2 xl:grid-cols-4">
          <article v-for="item in indicadores" :key="item.titulo" class="rounded-2xl bg-white p-5 shadow-sm">
            <p class="text-sm font-medium text-slate-500">{{ item.titulo }}</p>
            <p class="mt-2 text-2xl font-extrabold text-slate-900">{{ item.valor }}</p>
          </article>
        </section>

        <section class="grid gap-5 lg:grid-cols-2">
          <article v-for="grafico in graficos" :key="grafico.titulo" class="rounded-2xl bg-white p-5 shadow-sm">
            <h2 class="mb-4 text-lg font-bold text-slate-800">{{ grafico.titulo }}</h2>
            <BaseChart
              :tipo="grafico.tipo"
              :titulo="grafico.titulo"
              :rotulos="grafico.rotulos"
              :valores="grafico.valores"
            />
            <p v-if="!grafico.rotulos.length" class="text-sm text-slate-400">Sem dados para este gráfico.</p>
          </article>
        </section>

        <section class="rounded-2xl bg-white p-5 shadow-sm">
          <h2 class="text-lg font-bold text-slate-800">Principais insights</h2>
          <ul v-if="estatisticas.insights?.length" class="mt-3 list-inside list-disc space-y-2 text-slate-600">
            <li v-for="(insight, index) in estatisticas.insights" :key="index">{{ insight }}</li>
          </ul>
          <p v-else class="mt-3 text-sm text-slate-500">Nenhum insight disponível.</p>
        </section>

        <section v-if="estatisticas.topClientes?.length" class="overflow-hidden rounded-2xl bg-white shadow-sm">
          <h2 class="px-5 pt-5 text-lg font-bold text-slate-800">Maiores faturamentos</h2>
          <div class="overflow-x-auto">
            <table class="mt-3 w-full text-left text-sm">
              <thead class="bg-slate-50 text-slate-500">
                <tr>
                  <th class="px-5 py-3">Código</th>
                  <th class="px-5 py-3">Cliente</th>
                  <th class="px-5 py-3 text-right">Faturamento anual</th>
                </tr>
              </thead>
              <tbody>
                <tr v-for="cliente in estatisticas.topClientes" :key="cliente.codigoCliente" class="border-t border-slate-100">
                  <td class="px-5 py-3">{{ cliente.codigoCliente }}</td>
                  <td class="px-5 py-3">{{ cliente.nomeCliente }}</td>
                  <td class="px-5 py-3 text-right">{{ moeda(cliente.faturamentoAnual) }}</td>
                </tr>
              </tbody>
            </table>
          </div>
        </section>
      </div>
      <div v-else class="rounded-xl bg-white p-8 text-center text-slate-500 shadow-sm">
        Não há lotes processados disponíveis.
      </div>
    </main>
  </InnerShell>
</template>

<script setup>
import { computed, onMounted, ref } from 'vue'
import { useRouter } from 'vue-router'
import InnerShell from '../components/InnerShell.vue'
import BaseChart from '../components/BaseChart.vue'
import { dashboardService } from '../services/api'

const lotes = ref([])
const router = useRouter()
const lote = ref(null)
const estatisticas = ref(null)
const loteSelecionado = ref('')
const erro = ref('')
const carregando = ref(false)

const moeda = (valor) => Number(valor || 0).toLocaleString('pt-BR', {
  style: 'currency',
  currency: 'BRL'
})

function sair() {
  localStorage.removeItem('authToken')
  router.push('/login')
}

const indicadores = computed(() => {
  const resumo = estatisticas.value?.resumo
  if (!resumo) return []

  return [
    { titulo: 'Clientes analisados', valor: Number(resumo.totalClientes || 0).toLocaleString('pt-BR') },
    { titulo: 'Faturamento total', valor: moeda(resumo.totalFaturamento) },
    { titulo: 'Faturamento médio', valor: moeda(resumo.media) },
    { titulo: 'Mediana do faturamento', valor: moeda(resumo.mediana) }
  ]
})

const graficos = computed(() => {
  const dados = estatisticas.value
  if (!dados) return []

  return [
    {
      titulo: 'Clientes por segmento',
      tipo: 'bar',
      rotulos: (dados.porSegmento || []).map((item) => item.segmento),
      valores: (dados.porSegmento || []).map((item) => item.clientes)
    },
    {
      titulo: 'Clientes por nível',
      tipo: 'doughnut',
      rotulos: (dados.porNivel || []).map((item) => item.nivel),
      valores: (dados.porNivel || []).map((item) => item.clientes)
    },
    {
      titulo: 'Clientes por consultor',
      tipo: 'bar',
      rotulos: (dados.porConsultor || []).map((item) => item.consultor),
      valores: (dados.porConsultor || []).map((item) => item.clientes)
    },
    {
      titulo: 'Serviços contratados',
      tipo: 'bar',
      rotulos: (dados.porServico || []).map((item) => item.servico),
      valores: (dados.porServico || []).map((item) => item.clientes)
    },
    {
      titulo: 'Contratações por mês',
      tipo: 'line',
      rotulos: (dados.porMes || []).map((item) => item.mes),
      valores: (dados.porMes || []).map((item) => item.contratos)
    }
  ]
})

async function carregar(loteId = '') {
  carregando.value = true
  erro.value = ''

  try {
    const resposta = await dashboardService.obter(loteId || undefined)
    lote.value = resposta.lote
    estatisticas.value = resposta.estatisticas
    loteSelecionado.value = String(resposta.lote.id)
  } catch (falha) {
    lote.value = null
    estatisticas.value = null
    erro.value = falha.response?.data?.detail ||
      'Não foi possível carregar o dashboard. Verifique a conexão com o backend.'
  } finally {
    carregando.value = false
  }
}

onMounted(async () => {
  try {
    lotes.value = await dashboardService.lotes()
    await carregar()
  } catch (falha) {
    erro.value = falha.response?.data?.detail ||
      'Não foi possível carregar os lotes processados.'
  }
})
</script>
