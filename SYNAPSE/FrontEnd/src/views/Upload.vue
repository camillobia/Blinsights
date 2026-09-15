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
        <div
          class="absolute pointer-events-none"
          :style="{
            top: '-80px',
            right: '-40px',
            width: '320px',
            height: '320px',
            borderRadius: '50%',
            background: 'radial-gradient(circle, rgba(62,160,131,0.35), transparent 70%)'
          }"
        ></div>

        <div
          class="absolute pointer-events-none"
          :style="{
            bottom: '-120px',
            left: '120px',
            width: '280px',
            height: '280px',
            borderRadius: '50%',
            background: 'radial-gradient(circle, rgba(62,160,131,0.18), transparent 70%)'
          }"
        ></div>

        <div class="relative flex flex-col lg:flex-row lg:items-end lg:justify-between gap-6">
          <div>
            <div
              class="inline-flex items-center gap-2 rounded-full px-3 py-1 mb-4"
              style="background:rgba(255,255,255,0.1);border:1px solid rgba(255,255,255,0.15)"
            >
              <span
                class="w-1.5 h-1.5 rounded-full inline-block"
                :style="{ background: ACCENT }"
              ></span>

              <span
                class="text-xs font-bold tracking-widest uppercase"
                style="color:#A7E0CD"
              >
                Upload de planilha
              </span>
            </div>

            <h1 class="text-4xl font-extrabold mb-2 text-white tracking-tight">
              Envie seus dados
            </h1>

            <p
              class="text-sm max-w-md"
              style="color:rgba(255,255,255,0.7)"
            >
              Arraste um arquivo XLSX, XLS ou CSV. O Synapse lê no navegador e prepara tudo para virar dashboard automaticamente.
            </p>
          </div>

          <div class="flex items-center gap-2">
            <div
              v-for="(step, index) in steps"
              :key="step.n"
              class="flex items-center gap-2"
            >
              <div
                class="flex items-center gap-2 rounded-xl px-3 py-2"
                :style="{
                  background: step.done
                    ? 'rgba(62,160,131,0.25)'
                    : 'rgba(255,255,255,0.07)',
                  border: `1px solid ${
                    step.done
                      ? 'rgba(62,160,131,0.5)'
                      : 'rgba(255,255,255,0.12)'
                  }`
                }"
              >
                <span
                  class="w-5 h-5 rounded-full flex items-center justify-center text-[10px] font-bold text-white"
                  :style="{
                    background: step.done
                      ? ACCENT
                      : 'rgba(255,255,255,0.15)'
                  }"
                >
                  {{ step.done ? '✓' : step.n }}
                </span>

                <span
                  class="text-xs font-semibold whitespace-nowrap"
                  :style="{
                    color: step.done
                      ? '#fff'
                      : 'rgba(255,255,255,0.6)'
                  }"
                >
                  {{ step.label }}
                </span>
              </div>

              <span
                v-if="index < 2"
                class="hidden sm:block w-4 h-px"
                style="background:rgba(255,255,255,0.2)"
              ></span>
            </div>
          </div>
        </div>
      </div>

      <div class="grid lg:grid-cols-3 gap-8 items-start">
        <div class="lg:col-span-2 flex flex-col gap-6">
          <div
            @click="openFileDialog"
            @dragover.prevent="dragging = true"
            @dragleave="dragging = false"
            @drop.prevent="handleDrop"
            class="rounded-3xl border-2 border-dashed flex flex-col items-center justify-center py-24 cursor-pointer transition-all duration-300 relative overflow-hidden"
            :style="uploadAreaStyle"
          >
            <div
              v-if="processed"
              class="absolute inset-0 pointer-events-none"
              style="background:radial-gradient(ellipse at 50% 100%, rgba(62,160,131,0.08), transparent 70%)"
            ></div>

            <div class="relative z-10 flex flex-col items-center gap-4">
              <div
                class="w-16 h-16 rounded-2xl flex items-center justify-center"
                :style="{
                  background: processed
                    ? `linear-gradient(135deg, ${GREEN}22, ${ACCENT}22)`
                    : dragging
                      ? 'rgba(62,160,131,0.12)'
                      : 'rgba(14,27,48,0.05)'
                }"
              >
                <svg
                  v-if="processed"
                  viewBox="0 0 24 24"
                  fill="none"
                  :stroke="GREEN"
                  stroke-width="2"
                  class="w-8 h-8"
                >
                  <polyline
                    points="20 6 9 17 4 12"
                    stroke-linecap="round"
                    stroke-linejoin="round"
                  />
                </svg>

                <svg
                  v-else
                  viewBox="0 0 24 24"
                  fill="none"
                  :stroke="dragging ? ACCENT : NAVY"
                  stroke-width="1.8"
                  class="w-8 h-8"
                >
                  <path
                    d="M21 15v4a2 2 0 0 1-2 2H5a2 2 0 0 1-2-2v-4"
                    stroke-linecap="round"
                    stroke-linejoin="round"
                  />

                  <polyline
                    points="17 8 12 3 7 8"
                    stroke-linecap="round"
                    stroke-linejoin="round"
                  />

                  <line
                    x1="12"
                    y1="3"
                    x2="12"
                    y2="15"
                    stroke-linecap="round"
                  />
                </svg>
              </div>

              <div class="text-center">
                <p
                  class="font-bold text-base"
                  :style="{ color: processed ? GREEN : NAVY }"
                >
                  {{
                    loading
                      ? 'Processando arquivo…'
                      : processed
                        ? file.name
                        : file
                          ? file.name
                          : 'Arraste seu arquivo aqui'
                  }}
                </p>

                <p
                  class="text-sm mt-0.5"
                  style="color:#94A3B8"
                >
                  {{
                    loading
                      ? 'Aguarde enquanto lemos os dados'
                      : processed
                        ? `${fileSize} KB · processado com sucesso`
                        : file
                          ? `${fileSize} KB`
                          : 'ou clique para selecionar · XLSX, XLS, CSV'
                  }}
                </p>
              </div>

              <div
                v-if="loading"
                class="w-48 h-1.5 rounded-full overflow-hidden"
                style="background:#E2E8F0"
              >
                <div
                  class="h-full rounded-full animate-pulse"
                  style="width:60%;background:linear-gradient(90deg,#0F6E56,#3EA083)"
                ></div>
              </div>

              <button
                v-if="!file && !loading"
                type="button"
                @click.stop="openFileDialog"
                class="px-6 py-2.5 rounded-xl text-sm font-semibold text-white"
                :style="{
                  background: `linear-gradient(135deg, ${GREEN}, ${ACCENT})`,
                  boxShadow: '0 6px 20px rgba(15,110,86,0.25)'
                }"
              >
                Escolher arquivo
              </button>

              <button
                v-if="processed"
                type="button"
                @click.stop="removeFile"
                class="px-4 py-1.5 rounded-lg text-xs font-semibold"
                style="color:#E33838;background:rgba(227,56,56,0.07)"
              >
                Remover e trocar arquivo
              </button>
            </div>
          </div>

          <input
            ref="inputRef"
            type="file"
            accept=".xlsx,.xls,.csv"
            class="hidden"
            @change="handleInput"
          />

          <div class="flex flex-wrap gap-2">
            <span
              v-for="format in formats"
              :key="format"
              class="px-3 py-1 rounded-lg text-xs font-semibold"
              style="background:#fff;border:1px solid #E2E8F0;color:#64748B"
            >
              {{ format }}
            </span>

            <span
              class="px-3 py-1 rounded-lg text-xs font-semibold ml-auto"
              style="background:rgba(62,160,131,0.08);color:#3EA083"
            >
              Máx. 50 MB
            </span>
          </div>

          <!-- Prévia da Planilha -->
          <div
            v-if="processed && sheetData.length > 0"
            class="rounded-3xl overflow-hidden"
            style="background:#fff;border:1px solid #E2E8F0;box-shadow:0 2px 20px rgba(14,27,48,0.06)"
          >
            <div
              class="px-6 py-4"
              style="background:linear-gradient(135deg, rgba(15,110,86,0.04), rgba(62,160,131,0.04));border-bottom:1px solid #E2E8F0"
            >
              <p
                class="text-xs font-bold tracking-widest uppercase"
                style="color:#94A3B8"
              >
                Prévia dos dados
              </p>
              <p
                class="text-xs mt-1"
                style="color:#64748B"
              >
                Exibindo as primeiras linhas da planilha
              </p>
            </div>

            <div class="overflow-x-auto">
              <table class="w-full text-sm">
                <thead>
                  <tr style="border-bottom:1px solid #E2E8F0;background:#F8FAFC">
                    <th
                      v-for="(header, index) in sheetHeaders.slice(0, 10)"
                      :key="index"
                      class="px-4 py-3 text-left font-semibold"
                      style="color:#475569;white-space:nowrap"
                    >
                      {{ header }}
                    </th>
                  </tr>
                </thead>
                <tbody>
                  <tr
                    v-for="(row, rowIndex) in sheetData.slice(0, 8)"
                    :key="rowIndex"
                    style="border-bottom:1px solid #F1F5F9"
                    :style="{ background: rowIndex % 2 === 0 ? '#fff' : '#F8FAFC' }"
                  >
                    <td
                      v-for="(header, colIndex) in sheetHeaders.slice(0, 10)"
                      :key="colIndex"
                      class="px-4 py-3 text-left"
                      style="color:#64748B;white-space:nowrap;overflow:hidden;text-overflow:ellipsis;max-width:200px"
                    >
                      {{ row[header] ?? '-' }}
                    </td>
                  </tr>
                </tbody>
              </table>
            </div>

            <div
              v-if="sheetData.length > 8 || sheetHeaders.length > 10"
              class="px-6 py-3"
              style="background:#F8FAFC;border-top:1px solid #E2E8F0"
            >
              <p
                class="text-xs font-medium"
                style="color:#94A3B8"
              >
                <span v-if="sheetData.length > 8">
                  + {{ sheetData.length - 8 }} linhas adicionais
                </span>
                <span v-if="sheetHeaders.length > 10" class="ml-2">
                  + {{ sheetHeaders.length - 10 }} colunas adicionais
                </span>
              </p>
            </div>
          </div>
        
        </div>

        <div class="flex flex-col gap-6">
          <div
            class="rounded-3xl p-6"
            style="background:#fff;border:1px solid #E2E8F0;box-shadow:0 2px 20px rgba(14,27,48,0.06)"
          >
            <p
              class="text-xs font-bold tracking-widest uppercase mb-5"
              style="color:#94A3B8"
            >
              Resumo
            </p>

            <div
              v-if="loading"
              class="flex flex-col gap-5 animate-pulse"
            >
              <div v-for="i in 2" :key="i">
                <div
                  class="h-9 w-24 rounded-lg mb-1"
                  style="background:#F1F5F9"
                ></div>

                <div
                  class="h-3 w-32 rounded"
                  style="background:#F1F5F9"
                ></div>
              </div>
            </div>

            <template v-else>
              <div
                class="mb-6 pb-6"
                style="border-bottom:1px solid #F1F5F9"
              >
                <p
                  class="text-5xl font-extrabold leading-none mb-1"
                  :style="numberStyle"
                >
                  {{ processed ? formattedRows : '0' }}
                </p>

                <p
                  class="text-xs font-medium"
                  style="color:#94A3B8"
                >
                  linhas carregadas
                </p>
              </div>

              <div
                class="mb-6 pb-6"
                style="border-bottom:1px solid #F1F5F9"
              >
                <p
                  class="text-5xl font-extrabold leading-none mb-1"
                  :style="numberStyle"
                >
                  {{ processed ? cols : '0' }}
                </p>

                <p
                  class="text-xs font-medium"
                  style="color:#94A3B8"
                >
                  colunas detectadas
                </p>
              </div>

              <div
                class="rounded-2xl p-4 text-center"
                :style="{
                  background: processed
                    ? 'linear-gradient(135deg, rgba(15,110,86,0.06), rgba(62,160,131,0.06))'
                    : '#F8FAFC',
                  border: `1px solid ${
                    processed
                      ? 'rgba(62,160,131,0.2)'
                      : '#F1F5F9'
                  }`
                }"
              >
                <template v-if="processed">
                  <p
                    class="text-sm font-bold"
                    :style="{ color: GREEN }"
                  >
                    ✓ Processado com sucesso
                  </p>

                  <p
                    class="text-xs mt-0.5"
                    style="color:#64748B"
                  >
                    Pronto para análise
                  </p>
                </template>

                <p
                  v-else
                  class="text-xs"
                  style="color:#94A3B8"
                >
                  Aguardando arquivo
                </p>
              </div>
            </template>
          </div>

          <div
            class="rounded-3xl p-5"
            style="background:#fff;border:1px solid #E2E8F0"
          >
            <p
              class="text-xs font-bold tracking-widest uppercase mb-4"
              style="color:#94A3B8"
            >
              Ações
            </p>

            <button
              v-for="action in actions"
              :key="action.label"
              :disabled="!action.active"
              @click="action.onClick"
              class="w-full flex items-center gap-3 px-3 py-2.5 rounded-xl text-sm font-medium mb-1.5 text-left transition-all"
              :style="{
                color: action.active ? NAVY : '#CBD5E1',
                background: 'transparent',
                cursor: action.active ? 'pointer' : 'not-allowed'
              }"
            >
              <svg
                viewBox="0 0 24 24"
                fill="none"
                stroke="currentColor"
                stroke-width="2"
                class="w-4 h-4 shrink-0"
              >
                <path
                  :d="action.icon"
                  stroke-linecap="round"
                  stroke-linejoin="round"
                />
              </svg>

              {{ action.label }}
            </button>
          </div>
        </div>
      </div>
    </div>
  </InnerShell>
