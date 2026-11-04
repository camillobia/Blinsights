from __future__ import annotations

from typing import Any

import pandas as pd
from sqlalchemy import Column, Float, Integer, MetaData, String, Table, Text, create_engine
from sqlalchemy.engine import Engine

from config import get_database_url


metadata = MetaData()

clientes = Table(
    "clientes",
    metadata,
    Column("codigo_cliente", String, primary_key=True),
    Column("nome_cliente", String, nullable=False),
    Column("consultor", String),
    Column("segmento", String),
    Column("nivel_cliente", String),
    Column("faturamento_anual", Float),
    Column("cidade", String),
    Column("uf", String),
    Column("mes_contratacao", String),
)

contratos = Table(
    "contratos",
    metadata,
    Column("id", Integer, primary_key=True, autoincrement=True),
    Column("codigo_cliente", String, nullable=False),
    Column("servicos_contratados", Text),
    Column("data_contratacao", String),
)

resultado_dashboard = Table(
    "resultado_dashboard",
    metadata,
    Column("id", Integer, primary_key=True, autoincrement=True),
    Column("dados_json", Text),
)


def get_engine() -> Engine:
    return create_engine(get_database_url(), future=True)


def criar_tabelas(engine: Engine | None = None) -> None:
    engine = engine or get_engine()
    metadata.create_all(engine)


def salvar_dados(df_clientes: pd.DataFrame, dashboard_json: dict[str, Any], engine: Engine | None = None) -> None:
    engine = engine or get_engine()
    criar_tabelas(engine)

    with engine.begin() as conn:
        df = df_clientes[[
            "codigo_cliente",
            "nome_cliente",
            "consultor",
            "segmento",
            "nivel_cliente",
            "faturamento_anual",
            "cidade",
            "uf",
            "mes_contratacao",
        ]].copy()
        df.to_sql("clientes", conn, if_exists="append", index=False)

        contratos_lista = []
        for _, linha in df_clientes.iterrows():
            contratos_lista.append({
                "codigo_cliente": linha["codigo_cliente"],
                "servicos_contratados": "; ".join(linha["servicos_contratados"]),
                "data_contratacao": linha["data_contratacao"].strftime("%Y-%m-%d") if pd.notna(linha["data_contratacao"]) else None,
            })

        pd.DataFrame(contratos_lista).to_sql("contratos", conn, if_exists="append", index=False)

        conn.execute(
            resultado_dashboard.insert().values(dados_json=str(dashboard_json))
        )
