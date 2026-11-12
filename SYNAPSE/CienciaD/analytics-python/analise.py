from collections import Counter
from typing import Any

import pandas as pd


def _contagem(df: pd.DataFrame, coluna: str, rotulo: str) -> list[dict[str, Any]]:
    if df.empty or coluna not in df:
        return []

    contagens = df[coluna].fillna("Não informado").value_counts()
    return [{rotulo: str(chave), "clientes": int(valor)} for chave, valor in contagens.items()]


def calcular(df: pd.DataFrame, linhas_recebidas: int = 0) -> dict[str, Any]:
    faturamento = (
        pd.to_numeric(df["faturamento_anual"], errors="coerce").dropna()
        if not df.empty and "faturamento_anual" in df
        else pd.Series(dtype=float)
    )
    resumo = {
        "totalClientes": int(len(df)),
        "totalFaturamento": float(faturamento.sum()) if not faturamento.empty else 0.0,
        "media": float(faturamento.mean()) if not faturamento.empty else 0.0,
        "mediana": float(faturamento.median()) if not faturamento.empty else 0.0,
        "desvioPadrao": float(faturamento.std(ddof=1)) if len(faturamento) > 1 else 0.0,
        "minimo": float(faturamento.min()) if not faturamento.empty else 0.0,
        "maximo": float(faturamento.max()) if not faturamento.empty else 0.0,
        "linhasRecebidas": int(linhas_recebidas),
        "linhasValidas": int(len(df)),
    }

    por_segmento = _contagem(df, "segmento", "segmento")
    por_nivel = _contagem(df, "nivel_cliente", "nivel")
    por_consultor = _contagem(df, "consultor", "consultor")

    contagem_servicos: Counter[str] = Counter()
    if not df.empty and "servicos_contratados" in df:
        for servicos in df["servicos_contratados"]:
            contagem_servicos.update(servicos)
    por_servico = [
        {"servico": servico, "clientes": quantidade}
        for servico, quantidade in contagem_servicos.most_common()
    ]

    por_mes: list[dict[str, Any]] = []
    if not df.empty and "mes_contratacao" in df:
        contagens_mes = df["mes_contratacao"].dropna().value_counts().sort_index()
        por_mes = [
            {"mes": str(mes), "contratos": int(quantidade)}
            for mes, quantidade in contagens_mes.items()
        ]

    top_clientes: list[dict[str, Any]] = []
    if not df.empty:
        top = df.nlargest(10, "faturamento_anual")
        top_clientes = [
            {
                "codigoCliente": str(linha["codigo_cliente"]),
                "nomeCliente": str(linha["nome_cliente"]),
                "faturamentoAnual": float(linha["faturamento_anual"]),
            }
            for _, linha in top.iterrows()
        ]

    return {
        "resumo": resumo,
        "porSegmento": por_segmento,
        "porNivel": por_nivel,
        "porConsultor": por_consultor,
        "porServico": por_servico,
        "porMes": por_mes,
        "topClientes": top_clientes,
    }