</template>

<script setup>
import { computed, ref } from 'vue'
import { useRouter } from 'vue-router'
import * as XLSX from 'xlsx'
import InnerShell from '../components/InnerShell.vue'

const router = useRouter()

const NAVY = '#0E1B30'
const GREEN = '#0F6E56'
const ACCENT = '#3EA083'
const RED = '#E33838'

const inputRef = ref(null)
const file = ref(null)
const dragging = ref(false)
const rows = ref(0)
const cols = ref(0)
const loading = ref(false)
const processed = ref(false)
const sheetData = ref([])
const sheetHeaders = ref([])

const formats = ['XLSX', 'XLS', 'CSV', 'ODS']

const steps = computed(() => [
  {
    n: '1',
    label: 'Enviar',
    done: true
  },
  {
    n: '2',
    label: 'Ler dados',
    done: processed.value
  },
  {
    n: '3',
    label: 'Dashboard',
    done: false
  }
])

const fileSize = computed(() => {
  if (!file.value) return '0.0'
  return (file.value.size / 1024).toFixed(1)
})

const formattedRows = computed(() => {
  return rows.value.toLocaleString('pt-BR')
})

const numberStyle = computed(() => {
  if (!processed.value) {
    return {
      color: '#E2E8F0'
    }
  }

  return {
    backgroundImage: `linear-gradient(135deg, ${GREEN}, ${ACCENT})`,
    WebkitBackgroundClip: 'text',
    backgroundClip: 'text',
    color: 'transparent'
  }
})

