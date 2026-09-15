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

        <div class="relative ml-auto">
          <button
            @click="userMenuOpen = !userMenuOpen"
            class="w-10 h-10 rounded-full flex items-center justify-center font-bold text-sm transition-all"
            :style="{
              background: userMenuOpen ? '#0F6E56' : '#E2E8F0',
              color: userMenuOpen ? '#fff' : '#0E1B30'
            }"
          >
            B
          </button>

          <!-- Modal do usuário -->
          <div
            v-if="userMenuOpen"
            class="fixed inset-0 z-40"
            @click="userMenuOpen = false"
          ></div>

          <div
            v-if="userMenuOpen"
            class="absolute right-0 mt-2 w-56 rounded-lg shadow-lg z-50"
            style="background:#fff;border:1px solid #E2E8F0"
            @click.stop
          >
            <div class="px-4 py-3" style="border-bottom:1px solid #E2E8F0">
              <p class="text-sm font-semibold" style="color:#0E1B30">
                Usuário
              </p>
              <p class="text-xs mt-1" style="color:#94A3B8">
                beatriz@synapse.com
              </p>
            </div>

            <div class="py-2">
              <button
                @click="logout"
                class="w-full flex items-center gap-3 px-4 py-2.5 text-sm font-medium text-left transition-all hover:bg-red-50"
                style="color:#E33838"
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
        </div>
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
const userMenuOpen = ref(false)

const page = computed(() => {
  if (route.path.includes('/upload')) {
    return 'upload'
  }

  if (route.path.includes('/dashboard')) {
    return 'dashboard'
  }

  return 'landing'
})

function goHome() {
  router.push('/')
}

function logout() {
  userMenuOpen.value = false
  router.push('/')
}
</script>