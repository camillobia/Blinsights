<template>
  <div class="min-h-screen flex">

    <div
      class="hidden lg:flex flex-col justify-between relative overflow-hidden w-[48%] p-12"
      style="background: linear-gradient(160deg, #0E1B30 0%, #0a2540 60%, #082019 100%)"
    >
      <div
        class="absolute inset-0 opacity-20"
        style="background-image: radial-gradient(ellipse at 20% 50%, rgba(62,160,131,0.27) 0%, transparent 60%), radial-gradient(ellipse at 80% 20%, rgba(15,110,86,0.20) 0%, transparent 50%)"
      ></div>

      <div
        class="absolute inset-0 opacity-5"
        style="background-image: linear-gradient(#3EA083 1px, transparent 1px), linear-gradient(90deg, #3EA083 1px, transparent 1px); background-size: 40px 40px"
      ></div>

      <div class="relative z-10">
        <button
          type="button"
          @click="goHome"
          class="flex items-center gap-2"
        >
          <img
            :src="logo"
            alt="Synapse"
            class="w-10 h-10 object-contain"
          />

          <div class="flex items-center">
            <span class="text-white text-2xl font-extrabold">Syn</span>
            <span
              class="text-2xl font-extrabold"
              style="color: #0F6E56"
            >
              apse
            </span>
          </div>
        </button>
      </div>

      <div class="relative z-10">

        <div
          class="inline-flex items-center gap-2 rounded-full px-3 py-1 mb-6 text-xs font-semibold"
          style="background: rgba(62,160,131,0.15); color: #3EA083; border: 1px solid rgba(62,160,131,0.25)"
        >
          <span
            class="w-1.5 h-1.5 rounded-full"
            style="background: #3EA083"
          ></span>

          Plataforma confiada por + 3.200 empresas
        </div>

        <h2 class="text-3xl font-extrabold text-white leading-tight mb-4">
          Dados que
          <span style="color: #3EA083">falam</span>
          por si mesmos.
        </h2>

        <p
          class="text-sm leading-relaxed mb-10"
          style="color: rgba(255,255,255,0.5)"
        >
          Importe, visualize e decida — tudo em minutos. O Synapse transforma
          planilhas brutas em painéis executivos prontos para uso.
        </p>

        <div class="grid grid-cols-3 gap-3">

          <div
            v-for="stat in stats"
            :key="stat.l"
            class="rounded-2xl p-3 text-center"
            style="background: rgba(255,255,255,0.05); border: 1px solid rgba(255,255,255,0.07)"
          >
            <p
              class="text-lg font-extrabold"
              style="color: #3EA083"
            >
              {{ stat.v }}
            </p>

            <p
              class="text-xs"
              style="color: rgba(255,255,255,0.4)"
            >
              {{ stat.l }}
            </p>
          </div>

        </div>
      </div>

      <div
        class="relative z-10 rounded-2xl overflow-hidden"
        style="background: rgba(255,255,255,0.04); border: 1px solid rgba(255,255,255,0.07); padding: 16px"
      >
        <p
          class="mb-2"
          style="color: rgba(255,255,255,0.35); font-size: 9px; letter-spacing: 0.1em"
        >
          PRÉVIA DO DASHBOARD
        </p>

        <div class="h-28 overflow-hidden">

          <svg
            viewBox="0 0 340 112"
            class="w-full h-full"
            preserveAspectRatio="none"
          >
            <defs>
              <linearGradient
                id="loginGradient"
                x1="0"
                y1="0"
                x2="0"
                y2="1"
              >
                <stop
                  offset="0%"
                  stop-color="#3EA083"
                  stop-opacity="0.35"
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
              y1="20"
              x2="330"
              y2="20"
              stroke="rgba(255,255,255,0.05)"
            />

            <line
              x1="25"
              y1="50"
              x2="330"
              y2="50"
              stroke="rgba(255,255,255,0.05)"
            />

            <line
              x1="25"
              y1="80"
              x2="330"
              y2="80"
              stroke="rgba(255,255,255,0.05)"
            />

            <polygon
              points="25,82 75,70 125,74 175,48 225,55 275,30 325,20 325,100 25,100"
              fill="url(#loginGradient)"
            />

            <polyline
              points="25,82 75,70 125,74 175,48 225,55 275,30 325,20"
              fill="none"
              stroke="#3EA083"
              stroke-width="2"
            />

            <text
              x="25"
              y="108"
              fill="rgba(255,255,255,0.25)"
              font-size="8"
            >
              Jan
            </text>

            <text
              x="120"
              y="108"
              fill="rgba(255,255,255,0.25)"
              font-size="8"
            >
              Mar
            </text>

            <text
              x="215"
              y="108"
              fill="rgba(255,255,255,0.25)"
              font-size="8"
            >
              Mai
            </text>

            <text
              x="310"
              y="108"
              fill="rgba(255,255,255,0.25)"
              font-size="8"
            >
              Jun
            </text>
          </svg>

        </div>
      </div>

    </div>

    <div
      class="flex-1 flex items-center justify-center px-8 py-14"
      style="background: #F0F4F8"
    >

      <div class="w-full max-w-[380px]">

        <div class="lg:hidden mb-10">

          <button
            type="button"
            @click="goHome"
            class="flex items-center gap-2"
          >
            <img
              :src="logo"
              alt="Synapse"
              class="w-10 h-10 object-contain"
            />

            <div class="flex items-center">
              <span
                class="text-2xl font-extrabold"
                style="color: #0E1B30"
              >
                Syn
              </span>

              <span
                class="text-2xl font-extrabold"
                style="color: #0F6E56"
              >
                apse
              </span>
            </div>
          </button>

        </div>

        <div
          class="rounded-3xl p-8"
          style="background: #fff; box-shadow: 0 4px 40px rgba(14,27,48,0.08); border: 1px solid rgba(14,27,48,0.06)"
        >

          <div class="mb-7">

            <h1
              class="text-2xl font-extrabold mb-1"
              style="color: #0E1B30"
            >
              Bem-vindo de volta
            </h1>

            <p
              class="text-sm"
              style="color: #94A3B8"
            >
              Acesse sua conta para continuar
            </p>

          </div>

          <form
            @submit.prevent="handleLogin"
            class="flex flex-col gap-4"
          >

            <div>

              <label
                class="block text-xs font-semibold mb-1.5"
                style="color: #0E1B30"
              >
                E-mail
              </label>

              <div class="relative">

                <span
                  class="absolute left-3.5 top-1/2 -translate-y-1/2"
                >
                  <svg
                    viewBox="0 0 24 24"
                    fill="none"
                    :stroke="focused === 'email' ? '#3EA083' : '#CBD5E1'"
                    stroke-width="2"
                    class="w-4 h-4"
                  >
                    <rect
                      x="2"
                      y="4"
                      width="20"
                      height="16"
                      rx="2"
                      stroke-linecap="round"
                      stroke-linejoin="round"
                    />

                    <path
                      d="M22 7l-10 7L2 7"
                      stroke-linecap="round"
                      stroke-linejoin="round"
                    />
                  </svg>
                </span>

                <input
                  v-model="email"
                  type="email"
                  required
                  placeholder="voce@empresa.com.br"
                  @focus="focused = 'email'"
                  @blur="focused = null"
                  class="w-full rounded-xl pl-10 pr-4 py-3 text-sm outline-none transition-all"
                  :style="{
                    border: `1.5px solid ${focused === 'email' ? '#3EA083' : '#E2E8F0'}`,
                    background: focused === 'email' ? 'rgba(62,160,131,0.03)' : '#F8FAFC',
                    color: '#0E1B30',
                    boxShadow: focused === 'email' ? '0 0 0 3px rgba(62,160,131,0.1)' : 'none'
                  }"
                />

              </div>

            </div>

            <div>

              <div class="flex items-center justify-between mb-1.5">

                <label
                  class="text-xs font-semibold"
                  style="color: #0E1B30"
                >
                  Senha
                </label>

                <button
                  type="button"
                  class="text-xs font-semibold"
                  style="color: #3EA083"
                >
                  Esqueci a senha
                </button>

              </div>

              <div class="relative">

                <span
                  class="absolute left-3.5 top-1/2 -translate-y-1/2"
                >
                  <svg
                    viewBox="0 0 24 24"
                    fill="none"
                    :stroke="focused === 'pass' ? '#3EA083' : '#CBD5E1'"
                    stroke-width="2"
                    class="w-4 h-4"
                  >
                    <rect
                      x="3"
                      y="11"
                      width="18"
                      height="11"
                      rx="2"
                      stroke-linecap="round"
                      stroke-linejoin="round"
                    />

                    <path
                      d="M7 11V7a5 5 0 0 1 10 0v4"
                      stroke-linecap="round"
                      stroke-linejoin="round"
                    />
                  </svg>
                </span>

                <input
                  v-model="password"
                  :type="showPass ? 'text' : 'password'"
                  required
                  placeholder="••••••••"
                  @focus="focused = 'pass'"
                  @blur="focused = null"
                  class="w-full rounded-xl pl-10 pr-11 py-3 text-sm outline-none transition-all"
                  :style="{
                    border: `1.5px solid ${focused === 'pass' ? '#3EA083' : '#E2E8F0'}`,
                    background: focused === 'pass' ? 'rgba(62,160,131,0.03)' : '#F8FAFC',
                    color: '#0E1B30',
                    boxShadow: focused === 'pass' ? '0 0 0 3px rgba(62,160,131,0.1)' : 'none'
                  }"
                />

                <button
                  type="button"
                  @click="showPass = !showPass"
                  class="absolute right-3.5 top-1/2 -translate-y-1/2"
                  style="color: #94A3B8"
                >
                  <svg
                    v-if="showPass"
                    viewBox="0 0 24 24"
                    fill="none"
                    stroke="currentColor"
                    stroke-width="2"
                    class="w-4 h-4"
                  >
                    <path
                      d="M17.94 17.94A10.07 10.07 0 0 1 12 20c-7 0-11-8-11-8a18.45 18.45 0 0 1 5.06-5.94M9.9 4.24A9.12 9.12 0 0 1 12 4c7 0 11 8 11 8a18.5 18.5 0 0 1-2.16 3.19m-6.72-1.07a3 3 0 1 1-4.24-4.24"
                      stroke-linecap="round"
                      stroke-linejoin="round"
                    />

                    <line
                      x1="1"
                      y1="1"
                      x2="23"
                      y2="23"
                      stroke-linecap="round"
                    />
                  </svg>

                  <svg
                    v-else
                    viewBox="0 0 24 24"
                    fill="none"
                    stroke="currentColor"
                    stroke-width="2"
                    class="w-4 h-4"
                  >
                    <path
                      d="M1 12s4-8 11-8 11 8 11 8-4 8-11 8-11-8-11-8z"
                      stroke-linecap="round"
                      stroke-linejoin="round"
                    />

                    <circle
                      cx="12"
                      cy="12"
                      r="3"
                    />
                  </svg>
                </button>

              </div>

            </div>

            <button
              type="submit"
              :disabled="loading"
              class="w-full mt-1 py-3.5 rounded-xl text-sm font-bold text-white transition-all active:scale-95"
              :style="{
                background: 'linear-gradient(135deg, #0F6E56, #3EA083)',
                boxShadow: '0 8px 24px rgba(15,110,86,0.3)',
                opacity: loading ? 0.85 : 1
              }"
            >

              <span
                v-if="loading"
                class="flex items-center justify-center gap-2"
              >

                <svg
                  class="animate-spin w-4 h-4"
                  viewBox="0 0 24 24"
                  fill="none"
                >
                  <circle
                    cx="12"
                    cy="12"
                    r="10"
                    stroke="rgba(255,255,255,0.3)"
                    stroke-width="3"
                  />

                  <path
                    d="M12 2a10 10 0 0 1 10 10"
                    stroke="white"
                    stroke-width="3"
                    stroke-linecap="round"
                  />
                </svg>

                Verificando...

              </span>

              <span v-else>
                Entrar no sistema
              </span>

            </button>

          </form>

        </div>

        <p
          class="text-center text-xs mt-5"
          style="color: #94A3B8"
        >
          © 2026 Synapse · Todos os direitos reservados
        </p>

      </div>

    </div>

  </div>
</template>

<script setup>
import { ref } from 'vue'
import { useRouter } from 'vue-router'
import logo from '../assets/logo.png'

const router = useRouter()

const email = ref('')
const password = ref('')
const showPass = ref(false)
const loading = ref(false)
const focused = ref(null)

const stats = [
  { v: '98%', l: 'satisfação' },
  { v: '< 5min', l: '1.º insight' },
  { v: '12+', l: 'formatos' }
]

function goHome() {
  router.push('/')
}

function handleLogin() {
  loading.value = true

  setTimeout(() => {
    loading.value = false
    router.push('/sidebar/upload')
  }, 1000)
}
</script>