const uploadAreaStyle = computed(() => ({
  borderColor: dragging.value
    ? ACCENT
    : file.value
      ? `${GREEN}60`
      : '#CBD5E1',

  background: dragging.value
    ? 'linear-gradient(135deg, rgba(62,160,131,0.1), rgba(15,110,86,0.06))'
    : file.value
      ? 'linear-gradient(135deg, rgba(15,110,86,0.05), rgba(62,160,131,0.03))'
      : 'linear-gradient(160deg, #ffffff, #f7fafc)',

  boxShadow: dragging.value
    ? '0 0 0 4px rgba(62,160,131,0.12), 0 20px 50px rgba(14,27,48,0.1)'
    : '0 2px 24px rgba(14,27,48,0.05)',

  transform: dragging.value ? 'scale(1.01)' : 'scale(1)'
}))

const actions = computed(() => [
  {
    label: 'Ver dashboard',
    icon: 'M9 19v-6a2 2 0 0 0-2-2H5a2 2 0 0 0-2 2v6a2 2 0 0 0 2 2h2a2 2 0 0 0 2-2zm0 0V9a2 2 0 0 1 2-2h2a2 2 0 0 1 2 2v10m-6 0a2 2 0 0 0 2 2h2a2 2 0 0 0 2-2m0 0V5a2 2 0 0 1 2-2h2a2 2 0 0 1 2 2v14a2 2 0 0 1-2 2h-2a2 2 0 0 1-2-2z',
    active: processed.value,
    onClick: () => {
      if (processed.value) {
        router.push('/sidebar/dashboard')
      }
    }
  },
  {
    label: 'Exportar relatório',
    icon: 'M4 16v1a3 3 0 0 0 3 3h10a3 3 0 0 0 3-3v-1m-4-4l-4 4m0 0l-4-4m4 4V4',
    active: processed.value,
    onClick: () => {}
  }
])

