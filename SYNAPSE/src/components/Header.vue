<template>
    <header class="bg-[#0E1B30] w-full px-5 py-7 flex items-center">
          <div class="flex items-center gap-4">     
        <img :src="logo" alt="Logo" class="w-12 h-12" />
        <div class="flex items-center">
          <span class="text-white text-3xl font-inter font-extrabold">Syn</span>
          <span class="text-[#0F6E56] text-3xl font-inter font-extrabold">apse</span>
        </div>
        </div>
    <div class="absolute left-1/2 -translate-x-1/2">
      <ul class="flex gap-8">
        <li>
          <a 
            href="/"
            class="text-gray-200 text-xl font-inter font-semibold hover:text-white"
          >
            Recursos
          </a>
        </li>

        <li>
          <a 
            href="/contact"
            class="text-gray-200 text-xl font-inter font-semibold hover:text-white"
          >
            Contato
          </a>
        </li>
      </ul>
    </div>
  <button class="ml-auto bg-[#E33838] text-white px-6 py-3 rounded-lg font-inter font-normal hover:brightness-90 transition">
      Entrar no sistema
    </button>
 </header>
</template>

<script>
import logo from '../assets/logo.png'

export default {
  name: 'Header',
  data() {
    return {
      logo,
    }
  },
}
</script>