<template>
  <div class="min-h-screen" style="background:#F8FAFC;color:#0E1B30">
    <header
      class="h-20 flex items-center px-5 md:px-8 sticky top-0 z-40"
      style="background:#0E1B30"
    >
      <div class="flex items-center gap-3">
        <button
          class="lg:hidden w-10 h-10 rounded-lg flex items-center justify-center"
          style="background:rgba(255,255,255,0.08)"
          @click="menuOpen = !menuOpen"
        >
          <svg width="22" height="22" viewBox="0 0 24 24" fill="none" stroke="white" stroke-width="2">
            <path d="M4 6h16M4 12h16M4 18h16"/>
          </svg>
        </button>

        <div
          class="w-10 h-10 rounded-xl flex items-center justify-center"
          style="background:#0F6E56"
        >
          <span class="text-white font-extrabold text-lg">S</span>
        </div>

        <div>
          <div class="text-white text-xl font-extrabold leading-none">
            Syn<span style="color:#3EA083">apse</span>
          </div>
          <div class="text-[10px] mt-1" style="color:#94A3B8">
            Análise de dados
          </div>
        </div>
      </div>

      <div class="ml-auto flex items-center gap-3">
        <button
          class="hidden sm:flex items-center gap-2 px-4 py-2.5 rounded-lg text-xs font-bold"
          style="background:#0F6E56;color:white"
          @click="goUpload"
        >
          <svg width="16" height="16" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
            <path d="M12 3v12"/>
            <path d="m7 8 5-5 5 5"/>
            <path d="M5 21h14"/>
          </svg>
          Nova planilha
        </button>

        <button
          class="w-10 h-10 rounded-full flex items-center justify-center font-bold text-sm"
          style="background:#E2E8F0;color:#0E1B30"
        >
          B
        </button>
      </div>
    </header>

    <div
      v-if="menuOpen"
      class="fixed inset-0 z-50 lg:hidden"
      style="background:rgba(14,27,48,0.5)"
      @click="menuOpen = false"
    >
      <aside
        class="w-72 h-full p-5"
        style="background:#0E1B30"
        @click.stop
      >
        <div class="flex items-center justify-between mb-8">
          <div class="text-white text-xl font-extrabold">
            Syn<span style="color:#3EA083">apse</span>
          </div>

          <button
            class="text-white"
            @click="menuOpen = false"
          >
            ✕
          </button>
        </div>

        <div class="space-y-2">
          <button
            v-for="item in dashNavItems"
            :key="item.id"
            class="w-full flex items-center gap-3 px-4 py-3 rounded-xl text-left transition"
            :style="{
              background: dashView === item.id ? '#0F6E56' : 'transparent',
              color: dashView === item.id ? '#fff' : '#CBD5E1'
            }"
            @click="dashView = item.id; menuOpen = false"
          >
            <svg width="18" height="18" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="1.8">
              <path :d="item.icon"/>
            </svg>
            <span class="text-sm font-semibold">
              {{ item.label }}
            </span>
          </button>
        </div>

        <button
          class="w-full mt-8 px-4 py-3 rounded-xl text-sm font-semibold text-left"
          style="color:#CBD5E1;background:rgba(255,255,255,0.05)"
          @click="goHome"
        >
          ← Voltar para início
        </button>
      </aside>
    </div>

    <div class="flex">
      <aside
        class="hidden lg:flex w-64 min-h-[calc(100vh-80px)] flex-col p-4 sticky top-20 self-start"
        style="background:#0E1B30"
      >
        <div class="mb-5 px-3">
          <p
            class="text-[10px] uppercase tracking-[0.18em] font-bold"
            style="color:#64748B"
          >
            Análises
          </p>
        </div>

        <nav class="space-y-1">
          <button
            v-for="item in dashNavItems"
            :key="item.id"
            class="w-full flex items-center gap-3 px-3 py-3 rounded-xl text-left transition-all"
            :style="{
              background: dashView === item.id ? '#0F6E56' : 'transparent',
              color: dashView === item.id ? '#fff' : '#94A3B8'
            }"
            @click="dashView = item.id"
          >
            <svg
              width="18"
              height="18"
              viewBox="0 0 24 24"
              fill="none"
              stroke="currentColor"
              stroke-width="1.8"
              class="shrink-0"
            >
              <path :d="item.icon"/>
            </svg>

            <span class="text-xs font-semibold">
              {{ item.label }}
            </span>
          </button>
        </nav>

        <div class="mt-auto pt-5">
          <button
            class="w-full px-3 py-3 rounded-xl text-xs font-semibold text-left"
            style="color:#94A3B8;background:rgba(255,255,255,0.04)"
            @click="goHome"
          >
            ← Voltar para início
          </button>
        </div>
      </aside>

      <main class="flex-1 min-w-0">
        <div class="max-w-[1500px] mx-auto px-4 md:px-8 py-7">
          <div
            class="rounded-2xl px-5 md:px-7 py-6 mb-6"
            style="background:white;border:1px solid #E2E8F0"
          >
            <div class="flex flex-col md:flex-row md:items-center md:justify-between gap-5">
              <div>
                <div class="flex items-center gap-2 mb-2">
                  <span
                    class="px-2.5 py-1 rounded-full text-[10px] font-bold uppercase tracking-wider"
                    style="background:#E8F5F0;color:#0F6E56"
                  >
                    {{ currentNav?.shortLabel }}
                  </span>
                </div>

                <h1 class="text-2xl md:text-3xl font-extrabold">
                  {{ currentTitle.title }}
                </h1>

                <p class="text-sm mt-1" style="color:#94A3B8">
                  {{ currentTitle.subtitle }}
                </p>
              </div>

              <button
                class="flex items-center justify-center gap-2 px-4 py-2.5 rounded-lg text-xs font-bold shrink-0"
                style="background:#F1F5F9;color:#475569"
              >
                <svg width="16" height="16" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
                  <path d="M21 15v4a2 2 0 0 1-2 2H5a2 2 0 0 1-2-2v-4"/>
                  <polyline points="7 10 12 15 17 10"/>
                  <line x1="12" y1="15" x2="12" y2="3"/>
                </svg>
                Exportar PDF
              </button>
            </div>
          </div>

          <section v-if="dashView === 'carteira'">
            <div class="flex flex-wrap gap-3 mb-6">
              <FilterPills
                label="Segmento"
                :options="['Varejo','Serviços','Online','Atacado','Indústria']"
                v-model="segFilt"
              />
            </div>

            <div class="grid grid-cols-1 sm:grid-cols-2 xl:grid-cols-4 gap-4 mb-6">
              <KpiCard
                label="Total de clientes"
                :value="totalClientes.toLocaleString('pt-BR')"
                sub="Carteira total"
                accent
              />
              <KpiCard
                label="Nível A"
                :value="aTotal.toLocaleString('pt-BR')"
                sub="25% da carteira"
              />
              <KpiCard
                label="Nível B"
                :value="bTotal.toLocaleString('pt-BR')"
                sub="45% da carteira"
              />
              <KpiCard
                label="Nível C"
                :value="cTotal.toLocaleString('pt-BR')"
                sub="30% da carteira"
              />
            </div>

            <div class="grid grid-cols-1 xl:grid-cols-2 gap-5 mb-5">
              <ChartCard
                title="Clientes por Segmento"
                subtitle="Quantidade de clientes por segmento de mercado"
              >
                <SimpleBarChart
                  :data="segmentoData"
                  label-key="nome"
                  value-key="clientes"
                  :color="ACCENT"
                  horizontal
                />
              </ChartCard>

              <ChartCard
                title="Distribuição por Nível"
                subtitle="Composição da carteira por nível A, B e C"
              >
                <DonutChart :data="nivelPieData" />
              </ChartCard>
            </div>

            <ChartCard
              title="Serviços Contratados"
              subtitle="Quantidade de clientes por serviço"
            >
              <SimpleBarChart
                :data="servicosData"
                label-key="nome"
                value-key="qtd"
                :color="GREEN"
              />
            </ChartCard>
          </section>

          <section v-else-if="dashView === 'faturamento'">
            <div class="flex flex-wrap gap-5 mb-6">
              <FilterPills
                label="Segmento"
                :options="['Varejo','Serviços','Online','Atacado','Indústria']"
                v-model="faturamentoSegFilt"
              />

              <FilterPills
                label="Nível"
                :options="['A','B','C']"
                v-model="nivelFilt"
              />
            </div>

            <div class="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-3 xl:grid-cols-5 gap-4 mb-6">
              <KpiCard
                label="Média"
                value="R$ 2.184"
                sub="faturamento médio"
                accent
              />
              <KpiCard
                label="Mediana"
                value="R$ 1.850"
                sub="valor central"
              />
              <KpiCard
                label="Q1"
                value="R$ 980"
                sub="25º percentil"
              />
              <KpiCard
                label="Q3"
                value="R$ 2.840"
                sub="75º percentil"
              />
              <KpiCard
                label="Desvio padrão"
                value="R$ 1.420"
                sub="dispersão"
              />
            </div>

            <div class="grid grid-cols-1 xl:grid-cols-2 gap-5 mb-5">
              <ChartCard
                title="Histograma de Faturamento"
                subtitle="Distribuição de frequência por faixa de valor (R$)"
              >
                <SimpleBarChart
                  :data="histData"
                  label-key="range"
                  value-key="freq"
                  :color="ACCENT"
                />
              </ChartCard>

              <ChartCard
                title="Resumo Estatístico"
                subtitle="Principais medidas da distribuição"
              >
                <div class="space-y-3 py-2">
                  <div
                    v-for="row in [
                      ['Mínimo','R$ 350'],
                      ['Q1','R$ 980'],
                      ['Mediana','R$ 1.850'],
                      ['Média','R$ 2.184'],
                      ['Q3','R$ 2.840'],
                      ['Máximo','R$ 8.200']
                    ]"
                    :key="row[0]"
                    class="flex items-center justify-between py-3 border-b"
                    style="border-color:#F1F5F9"
                  >
                    <span class="text-sm" style="color:#64748B">
                      {{ row[0] }}
                    </span>
                    <span class="text-sm font-bold">
                      {{ row[1] }}
                    </span>
                  </div>
                </div>
              </ChartCard>
            </div>

            <ChartCard
              title="Boxplot — Faturamento por Segmento"
              subtitle="Q1, mediana, Q3 e intervalos interquartil por segmento de mercado"
            >
              <BoxPlotChart :data="boxplotData" />

              <div
                class="flex flex-wrap items-center gap-6 mt-4 pt-4"
                :style="{ borderTop: '1px solid #F1F5F9' }"
              >
                <span
                  class="flex items-center gap-2 text-xs"
                  style="color:#64748B"
                >
                  <span
                    class="w-8 h-0.5 rounded"
                    style="background:#CBD5E1"
                  ></span>
                  Mín / Máx (whiskers)
                </span>

                <span
                  class="flex items-center gap-2 text-xs"
                  style="color:#64748B"
                >
                  <span
                    class="w-8 h-0.5 rounded"
                    :style="{ background: ACCENT }"
                  ></span>
                  Q1–Q3 (IQR)
                </span>

                <span
                  class="flex items-center gap-2 text-xs"
                  style="color:#64748B"
                >
                  <span
                    class="w-8 h-0.5 rounded"
                    :style="{ background: GREEN }"
                  ></span>
                  Mediana
                </span>
              </div>
            </ChartCard>
          </section>

          <section v-else-if="dashView === 'associacoes'">
            <div class="flex flex-col md:flex-row md:items-center md:justify-between gap-4 mb-6">
              <NivelLegend />

              <div
                class="flex rounded-lg p-1"
                style="background:#F1F5F9"
              >
                <button
                  class="px-4 py-2 rounded-md text-xs font-bold"
                  :style="{
                    background: assocMode === 'grouped' ? '#fff' : 'transparent',
                    color: assocMode === 'grouped' ? NAVY : '#64748B',
                    boxShadow: assocMode === 'grouped' ? '0 1px 3px rgba(0,0,0,0.08)' : 'none'
                  }"
                  @click="assocMode = 'grouped'"
                >
                  Agrupado
                </button>

                <button
                  class="px-4 py-2 rounded-md text-xs font-bold"
                  :style="{
                    background: assocMode === 'stacked' ? '#fff' : 'transparent',
                    color: assocMode === 'stacked' ? NAVY : '#64748B',
                    boxShadow: assocMode === 'stacked' ? '0 1px 3px rgba(0,0,0,0.08)' : 'none'
                  }"
                  @click="assocMode = 'stacked'"
                >
                  Empilhado
                </button>
              </div>
            </div>

            <div class="grid grid-cols-1 xl:grid-cols-2 gap-5 mb-5">
              <ChartCard
                title="Segmento × Nível"
                subtitle="Distribuição dos níveis A, B e C por segmento"
              >
                <GroupedBarChart
                  :data="assocSegNivel"
                  label-key="seg"
                  :series="[
                    { key:'A', name:'Nível A', color:COLOR_A },
                    { key:'B', name:'Nível B', color:COLOR_B },
                    { key:'C', name:'Nível C', color:COLOR_C }
                  ]"
                  :stacked="assocMode === 'stacked'"
                />
              </ChartCard>

              <ChartCard
                title="Nível × Serviços"
                subtitle="Quantidade de serviços contratados por nível"
              >
                <GroupedBarChart
                  :data="assocNivelSvcs"
                  label-key="nivel"
                  :series="[
                    { key:'s1', name:'Consultoria', color:COLOR_A },
                    { key:'s2', name:'Suporte', color:COLOR_B },
                    { key:'s3', name:'Manutenção', color:COLOR_C },
                    { key:'s4', name:'Treinamento', color:'#8B5CF6' }
                  ]"
                  :stacked="assocMode === 'stacked'"
                />
              </ChartCard>
            </div>

            <div
              class="rounded-2xl p-6"
              style="background:#E8F5F0;border:1px solid #CDE9DE"
            >
              <div class="flex gap-4">
                <div
                  class="w-10 h-10 rounded-xl flex items-center justify-center shrink-0"
                  style="background:#0F6E56;color:white"
                >
                  <svg width="18" height="18" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
                    <path d="M12 20V10"/>
                    <path d="M18 20V4"/>
                    <path d="M6 20v-4"/>
                  </svg>
                </div>

                <div>
                  <h3 class="font-extrabold text-sm">
                    Oportunidade de upsell
                  </h3>

                  <p class="text-sm mt-1" style="color:#527064">
                    Clientes dos níveis B e C apresentam concentração relevante em serviços básicos,
                    indicando oportunidade para campanhas de expansão de contratos.
                  </p>
                </div>
              </div>
            </div>
          </section>

          <section v-else-if="dashView === 'distribuicao'">
            <div class="grid grid-cols-1 md:grid-cols-3 gap-4 mb-6">
              <div
                v-for="item in distribuicaoData"
                :key="item.name"
                class="rounded-2xl p-5"
                style="background:white;border:1px solid #E2E8F0"
              >
                <div class="flex items-center justify-between mb-4">
                  <span
                    class="w-10 h-10 rounded-xl flex items-center justify-center text-white font-bold"
                    :style="{ background:item.color }"
                  >
                    {{ item.name.replace('Nível ','') }}
                  </span>

                  <span class="text-sm font-bold" style="color:#64748B">
                    {{ item.pct }}%
                  </span>
                </div>

                <p class="text-2xl font-extrabold">
                  {{ item.value.toLocaleString('pt-BR') }}
                </p>

                <p class="text-xs mt-1" style="color:#94A3B8">
                  {{ item.name }}
                </p>

                <div
                  class="h-2 rounded-full mt-4 overflow-hidden"
                  style="background:#F1F5F9"
                >
                  <div
                    class="h-full rounded-full"
                    :style="{
                      width:item.pct + '%',
                      background:item.color
                    }"
                  ></div>
                </div>
              </div>
            </div>

            <div class="grid grid-cols-1 xl:grid-cols-2 gap-5 mb-5">
              <ChartCard
                title="Distribuição da Carteira"
                subtitle="Proporção de clientes por nível"
              >
                <DonutChart :data="distribuicaoData" />
              </ChartCard>

              <ChartCard
                title="Quantidade Absoluta"
                subtitle="Comparação direta entre os níveis"
              >
                <SimpleBarChart
                  :data="distribuicaoData"
                  label-key="name"
                  value-key="value"
                  :colors="[COLOR_A,COLOR_B,COLOR_C]"
                  horizontal
                />
              </ChartCard>
            </div>

            <ChartCard
              title="Resumo da Carteira"
              subtitle="Visão consolidada dos níveis"
            >
              <div class="overflow-x-auto">
                <table class="w-full text-left">
                  <thead>
                    <tr
                      class="text-xs uppercase tracking-wider"
                      style="color:#94A3B8;border-bottom:1px solid #E2E8F0"
                    >
                      <th class="py-3 px-3">Nível</th>
                      <th class="py-3 px-3">Clientes</th>
                      <th class="py-3 px-3">Participação</th>
                      <th class="py-3 px-3">Status</th>
                    </tr>
                  </thead>

                  <tbody>
                    <tr
                      v-for="item in distribuicaoData"
                      :key="item.name"
                      style="border-bottom:1px solid #F1F5F9"
                    >
                      <td class="py-4 px-3 font-bold">
                        {{ item.name }}
                      </td>

                      <td class="py-4 px-3">
                        {{ item.value.toLocaleString('pt-BR') }}
                      </td>

                      <td class="py-4 px-3">
                        {{ item.pct }}%
                      </td>

                      <td class="py-4 px-3">
                        <span
                          class="px-2.5 py-1 rounded-full text-xs font-bold"
                          :style="{
                            background:item.name === 'Nível A'
                              ? '#E8F5F0'
                              : item.name === 'Nível B'
                              ? '#EFF6FF'
                              : '#FFF7ED',
                            color:item.color
                          }"
                        >
                          {{ item.name === 'Nível A' ? 'Premium' : item.name === 'Nível B' ? 'Intermediário' : 'Base' }}
                        </span>
                      </td>
                    </tr>
                  </tbody>
                </table>
              </div>
            </ChartCard>
          </section>

          <section v-else-if="dashView === 'evolucao'">
            <div class="flex flex-wrap gap-3 mb-6">
              <FilterPills
                label="Segmento"
                :options="['Varejo','Serviços','Online','Atacado','Indústria']"
                v-model="evolucaoSegFilt"
              />
            </div>

            <div class="grid grid-cols-1 xl:grid-cols-2 gap-5 mb-5">
              <ChartCard
                title="Evolução de Contratações"
                subtitle="Quantidade de novos contratos ao longo de 2026"
              >
                <LineChartCustom :data="evolucaoData" />
              </ChartCard>

              <ChartCard
                title="Correlação — Serviços × Faturamento"
                subtitle="Relação entre quantidade de serviços e faturamento"
              >
                <ScatterChartCustom :data="scatterData" />
              </ChartCard>
            </div>

            <div class="grid grid-cols-1 xl:grid-cols-2 gap-5">
              <ChartCard
                title="Distribuição de Faturamento"
                subtitle="Frequência por faixa de faturamento"
              >
                <SimpleBarChart
                  :data="histData"
                  label-key="range"
                  value-key="freq"
                  :color="ACCENT"
                />
              </ChartCard>

              <ChartCard
                title="Faturamento por Segmento"
                subtitle="Dispersão dos valores por segmento"
              >
                <BoxPlotChart :data="boxplotData" />
              </ChartCard>
            </div>
          </section>
        </div>
      </main>
    </div>
  </div>
