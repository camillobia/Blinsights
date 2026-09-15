<template>
  <div
    @click="closeMenu"
    :style="{
      position: 'fixed',
      inset: 0,
      zIndex: 40,
      background: 'rgba(14,27,48,0.6)',
      backdropFilter: open ? 'blur(4px)' : 'none',
      WebkitBackdropFilter: open ? 'blur(4px)' : 'none',
      opacity: open ? 1 : 0,
      pointerEvents: open ? 'auto' : 'none',
      transition: 'opacity 0.3s ease'
    }"
  ></div>

  <div
    :style="{
      position: 'fixed',
      top: 0,
      left: 0,
      bottom: 0,
      width: '300px',
      zIndex: 50,
      display: 'flex',
      flexDirection: 'column',
      background: '#0E1B30',
      transform: open ? 'translateX(0%)' : 'translateX(-100%)',
      transition: 'transform 0.35s cubic-bezier(0.4,0,0.2,1)',
      boxShadow: open ? '20px 0 60px rgba(0,0,0,0.45)' : 'none'
    }"
    @click.stop
  >
    <div
      class="flex items-center justify-between px-6 py-5"
      style="border-bottom: 1px solid rgba(255,255,255,0.07)"
    >
      <div
        class="flex items-center gap-2 cursor-pointer"
        @click="goHome"
      >
        <img
          :src="logo"
          alt="Synapse"
          class="w-8 h-8 object-contain"
        />

        <div class="flex items-center">
          <span class="text-white text-xl font-extrabold">Syn</span>
          <span class="text-[#3EA083] text-xl font-extrabold">apse</span>
        </div>
      </div>

      <button
        @click="closeMenu"
        class="w-8 h-8 rounded-lg flex items-center justify-center"
        style="background: rgba(255,255,255,0.06); color: rgba(255,255,255,0.6)"
      >
        <svg
          viewBox="0 0 24 24"
          fill="none"
          stroke="currentColor"
          stroke-width="2"
          class="w-4 h-4"
        >
          <line x1="18" y1="6" x2="6" y2="18" />
          <line x1="6" y1="6" x2="18" y2="18" />
        </svg>
      </button>
    </div>

    <nav class="flex-1 px-4 py-6 flex flex-col gap-1">
      <p
        class="text-xs font-semibold px-3 mb-3 tracking-widest uppercase"
        style="color: rgba(255,255,255,0.3)"
      >
        Navegação
      </p>

      <button
        @click="goHome"
        class="flex items-center gap-3 px-3 py-3 rounded-xl text-sm font-medium text-left w-full transition-all"
        :style="navStyle('landing')"
      >
        <svg
          viewBox="0 0 24 24"
          fill="none"
          stroke="currentColor"
          stroke-width="1.8"
          class="w-4 h-4 shrink-0"
        >
          <path
            d="M3 9l9-7 9 7v11a2 2 0 0 1-2 2H5a2 2 0 0 1-2-2z"
            stroke-linecap="round"
            stroke-linejoin="round"
          />
        </svg>

        Página inicial
      </button>

      <button
        @click="goUpload"
        class="flex items-center gap-3 px-3 py-3 rounded-xl text-sm font-medium text-left w-full transition-all"
        :style="navStyle('upload')"
      >
        <svg
          viewBox="0 0 24 24"
          fill="none"
          stroke="currentColor"
          stroke-width="1.8"
          class="w-4 h-4 shrink-0"
        >
          <path
            d="M21 15v4a2 2 0 0 1-2 2H5a2 2 0 0 1-2-2v-4M17 8l-5-5-5 5M12 3v12"
            stroke-linecap="round"
            stroke-linejoin="round"
          />
        </svg>

        Upload de Planilha
      </button>

      <button
        @click="goDashboard"
        class="flex items-center gap-3 px-3 py-3 rounded-xl text-sm font-medium text-left w-full transition-all"
        :style="navStyle('dashboard')"
      >
        <svg
          viewBox="0 0 24 24"
          fill="none"
          stroke="currentColor"
          stroke-width="1.8"
          class="w-4 h-4 shrink-0"
        >
          <path
            d="M3 13h8V3H3zM13 21h8V11h-8zM3 21h8v-6H3zM13 9h8V3h-8z"
            stroke-linecap="round"
            stroke-linejoin="round"
          />
        </svg>

        Dashboard
      </button>
    </nav>

    <div
      class="px-4 py-6"
      style="border-top: 1px solid rgba(255,255,255,0.07)"
    >
      <div
        class="rounded-xl p-4 mb-4"
        style="background: rgba(255,255,255,0.04); border: 1px solid rgba(255,255,255,0.06)"
      >
        <p class="text-xs font-semibold text-white mb-0.5">
          Plano Profissional
        </p>

        <p
          class="text-xs"
          style="color: rgba(255,255,255,0.4)"
        >
          3.200 uploads restantes
        </p>

        <div
          class="mt-2 h-1.5 rounded-full overflow-hidden"
          style="background: rgba(255,255,255,0.08)"
        >
          <div
            class="h-full rounded-full"
            style="width: 62%; background: linear-gradient(90deg, #0F6E56, #3EA083)"
          ></div>
        </div>
      </div>

      <button
        @click="goHome"
        class="w-full flex items-center gap-2 px-3 py-2.5 rounded-xl text-sm font-medium"
        style="color: #E33838; background: rgba(227,56,56,0.07)"
      >
        <svg
          viewBox="0 0 24 24"
          fill="none"
          stroke="currentColor"
          stroke-width="2"
          class="w-4 h-4"
        >
          <path
            d="M9 21H5a2 2 0 0 1-2-2V5a2 2 0 0 1 2-2h4M16 17l5-5-5-5M21 12H9"
            stroke-linecap="round"
            stroke-linejoin="round"
          />
        </svg>

        Sair da conta
      </button>
    </div>
  </div>
</template>

<script setup>
import { onBeforeUnmount, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import logo from '../assets/logo.png'

const props = defineProps({
  open: {
    type: Boolean,
    default: false
  },
  page: {
    type: String,
    default: 'landing'
  }
})

const emit = defineEmits(['close'])

const router = useRouter()

function closeMenu() {
  emit('close')
}

function goHome() {
  closeMenu()
  router.push('/')
}

function goUpload() {
  closeMenu()
  router.push('/sidebar/upload')
}

function goDashboard() {
  closeMenu()
  router.push('/sidebar/dashboard')
}

function navStyle(target) {
  const active = props.page === target

  return {
    background: active ? 'rgba(62,160,131,0.15)' : 'transparent',
    color: active ? '#3EA083' : 'rgba(255,255,255,0.65)',
    borderLeft: active ? '3px solid #3EA083' : '3px solid transparent'
  }
}

function handleEscape(event) {
  if (event.key === 'Escape') {
    closeMenu()
  }
}

onMounted(() => {
  document.addEventListener('keydown', handleEscape)
})

onBeforeUnmount(() => {
  document.removeEventListener('keydown', handleEscape)
})
</script>