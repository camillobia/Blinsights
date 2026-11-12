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
    texto = limpar_texto(valor)
    if not texto:
        return ""
    return " / ".join(
        SEGMENTOS.get(parte.strip().upper(), parte.strip().title())
        for parte in texto.split("/")
        if parte.strip()
    )


def padronizar_servicos(valor: Any) -> list[str]:
    texto = limpar_texto(valor)
    if not texto:
        return []
    return [item.strip() for item in texto.split(";") if item.strip()]


def parse_numero(valor: Any) -> float:
    if pd.isna(valor):
        return float("nan")
    if isinstance(valor, (int, float)):
        return float(valor)

    texto = limpar_texto(valor).replace("R$", "").replace("%", "").replace(" ", "")
    if "," in texto:
        texto = texto.replace(".", "").replace(",", ".")
    try:
        return float(texto)
    except (TypeError, ValueError):
        return float("nan")


def parse_data(valor: Any) -> Any:
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

    def coluna(nome: str, padrao: Any = "") -> pd.Series:
        return df[nome] if nome in df else pd.Series(padrao, index=df.index)

    df["codigo_cliente"] = coluna("codigo_cliente").map(limpar_texto).str.upper()
    df["nome_cliente"] = coluna("nome_cliente").map(titulo)
    df["consultor"] = coluna("consultor").map(titulo)
    df["segmento"] = coluna("segmento").map(padronizar_segmento)
    df["nivel_cliente"] = coluna("nivel_cliente").map(
        lambda x: limpar_texto(x).upper()
    )
    df["faturamento_anual"] = coluna("faturamento_anual", 0).map(parse_numero)
    df["servicos_contratados"] = coluna("servicos_contratados").map(
        padronizar_servicos
    )
    df["data_contratacao"] = coluna("data_contratacao").map(parse_data)

    cidade_uf = df.get("cidade_uf", pd.Series("", index=df.index))
    cidade_legada = cidade_uf.map(
        lambda x: limpar_texto(x).split("/")[0].strip()
        if "/" in limpar_texto(x)
        else limpar_texto(x)
    )
    uf_legada = cidade_uf.map(
        lambda x: limpar_texto(x).split("/")[-1].strip().upper()
        if "/" in limpar_texto(x)
        else ""
    )
    df["cidade"] = df.get("cidade", cidade_legada).map(limpar_texto)
    df["uf"] = df.get("uf", uf_legada).map(limpar_texto).str.upper()

    df["mes_contratacao"] = df["data_contratacao"].dt.to_period("M").astype(str)
    df["servicos_quantidade"] = df["servicos_contratados"].map(len)

    avisos = []
    for _, linha in df.iterrows():
        if pd.isna(linha["data_contratacao"]):
            avisos.append(
                f"Data inválida para {linha.get('nome_cliente', 'cliente')}"
            )

    return df, avisos
