<template>
  <div class="min-h-screen" style="background: #F0F4F8">

    <header
      class="sticky top-0 z-40"
      style="background: #0E1B30; box-shadow: 0 1px 0 rgba(255,255,255,0.05)"
    >
      <div class="max-w-6xl mx-auto px-6 py-4 flex items-center justify-between">

        <div class="flex items-center gap-3">
          <img
            :src="logo"
            alt="Logo"
            class="w-10 h-10 object-contain"
          />

          <div class="flex items-center">
            <span class="text-white text-2xl font-extrabold">Syn</span>
            <span class="text-[#0F6E56] text-2xl font-extrabold">apse</span>
          </div>
        </div>

        <nav class="hidden md:flex items-center gap-8">
          <a
            href="#recursos"
            class="text-sm font-medium"
            style="color: rgba(255,255,255,0.65)"
          >
            Recursos
          </a>

          <a
            href="#contato"
            class="text-sm font-medium"
            style="color: rgba(255,255,255,0.65)"
          >
            Contato
          </a>
        </nav>

        <div class="flex items-center gap-3">

          <button
            @click="goToLogin"
            class="hidden md:inline-flex items-center gap-2 px-5 py-2.5 rounded-xl text-sm font-bold text-white transition-all hover:opacity-90 active:scale-95"
            style="background: #E33838; box-shadow: 0 4px 16px rgba(227,56,56,0.3)"
          >
            Entrar no sistema

            <svg
              viewBox="0 0 16 16"
              fill="none"
              stroke="currentColor"
              stroke-width="2"
              class="w-3.5 h-3.5"
            >
              <path
                d="M3 8h10M9 4l4 4-4 4"
                stroke-linecap="round"
                stroke-linejoin="round"
              />
            </svg>
          </button>

          <button
            class="md:hidden"
            @click="mobileOpen = !mobileOpen"
            style="color: #fff"
          >
            <svg
              viewBox="0 0 24 24"
              fill="none"
              stroke="currentColor"
              stroke-width="2"
              class="w-6 h-6"
            >
              <path
                d="M3 6h18M3 12h18M3 18h18"
                stroke-linecap="round"
              />
            </svg>
          </button>

        </div>
      </div>

      <div
        v-if="mobileOpen"
        class="md:hidden px-6 pb-5 flex flex-col gap-4"
        style="border-top: 1px solid rgba(255,255,255,0.06)"
      >
        <a
          href="#recursos"
          class="text-sm font-medium"
          style="color: rgba(255,255,255,0.7)"
          @click="mobileOpen = false"
        >
          Recursos
        </a>

        <a
          href="#contato"
          class="text-sm font-medium"
          style="color: rgba(255,255,255,0.7)"
          @click="mobileOpen = false"
        >
          Contato
        </a>

        <button
          @click="goToLogin"
          class="self-start px-5 py-2.5 rounded-xl text-sm font-bold text-white"
          style="background: #E33838"
        >
          Entrar no sistema
        </button>
      </div>
    </header>

    <section class="relative overflow-hidden">

      <div
        class="absolute inset-0 pointer-events-none"
        style="background: radial-gradient(ellipse at 70% 50%, rgba(62,160,131,0.07) 0%, transparent 60%), radial-gradient(ellipse at 20% 80%, rgba(14,27,48,0.05) 0%, transparent 50%)"
      ></div>

      <div
        class="absolute top-0 left-0 right-0 h-1"
        style="background: linear-gradient(90deg, #0F6E56, #3EA083, #0F6E56)"
      ></div>

      <div class="relative max-w-6xl mx-auto px-6 pt-20 pb-24 grid md:grid-cols-2 gap-14 items-center">

        <div>

          <div
            class="inline-flex items-center gap-2 rounded-full px-3.5 py-1.5 text-xs font-bold mb-7"
            style="background: rgba(62,160,131,0.1); color: #3EA083; border: 1px solid rgba(62,160,131,0.2)"
          >
            <span class="relative flex h-2 w-2">
              <span
                class="animate-ping absolute inline-flex h-full w-full rounded-full opacity-75"
                style="background: #3EA083"
              ></span>

              <span
                class="relative inline-flex rounded-full h-2 w-2"
                style="background: #3EA083"
              ></span>
            </span>

            Plataforma de análise de dados
          </div>

          <h1
            class="text-5xl font-extrabold leading-[1.1] mb-6 tracking-tight"
            style="color: #0E1B30"
          >
            Simplifique seus dados.

            <span class="relative inline-block">
              <span style="color: #3EA083">
                Potencialize
              </span>

              <svg
                class="absolute -bottom-1 left-0 w-full"
                viewBox="0 0 200 8"
                fill="none"
              >
                <path
                  d="M2 6 Q50 2 100 6 Q150 10 198 6"
                  stroke="#3EA083"
                  stroke-width="2.5"
                  stroke-linecap="round"
                  opacity="0.5"
                />
              </svg>
            </span>

            suas decisões.
          </h1>

          <p
            class="text-lg leading-relaxed mb-10"
            style="color: #475569"
          >
            Transforme dados em clareza, insights em ação e decisões em
            resultados. Basta carregar sua planilha — nós cuidamos do resto.
          </p>

          <div class="flex flex-wrap gap-3 mb-12">

            <button
              @click="goToLogin"
              class="flex items-center gap-2 px-7 py-3.5 rounded-xl text-sm font-bold text-white transition-all hover:opacity-90 active:scale-95"
              style="background: linear-gradient(135deg, #0F6E56, #3EA083); box-shadow: 0 10px 30px rgba(15,110,86,0.35)"
            >
              Comece agora — é grátis

              <svg
                viewBox="0 0 16 16"
                fill="none"
                stroke="currentColor"
                stroke-width="2.5"
                class="w-3.5 h-3.5"
              >
                <path
                  d="M3 8h10M9 4l4 4-4 4"
                  stroke-linecap="round"
                  stroke-linejoin="round"
                />
              </svg>
            </button>

            <button
              class="px-7 py-3.5 rounded-xl text-sm font-bold transition-all"
              style="color: #0E1B30; border: 1.5px solid #CBD5E1; background: rgba(255,255,255,0.5)"
            >
              Ver demonstração
            </button>

          </div>

          <div
            class="flex items-center gap-6 pt-8"
            style="border-top: 1px solid #E2E8F0"
          >

            <div>
              <p
                class="text-xl font-extrabold leading-none"
                style="color: #0F6E56"
              >
                + 3.200
              </p>

              <p
                class="text-xs mt-0.5"
                style="color: #94A3B8"
              >
                empresas ativas
              </p>
            </div>

            <div
              class="w-px h-8"
              style="background: #E2E8F0"
            ></div>

            <div>
              <p
                class="text-xl font-extrabold leading-none"
                style="color: #0F6E56"
              >
                98%
              </p>

              <p
                class="text-xs mt-0.5"
                style="color: #94A3B8"
              >
                satisfação
              </p>
            </div>

            <div
              class="w-px h-8"
              style="background: #E2E8F0"
            ></div>

            <div>
              <p
                class="text-xl font-extrabold leading-none"
                style="color: #0F6E56"
              >
                &lt; 5 min
              </p>

              <p
                class="text-xs mt-0.5"
                style="color: #94A3B8"
              >
                para o 1.º insight
              </p>
            </div>

          </div>
        </div>

        <div class="relative">

          <div
            class="absolute -inset-12 rounded-full opacity-20 blur-3xl pointer-events-none"
            style="background: radial-gradient(ellipse, #3EA083, transparent 70%)"
          ></div>

          <div
            class="relative"
            style="transform: perspective(1200px) rotateY(-4deg) rotateX(2deg)"
          >
            <DashboardMockup />
          </div>

        </div>

      </div>
    </section>

    <section
      id="recursos"
      class="max-w-6xl mx-auto px-6 pb-28"
    >

      <div class="text-center mb-14">

        <p
          class="text-xs font-bold tracking-widest uppercase mb-3"
          style="color: #3EA083"
        >
          Funcionalidades
        </p>

        <h2
          class="text-3xl font-extrabold mb-4"
          style="color: #0E1B30"
        >
          Tudo que você precisa para<br />
          tomar decisões melhores
        </h2>

        <p
          class="text-base max-w-lg mx-auto"
          style="color: #64748B"
        >
          Da planilha bruta ao relatório executivo em minutos.
          Sem código, sem configuração complexa.
        </p>

      </div>

      <div class="grid md:grid-cols-3 gap-6">

        <div
          v-for="(feature, index) in features"
          :key="feature.title"
          class="rounded-3xl p-7 cursor-default transition-all duration-300 hover:-translate-y-2"
          :style="{
            background: index === 1
              ? 'linear-gradient(145deg, #0E1B30, #0a2540)'
              : '#fff',
            border: index === 1
              ? 'none'
              : '1px solid #E8EFF6',
            boxShadow: index === 1
              ? '0 20px 50px rgba(14,27,48,0.25)'
              : '0 2px 10px rgba(14,27,48,0.05)'
          }"
        >

          <div
            class="w-12 h-12 rounded-2xl flex items-center justify-center mb-5"
            :style="{
              background: index === 1
                ? 'rgba(62,160,131,0.15)'
                : 'rgba(15,110,86,0.08)',
              color: index === 1
                ? '#3EA083'
                : '#0F6E56'
            }"
          >

            <svg
              v-if="index === 0"
              viewBox="0 0 24 24"
              fill="none"
              class="w-6 h-6"
              stroke="currentColor"
              stroke-width="1.8"
            >
              <path
                d="M9 17H4a2 2 0 0 1-2-2V5a2 2 0 0 1 2-2h11l5 5v10a2 2 0 0 1-2 2h-5"
                stroke-linecap="round"
                stroke-linejoin="round"
              />

              <polyline
                points="9 17 9 11 15 11 15 17"
                stroke-linecap="round"
                stroke-linejoin="round"
              />

              <polyline
                points="13 3 13 8 18 8"
                stroke-linecap="round"
                stroke-linejoin="round"
              />
            </svg>

            <svg
              v-else-if="index === 1"
              viewBox="0 0 24 24"
              fill="none"
              class="w-6 h-6"
              stroke="currentColor"
              stroke-width="1.8"
            >
              <rect
                x="2"
                y="3"
                width="20"
                height="14"
                rx="2"
                stroke-linecap="round"
                stroke-linejoin="round"
              />

              <path
                d="M8 21h8M12 17v4"
                stroke-linecap="round"
                stroke-linejoin="round"
              />

              <path
                d="M7 13l3-4 3 4 3-6"
                stroke-linecap="round"
                stroke-linejoin="round"
              />
            </svg>

            <svg
              v-else
              viewBox="0 0 24 24"
              fill="none"
              class="w-6 h-6"
              stroke="currentColor"
              stroke-width="1.8"
            >
              <circle
                cx="12"
                cy="12"
                r="10"
                stroke-linecap="round"
                stroke-linejoin="round"
              />

              <path
                d="M12 8v4l3 3"
                stroke-linecap="round"
                stroke-linejoin="round"
              />
            </svg>

          </div>

          <span
            class="inline-flex px-2.5 py-0.5 rounded-full text-xs font-semibold mb-3"
            :style="{
              background: index === 1
                ? 'rgba(62,160,131,0.12)'
                : 'rgba(15,110,86,0.07)',
              color: index === 1
                ? '#3EA083'
                : '#0F6E56'
            }"
          >
            {{ feature.tag }}
          </span>

          <h3
            class="font-extrabold text-lg mb-2.5"
            :style="{
              color: index === 1 ? '#fff' : '#0E1B30'
            }"
          >
            {{ feature.title }}
          </h3>

          <p
            class="text-sm leading-relaxed"
            :style="{
              color: index === 1
                ? 'rgba(255,255,255,0.55)'
                : '#64748B'
            }"
          >
            {{ feature.description }}
          </p>

        </div>

      </div>
    </section>

    <section
      id="contato"
      class="relative overflow-hidden py-24 px-6"
      style="background: #0E1B30"
    >

      <div
        class="absolute inset-0 pointer-events-none opacity-10"
        style="background-image: radial-gradient(ellipse at 10% 50%, #3EA08355 0%, transparent 50%), radial-gradient(ellipse at 90% 50%, #0F6E5644 0%, transparent 50%)"
      ></div>

      <div
        class="absolute inset-0 pointer-events-none opacity-5"
        style="background-image: linear-gradient(#3EA083 1px, transparent 1px), linear-gradient(90deg, #3EA083 1px, transparent 1px); background-size: 48px 48px"
      ></div>

      <div class="relative max-w-5xl mx-auto grid md:grid-cols-2 gap-14 items-center">

        <div>

          <div
            class="inline-flex items-center gap-2 rounded-full px-3.5 py-1.5 text-xs font-bold mb-6"
            style="background: rgba(62,160,131,0.12); color: #3EA083; border: 1px solid rgba(62,160,131,0.2)"
          >
            <span
              class="w-1.5 h-1.5 rounded-full inline-block"
              style="background: #3EA083"
            ></span>

            Sobre o Synapse
          </div>

          <h2 class="text-3xl font-extrabold text-white mb-5 leading-tight">
            Uma plataforma pensada para o mercado brasileiro
          </h2>

          <p
            class="text-sm leading-relaxed mb-4"
            style="color: rgba(255,255,255,0.55)"
          >
            O Synapse nasceu da necessidade real de pequenas e médias empresas
            que acumulam dados em planilhas, mas não têm estrutura para
            transformá-los em decisões.
          </p>

          <p
            class="text-sm leading-relaxed"
            style="color: rgba(255,255,255,0.55)"
          >
            Seja para acompanhar faturamento, monitorar metas ou entender o
            comportamento dos clientes, o Synapse centraliza tudo — simples,
            rápido e acessível.
          </p>

        </div>

        <div class="flex flex-col gap-5">

          <div class="grid grid-cols-2 gap-3">

            <div
              v-for="stat in aboutStats"
              :key="stat.l"
              class="rounded-2xl p-4"
              style="background: rgba(255,255,255,0.05); border: 1px solid rgba(255,255,255,0.07)"
            >
              <p
                class="text-2xl font-extrabold mb-0.5"
                style="color: #3EA083"
              >
                {{ stat.v }}
              </p>

              <p
                class="text-xs leading-tight"
                style="color: rgba(255,255,255,0.4)"
              >
                {{ stat.l }}
              </p>
            </div>

          </div>

          <button
            @click="goToLogin"
            class="self-start flex items-center gap-2 px-7 py-3.5 rounded-xl text-sm font-bold text-white transition-all hover:opacity-90 active:scale-95"
            style="background: #E33838; box-shadow: 0 8px 24px rgba(227,56,56,0.3)"
          >
            Entrar no sistema

            <svg
              viewBox="0 0 16 16"
              fill="none"
              stroke="currentColor"
              stroke-width="2.5"
              class="w-3.5 h-3.5"
            >
              <path
                d="M3 8h10M9 4l4 4-4 4"
                stroke-linecap="round"
                stroke-linejoin="round"
              />
            </svg>
          </button>

        </div>

      </div>
    </section>

    <footer
      class="py-6 text-center text-xs"
      style="background: #08111F; color: rgba(255,255,255,0.25)"
    >
      © 2026 Synapse · Todos os direitos reservados
    </footer>

  </div>
