import re
from typing import Any, List, Tuple

import pandas as pd


SEGMENTOS = {
    "IND.": "Indústria",
    "INDUSTRIA": "Indústria",
    "COMERCIO": "Comércio",
    "COMÉRCIO": "Comércio",
    "SERVICOS": "Serviços",
    "SERVIÇOS": "Serviços",
    "EDUCACAO": "Educação",
    "EDUCAÇÃO": "Educação",
    "SAUDE": "Saúde",
    "SAÚDE": "Saúde",
    "FINANCEIRO": "Financeiro",
    "PUBLICO": "Público",
    "PÚBLICO": "Público",
}


def limpar_texto(valor: Any) -> str:
    if pd.isna(valor):
        return ""
    return str(valor).strip()


def titulo(valor: Any) -> str:
    texto = limpar_texto(valor)
    if not texto:
        return ""
    return " ".join(parte.capitalize() for parte in texto.split())


def padronizar_segmento(valor: Any) -> str:
    texto = limpar_texto(valor).upper()
    if not texto:
        return ""
    return SEGMENTOS.get(texto, texto.title())


def padronizar_servicos(valor: Any) -> list[str]:
    texto = limpar_texto(valor)
    if not texto:
        return []
    return [item.strip() for item in texto.split(";") if item.strip()]


def parse_numero(valor: Any) -> float:
    if pd.isna(valor):
        return 0.0
    texto = limpar_texto(valor).replace(".", "").replace(" ", "")
    texto = texto.replace("R$", "").replace("%", "")
    texto = texto.replace(".", "").replace(",", ".")
    try:
        return float(texto)
    except (TypeError, ValueError):
        return 0.0


def parse_data(valor: Any) -> pd.Timestamp | pd.NaT:
    texto = limpar_texto(valor)
    if not texto:
        return pd.NaT
    try:
        return pd.to_datetime(texto, errors="raise")
    except (TypeError, ValueError):
        return pd.NaT


def tratar(bruto: List[dict]) -> Tuple[pd.DataFrame, List[str]]:
    if not bruto:
        return pd.DataFrame(), []

    df = pd.DataFrame(bruto)
    colunas = {
        "codigoCliente": "codigo_cliente",
        "nomeCliente": "nome_cliente",
        "consultor": "consultor",
        "segmento": "segmento",
        "nivelCliente": "nivel_cliente",
        "faturamentoAnual": "faturamento_anual",
        "servicosContratados": "servicos_contratados",
        "dataContratacao": "data_contratacao",
        "cidadeUf": "cidade_uf",
    }
    df = df.rename(columns=colunas)

    df["codigo_cliente"] = df.get("codigo_cliente", "").map(limpar_texto).str.upper()
    df["nome_cliente"] = df.get("nome_cliente", "").map(titulo)
    df["consultor"] = df.get("consultor", "").map(titulo)
    df["segmento"] = df.get("segmento", "").map(padronizar_segmento)
    df["nivel_cliente"] = df.get("nivel_cliente", "").map(lambda x: limpar_texto(x).upper())
    df["faturamento_anual"] = df.get("faturamento_anual", 0).map(parse_numero)
    df["servicos_contratados"] = df.get("servicos_contratados", "").map(padronizar_servicos)
    df["data_contratacao"] = df.get("data_contratacao", "").map(parse_data)

    cidade_uf = df.get("cidade_uf", "")
    cidade = cidade_uf.map(lambda x: limpar_texto(x).split("/")[0].strip() if "/" in limpar_texto(x) else limpar_texto(x))
    uf = cidade_uf.map(lambda x: limpar_texto(x).split("/")[-1].strip().upper() if "/" in limpar_texto(x) else "")
    df["cidade"] = cidade
    df["uf"] = uf

    df["mes_contratacao"] = df["data_contratacao"].dt.to_period("M").astype(str)
    df["servicos_quantidade"] = df["servicos_contratados"].map(len)

    avisos = []
    for _, linha in df.iterrows():
        if linha["codigo_cliente"] == "":
            avisos.append("Código do cliente ausente")
        if pd.isna(linha["data_contratacao"]):
            avisos.append(f"Data inválida para {linha.get('nome_cliente', 'cliente')}")

    return df, avisos
