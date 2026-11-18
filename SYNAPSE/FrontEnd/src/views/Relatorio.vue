<template>
  <InnerShell>
    <div class="max-w-7xl mx-auto px-8 py-12">
      <div
        class="relative overflow-hidden rounded-3xl mb-10 px-8 py-9"
        :style="{
          background: `linear-gradient(120deg, ${NAVY} 0%, #123152 55%, ${GREEN} 130%)`,
          boxShadow: '0 20px 50px rgba(14,27,48,0.28)'
        }"
      >
        <h1 class="text-4xl font-extrabold mb-2 text-white tracking-tight">
          Relatório de validação
        </h1>
        <p class="text-sm max-w-xl" style="color:rgba(255,255,255,0.7)">
          {{
            temDados
              ? `Resultado da validação de ${uploadStore.nomeArquivo}. Só os registros válidos são enviados e gravados.`
              : 'Envie uma planilha para ver o relatório de validação.'
          }}
        </p>
      </div>

      <div
        v-if="!temDados"
        class="rounded-3xl p-10 text-center"
        style="background:#fff;border:1px solid #E2E8F0"
      >
        <p class="text-sm mb-4" style="color:#64748B">
          Nenhum arquivo foi lido nesta sessão.
        </p>
        <button
          type="button"
          @click="router.push('/sidebar/upload')"
          class="px-6 py-2.5 rounded-xl text-sm font-semibold text-white"
          :style="{ background: `linear-gradient(135deg, ${GREEN}, ${ACCENT})` }"
        >
          Ir para o upload
        </button>
      </div>

      <template v-else>
        <div class="grid sm:grid-cols-2 lg:grid-cols-4 gap-6 mb-8">
          <div
            v-for="card in cards"
            :key="card.label"
            class="rounded-3xl p-6"
            style="background:#fff;border:1px solid #E2E8F0;box-shadow:0 2px 20px rgba(14,27,48,0.06)"
          >
            <p class="text-4xl font-extrabold leading-none mb-1" :style="{ color: card.color }">
              {{ card.value }}
            </p>
            <p class="text-xs font-medium" style="color:#94A3B8">{{ card.label }}</p>
          </div>
        </div>

        <div class="grid lg:grid-cols-3 gap-8 items-start">
          <div
            class="rounded-3xl p-6"
            style="background:#fff;border:1px solid #E2E8F0;box-shadow:0 2px 20px rgba(14,27,48,0.06)"
          >
            <p class="text-xs font-bold tracking-widest uppercase mb-5" style="color:#94A3B8">
              Ocorrências por tipo
            </p>

            <p v-if="!tipos.length" class="text-sm" style="color:#64748B">
              Nenhuma ocorrência encontrada.
            </p>

            <div
              v-for="item in tipos"
              :key="item.tipo"
              class="flex items-center justify-between py-2"
              style="border-bottom:1px solid #F1F5F9"
            >
              <span class="text-sm" style="color:#475569">{{ item.tipo }}</span>
              <span class="text-sm font-bold" :style="{ color: NAVY }">{{ item.total }}</span>
            </div>
          </div>

          <div
            class="lg:col-span-2 rounded-3xl overflow-hidden"
            style="background:#fff;border:1px solid #E2E8F0;box-shadow:0 2px 20px rgba(14,27,48,0.06)"
          >
            <div
              class="px-6 py-4"
              style="background:linear-gradient(135deg, rgba(15,110,86,0.04), rgba(62,160,131,0.04));border-bottom:1px solid #E2E8F0"
            >
              <p class="text-xs font-bold tracking-widest uppercase" style="color:#94A3B8">
                Detalhes
              </p>
            </div>

            <p v-if="!uploadStore.erros.length" class="px-6 py-6 text-sm" style="color:#64748B">
              Nenhum problema encontrado na planilha.
            </p>

            <div v-else class="overflow-x-auto">
              <table class="w-full text-sm">
                <thead>
                  <tr style="border-bottom:1px solid #E2E8F0;background:#F8FAFC">
                    <th class="px-4 py-3 text-left font-semibold" style="color:#475569">Linha</th>
                    <th class="px-4 py-3 text-left font-semibold" style="color:#475569">Campo</th>
                    <th class="px-4 py-3 text-left font-semibold" style="color:#475569">Tipo</th>
                    <th class="px-4 py-3 text-left font-semibold" style="color:#475569">Descrição</th>
                  </tr>
                </thead>
                <tbody>
                  <tr
                    v-for="(erro, index) in uploadStore.erros.slice(0, LIMITE)"
                    :key="index"
                    :style="{
                      borderBottom: '1px solid #F1F5F9',
                      background: index % 2 === 0 ? '#fff' : '#F8FAFC'
                    }"
                  >
                    <td class="px-4 py-3" style="color:#64748B">{{ erro.linha || '-' }}</td>
                    <td class="px-4 py-3" style="color:#64748B">{{ erro.campo }}</td>
                    <td class="px-4 py-3" style="color:#64748B">{{ erro.tipo }}</td>
                    <td class="px-4 py-3" style="color:#64748B">{{ erro.descricao }}</td>
                  </tr>
                </tbody>
              </table>
            </div>

            <div
              v-if="uploadStore.erros.length > LIMITE"
              class="px-6 py-3"
              style="background:#F8FAFC;border-top:1px solid #E2E8F0"
            >
              <p class="text-xs font-medium" style="color:#94A3B8">
                + {{ uploadStore.erros.length - LIMITE }} ocorrências adicionais
              </p>
            </div>
          </div>
        </div>
      </template>
    </div>
  </InnerShell>
</template>

<script setup>
import { computed } from 'vue'
import { useRouter } from 'vue-router'
import { useUploadstore } from '../store/Uploadstore'
import InnerShell from '../components/InnerShell.vue'

const router = useRouter()
const uploadStore = useUploadstore()

const NAVY = '#0E1B30'
const GREEN = '#0F6E56'
const ACCENT = '#3EA083'
const RED = '#E33838'
const LIMITE = 50

const temDados = computed(() => uploadStore.totalLinhas > 0 || uploadStore.erros.length > 0)

const cards = computed(() => [
  { label: 'linhas lidas', value: uploadStore.totalLinhas, color: NAVY },
  { label: 'registros válidos', value: uploadStore.registrosValidos, color: GREEN },
  { label: 'registros com erro', value: uploadStore.registrosComErro, color: RED },
  { label: 'ocorrências', value: uploadStore.totalErros, color: ACCENT }
])

const tipos = computed(() =>
  Object.entries(uploadStore.errosPorTipo)
    .map(([tipo, total]) => ({ tipo, total }))
    .sort((a, b) => b.total - a.total)
)
</script>