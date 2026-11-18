<template>
  <main class="min-h-screen p-6 md:p-10" style="background:#F0F4F8">
    <section class="max-w-5xl mx-auto rounded-2xl bg-white p-6" style="border:1px solid #E2E8F0">
      <div class="flex items-center justify-between mb-6">
        <div>
          <h1 class="text-2xl font-extrabold" style="color:#0E1B30">Usuários</h1>
          <p class="text-sm" style="color:#94A3B8">Aprove ou bloqueie contas.</p>
        </div>
        <select v-model="filtro" class="rounded-xl border px-3 py-2" @change="carregar">
          <option value="">Todos</option>
          <option value="PENDENTE">Pendentes</option>
          <option value="APROVADO">Aprovados</option>
          <option value="BLOQUEADO">Bloqueados</option>
        </select>
      </div>

      <p v-if="erro" class="mb-4 text-sm text-red-600">{{ erro }}</p>
      <div class="overflow-x-auto">
        <table class="w-full text-sm">
          <thead style="color:#64748B">
            <tr class="text-left border-b">
              <th class="py-3">Nome</th>
              <th>E-mail</th>
              <th>Matrícula</th>
              <th>Status</th>
              <th>Ações</th>
            </tr>
          </thead>
          <tbody>
            <tr v-for="usuario in usuarios" :key="usuario.id" class="border-b">
              <td class="py-3">{{ usuario.nome }}</td>
              <td>{{ usuario.email }}</td>
              <td>{{ usuario.matricula || '-' }}</td>
              <td>{{ usuario.status || 'PENDENTE' }}</td>
              <td class="py-3">
                <button v-if="usuario.status !== 'APROVADO'" class="mr-2 rounded-lg px-3 py-1 text-white" style="background:#0F6E56" @click="alterar(usuario.id, 'APROVADO')">Aprovar</button>
                <button v-if="usuario.status !== 'BLOQUEADO'" class="rounded-lg px-3 py-1 text-white" style="background:#E33838" @click="alterar(usuario.id, 'BLOQUEADO')">Bloquear</button>
              </td>
            </tr>
          </tbody>
        </table>
      </div>
    </section>
  </main>
</template>

<script setup>
import { onMounted, ref } from 'vue'
import { adminService } from '../services/api'

const usuarios = ref([])
const filtro = ref('')
const erro = ref('')

async function carregar() {
  erro.value = ''
  try {
    usuarios.value = await adminService.listar(filtro.value)
  } catch {
    erro.value = 'Não foi possível carregar os usuários.'
  }
}

async function alterar(id, status) {
  try {
    await adminService.alterarStatus(id, status)
    await carregar()
  } catch {
    erro.value = 'Não foi possível alterar o usuário.'
  }
}

onMounted(carregar)
</script>