</template>

<script setup>
import { ref } from 'vue'
import { useRouter } from 'vue-router'
import logo from '../assets/logo.png'
import DashboardMockup from '../components/DashboardMockup.vue'

const router = useRouter()

const mobileOpen = ref(false)

const features = [
  {
    title: 'Importação de Planilhas',
    description: 'Carregue arquivos Excel ou CSV nos formatos padrão do mercado. O sistema identifica automaticamente as colunas e prepara os dados para análise.',
    tag: 'XLSX · XLS · CSV'
  },
  {
    title: 'Dashboards Automáticos',
    description: 'Seus dados viram gráficos interativos em segundos — barras, linhas, pizza e KPIs executivos, atualizados em tempo real conforme você filtra.',
    tag: 'Interativo · Tempo real'
  },
  {
    title: 'Insights em Tempo Real',
    description: 'Identifique tendências e anomalias antes que se tornem problemas. Relatórios executivos gerados automaticamente para decisões estratégicas.',
    tag: 'IA · Relatórios'
  }
]

const aboutStats = [
  {
    v: '+ 3.200',
    l: 'empresas confiam no Synapse'
  },
  {
    v: '12+',
    l: 'formatos de planilha suportados'
  },
  {
    v: '< 5 min',
    l: 'do upload ao primeiro insight'
  },
  {
    v: '99,9%',
    l: 'de uptime garantido'
  }
]

function goToLogin() {
  mobileOpen.value = false
  router.push('/login')
}
</script>