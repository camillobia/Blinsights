<template>
  <div
    class="rounded-2xl overflow-hidden"
    style="background: #0B1525; border: 1px solid rgba(255,255,255,0.07); box-shadow: 0 32px 80px rgba(0,0,0,0.45)"
  >
    <div
      class="flex items-center justify-between px-4 py-3"
      style="border-bottom: 1px solid rgba(255,255,255,0.06); background: rgba(255,255,255,0.03)"
    >
      <div class="flex items-center gap-1.5">
        <span
          class="w-2.5 h-2.5 rounded-full"
          style="background: #E33838"
        ></span>

        <span
          class="w-2.5 h-2.5 rounded-full"
          style="background: #F59E0B"
        ></span>

        <span
          class="w-2.5 h-2.5 rounded-full"
          style="background: #3EA083"
        ></span>
      </div>

      <span
        class="text-xs font-medium px-3 py-0.5 rounded-full"
        style="color: rgba(255,255,255,0.3); background: rgba(255,255,255,0.05)"
      >
        synapse · dashboard
      </span>

      <div class="w-12"></div>
    </div>

    <div class="grid grid-cols-3 gap-2 p-3">

      <div
        v-for="card in kpis"
        :key="card.label"
        class="rounded-xl p-2.5"
        style="background: rgba(255,255,255,0.04); border: 1px solid rgba(255,255,255,0.06)"
      >
        <p
          class="mb-1"
          style="color: rgba(255,255,255,0.35); font-size: 8px; letter-spacing: 0.08em"
        >
          {{ card.label.toUpperCase() }}
        </p>

        <div
          v-if="card.circle"
          class="flex items-center gap-1.5"
        >
          <div class="relative w-7 h-7">

            <svg
              viewBox="0 0 36 36"
              class="w-7 h-7 -rotate-90"
            >
              <circle
                cx="18"
                cy="18"
                r="14"
                fill="none"
                stroke="rgba(255,255,255,0.08)"
                stroke-width="4"
              />

              <circle
                cx="18"
                cy="18"
                r="14"
                fill="none"
                stroke="#3EA083"
                stroke-width="4"
                stroke-dasharray="66 88"
                stroke-linecap="round"
              />
            </svg>

            <span
              class="absolute inset-0 flex items-center justify-center text-white font-bold"
              style="font-size: 6px"
            >
              75%
            </span>

          </div>

          <span class="text-white font-bold text-sm">
            {{ card.value }}
          </span>
        </div>

        <template v-else>
          <p
            class="text-white font-bold"
            style="font-size: 13px"
          >
            {{ card.value }}
          </p>

          <p
            style="color: #3EA083; font-size: 8px"
          >
            {{ card.delta }}
          </p>
        </template>

      </div>
    </div>

    <div class="grid grid-cols-2 gap-2 px-3 pb-3">

      <div
        class="rounded-xl p-2.5"
        style="background: rgba(255,255,255,0.04); border: 1px solid rgba(255,255,255,0.06)"
      >
        <p
          class="mb-2"
          style="color: rgba(255,255,255,0.35); font-size: 8px; letter-spacing: 0.08em"
        >
          RECEITA MENSAL
        </p>

        <div class="h-24 overflow-hidden">

          <svg
            viewBox="0 0 180 96"
            class="w-full h-full"
            preserveAspectRatio="none"
          >
            <line
              x1="25"
              y1="10"
              x2="175"
              y2="10"
              stroke="rgba(255,255,255,0.05)"
            />

            <line
              x1="25"
              y1="40"
              x2="175"
              y2="40"
              stroke="rgba(255,255,255,0.05)"
            />

            <line
              x1="25"
              y1="70"
              x2="175"
              y2="70"
              stroke="rgba(255,255,255,0.05)"
            />

            <g
              v-for="(bar, index) in barData"
              :key="bar.mes"
            >
              <rect
                :x="30 + index * 24"
                :y="82 - bar.valor * 10"
                width="10"
                :height="bar.valor * 10"
                rx="3"
                fill="#3EA083"
              />

              <text
                :x="35 + index * 24"
                y="94"
                text-anchor="middle"
                fill="rgba(255,255,255,0.25)"
                font-size="7"
              >
                {{ bar.mes }}
              </text>
            </g>

            <text
              x="2"
              y="14"
              fill="rgba(255,255,255,0.25)"
              font-size="7"
            >
              8M
            </text>

            <text
              x="2"
              y="44"
              fill="rgba(255,255,255,0.25)"
              font-size="7"
            >
              4M
            </text>

            <text
              x="5"
              y="74"
              fill="rgba(255,255,255,0.25)"
              font-size="7"
            >
              0
            </text>
          </svg>

        </div>
      </div>

      <div
        class="rounded-xl p-2.5"
        style="background: rgba(255,255,255,0.04); border: 1px solid rgba(255,255,255,0.06)"
      >
        <p
          class="mb-2"
          style="color: rgba(255,255,255,0.35); font-size: 8px; letter-spacing: 0.08em"
        >
          CRESCIMENTO
        </p>

        <div class="h-24 overflow-hidden">

          <svg
            viewBox="0 0 180 96"
            class="w-full h-full"
            preserveAspectRatio="none"
          >
            <defs>
              <linearGradient
                id="growthGradient"
                x1="0"
                y1="0"
                x2="0"
                y2="1"
              >
                <stop
                  offset="0%"
                  stop-color="#3EA083"
                  stop-opacity="0.3"
                />

                <stop
                  offset="100%"
                  stop-color="#3EA083"
                  stop-opacity="0"
                />
              </linearGradient>
            </defs>

            <line
              x1="25"
              y1="15"
              x2="175"
              y2="15"
              stroke="rgba(255,255,255,0.05)"
            />

            <line
              x1="25"
              y1="45"
              x2="175"
              y2="45"
              stroke="rgba(255,255,255,0.05)"
            />

            <line
              x1="25"
              y1="75"
              x2="175"
              y2="75"
              stroke="rgba(255,255,255,0.05)"
            />

            <polygon
              points="25,70 50,60 75,64 100,45 125,50 150,28 175,20 175,80 25,80"
              fill="url(#growthGradient)"
            />

            <polyline
              points="25,70 50,60 75,64 100,45 125,50 150,28 175,20"
              fill="none"
              stroke="#3EA083"
              stroke-width="1.5"
            />

            <polyline
              points="25,75 50,68 75,70 100,60 125,55 150,45 175,35"
              fill="none"
              stroke="#0F6E56"
              stroke-width="1.5"
            />

            <text
              x="4"
              y="19"
              fill="rgba(255,255,255,0.25)"
              font-size="7"
            >
              100
            </text>

            <text
              x="8"
              y="49"
              fill="rgba(255,255,255,0.25)"
              font-size="7"
            >
              50
            </text>

            <text
              x="11"
              y="79"
              fill="rgba(255,255,255,0.25)"
              font-size="7"
            >
              0
            </text>

            <text
              x="25"
              y="94"
              fill="rgba(255,255,255,0.25)"
              font-size="7"
            >
              Jan
            </text>

            <text
              x="72"
              y="94"
              fill="rgba(255,255,255,0.25)"
              font-size="7"
            >
              Mar
            </text>

            <text
              x="120"
              y="94"
              fill="rgba(255,255,255,0.25)"
              font-size="7"
            >
              Mai
            </text>

            <text
              x="165"
              y="94"
              fill="rgba(255,255,255,0.25)"
              font-size="7"
            >
              Jun
            </text>
          </svg>

        </div>
      </div>

    </div>
  </div>
</template>

<script setup>
const kpis = [
  {
    label: 'Faturamento',
    value: 'R$ 2,45M',
    delta: '+9,8%'
  },
  {
    label: 'Clientes',
    value: '1.248',
    delta: '+6,1%'
  },
  {
    label: 'Meta',
    value: '75%',
    circle: true
  }
]

const barData = [
  { mes: 'Jan', valor: 5.5 },
  { mes: 'Fev', valor: 7.2 },
  { mes: 'Mar', valor: 4.8 },
  { mes: 'Abr', valor: 6.8 },
  { mes: 'Mai', valor: 8.2 },
  { mes: 'Jun', valor: 6.3 }
]
</script>