</template>

<script setup>
import { computed, ref } from 'vue'
import { useRouter } from 'vue-router'

const router = useRouter()

const NAVY = '#0E1B30'
const GREEN = '#0F6E56'
const ACCENT = '#3EA083'
const RED = '#E33838'
const BLUE = '#2563EB'
const ORANGE = '#F97316'

const COLOR_A = GREEN
const COLOR_B = BLUE
const COLOR_C = ORANGE

const menuOpen = ref(false)
const dashView = ref('carteira')
const segFilt = ref('Todos')
const faturamentoSegFilt = ref('Todos')
const nivelFilt = ref('Todos')
const evolucaoSegFilt = ref('Todos')
const assocMode = ref('grouped')

const totalClientes = 1248
const aTotal = 312
const bTotal = 562
const cTotal = 374

const dashNavItems = [
  {
    id: 'carteira',
    label: 'Perfil da Carteira',
    shortLabel: 'Carteira',
    icon: 'M17 21v-2a4 4 0 0 0-4-4H5a4 4 0 0 0-4 4v2M23 21v-2a4 4 0 0 0-3-3.87M16 3.13a4 4 0 1 1 0 7.75M9 7a4 4 0 1 0 0 8 4 4 0 0 0-8z'
  },
  {
    id: 'faturamento',
    label: 'Análise de Faturamento',
    shortLabel: 'Faturamento',
    icon: 'M12 2v20M17 5H9.5a3.5 3.5 0 0 0 0 7h5a3.5 3.5 0 0 1 0 7H6'
  },
  {
    id: 'associacoes',
    label: 'Associações',
    shortLabel: 'Associações',
    icon: 'M8 6h13M8 12h13M8 18h13M3 6h.01M3 12h.01M3 18h.01'
  },
  {
    id: 'distribuicao',
    label: 'Distribuição da Carteira',
    shortLabel: 'Distribuição',
    icon: 'M21 21l-6-6m2-5a7 7 0 1 1-14 0 7 7 0 0 1 14 0z'
  },
  {
    id: 'evolucao',
    label: 'Evolução e Correlações',
    shortLabel: 'Evolução',
    icon: 'M23 6l-9.5 9.5-5-5L1 18'
  }
]

