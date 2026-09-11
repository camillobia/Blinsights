<template>
  <div>
    <Header />

    <section class="bg-[#F4F6F8]">
      <div class="mx-auto grid min-h-[650px] max-w-[1500px] items-center gap-16 px-6 py-16 lg:grid-cols-[0.9fr_1.1fr] lg:px-10 xl:px-16">

        <div class="max-w-2xl">
          <div class="inline-flex items-center gap-2 rounded-full bg-[#EAF7F7] px-4 py-2 text-sm font-medium text-[#5A9999]">
            <span class="text-base">✦</span>
            Plataforma de análise de dados
          </div>

          <h1 class="mt-7 text-5xl font-bold leading-[1.1] tracking-tight text-[#1E2A44] md:text-6xl">
            Simplifique seus
            <br />
            dados.
            <br />
            <span class="text-[#0F6E56]">Potencialize</span>
            suas decisões.
          </h1>

          <p class="mt-7 max-w-lg text-base leading-7 text-slate-500 md:text-lg">
            Transforme dados em clareza, insights em ação e decisões em
            resultados. Basta carregar sua planilha — nós cuidamos do resto.
          </p>

          <div class="mt-8 flex items-center gap-6">
            <button
              type="button"
              class="rounded-xl bg-[#0F6E56] px-7 py-4 text-sm font-semibold text-white transition duration-200 hover:scale-[1.02] hover:bg-[#3E8585]"
            >
              Comece agora
            </button>

            <a
              href="#recursos"
              class="rounded-xl border border-[#4E9999] px-6 py-3 text-sm font-semibold text-[#4E9999] transition duration-200 hover:bg-[#4E9999] hover:text-white"
            >
              Saiba mais
            </a>
          </div>

          <div class="mt-10 flex flex-wrap gap-8 md:gap-12">
            <div>
              <p class="text-xl font-bold text-[#4E9999]">+ 3.200</p>
              <span class="mt-1 block text-xs text-slate-400">
                usuários ativos
              </span>
            </div>

            <div>
              <p class="text-xl font-bold text-[#4E9999]">98%</p>
              <span class="mt-1 block text-xs text-slate-400">
                de satisfação
              </span>
            </div>

            <div>
              <p class="text-xl font-bold text-[#4E9999]">&lt; 5 min</p>
              <span class="mt-1 block text-xs text-slate-400">
                para começar a analisar
              </span>
            </div>
          </div>
        </div>

        <div class="w-full max-w-[650px] justify-self-end rounded-2xl bg-[#162039] p-6 shadow-2xl md:p-8">

          <div class="mb-8 flex gap-3">
            <span class="h-3 w-3 rounded-full bg-[#E35B55]"></span>
            <span class="h-3 w-3 rounded-full bg-[#E4B93B]"></span>
            <span class="h-3 w-3 rounded-full bg-[#66B6A6]"></span>
          </div>

          <div class="grid grid-cols-3 gap-3">
            <div class="rounded-xl bg-[#22304D] p-4">
              <p class="text-[10px] uppercase tracking-wide text-slate-400">
                Total de dados
              </p>
              <p class="mt-2 text-lg font-bold text-white md:text-xl">
                R$ 2.45M
              </p>
            </div>

            <div class="rounded-xl bg-[#22304D] p-4">
              <p class="text-[10px] uppercase tracking-wide text-slate-400">
                Clientes ativos
              </p>
              <p class="mt-2 text-lg font-bold text-white md:text-xl">
                1.248
              </p>
            </div>

            <div class="rounded-xl bg-[#22304D] p-4">
              <p class="text-[10px] uppercase tracking-wide text-slate-400">
                Meta do mês
              </p>
              <p class="mt-2 text-lg font-bold text-white md:text-xl">
                76%
              </p>
            </div>
          </div>

          <p class="mb-6 mt-10 text-xs font-medium uppercase tracking-wider text-slate-400">
            Receita por mês
          </p>

          <div class="flex h-52 items-end justify-between gap-4">

            <div class="flex h-full flex-1 flex-col items-center justify-end">
              <div class="h-[55%] w-full max-w-[35px] rounded-t bg-[#6AB4AF]"></div>
              <span class="mt-3 text-[10px] text-slate-500">Jan</span>
            </div>

            <div class="flex h-full flex-1 flex-col items-center justify-end">
              <div class="h-[72%] w-full max-w-[35px] rounded-t bg-[#7CC8C1]"></div>
              <span class="mt-3 text-[10px] text-slate-500">Fev</span>
            </div>

            <div class="flex h-full flex-1 flex-col items-center justify-end">
              <div class="h-[48%] w-full max-w-[35px] rounded-t bg-[#6AB4AF]"></div>
              <span class="mt-3 text-[10px] text-slate-500">Mar</span>
            </div>

            <div class="flex h-full flex-1 flex-col items-center justify-end">
              <div class="h-[68%] w-full max-w-[35px] rounded-t bg-[#7CC8C1]"></div>
              <span class="mt-3 text-[10px] text-slate-500">Abr</span>
            </div>

            <div class="flex h-full flex-1 flex-col items-center justify-end">
              <div class="h-[82%] w-full max-w-[35px] rounded-t bg-[#6AB4AF]"></div>
              <span class="mt-3 text-[10px] text-slate-500">Mai</span>
            </div>

            <div class="flex h-full flex-1 flex-col items-center justify-end">
              <div class="h-[38%] w-full max-w-[35px] rounded-t bg-[#7CC8C1]"></div>
              <span class="mt-3 text-[10px] text-slate-500">Jun</span>
            </div>

          </div>
        </div>
      </div>
    </section>

    <section id="recursos" class="bg-[#F4F6F8] px-6 py-20 lg:px-12">
      <div class="mx-auto max-w-6xl">

        <div class="mx-auto max-w-2xl text-center">
          <h2 class="text-3xl font-bold tracking-tight text-[#162039] md:text-4xl">
            Tudo que você precisa para tomar decisões melhores
          </h2>

          <p class="mt-4 text-sm leading-6 text-slate-400">
            Da planilha bruta ao relatório executivo em minutos. Sem código,
            sem configuração complexa.
          </p>
        </div>

        <div class="mt-12 grid gap-5 md:grid-cols-3">

          <div class="rounded-xl border border-slate-200 bg-white p-6 shadow-sm transition duration-200 hover:-translate-y-1 hover:shadow-md">

            <div class="flex h-10 w-10 items-center justify-center rounded-lg bg-[#EAF7F7] text-[#4E9999]">
              <svg
                xmlns="http://www.w3.org/2000/svg"
                class="h-5 w-5"
                fill="none"
                viewBox="0 0 24 24"
                stroke="currentColor"
                stroke-width="1.8"
              >
                <path
                  stroke-linecap="round"
                  stroke-linejoin="round"
                  d="M9 12h6m-6 4h6m2 5H7a2 2 0 01-2-2V5a2 2 0 012-2h6l5 5v11a2 2 0 01-2 2z"
                />
                <path
                  stroke-linecap="round"
                  stroke-linejoin="round"
                  d="M13 3v5h5"
                />
              </svg>
            </div>

            <h3 class="mt-5 text-sm font-bold text-[#162039]">
              Importação de Planilhas
            </h3>

            <p class="mt-3 text-xs leading-5 text-slate-500">
              Carregue arquivos Excel ou CSV nos formatos padrão do mercado.
              O sistema identifica automaticamente as colunas, valida os dados
              e prepara tudo para análise — sem configuração manual.
            </p>
          </div>

          <div class="rounded-xl border border-slate-200 bg-white p-6 shadow-sm transition duration-200 hover:-translate-y-1 hover:shadow-md">

            <div class="flex h-10 w-10 items-center justify-center rounded-lg bg-[#EAF7F7] text-[#4E9999]">
              <svg
                xmlns="http://www.w3.org/2000/svg"
                class="h-5 w-5"
                fill="none"
                viewBox="0 0 24 24"
                stroke="currentColor"
                stroke-width="1.8"
              >
                <path
                  stroke-linecap="round"
                  stroke-linejoin="round"
                  d="M4 19V5m0 14h16"
                />
                <path
                  stroke-linecap="round"
                  stroke-linejoin="round"
                  d="M7 15l3-4 3 2 4-6"
                />
              </svg>
            </div>

            <h3 class="mt-5 text-sm font-bold text-[#162039]">
              Dashboards Automáticos
            </h3>

            <p class="mt-3 text-xs leading-5 text-slate-500">
              Seus dados viram gráficos de barras, linhas, pizza e KPIs em
              segundos. Visualizações interativas que atualizam em tempo real
              conforme você filtra períodos, segmentos e categorias.
            </p>
          </div>

          <div class="rounded-xl border border-slate-200 bg-white p-6 shadow-sm transition duration-200 hover:-translate-y-1 hover:shadow-md">

            <div class="flex h-10 w-10 items-center justify-center rounded-lg bg-[#EAF7F7] text-[#4E9999]">
              <svg
                xmlns="http://www.w3.org/2000/svg"
                class="h-5 w-5"
                fill="none"
                viewBox="0 0 24 24"
                stroke="currentColor"
                stroke-width="1.8"
              >
                <circle cx="12" cy="12" r="8" />
                <path
                  stroke-linecap="round"
                  stroke-linejoin="round"
                  d="M12 8v4l3 2"
                />
              </svg>
            </div>

            <h3 class="mt-5 text-sm font-bold text-[#162039]">
              Insights em Tempo Real
            </h3>

            <p class="mt-3 text-xs leading-5 text-slate-500">
              Identifique tendências, anomalias e oportunidades antes que se
              tornem problemas. Relatórios executivos gerados automaticamente
              para apoiar decisões estratégicas com dados concretos.
            </p>
          </div>

        </div>
      </div>
    </section>

    <section class="bg-[#0D1B32] px-6 py-20 lg:px-12">
      <div class="mx-auto grid max-w-6xl items-center gap-12 lg:grid-cols-[1.05fr_0.95fr]">

        <div>
          <span class="inline-flex rounded-full bg-[#123F47] px-3 py-1 text-[15px] font-medium text-[#5CC0B3]">
            ✦ Sobre o Synapse
          </span>

          <h2 class="mt-5 max-w-xl text-3xl font-bold leading-tight text-white md:text-4xl">
            Uma plataforma pensada
            <br />
            para o mercado brasileiro
          </h2>

          <p class="mt-5 max-w-xl text-sm leading-6 text-slate-400">
            O Synapse nasceu da necessidade real de pequenas e médias empresas
            que acumulam dados em planilhas, mas não têm estrutura para
            transformá-los em decisões. Nossa plataforma lê os formatos mais
            usados no mercado e entrega painéis claros, sem precisar de equipe
            de TI.
          </p>

          <p class="mt-4 max-w-xl text-sm leading-6 text-slate-400">
            Seja para acompanhar faturamento, monitorar metas ou entender o
            comportamento dos seus clientes, o Synapse centraliza tudo em um
            único lugar — simples, rápido e acessível.
          </p>
        </div>

        <div class="flex flex-col items-center lg:items-end">
          <div class="flex gap-3">

            <a
              href="https://www.whatsapp.com/"
              target="_blank"
              rel="noopener noreferrer"
              aria-label="WhatsApp"
              class="flex h-14 w-14 items-center justify-center rounded-xl border border-[#29405A] bg-[#12243D] text-slate-400 transition duration-200 hover:-translate-y-1 hover:border-[#5CC0B3] hover:bg-[#123F47] hover:text-[#5CC0B3]"
            >
             <svg
               xmlns="http://www.w3.org/2000/svg"
               class="h-6 w-6"
               viewBox="0 0 24 24"
              fill="currentColor">
               <path
              d="M20.52 3.48A11.86 11.86 0 0 0 12.06 0C5.51 0 .18 5.33.18 11.88c0 2.09.55 4.13 1.59 5.93L.08 24l6.34-1.66a11.9 11.9 0 0 0 5.64 1.43h.01c6.55 0 11.88-5.33 11.88-11.88 0-3.17-1.23-6.15-3.43-8.41ZM12.07 21.78h-.01a9.88 9.88 0 0 1-5.04-1.38l-.36-.21-3.76.98 1-3.66-.23-.38a9.86 9.86 0 0 1-1.52-5.25c0-5.47 4.45-9.91 9.92-9.91a9.83 9.83 0 0 1 7 2.9 9.83 9.83 0 0 1 2.9 7c0 5.47-4.45 9.91-9.9 9.91Zm5.43-7.42c-.3-.15-1.77-.87-2.04-.97-.27-.1-.47-.15-.67.15-.2.3-.77.97-.94 1.17-.17.2-.35.22-.65.07-.3-.15-1.25-.46-2.39-1.47-.88-.79-1.48-1.76-1.65-2.06-.17-.3-.02-.46.13-.61.13-.13.3-.35.45-.52.15-.17.2-.3.3-.5.1-.2.05-.37-.03-.52-.07-.15-.67-1.61-.91-2.2-.24-.58-.49-.5-.67-.51h-.57c-.2 0-.52.07-.79.37-.27.3-1.04 1.02-1.04 2.48s1.07 2.88 1.21 3.07c.15.2 2.1 3.2 5.08 4.49.71.31 1.26.49 1.69.63.71.23 1.36.19 1.87.12.57-.09 1.76-.72 2.01-1.41.25-.69.25-1.29.17-1.41-.07-.12-.27-.2-.57-.35Z"
              />
             </svg>

            </a>

            <a
              href="https://www.facebook.com/"
              target="_blank"
              rel="noopener noreferrer"
              aria-label="Facebook"
              class="flex h-14 w-14 items-center justify-center rounded-xl border border-[#29405A] bg-[#12243D] text-slate-400 transition duration-200 hover:-translate-y-1 hover:border-[#5CC0B3] hover:bg-[#123F47] hover:text-[#5CC0B3]"
            >
              <svg
                xmlns="http://www.w3.org/2000/svg"
                class="h-5 w-5"
                fill="currentColor"
                viewBox="0 0 24 24"
              >
                <path
                  d="M24 12.073C24 5.405 18.627 0 12 0S0 5.405 0 12.073c0 6.016 4.388 11.006 10.125 11.927v-8.438H7.078v-3.49h3.047V9.414c0-3.025 1.792-4.698 4.533-4.698 1.312 0 2.686.235 2.686.235v2.973h-1.514c-1.491 0-1.956.93-1.956 1.886v2.262h3.328l-.532 3.49h-2.796V24C19.612 23.079 24 18.089 24 12.073"
                />
              </svg>
            </a>

            <a
              href="https://www.instagram.com/"
              target="_blank"
              rel="noopener noreferrer"
              aria-label="Instagram"
              class="flex h-14 w-14 items-center justify-center rounded-xl border border-[#29405A] bg-[#12243D] text-slate-400 transition duration-200 hover:-translate-y-1 hover:border-[#5CC0B3] hover:bg-[#123F47] hover:text-[#5CC0B3]"
            >
              <svg
                xmlns="http://www.w3.org/2000/svg"
                class="h-5 w-5"
                fill="none"
                viewBox="0 0 24 24"
                stroke="currentColor"
                stroke-width="1.8"
              >
                <rect x="3" y="3" width="18" height="18" rx="5" />
                <circle cx="12" cy="12" r="4" />
                <circle cx="17.5" cy="6.5" r="1" fill="currentColor" stroke="none" />
              </svg>
            </a>

            <a
              href="https://www.linkedin.com/"
              target="_blank"
              rel="noopener noreferrer"
              aria-label="LinkedIn"
              class="flex h-14 w-14 items-center justify-center rounded-xl border border-[#29405A] bg-[#12243D] text-slate-400 transition duration-200 hover:-translate-y-1 hover:border-[#5CC0B3] hover:bg-[#123F47] hover:text-[#5CC0B3]"
            >
              <svg
                xmlns="http://www.w3.org/2000/svg"
                class="h-5 w-5"
                fill="currentColor"
                viewBox="0 0 24 24"
              >
                <path
                  d="M20.447 20.452h-3.554v-5.569c0-1.328-.027-3.037-1.852-3.037-1.853 0-2.136 1.445-2.136 2.939v5.667H9.351V8.999h3.414v1.561h.046c.476-.9 1.637-1.85 3.37-1.85 3.601 0 4.267 2.37 4.267 5.455v6.287zM5.337 7.433a2.062 2.062 0 11.001-4.124 2.062 2.062 0 010 4.124zM7.114 20.452H3.56V8.999h3.554v11.453zM22.225 0H1.771C.792 0 0 .774 0 1.729v20.542C0 23.227.792 24 1.771 24h20.451C23.2 24 24 23.227 24 22.271V1.729C24 .774 23.2 .227 22.222 0h.003z"
                />
              </svg>
            </a>

          </div>
        </div>
      </div>
    </section>

    <footer class="bg-[#091528] px-6 py-5">
      <div class="mx-auto max-w-6xl text-center">
        <p class="text-[10px] text-slate-500">
          © 2026 Synapse · Todos os direitos reservados
        </p>
      </div>
    </footer>
  </div>
</template>

<script setup>
import Header from '../components/Header.vue'
</script>