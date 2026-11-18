<template>
  <main class="min-h-screen flex items-center justify-center px-6 py-12" style="background:#F0F4F8">
    <section class="w-full max-w-md rounded-3xl bg-white p-8" style="border:1px solid #E2E8F0">
      <h1 class="text-2xl font-extrabold" style="color:#0E1B30">Criar conta</h1>
      <p class="mt-1 mb-7 text-sm" style="color:#94A3B8">Sua conta ficará pendente até a aprovação de um administrador.</p>

      <form class="flex flex-col gap-4" @submit.prevent="cadastrar">
        <input v-model="form.nome" required placeholder="Nome completo" class="campo">
        <input v-model="form.email" required type="email" placeholder="E-mail" class="campo">
        <input v-model="form.senha" required minlength="6" type="password" placeholder="Senha" class="campo">
        <input v-model="form.matricula" placeholder="Matrícula" class="campo">
        <input v-model="form.turma" placeholder="Turma" class="campo">
        <p v-if="erro" class="text-sm text-red-600">{{ erro }}</p>
        <p v-if="sucesso" class="text-sm font-semibold" style="color:#0F6E56">{{ sucesso }}</p>
        <button :disabled="carregando" type="submit" class="py-3.5 rounded-xl text-sm font-bold text-white" style="background:#0F6E56">
          {{ carregando ? 'Cadastrando...' : 'Solicitar cadastro' }}
        </button>
      </form>
      <button type="button" class="mt-5 w-full text-sm font-semibold" style="color:#0F6E56" @click="router.push('/login')">
        Voltar ao login
      </button>
    </section>
  </main>
</template>

<script setup>
import { reactive, ref } from 'vue'
import { useRouter } from 'vue-router'
import { authService } from '../services/api'

const router = useRouter()
const form = reactive({ nome: '', email: '', senha: '', matricula: '', turma: '' })
const erro = ref('')
const sucesso = ref('')
const carregando = ref(false)

async function cadastrar() {
  erro.value = ''
  sucesso.value = ''
  carregando.value = true
  try {
    await authService.cadastrar(form)
    sucesso.value = 'Cadastro enviado. Aguarde a aprovação.'
    Object.assign(form, { nome: '', email: '', senha: '', matricula: '', turma: '' })
  } catch (e) {
    erro.value = e.response?.status === 409
      ? 'Já existe uma conta com este e-mail.'
      : 'Não foi possível concluir o cadastro.'
  } finally {
    carregando.value = false
  }
}
</script>

<style scoped>
.campo {
  width: 100%;
  border: 1px solid #E2E8F0;
  border-radius: 0.75rem;
  background: #F8FAFC;
  padding: 0.75rem 1rem;
  color: #0E1B30;
  outline: none;
}
</style>