const dashViewTitles = {
  carteira: {
    title: 'Perfil da Carteira',
    subtitle: 'Segmentos, níveis A/B/C e serviços contratados'
  },
  faturamento: {
    title: 'Análise de Faturamento',
    subtitle: 'Estatísticas descritivas, histograma e boxplot'
  },
  associacoes: {
    title: 'Associações',
    subtitle: 'Cruzamentos entre segmento, nível e serviços'
  },
  distribuicao: {
    title: 'Distribuição da Carteira',
    subtitle: 'Proporção de clientes A × B × C'
  },
  evolucao: {
    title: 'Evolução e Correlações',
    subtitle: 'Contratações no tempo, dispersão e distribuição'
  }
}

const currentTitle = computed(() => dashViewTitles[dashView.value])

const currentNav = computed(() =>
  dashNavItems.find(item => item.id === dashView.value)
)

const segmentoData = [
  { nome: 'Varejo', clientes: 342 },
  { nome: 'Serviços', clientes: 287 },
  { nome: 'Online', clientes: 265 },
  { nome: 'Atacado', clientes: 198 },
  { nome: 'Indústria', clientes: 156 }
]

const nivelPieData = [
  { name: 'Nível A', value: 312, color: COLOR_A },
  { name: 'Nível B', value: 562, color: COLOR_B },
  { name: 'Nível C', value: 374, color: COLOR_C }
]