function openFileDialog() {
  inputRef.value?.click()
}

function handleInput(event) {
  const selectedFile = event.target.files?.[0]

  if (selectedFile) {
    handleFile(selectedFile)
  }
}

function handleDrop(event) {
  dragging.value = false

  const droppedFile = event.dataTransfer.files?.[0]

  if (droppedFile) {
    handleFile(droppedFile)
  }
}

function handleFile(selectedFile) {
  file.value = selectedFile
  processed.value = false
  loading.value = true
  sheetData.value = []
  sheetHeaders.value = []

  // Simular processamento com delay
  setTimeout(() => {
    try {
      const reader = new FileReader()
      
      reader.onload = (event) => {
        const data = new Uint8Array(event.target.result)
        const workbook = XLSX.read(data, { type: 'array' })
        const sheetName = workbook.SheetNames[0]
        const worksheet = workbook.Sheets[sheetName]
        
        // Ler dados com headers
        const jsonData = XLSX.utils.sheet_to_json(worksheet)
        
        // Armazenar headers
        if (jsonData.length > 0) {
          sheetHeaders.value = Object.keys(jsonData[0])
        }
        
        // Armazenar apenas as primeiras 10 linhas para prévia
        sheetData.value = jsonData.slice(0, 10)
        
        rows.value = jsonData.length
        cols.value = sheetHeaders.value.length
        loading.value = false
        processed.value = true
      }
      
      reader.readAsArrayBuffer(selectedFile)
    } catch (error) {
      console.error('Erro ao processar arquivo:', error)
      loading.value = false
      processed.value = false
    }
  }, 1400)
}

function removeFile() {
  file.value = null
  rows.value = 0
  cols.value = 0
  processed.value = false
  sheetData.value = []
  sheetHeaders.value = []

  if (inputRef.value) {
    inputRef.value.value = ''
  }
}
</script>