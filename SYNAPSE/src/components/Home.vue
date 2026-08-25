<template>
  <Header />
<section class="bg-[#F4F6F8]">
    <div
      class="mx-auto grid min-h-[650px] max-w-[1500px] items-center gap-16 px-6 py-16 lg:grid-cols-[0.9fr_1.1fr] lg:px-10 xl:px-16"
    >
      <div class="max-w-2xl">
        <div
          class="inline-flex items-center gap-2 rounded-full bg-[#EAF7F7] px-4 py-2 text-sm font-medium text-[#5A9999]"
        >
          <span class="text-base">✦</span>
          Plataforma de análise de dados
        </div>

        <h1
          class="mt-7 text-5xl font-bold leading-[1.1] tracking-tight text-[#1E2A44] md:text-6xl"
        >
          Simplifique seus
          <br />
          dados.
          <br />
          <span class="text-[#0F6E56]">
            Potencialize
          </span>
          suas decisões.
        </h1>

        <p
          class="mt-7 max-w-lg text-base leading-7 text-slate-500 md:text-lg"
        >
          Transforme dados em clareza, insights em ação e decisões em
          resultados. Basta carregar sua planilha — nós cuidamos do resto.
        </p>

        <div class="mt-8 flex items-center gap-6">
          <button
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
            <p class="text-xl font-bold text-[#4E9999]">
              + 3.200
            </p>
            <span class="mt-1 block text-xs text-slate-400">
              usuários ativos
            </span>
          </div>

          <div>
            <p class="text-xl font-bold text-[#4E9999]">
              98%
            </p>
            <span class="mt-1 block text-xs text-slate-400">
              de satisfação
            </span>
          </div>

          <div>
            <p class="text-xl font-bold text-[#4E9999]">
              &lt; 5 min
            </p>
            <span class="mt-1 block text-xs text-slate-400">
              para começar a analisar
            </span>
          </div>
        </div>
      </div>

      <div
        class="w-full max-w-[650px] justify-self-end rounded-2xl bg-[#162039] p-6 shadow-2xl md:p-8"
      >
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

        <p
          class="mb-6 mt-10 text-xs font-medium uppercase tracking-wider text-slate-400"
        >
          Receita por mês
        </p>

        <div class="flex h-52 items-end justify-between gap-4">
          <div
            class="flex h-full flex-1 flex-col items-center justify-end"
          >
            <div
              class="h-[55%] w-full max-w-[35px] rounded-t bg-[#6AB4AF]"
            ></div>
            <span class="mt-3 text-[10px] text-slate-500">
              Jan
            </span>
          </div>

          <div
            class="flex h-full flex-1 flex-col items-center justify-end"
          >
            <div
              class="h-[72%] w-full max-w-[35px] rounded-t bg-[#7CC8C1]"
            ></div>
            <span class="mt-3 text-[10px] text-slate-500">
              Fev
            </span>
          </div>

          <div
            class="flex h-full flex-1 flex-col items-center justify-end"
          >
            <div
              class="h-[48%] w-full max-w-[35px] rounded-t bg-[#6AB4AF]"
            ></div>
            <span class="mt-3 text-[10px] text-slate-500">
              Mar
            </span>
          </div>

          <div
            class="flex h-full flex-1 flex-col items-center justify-end"
          >
            <div
              class="h-[68%] w-full max-w-[35px] rounded-t bg-[#7CC8C1]"
            ></div>
            <span class="mt-3 text-[10px] text-slate-500">
              Abr
            </span>
          </div>

          <div
            class="flex h-full flex-1 flex-col items-center justify-end"
          >
            <div
              class="h-[82%] w-full max-w-[35px] rounded-t bg-[#6AB4AF]"
            ></div>
            <span class="mt-3 text-[10px] text-slate-500">
              Mai
            </span>
          </div>

          <div
            class="flex h-full flex-1 flex-col items-center justify-end"
          >
            <div
              class="h-[38%] w-full max-w-[35px] rounded-t bg-[#7CC8C1]"
            ></div>
            <span class="mt-3 text-[10px] text-slate-500">
              Jun
            </span>
          </div>
        </div>
      </div>
    </div>
  </section>

  <section
    id="recursos"
    class="bg-[#F4F6F8] px-6 py-20 lg:px-12"
  >
    <div class="mx-auto max-w-6xl">
      <div class="mx-auto max-w-2xl text-center">
        <h2
          class="text-3xl font-bold tracking-tight text-[#162039] md:text-4xl"
        >
          Tudo que você precisa para tomar decisões melhores
        </h2>

        <p class="mt-4 text-sm leading-6 text-slate-400">
          Da planilha bruta ao relatório executivo em minutos. Sem código,
          sem configuração complexa.
        </p>
      </div>

      <div class="mt-12 grid gap-5 md:grid-cols-3">
        <div
          class="rounded-xl border border-slate-200 bg-white p-6 shadow-sm transition duration-200 hover:-translate-y-1 hover:shadow-md"
        >
          <div
            class="flex h-10 w-10 items-center justify-center rounded-lg bg-[#EAF7F7] text-[#4E9999]"
          >
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

        <div
          class="rounded-xl border border-slate-200 bg-white p-6 shadow-sm transition duration-200 hover:-translate-y-1 hover:shadow-md"
        >
          <div
            class="flex h-10 w-10 items-center justify-center rounded-lg bg-[#EAF7F7] text-[#4E9999]"
          >
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

        <div
          class="rounded-xl border border-slate-200 bg-white p-6 shadow-sm transition duration-200 hover:-translate-y-1 hover:shadow-md"
        >
          <div
            class="flex h-10 w-10 items-center justify-center rounded-lg bg-[#EAF7F7] text-[#4E9999]"
          >
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
  <div
    class="mx-auto grid max-w-6xl items-center gap-12 lg:grid-cols-[1.05fr_0.95fr]"
  >
    <div>
      <span
        class="inline-flex rounded-full bg-[#123F47] px-3 py-1 text-[15px] font-medium text-[#5CC0B3]"
      >
        ✦ Sobre o Synapse
      </span>

      <h2
        class="mt-5 max-w-xl text-3xl font-bold leading-tight text-white md:text-4xl"
      >
        Uma plataforma pensada
        <br />
        para o mercado brasileiro
      </h2>

      <p
        class="mt-5 max-w-xl text-sm leading-6 text-slate-400"
      >
        O Synapse nasceu da necessidade real de pequenas e médias empresas
        que acumulam dados em planilhas, mas não têm estrutura para
        transformá-los em decisões. Nossa plataforma lê os formatos mais
        usados no mercado e entrega painéis claros, sem precisar de equipe
        de TI.
      </p>

      <p
        class="mt-4 max-w-xl text-sm leading-6 text-slate-400"
      >
        Seja para acompanhar faturamento, monitorar metas ou entender o
        comportamento dos seus clientes, o Synapse centraliza tudo em um
        único lugar — simples, rápido e acessível.
      </p>
    </div>

    <div class="flex flex-col items-center lg:items-end">
      <div class="flex gap-3">
        <a
          href="https://web.whatsapp.com/"
          aria-label="WhatsApp"
          class="flex h-15 w-15 items-center justify-center rounded-xl border border-[#29405A] bg-[#12243D] text-slate-400 transition duration-200 hover:-translate-y-1 hover:border-[#5CC0B3] hover:bg-[#123F47] hover:text-[#5CC0B3]"
        >
          <svg
            xmlns="https://web.whatsapp.com/"
            class="h-5 w-5"
            fill="currentColor"
            viewBox="0 0 24 24"
          >
            <path
              d="M17.472 14.382c-.297-.149-1.758-.867-2.03-.967-.273-.099-.471-.148-.67.15-.197.297-.767.966-.94 1.164-.173.199-.347.223-.644.075-.297-.15-1.255-.463-2.39-1.475-.883-.788-1.48-1.761-1.653-2.059-.173-.297-.018-.458.13-.606.134-.133.298-.347.446-.52.149-.174.198-.298.298-.497.099-.198.05-.371-.025-.52-.075-.149-.669-1.611-.916-2.206-.242-.579-.487-.5-.67-.51l-.57-.01c-.198 0-.52.074-.792.372-.272.297-1.04 1.016-1.04 2.479 0 1.462 1.065 2.875 1.213 3.074.149.198 2.095 3.2 5.076 4.487.709.306 1.262.489 1.694.626.712.226 1.36.194 1.872.118.571-.085 1.758-.719 2.006-1.413.248-.694.248-1.289.173-1.413-.074-.124-.272-.198-.57-.347m-5.421 7.403h-.004a9.87 9.87 0 01-5.031-1.378l-.361-.214-3.741.982 1-3.648-.235-.374a9.86 9.86 0 01-1.511-5.26c.001-5.45 4.436-9.884 9.888-9.884 2.64 0 5.122 1.03 6.988 2.898a9.825 9.825 0 012.893 6.994c-.002 5.45-4.437 9.884-9.886 9.884m8.413-18.297A11.815 11.815 0 0012.05 0C5.495 0 .16 5.335.157 11.89c0 2.096.547 4.142 1.588 5.946L.05 24l6.304-1.654a11.88 11.88 0 005.694 1.447h.005c6.554 0 11.89-5.335 11.893-11.89a11.855 11.855 0 00-3.482-8.415"
            />
          </svg>
        </a>

        <a
          href="https://www.facebook.com/"
          aria-label="Facebook"
          class="flex h-15 w-15 items-center justify-center rounded-xl border border-[#29405A] bg-[#12243D] text-slate-400 transition duration-200 hover:-translate-y-1 hover:border-[#5CC0B3] hover:bg-[#123F47] hover:text-[#5CC0B3]"
        >
          <svg
            xmlns="https://www.facebook.com/"
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
          href="https://www.instagram.com"
          aria-label="Instagram"
          class="flex h-15 w-15 items-center justify-center rounded-xl border border-[#29405A] bg-[#12243D] text-slate-400 transition duration-200 hover:-translate-y-1 hover:border-[#5CC0B3] hover:bg-[#123F47] hover:text-[#5CC0B3]"
        >
          <svg
            xmlns="https://www.instagram.com"
            class="h-5 w-5"
            fill="none"
            viewBox="0 0 24 24"
            stroke="currentColor"
            stroke-width="1.8"
          >
            <rect
              x="3"
              y="3"
              width="18"
              height="18"
              rx="5"
            />
            <circle cx="12" cy="12" r="4" />
            <circle
              cx="17.5"
              cy="6.5"
              r="1"
              fill="currentColor"
              stroke="none"
            />
          </svg>
        </a>

        <a
          href="https://www.linkedin.com/"
          aria-label="LinkedIn"
          class="flex h-15 w-15 items-center justify-center rounded-xl border border-[#29405A] bg-[#12243D] text-slate-400 transition duration-200 hover:-translate-y-1 hover:border-[#5CC0B3] hover:bg-[#123F47] hover:text-[#5CC0B3]"
        >
          <svg
            xmlns="https://www.linkedin.com/"
            class="h-5 w-5"
            fill="currentColor"
            viewBox="0 0 24 24"
          >
            <path
              d="M20.447 20.452h-3.554v-5.569c0-1.328-.027-3.037-1.852-3.037-1.853 0-2.136 1.445-2.136 2.939v5.667H9.351V8.999h3.414v1.561h.046c.476-.9 1.637-1.85 3.37-1.85 3.601 0 4.267 2.37 4.267 5.455v6.287zM5.337 7.433a2.062 2.062 0 11.001-4.124 2.062 2.062 0 010 4.124zM7.114 20.452H3.56V8.999h3.554v11.453zM22.225 0H1.771C.792 0 0 .774 0 1.729v20.542C0 23.227.792 24 1.771 24h20.451C23.2 24 24 23.227 24 22.271V1.729C24 .774 23.2 0 22.222 0h.003z"
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
</template>

<script setup>
import Header from './Header.vue'
</script>