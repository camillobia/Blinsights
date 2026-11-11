
import { defineStore } from 'pinia'
import * as XLSX from 'xlsx'
import api from '../services/api'

// Converte datas do Excel ou textos para o formato esperado pelo Java.
function converterData(valor) {
  if (valor instanceof Date) {
    const ano = valor.getFullYear()
    const mes = String(valor.getMonth() + 1).padStart(2, '0')
    const dia = String(valor.getDate()).padStart(2, '0')

    return `${ano}-${mes}-${dia}`
  }

  // Datas armazenadas como números seriais do Excel.
  if (typeof valor === 'number') {
    const data = XLSX.SSF.parse_date_code(valor)

    if (!data) {
      throw new Error('Data de contratação inválida.')
    }

    const mes = String(data.m).padStart(2, '0')
    const dia = String(data.d).padStart(2, '0')

    return `${data.y}-${mes}-${dia}`
  }

  if (typeof valor === 'string') {
    const data = valor.trim()

    // Formato DD/MM/AAAA.
    const brasileira = data.match(/^(\d{2})\/(\d{2})\/(\d{4})$/)

    if (brasileira) {
      const [, dia, mes, ano] = brasileira
      return `${ano}-${mes}-${dia}`
    }

    // Formato AAAA-MM-DD.
    const iso = data.match(/^(\d{4})-(\d{2})-(\d{2})$/)

    if (iso) {
      return data
    }
  }

  throw new Error('Data de contratação inválida ou em formato não reconhecido.')
}

export const useUploadstore = defineStore('upload', {
  state: () => ({
    arquivo: null,
    dadosOriginais: [],
    dadosTratados: [],
    erro: '',
    erros: [],
    enviando: false,
    erroBackend: '',
    clientesValidados: []
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
      this.erroBackend = ''
      this.clientesValidados = []
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
            novaLinha.nivel_cliente.trim().toUpperCase()
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
        'data_contratacao'
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

    async enviarParaBackend() {
      this.erroBackend = ''
      this.clientesValidados = []

      if (this.enviando) return

      // Não envia linhas que tenham erros de validação.
      const linhasComErro = new Set(
        this.erros
          .filter((erro) => erro.tipo !== 'Padronização')
          .map((erro) => erro.linha)
      )

      const linhasValidas = this.dadosTratados.filter(
        (_, index) => !linhasComErro.has(index + 2)
      )

      if (!linhasValidas.length) {
        this.erroBackend = 'Não existem registros válidos para enviar.'
        return
      }

      try {
        this.enviando = true

        const clientes = linhasValidas.map((linha) => ({
          codigoCliente: String(linha.codigo_cliente),
          nomeCliente: String(linha.nome_cliente),
          consultor: String(linha.consultor),
          segmento: String(linha.segmento),
          nivelCliente: String(linha.nivel_cliente),
          faturamentoAnual: Number(linha.faturamento_anual),
          servicosContratados: String(linha.servicos_contratados),
          dataContratacao: converterData(linha.data_contratacao),
          cidade: linha.cidade ? String(linha.cidade) : '',
          uf: linha.uf ? String(linha.uf) : ''
        }))

        const resposta = await api.post(
          '/api/clientes/validar',
          clientes
        )

        this.clientesValidados = resposta.data

        return resposta.data
      } catch (error) {
        console.error('Erro ao enviar clientes para o backend:', error)

        const dadosErro = error.response?.data

        if (typeof dadosErro === 'string') {
          this.erroBackend = dadosErro
        } else if (dadosErro?.message) {
          this.erroBackend = dadosErro.message
        } else if (dadosErro?.mensagem) {
          this.erroBackend = dadosErro.mensagem
        } else {
          this.erroBackend =
            'Não foi possível validar os clientes no servidor. Verifique se o backend está em execução.'
        }
      } finally {
        this.enviando = false
      }
    },

    limpar() {
      this.arquivo = null
      this.dadosOriginais = []
      this.dadosTratados = []
      this.erro = ''
      this.erros = []
      this.enviando = false
      this.erroBackend = ''
      this.clientesValidados = []
    }
  }
})
  