const servicosData = [
  { nome: 'Consultoria', qtd: 428 },
  { nome: 'Suporte', qtd: 356 },
  { nome: 'Manutenção', qtd: 312 },
  { nome: 'Treinamento', qtd: 287 },
  { nome: 'Implantação', qtd: 198 }
]

const histData = [
  { range: '0–500', freq: 45 },
  { range: '500–1k', freq: 98 },
  { range: '1k–1.5k', freq: 187 },
  { range: '1.5k–2k', freq: 234 },
  { range: '2k–2.5k', freq: 198 },
  { range: '2.5k–3k', freq: 142 },
  { range: '3k–4k', freq: 87 },
  { range: '4k+', freq: 57 }
]

const boxplotData = [
  { seg: 'Varejo', min: 580, q1: 1200, med: 1850, q3: 2600, max: 5400 },
  { seg: 'Serviços', min: 450, q1: 980, med: 1620, q3: 2200, max: 4800 },
  { seg: 'Atacado', min: 820, q1: 1800, med: 2400, q3: 3200, max: 7800 },
  { seg: 'Indústria', min: 1200, q1: 2200, med: 3100, q3: 4500, max: 8200 },
  { seg: 'Online', min: 350, q1: 780, med: 1240, q3: 1800, max: 3600 }
]

