import { defineStore } from 'pinia'
import * as XLSX from 'xlsx'

export const useUploadstore = defineStore('upload', {
  state: () => ({
    arquivo: null,
    dadosOriginais: [],
    dadosTratados: [],
    erro: '',
    erros: []
  }),

  getters: {
    totalLinhas: (state) => state.dadosTratados.length,

    totalColunas: (state) => {
      if (!state.dadosTratados.length) return 0
      return Object.keys(state.dadosTratados[0]).length
    },

    colunas: (state) => {
      if (!state.dadosTratados.length) return []
      return Object.keys(state.dadosTratados[0])
    },

    nomeArquivo: (state) => {
      return state.arquivo?.name || ''
    },

    totalErros: (state) => state.erros.length,

    linhasComErro: (state) => {
      return [
        ...new Set(
          state.erros
            .filter((erro) => erro.tipo !== 'Padronização')
            .map((erro) => erro.linha)
        )
      ]
    },

    registrosComErro: (state) => {
      return new Set(
        state.erros
          .filter((erro) => erro.tipo !== 'Padronização')
          .map((erro) => erro.linha)
      ).size
    },

    registrosValidos: (state) => {
      const total = state.dadosTratados.length

      const registrosComErro = new Set(
        state.erros
          .filter((erro) => erro.tipo !== 'Padronização')
          .map((erro) => erro.linha)
      ).size

      return total - registrosComErro
    },

    errosPorTipo: (state) => {
      return state.erros.reduce((acumulado, erro) => {
        acumulado[erro.tipo] = (acumulado[erro.tipo] || 0) + 1
        return acumulado
      }, {})
    }
  },

  actions: {
    async lerArquivo(file) {
      this.erro = ''
      this.arquivo = file
      this.dadosOriginais = []
      this.dadosTratados = []
      this.erros = []

      if (!file) return

      const extensao = file.name.split('.').pop()?.toLowerCase()

      if (!['xlsx', 'xls', 'csv'].includes(extensao)) {
        this.erro = 'Formato inválido. Use XLSX, XLS ou CSV.'
        return
      }

      try {
        const buffer = await file.arrayBuffer()

        const workbook = XLSX.read(buffer, {
          type: 'array'
        })

        const nomePrimeiraAba = workbook.SheetNames[0]
        const worksheet = workbook.Sheets[nomePrimeiraAba]

        this.dadosOriginais = XLSX.utils.sheet_to_json(
          worksheet,
          {
            defval: ''
          }
        )

        this.tratarDados()
        this.validarDados()
      } catch (error) {
        console.error(error)
        this.erro = 'Não foi possível ler a planilha.'
      }
    },

    tratarDados() {
      this.dadosTratados = this.dadosOriginais.map((linha) => {
        const novaLinha = {}

        for (const [chave, valor] of Object.entries(linha)) {
          if (typeof valor === 'string') {
            novaLinha[chave] = valor.trim()
          } else {
            novaLinha[chave] = valor
          }
        }

        if (typeof novaLinha.segmento === 'string') {
          const segmento = novaLinha.segmento
            .trim()
            .toUpperCase()

          const mapaSegmentos = {
            'IND.': 'Indústria',
            'INDUSTRIA': 'Indústria',
            'INDÚSTRIA': 'Indústria',
            'COMERCIO': 'Comércio',
            'COMÉRCIO': 'Comércio',
            'SERVICOS': 'Serviços',
            'SERVIÇOS': 'Serviços',
            'TECNOLOGIA': 'Tecnologia',
            'EDUCACAO': 'Educação',
            'EDUCAÇÃO': 'Educação',
            'SAUDE': 'Saúde',
            'SAÚDE': 'Saúde'
          }

          novaLinha.segmento =
            mapaSegmentos[segmento] || novaLinha.segmento
        }

        if (typeof novaLinha.nivel_cliente === 'string') {
          novaLinha.nivel_cliente =
            novaLinha.nivel_cliente
              .trim()
              .toUpperCase()
        }

        return novaLinha
      })
    },

    validarDados() {
      this.erros = []

      const camposObrigatorios = [
        'codigo_cliente',
        'nome_cliente',
        'consultor',
        'segmento',
        'nivel_cliente',
        'faturamento_anual',
        'servicos_contratados',
        'data_contratacao',
        'cidade'
      ]

      this.dadosOriginais.forEach((linha, index) => {
        const numeroLinha = index + 2

        Object.entries(linha).forEach(([campo, valor]) => {
          if (
            typeof valor === 'string' &&
            valor !== valor.trim()
          ) {
            this.erros.push({
              linha: numeroLinha,
              campo,
              tipo: 'Espaço desnecessário',
              descricao: `O campo ${campo} possui espaços desnecessários.`
            })
          }
        })
      })

      this.dadosTratados.forEach((linha, index) => {
        const numeroLinha = index + 2

        camposObrigatorios.forEach((campo) => {
          const valor = linha[campo]

          if (
            valor === null ||
            valor === undefined ||
            valor === ''
          ) {
            this.erros.push({
              linha: numeroLinha,
              campo,
              tipo: 'Campo obrigatório',
              descricao: `O campo ${campo} está vazio.`
            })
          }
        })
      })

      const codigosEncontrados = new Map()

      this.dadosTratados.forEach((linha, index) => {
        const codigo = linha.codigo_cliente
        const numeroLinha = index + 2

        if (!codigo) return

        if (codigosEncontrados.has(codigo)) {
          this.erros.push({
            linha: numeroLinha,
            campo: 'codigo_cliente',
            tipo: 'Registro duplicado',
            descricao: `O código do cliente ${codigo} está duplicado.`
          })
        } else {
          codigosEncontrados.set(codigo, numeroLinha)
        }
      })

      const niveisPermitidos = ['A', 'B', 'C']

      this.dadosTratados.forEach((linha, index) => {
        const numeroLinha = index + 2
        const nivel = linha.nivel_cliente

        if (
          nivel &&
          !niveisPermitidos.includes(nivel)
        ) {
          this.erros.push({
            linha: numeroLinha,
            campo: 'nivel_cliente',
            tipo: 'Padrão inválido',
            descricao: `O nível ${nivel} não é permitido. Use A, B ou C.`
          })
        }
      })

      const segmentosPadronizados = {
        'IND.': 'Indústria',
        'INDUSTRIA': 'Indústria',
        'INDÚSTRIA': 'Indústria',
        'COMERCIO': 'Comércio',
        'COMÉRCIO': 'Comércio',
        'SERVICOS': 'Serviços',
        'SERVIÇOS': 'Serviços',
        'TECNOLOGIA': 'Tecnologia',
        'EDUCACAO': 'Educação',
        'EDUCAÇÃO': 'Educação',
        'SAUDE': 'Saúde',
        'SAÚDE': 'Saúde'
      }

      this.dadosOriginais.forEach((linha, index) => {
        const numeroLinha = index + 2
        const segmentoOriginal = linha.segmento

        if (
          segmentoOriginal === null ||
          segmentoOriginal === undefined ||
          segmentoOriginal === ''
        ) {
          return
        }

        const chave = String(segmentoOriginal)
          .trim()
          .toUpperCase()

        const segmentoPadronizado =
          segmentosPadronizados[chave]

        if (
          segmentoPadronizado &&
          String(segmentoOriginal).trim() !== segmentoPadronizado
        ) {
          this.erros.push({
            linha: numeroLinha,
            campo: 'segmento',
            tipo: 'Padronização',
            descricao: `O valor ${segmentoOriginal} foi padronizado para ${segmentoPadronizado}.`
          })
        }
      })

      this.dadosTratados.forEach((linha, index) => {
        const numeroLinha = index + 2
        const email = linha.email

        if (!email) return

        const emailValido =
          /^[^\s@]+@[^\s@]+\.[^\s@]+$/.test(email)

        if (!emailValido) {
          this.erros.push({
            linha: numeroLinha,
            campo: 'email',
            tipo: 'E-mail inválido',
            descricao: `O e-mail ${email} não está em um formato válido.`
          })
        }
      })
    },

    limpar() {
      this.arquivo = null
      this.dadosOriginais = []
      this.dadosTratados = []
      this.erro = ''
      this.erros = []
    }
  }
})