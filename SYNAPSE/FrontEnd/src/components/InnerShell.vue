<template>
  <div class="min-h-screen" style="background: #F0F4F8">
    <SideMenu
      :open="menuOpen"
      :page="page"
      @close="menuOpen = false"
    />

    <header
      class="sticky top-0 z-30"
      style="background: #0E1B30; box-shadow: 0 1px 0 rgba(255,255,255,0.06)"
    >
      <div class="w-full px-6 py-4 flex items-center justify-between">
        <div class="flex items-center gap-4">
          <button
            @click="menuOpen = true"
            class="flex flex-col items-center justify-center"
            style="gap:5px;width:40px;height:40px;border-radius:10px;background:rgba(255,255,255,0.07);border:none;cursor:pointer"
          >
            <span style="width:20px;height:2px;background:#fff"></span>
            <span style="width:14px;height:2px;background:#fff"></span>
            <span style="width:20px;height:2px;background:#fff"></span>
          </button>

          <span style="width:1px;height:24px;background:rgba(255,255,255,0.12)"></span>

          <button
            @click="goHome"
            class="flex items-center gap-2"
            style="background:transparent;border:none;cursor:pointer"
          >
            <img
              :src="logo"
              alt="Logo"
              class="w-9 h-9 object-contain"
            />

            <div class="flex items-center">
              <span class="text-white text-2xl font-extrabold">Syn</span>
              <span class="text-[#0F6E56] text-2xl font-extrabold">apse</span>
            </div>
          </button>
        </div>

        <span
          class="inline-flex items-center gap-1.5 px-3 py-1 rounded-full text-xs font-semibold"
          style="background:rgba(62,160,131,0.12);color:#3EA083"
        >
          <span
            class="w-1.5 h-1.5 rounded-full"
            style="background:#3EA083"
          ></span>

          {{ pageTitle }}
        </span>
      </div>
    </header>

    <main style="display:block;min-height:500px">
      <slot></slot>
    </main>
  </div>
</template>

<script setup>
import { computed, ref } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import logo from '../assets/logo.png'
import SideMenu from './Sidemenu.vue'

const router = useRouter()
const route = useRoute()

const menuOpen = ref(false)

const page = computed(() => {
  if (route.path.includes('/upload')) {
    return 'upload'
  }

  if (route.path.includes('/dashboard')) {
    return 'dashboard'
  }

  return 'landing'
})

const pageTitle = computed(() => {
  if (page.value === 'upload') {
    return 'Upload de Planilha'
  }

  if (page.value === 'dashboard') {
    return 'Dashboard'
  }

  return 'Sistema'
})

function goHome() {
  router.push('/')
}
</script>