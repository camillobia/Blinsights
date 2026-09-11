<template>
  <section class="mx-auto max-w-7xl px-4 py-8 sm:px-6 lg:px-8">
    <div class="mb-6">
      <p class="text-sm font-semibold uppercase text-red-600">Upload</p>
      <h1 class="text-3xl font-bold text-slate-900">Enviar planilha</h1>
      <p class="mt-2 text-slate-600">
        Selecione XLSX, XLS ou CSV. O arquivo será lido no navegador e os dados
        serão armazenados no Pinia.
      </p>
    </div>

    <div class="grid gap-6 lg:grid-cols-3">
      <div class="rounded-2xl border border-slate-200 bg-white p-6 shadow-sm lg:col-span-2">
        <label class="mb-2 block text-sm font-semibold">Escolha o arquivo</label>

        <input
          type="file"
          accept=".xlsx,.xls,.csv"
          @change="escolherArquivo"
          class="block w-full rounded-lg border border-slate-300 bg-white p-3 text-sm"
        />

        <p v-if="store.arquivo" class="mt-4 text-sm text-slate-700">
          Arquivo: <strong>{{ store.arquivo.name }}</strong>
        </p>

        <p v-if="store.erro" class="mt-4 rounded-lg bg-red-50 p-3 text-sm text-red-700">
          {{ store.erro }}
        </p>
      </div>

      <div class="rounded-2xl border border-slate-200 bg-white p-6 shadow-sm">
        <p class="text-sm text-slate-500">Resumo</p>
        <p class="mt-2 text-3xl font-bold text-red-600">{{ store.totalLinhas }}</p>
        <p class="text-sm text-slate-600">linhas carregadas</p>

        <p class="mt-4 text-3xl font-bold text-slate-900">{{ store.totalColunas }}</p>
        <p class="text-sm text-slate-600">colunas</p>

        <button
          v-if="store.dadosTratados.length"
          @click="store.limpar()"
          class="mt-5 rounded-lg border border-red-600 px-4 py-2 text-sm font-semibold text-red-600 hover:bg-red-50"
        >
          Limpar
        </button>
      </div>
    </div>

    <div
      v-if="store.dadosTratados.length"
      class="mt-6 overflow-hidden rounded-2xl border border-slate-200 bg-white shadow-sm"
    >
      <div class="border-b border-slate-200 p-4">
        <h2 class="font-bold">Prévia dos dados tratados</h2>
        <p class="text-sm text-slate-500">Mostrando até 10 linhas.</p>
      </div>

      <div class="overflow-x-auto">
        <table class="min-w-full text-sm">
          <thead class="bg-slate-100">
            <tr>
              <th
                v-for="coluna in store.colunas"
                :key="coluna"
                class="whitespace-nowrap px-4 py-3 text-left font-semibold text-slate-700"
              >
                {{ coluna }}
              </th>
            </tr>
          </thead>

          <tbody>
            <tr
              v-for="(linha, indice) in store.dadosTratados.slice(0, 10)"
              :key="indice"
              class="border-t border-slate-100"
            >
              <td
                v-for="coluna in store.colunas"
                :key="coluna"
                class="whitespace-nowrap px-4 py-3 text-slate-600"
              >
                {{ linha[coluna] }}
              </td>
            </tr>
          </tbody>
        </table>
      </div>
    </div>

    <div class="mt-6 rounded-2xl border border-red-100 bg-red-50 p-5">
      <p class="font-semibold text-red-800">Próxima etapa do projeto</p>
      <p class="mt-1 text-sm text-red-700">
        Depois, uma action do Pinia poderá usar Axios para enviar os dados ao Spring Boot.
        O tratamento completo de Ciência de Dados ficará em Python/Pandas e a persistência
        ficará no PostgreSQL/Azure.
      </p>
    </div>
  </section>
</template>

<script setup>
import { useUploadstore } from '../store/Uploadstore'

const store = useUploadstore()

function escolherArquivo(event) {
  const file = event.target.files?.[0]
  if (file) {
    store.lerArquivo(file)
  }
}
</script>