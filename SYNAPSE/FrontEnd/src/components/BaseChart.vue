<template>
  <div class="h-72">
    <canvas ref="canvas"></canvas>
  </div>
</template>

<script setup>
import { onBeforeUnmount, onMounted, ref, watch } from 'vue'
import {
  Chart,
  CategoryScale,
  LinearScale,
  BarElement,
  PointElement,
  LineElement,
  ArcElement,
  Tooltip,
  Legend
} from 'chart.js'

Chart.register(
  CategoryScale,
  LinearScale,
  BarElement,
  PointElement,
  LineElement,
  ArcElement,
  Tooltip,
  Legend
)

const props = defineProps({
  tipo: {
    type: String,
    default: 'bar'
  },
  rotulos: {
    type: Array,
    default: () => []
  },
  valores: {
    type: Array,
    default: () => []
  },
  titulo: {
    type: String,
    required: true
  }
})

const canvas = ref(null)
let grafico

function desenhar() {
  grafico?.destroy()

  if (!canvas.value) return

  grafico = new Chart(canvas.value, {
    type: props.tipo,
    data: {
      labels: props.rotulos,
      datasets: [{
        label: props.titulo,
        data: props.valores,
        backgroundColor: [
          '#0F6E56',
          '#3EA083',
          '#2563EB',
          '#F97316',
          '#8B5CF6',
          '#DB2777',
          '#0891B2'
        ],
        borderColor: '#0F6E56',
        borderWidth: props.tipo === 'line' ? 2 : 1,
        fill: false,
        tension: 0.3
      }]
    },
    options: {
      responsive: true,
      maintainAspectRatio: false,
      indexAxis: props.tipo === 'bar' && props.rotulos.length > 6 ? 'y' : 'x',
      plugins: {
        legend: {
          display: props.tipo === 'doughnut',
          position: 'bottom'
        }
      },
      scales: props.tipo === 'doughnut'
        ? {}
        : { y: { beginAtZero: true } }
    }
  })
}

onMounted(desenhar)
watch(
  () => [props.tipo, props.rotulos, props.valores, props.titulo],
  desenhar,
  { deep: true }
)
onBeforeUnmount(() => grafico?.destroy())
</script>