const assocSegNivel = [
  { seg: 'Varejo', A: 68, B: 156, C: 118 },
  { seg: 'Serviços', A: 82, B: 128, C: 77 },
  { seg: 'Atacado', A: 54, B: 89, C: 55 },
  { seg: 'Indústria', A: 62, B: 64, C: 30 },
  { seg: 'Online', A: 46, B: 125, C: 94 }
]

const assocNivelSvcs = [
  { nivel: 'A', s1: 45, s2: 98, s3: 112, s4: 57 },
  { nivel: 'B', s1: 128, s2: 198, s3: 154, s4: 82 },
  { nivel: 'C', s1: 198, s2: 112, s3: 48, s4: 16 }
]

const evolucaoData = [
  { mes: 'Jan/26', contratos: 42 },
  { mes: 'Fev/26', contratos: 58 },
  { mes: 'Mar/26', contratos: 47 },
  { mes: 'Abr/26', contratos: 73 },
  { mes: 'Mai/26', contratos: 89 },
  { mes: 'Jun/26', contratos: 64 },
  { mes: 'Jul/26', contratos: 95 },
  { mes: 'Ago/26', contratos: 112 }
]

const scatterData = [
  { servicos: 1, fat: 850 },
  { servicos: 1, fat: 920 },
  { servicos: 1, fat: 680 },
  { servicos: 1, fat: 750 },
  { servicos: 1, fat: 1050 },
  { servicos: 2, fat: 1450 },
  { servicos: 2, fat: 1820 },
  { servicos: 2, fat: 1240 },
  { servicos: 2, fat: 1600 },
  { servicos: 2, fat: 1100 },
  { servicos: 2, fat: 1980 },
  { servicos: 3, fat: 2100 },
  { servicos: 3, fat: 2450 },
  { servicos: 3, fat: 1980 },
  { servicos: 3, fat: 2800 },
  { servicos: 3, fat: 2200 },
  { servicos: 3, fat: 2600 },
  { servicos: 4, fat: 3200 },
  { servicos: 4, fat: 2900 },
  { servicos: 4, fat: 3600 },
  { servicos: 4, fat: 3100 },
  { servicos: 4, fat: 3400 },
  { servicos: 5, fat: 4200 },
  { servicos: 5, fat: 3800 },
  { servicos: 5, fat: 5100 },
  { servicos: 5, fat: 4800 },
  { servicos: 5, fat: 4500 }
]

