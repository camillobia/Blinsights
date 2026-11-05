from __future__ import annotations

from typing import Any

import pandas as pd
from sqlalchemy import (
    Column,
    Integer,
    MetaData,
    Numeric,
    String,
    Table,
    Text,
    Date,
    create_engine,
)
from sqlalchemy.dialects.postgresql import JSONB
from sqlalchemy.engine import Engine

from config import get_database_url

metadata = MetaData()

clientes = Table(
    "clientes",
    metadata,
    Column("codigo_cliente", String(50), primary_key=True),
    Column("nome_cliente", String(200), nullable=False),
    Column("consultor", String(120)),
    Column("segmento", String(100)),
    Column("nivel_cliente", String(20)),
    Column("faturamento_anual", Numeric(18, 2)),
    Column("cidade", String(120)),
    Column("uf", String(10)),
    Column("mes_contratacao", String(20)),
)

contratos = Table(
    "contratos",
    metadata,
    Column("id", Integer, primary_key=True, autoincrement=True),
    Column("codigo_cliente", String(50), nullable=False),
    Column("servicos_contratados", Text),
    Column("data_contratacao", Date),
)

resultado_dashboard = Table(
    "resultado_dashboard",
    metadata,
    Column("id", Integer, primary_key=True, autoincrement=True),
    Column("dados_json", JSONB),
)


def get_engine() -> Engine:
    return create_engine(get_database_url(), future=True)


def criar_tabelas(engine: Engine | None = None) -> None:
    engine = engine or get_engine()
    metadata.create_all(engine)


def salvar_dados(
    df_clientes: pd.DataFrame,
    dashboard_json: dict[str, Any],
    engine: Engine | None = None,
) -> None:
    engine = engine or get_engine()
    criar_tabelas(engine)

    with engine.begin() as conn:
        df = df_clientes[
            [
                "codigo_cliente",
                "nome_cliente",
                "consultor",
                "segmento",
                "nivel_cliente",
                "faturamento_anual",
                "cidade",
                "uf",
                "mes_contratacao",
            ]
        ].copy()

        df.to_sql("clientes", conn, if_exists="append", index=False)

        contratos_lista = []

        for _, linha in df_clientes.iterrows():
            contratos_lista.append({
                "codigo_cliente": linha["codigo_cliente"],
                "servicos_contratados": "; ".join(
                    linha["servicos_contratados"]
                ),
                "data_contratacao": (
                    linha["data_contratacao"].date()
                    if pd.notna(linha["data_contratacao"])
                    else None
                ),
            })

        pd.DataFrame(contratos_lista).to_sql(
            "contratos",
            conn,
            if_exists="append",
            index=False,
        )

        conn.execute(
            resultado_dashboard.insert().values(
                dados_json=dashboard_json
            )
        )