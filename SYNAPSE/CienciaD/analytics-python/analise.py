import math
from typing import Any, Dict

import numpy as np
import pandas as pd
from scipy import stats


def _resumo_serie(serie: pd.Series) -> Dict[str, float | int]:
    valores = pd.to_numeric(serie, errors="coerce").dropna()
    if valores.empty:
        return {
            "media": 0.0,
            "mediana": 0.0,
            "desvio_padrao": 0.0,
            "minimo": 0.0,
            "maximo": 0.0,
            "q1": 0.0,
            "q3": 0.0,
        }

    return {
        "media": float(valores.mean()),
        "mediana": float(valores.median()),
        "desvio_padrao": float(valores.std(ddof=1)) if len(valores) > 1 else 0.0,
        "minimo": float(valores.min()),
        "maximo": float(valores.max()),
        "q1": float(valores.quantile(0.25)),
        "q3": float(valores.quantile(0.75)),
    }


def _agrupar(df: pd.DataFrame, coluna: str) -> Dict[str, int]:
    if coluna not in df.columns:
        return {}
    return df[coluna].fillna("Não informado").value_counts().to_dict()


def calcular(res: pd.DataFrame, linhas_recebidas: int = 0) -> Dict[str, Any]:
    if res.empty:
        return {
            "linhas_recebidas": linhas_recebidas,
            "linhas_validas": 0,
            "media_faturamento": 0.0,
            "mediana_faturamento": 0.0,
            "desvio_padrao_faturamento": 0.0,
            "minimo_faturamento": 0.0,
            "maximo_faturamento": 0.0,
            "segmentos": {},
            "niveis": {},
            "consultores": {},
            "servicos_mais_contratados": {},
            "contratacoes_por_mes": {},
            "correlacao_servicos_faturamento": 0.0,
            "segmento_mais_frequente": {"nome": "", "quantidade": 0},
            "nivel_mais_frequente": {"nome": "", "quantidade": 0},
            "servico_mais_frequente": {"nome": "", "quantidade": 0},
        }

    faturamento = res["faturamento_anual"].dropna()
    servicos_qtd = res["servicos_quantidade"].dropna()
    resumo = _resumo_serie(faturamento)

    servicos_agrupados = {}
    for itens in res["servicos_contratados"]:
        for servico in itens:
            servicos_agrupados[servico] = servicos_agrupados.get(servico, 0) + 1

    segmento_mais_frequente = None
    if not res["segmento"].empty:
        segmento_mais_frequente = res["segmento"].value_counts().idxmax(), res["segmento"].value_counts().max()

    nivel_mais_frequente = None
    if not res["nivel_cliente"].empty:
        nivel_mais_frequente = res["nivel_cliente"].value_counts().idxmax(), res["nivel_cliente"].value_counts().max()

    servico_mais_frequente = None
    if servicos_agrupados:
        servico_mais_frequente = max(servicos_agrupados.items(), key=lambda item: item[1])

    corr = 0.0
    if len(faturamento) > 1 and len(servicos_qtd) > 1 and not (faturamento.empty or servicos_qtd.empty):
        try:
            corr, _ = stats.pearsonr(faturamento.to_numpy(), servicos_qtd.to_numpy())
        except Exception:
            corr = 0.0

    resultado = {
        "linhas_recebidas": linhas_recebidas,
        "linhas_validas": int(len(res)),
        "media_faturamento": float(resumo["media"]),
        "mediana_faturamento": float(resumo["mediana"]),
        "desvio_padrao_faturamento": float(resumo["desvio_padrao"]),
        "minimo_faturamento": float(resumo["minimo"]),
        "maximo_faturamento": float(resumo["maximo"]),
        "segmentos": _agrupar(res, "segmento"),
        "niveis": _agrupar(res, "nivel_cliente"),
        "consultores": _agrupar(res, "consultor"),
        "servicos_mais_contratados": dict(sorted(servicos_agrupados.items(), key=lambda item: item[1], reverse=True)),
        "contratacoes_por_mes": dict(sorted(res["mes_contratacao"].fillna("Não informado").value_counts().to_dict().items())),
        "correlacao_servicos_faturamento": float(corr) if isinstance(corr, (float, int)) else 0.0,
        "segmento_mais_frequente": {
            "nome": segmento_mais_frequente[0] if segmento_mais_frequente else "",
            "quantidade": int(segmento_mais_frequente[1]) if segmento_mais_frequente else 0,
        },
        "nivel_mais_frequente": {
            "nome": nivel_mais_frequente[0] if nivel_mais_frequente else "",
            "quantidade": int(nivel_mais_frequente[1]) if nivel_mais_frequente else 0,
        },
        "servico_mais_frequente": {
            "nome": servico_mais_frequente[0] if servico_mais_frequente else "",
            "quantidade": int(servico_mais_frequente[1]) if servico_mais_frequente else 0,
        },
    }
    return resultado