const distribuicaoData = [
  { name: 'Nível A', value: 312, color: COLOR_A, pct: 25 },
  { name: 'Nível B', value: 562, color: COLOR_B, pct: 45 },
  { name: 'Nível C', value: 374, color: COLOR_C, pct: 30 }
]

function goHome() {
  menuOpen.value = false
  router.push('/')
}

function goUpload() {
  menuOpen.value = false
  router.push('/sidebar/upload')
}
</script>

<script>
export default {
  components: {
    KpiCard: {
      props: {
        label: String,
        value: String,
        sub: String,
        accent: Boolean
      },
      template: `
        <div
          class="rounded-2xl p-5"
          style="background:#fff;border:1px solid #E2E8F0;box-shadow:0 1px 12px rgba(14,27,48,0.05)"
        >
          <p
            class="text-xs font-bold tracking-widest uppercase mb-2"
            style="color:#94A3B8"
          >
            {{ label }}
          </p>

          <p
            class="text-2xl font-extrabold"
            :style="{color:accent ? '#0F6E56' : '#0E1B30'}"
          >
            {{ value }}
          </p>

          <p
            v-if="sub"
            class="text-xs mt-1"
            style="color:#94A3B8"
          >
            {{ sub }}
          </p>
        </div>
      `
    },

    ChartCard: {
      props: {
        title: String,
        subtitle: String
      },
      template: `
        <div
          class="rounded-2xl p-6"
          style="background:#fff;border:1px solid #E2E8F0;box-shadow:0 1px 12px rgba(14,27,48,0.05)"
        >
          <div class="mb-4">
            <h3
              class="text-sm font-extrabold"
              style="color:#0E1B30"
            >
              {{ title }}
            </h3>

            <p
              v-if="subtitle"
              class="text-xs mt-0.5"
              style="color:#94A3B8"
            >
              {{ subtitle }}
            </p>
          </div>

          <slot />
        </div>
      `
    },

    FilterPills: {
      props: {
        label: String,
        options: Array,
        modelValue: String
      },
      emits: ['update:modelValue'],
      template: `
        <div class="flex items-center gap-2 flex-wrap">
          <span
            class="text-xs font-semibold"
            style="color:#64748B"
          >
            {{ label }}:
          </span>

          <button
            v-for="opt in ['Todos', ...options]"
            :key="opt"
            @click="$emit('update:modelValue', opt)"
            class="px-3 py-1 rounded-full text-xs font-semibold transition-all"
            :style="{
              background:modelValue === opt ? '#0E1B30' : '#F1F5F9',
              color:modelValue === opt ? '#fff' : '#64748B'
            }"
          >
            {{ opt }}
          </button>
        </div>
      `
    },

    NivelLegend: {
      template: `
        <div class="flex items-center gap-4 flex-wrap">
          <span
            class="flex items-center gap-1.5 text-xs font-semibold"
            style="color:#64748B"
          >
            <span
              class="w-3 h-3 rounded-sm"
              style="background:#0F6E56"
            ></span>
            Nível A
          </span>

          <span
            class="flex items-center gap-1.5 text-xs font-semibold"
            style="color:#64748B"
          >
            <span
              class="w-3 h-3 rounded-sm"
              style="background:#2563EB"
            ></span>
            Nível B
          </span>

          <span
            class="flex items-center gap-1.5 text-xs font-semibold"
            style="color:#64748B"
          >
            <span
              class="w-3 h-3 rounded-sm"
              style="background:#F97316"
            ></span>
            Nível C
          </span>
        </div>
      `
    },

    SimpleBarChart: {
      props: {
        data: Array,
        labelKey: String,
        valueKey: String,
        color: String,
        colors: Array,
        horizontal: Boolean
      },

      computed: {
        maxValue() {
          return Math.max(
            ...this.data.map(item => Number(item[this.valueKey]))
          )
        }
      },

      template: `
        <div
          v-if="horizontal"
          class="space-y-4 py-2"
        >
          <div
            v-for="(item,index) in data"
            :key="index"
          >
            <div class="flex items-center gap-3 mb-1">
              <span
                class="w-20 text-xs font-medium text-right"
                style="color:#475569"
              >
                {{ item[labelKey] }}
              </span>

              <div
                class="flex-1 h-7 rounded-md overflow-hidden"
                style="background:#F8FAFC"
              >
                <div
                  class="h-full rounded-md flex items-center justify-end pr-2 transition-all"
                  :style="{
                    width:(Number(item[valueKey]) / maxValue * 100) + '%',
                    background:colors
                      ? colors[index % colors.length]
                      : color
                  }"
                >
                  <span class="text-[10px] font-bold text-white">
                    {{ item[valueKey] }}
                  </span>
                </div>
              </div>
            </div>
          </div>
        </div>

        <div
          v-else
          class="overflow-x-auto"
        >
          <div
            class="min-w-[500px] h-[250px] flex items-end gap-4 px-3 pb-8 pt-4"
          >
            <div
              v-for="(item,index) in data"
              :key="index"
              class="flex-1 h-full flex flex-col justify-end items-center"
            >
              <span
                class="text-[10px] font-bold mb-1"
                style="color:#64748B"
              >
                {{ item[valueKey] }}
              </span>

              <div
                class="w-full max-w-[55px] rounded-t-md transition-all"
                :style="{
                  height:(Number(item[valueKey]) / maxValue * 78) + '%',
                  background:colors
                    ? colors[index % colors.length]
                    : color
                }"
              ></div>

              <span
                class="text-[10px] mt-2 text-center whitespace-nowrap"
                style="color:#475569"
              >
                {{ item[labelKey] }}
              </span>
            </div>
          </div>
        </div>
      `
    },

    GroupedBarChart: {
      props: {
        data: Array,
        labelKey: String,
        series: Array,
        stacked: Boolean
      },

      computed: {
        maxValue() {
          let max = 0

          this.data.forEach(item => {
            if (this.stacked) {
              const total = this.series.reduce(
                (sum, s) => sum + Number(item[s.key] || 0),
                0
              )

              max = Math.max(max, total)
            } else {
              this.series.forEach(s => {
                max = Math.max(
                  max,
                  Number(item[s.key] || 0)
                )
              })
            }
          })

          return max
        }
      },

      methods: {
        getHeight(value) {
          return (Number(value || 0) / this.maxValue * 100) + '%'
        }
      },

      template: `
        <div class="overflow-x-auto">
          <div
            class="min-w-[650px] h-[300px] flex items-end gap-7 px-4 pb-9 pt-5"
          >
            <div
              v-for="(item,index) in data"
              :key="index"
              class="flex-1 h-full flex flex-col justify-end items-center"
            >
              <div
                v-if="!stacked"
                class="w-full h-full flex items-end justify-center gap-1"
              >
                <div
                  v-for="s in series"
                  :key="s.key"
                  class="rounded-t-sm"
                  :style="{
                    width:(100 / series.length - 4) + '%',
                    height:getHeight(item[s.key]),
                    background:s.color
                  }"
                ></div>
              </div>

              <div
                v-else
                class="w-full h-full flex items-end justify-center"
              >
                <div
                  class="w-[65%] h-full flex flex-col-reverse"
                >
                  <div
                    v-for="s in series"
                    :key="s.key"
                    class="w-full"
                    :style="{
                      height:getHeight(item[s.key]),
                      background:s.color
                    }"
                  ></div>
                </div>
              </div>

              <span
                class="text-xs mt-2 font-semibold"
                style="color:#475569"
              >
                {{ item[labelKey] }}
              </span>
            </div>
          </div>

          <div class="flex justify-center gap-5 flex-wrap pt-1">
            <span
              v-for="s in series"
              :key="s.key"
              class="flex items-center gap-1.5 text-xs"
              style="color:#64748B"
            >
              <span
                class="w-2.5 h-2.5 rounded-full"
                :style="{background:s.color}"
              ></span>

              {{ s.name }}
            </span>
          </div>
        </div>
      `
    },

    DonutChart: {
      props: {
        data: Array
      },

      computed: {
        total() {
          return this.data.reduce(
            (sum, d) => sum + d.value,
            0
          )
        },

        segments() {
          let current = 0

          return this.data.map(d => {
            const start = current

            current +=
              (d.value / this.total) * 360

            return {
              ...d,
              start,
              end: current
            }
          })
        }
      },

      methods: {
        polar(angle, radius = 80) {
          const radians =
            (angle - 90) * Math.PI / 180

          return {
            x:100 + radius * Math.cos(radians),
            y:100 + radius * Math.sin(radians)
          }
        },

        arcPath(start, end) {
          const p1 = this.polar(start)
          const p2 = this.polar(end)

          const large =
            end - start > 180 ? 1 : 0

          return `M ${p1.x} ${p1.y} A 80 80 0 ${large} 1 ${p2.x} ${p2.y}`
        }
      },

      template: `
        <div class="flex flex-col items-center">
          <div class="relative w-[260px] h-[260px]">
            <svg
              viewBox="0 0 200 200"
              class="w-full h-full"
            >
              <circle
                cx="100"
                cy="100"
                r="80"
                fill="none"
                stroke="#F1F5F9"
                stroke-width="28"
              />

              <path
                v-for="segment in segments"
                :key="segment.name"
                :d="arcPath(segment.start, segment.end)"
                fill="none"
                :stroke="segment.color"
                stroke-width="28"
                stroke-linecap="butt"
              />
            </svg>

            <div
              class="absolute inset-0 flex flex-col items-center justify-center"
            >
              <span
                class="text-2xl font-extrabold"
                style="color:#0E1B30"
              >
                {{ total.toLocaleString('pt-BR') }}
              </span>

              <span
                class="text-xs"
                style="color:#94A3B8"
              >
                clientes
              </span>
            </div>
          </div>

          <div class="flex justify-center gap-5 flex-wrap">
            <span
              v-for="d in data"
              :key="d.name"
              class="flex items-center gap-1.5 text-xs"
              style="color:#64748B"
            >
              <span
                class="w-2.5 h-2.5 rounded-full"
                :style="{background:d.color}"
              ></span>

              {{ d.name }} · {{ Math.round(d.value / total * 100) }}%
            </span>
          </div>
        </div>
      `
    },

    BoxPlotChart: {
      props: {
        data: Array
      },

      computed: {
        svgWidth() {
          return this.data.length * 120 + 80
        }
      },

      methods: {
        y(value) {
          const max = 9000
          const top = 15
          const height = 200

          return top + (1 - value / max) * height
        }
      },

      template: `
        <div class="overflow-x-auto">
          <svg
            :width="svgWidth"
            height="260"
            style="max-width:100%;display:block"
          >
            <g
              v-for="value in [0,2000,4000,6000,8000]"
              :key="value"
            >
              <line
                x1="55"
                :x2="svgWidth - 15"
                :y1="y(value)"
                :y2="y(value)"
                stroke="#F1F5F9"
                stroke-dasharray="4 3"
              />

              <text
                x="48"
                :y="y(value) + 4"
                text-anchor="end"
                font-size="9"
                fill="#94A3B8"
              >
                {{ value === 0 ? 'R$0' : 'R$' + value / 1000 + 'k' }}
              </text>
            </g>

            <g
              v-for="(d,index) in data"
              :key="d.seg"
            >
              <line
                :x1="80 + index * 120"
                :x2="80 + index * 120"
                :y1="y(d.max)"
                :y2="y(d.min)"
                stroke="#CBD5E1"
                stroke-width="1.5"
              />

              <line
                :x1="70 + index * 120"
                :x2="90 + index * 120"
                :y1="y(d.max)"
                :y2="y(d.max)"
                stroke="#94A3B8"
                stroke-width="1.5"
              />

              <line
                :x1="70 + index * 120"
                :x2="90 + index * 120"
                :y1="y(d.min)"
                :y2="y(d.min)"
                stroke="#94A3B8"
                stroke-width="1.5"
              />

              <rect
                :x="63 + index * 120"
                :y="y(d.q3)"
                width="34"
                :height="Math.max(y(d.q1) - y(d.q3), 2)"
                fill="rgba(62,160,131,0.12)"
                stroke="#3EA083"
                stroke-width="1.5"
                rx="4"
              />

              <line
                :x1="63 + index * 120"
                :x2="97 + index * 120"
                :y1="y(d.med)"
                :y2="y(d.med)"
                stroke="#0F6E56"
                stroke-width="2.5"
                stroke-linecap="round"
              />

              <text
                :x="80 + index * 120"
                y="250"
                text-anchor="middle"
                font-size="10"
                fill="#64748B"
              >
                {{ d.seg }}
              </text>
            </g>
          </svg>
        </div>
      `
    },

    LineChartCustom: {
      props: {
        data: Array
      },

      computed: {
        maxValue() {
          return Math.max(
            ...this.data.map(d => d.contratos)
          )
        },

        points() {
          return this.data
            .map((d, i) => {
              const x =
                45 +
                i * (520 / (this.data.length - 1))

              const y =
                220 -
                (d.contratos / this.maxValue) * 175

              return `${x},${y}`
            })
            .join(' ')
        }
      },

      template: `
        <div class="overflow-x-auto">
          <svg
            viewBox="0 0 610 260"
            class="w-full min-w-[550px] h-[260px]"
          >
            <g
              v-for="value in [0,30,60,90,120]"
              :key="value"
            >
              <line
                x1="45"
                x2="565"
                :y1="220 - value / maxValue * 175"
                :y2="220 - value / maxValue * 175"
                stroke="#F1F5F9"
                stroke-dasharray="4 3"
              />

              <text
                x="38"
                :y="224 - value / maxValue * 175"
                text-anchor="end"
                font-size="9"
                fill="#94A3B8"
              >
                {{ value }}
              </text>
            </g>

            <polyline
              :points="points"
              fill="none"
              stroke="#0F6E56"
              stroke-width="3"
              stroke-linecap="round"
              stroke-linejoin="round"
            />

            <circle
              v-for="(d,i) in data"
              :key="i"
              :cx="45 + i * (520 / (data.length - 1))"
              :cy="220 - d.contratos / maxValue * 175"
              r="4"
              fill="#0F6E56"
            />

            <text
              v-for="(d,i) in data"
              :key="'label' + i"
              :x="45 + i * (520 / (data.length - 1))"
              y="245"
              text-anchor="middle"
              font-size="9"
              fill="#475569"
            >
              {{ d.mes }}
            </text>
          </svg>
        </div>
      `
    },

    ScatterChartCustom: {
      props: {
        data: Array
      },

      methods: {
        x(value) {
          return 55 + (value / 6) * 500
        },

        y(value) {
          return 225 - (value / 5500) * 190
        }
      },

      template: `
        <div class="overflow-x-auto">
          <svg
            viewBox="0 0 590 270"
            class="w-full min-w-[520px] h-[270px]"
          >
            <g
              v-for="value in [0,1000,2000,3000,4000,5000]"
              :key="value"
            >
              <line
                x1="55"
                x2="555"
                :y1="y(value)"
                :y2="y(value)"
                stroke="#F1F5F9"
                stroke-dasharray="4 3"
              />

              <text
                x="48"
                :y="y(value) + 4"
                text-anchor="end"
                font-size="9"
                fill="#94A3B8"
              >
                R$ {{ value / 1000 }}k
              </text>
            </g>

            <circle
              v-for="(point,index) in data"
              :key="index"
              :cx="x(point.servicos)"
              :cy="y(point.fat)"
              r="5"
              fill="#3EA083"
              fill-opacity="0.7"
            />

            <text
              v-for="value in [1,2,3,4,5]"
              :key="'x' + value"
              :x="x(value)"
              y="248"
              text-anchor="middle"
              font-size="10"
              fill="#475569"
            >
              {{ value }}
            </text>

            <text
              x="305"
              y="265"
              text-anchor="middle"
              font-size="10"
              fill="#94A3B8"
            >
              Qtd. Serviços
            </text>
          </svg>
        </div>
      `
    }
  }
}
</script>