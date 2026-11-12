from __future__ import annotations

from datetime import date, datetime
from typing import Any

import pandas as pd
from sqlalchemy import (
    JSON,
    BigInteger,
    Column,
    Date,
    DateTime,
    ForeignKey,
    Integer,
    MetaData,
    Numeric,
    String,
    Table,
    Text,
    create_engine,
    inspect,
    update,
)
from sqlalchemy.engine import Engine

from config import get_database_url

metadata = MetaData()
ID_TYPE = BigInteger().with_variant(Integer, "sqlite")

upload_lote = Table(
    "upload_lote",
    metadata,
    Column("id", ID_TYPE, primary_key=True, autoincrement=True),
    Column("arquivo_nome", String(255), nullable=False),
    Column("enviado_em", DateTime(timezone=True), nullable=False),
    Column("status", String(20), nullable=False),
    Column("total_linhas", Integer, nullable=False),
    Column("linhas_validas", Integer, nullable=False),
    Column("mensagem", Text),
)

cliente = Table(
    "cliente",
    metadata,
    Column("id", ID_TYPE, primary_key=True, autoincrement=True),
    Column("nome", String(150), nullable=False),
    Column("email", String(254)),
    Column("segmento", String(100)),
    Column("consultor_id", BigInteger),
    Column("lote_id", BigInteger, ForeignKey("upload_lote.id"), nullable=False),
    Column("codigo_cliente", String(50), nullable=False),
    Column("nome_cliente", String(200), nullable=False),
    Column("consultor", String(120), nullable=False),
    Column("nivel_cliente", String(20), nullable=False),
    Column("faturamento_anual", Numeric(18, 2), nullable=False),
    Column("cidade", String(120)),
    Column("uf", String(10)),
    Column("data_contratacao", Date),
    Column("mes_contratacao", String(20)),
)

contrato = Table(
    "contrato",
    metadata,
    Column("id", ID_TYPE, primary_key=True, autoincrement=True),
    Column("data_inicio", Date),
    Column("data_fim", Date),
    Column("valor", Numeric(18, 2)),
    Column("cliente_id", BigInteger, ForeignKey("cliente.id"), nullable=False),
    Column("servico_id", BigInteger),
    Column("lote_id", BigInteger, ForeignKey("upload_lote.id"), nullable=False),
    Column("servicos_contratados", Text, nullable=False),
    Column("data_contratacao", Date),
)

estatistica_lote = Table(
    "estatistica_lote",
    metadata,
    Column("lote_id", BigInteger, ForeignKey("upload_lote.id"), primary_key=True),
    Column("gerado_em", DateTime(timezone=True), nullable=False),
    Column("resultado", JSON, nullable=False),
)

_MIGRACOES = {
    "cliente": {
        "lote_id": "BIGINT",
        "codigo_cliente": "VARCHAR(50)",
        "nome_cliente": "VARCHAR(200)",
        "consultor": "VARCHAR(120)",
        "nivel_cliente": "VARCHAR(20)",
        "faturamento_anual": "NUMERIC(18, 2)",
        "cidade": "VARCHAR(120)",
        "uf": "VARCHAR(10)",
        "data_contratacao": "DATE",
        "mes_contratacao": "VARCHAR(20)",
    },
    "contrato": {
        "lote_id": "BIGINT",
        "servicos_contratados": "TEXT",
        "data_contratacao": "DATE",
    },
}


def get_engine() -> Engine:
    return create_engine(get_database_url(), pool_pre_ping=True)


def garantir_schema(engine: Engine) -> None:
    metadata.create_all(engine)
    inspector = inspect(engine)

    with engine.begin() as conn:
        for tabela, colunas in _MIGRACOES.items():
            existentes = {coluna["name"] for coluna in inspector.get_columns(tabela)}
            for nome, tipo in colunas.items():
                if nome not in existentes:
                    conn.exec_driver_sql(
                        f"ALTER TABLE {tabela} ADD COLUMN {nome} {tipo}"
                    )


def reservar_lote(
    engine: Engine,
    arquivo_nome: str,
    total_linhas: int,
    linhas_validas: int,
) -> int:
    garantir_schema(engine)
    with engine.begin() as conn:
        resultado = conn.execute(
            upload_lote.insert().values(
                arquivo_nome=arquivo_nome[:255] or "upload",
                enviado_em=datetime.now().astimezone(),
                status="PROCESSANDO",
                total_linhas=total_linhas,
                linhas_validas=linhas_validas,
            )
        )
        return int(resultado.inserted_primary_key[0])


def marcar_erro(engine: Engine, lote_id: int, mensagem: str) -> None:
    garantir_schema(engine)
    with engine.begin() as conn:
        conn.execute(
            update(upload_lote)
            .where(upload_lote.c.id == lote_id)
            .values(status="ERRO", mensagem=mensagem[:2000])
        )


def _data(valor: Any) -> date | None:
    if valor is None or pd.isna(valor):
        return None
    return valor.date() if isinstance(valor, (datetime, pd.Timestamp)) else valor


def salvar_dados(
    engine: Engine,
    lote_id: int,
    df_clientes: pd.DataFrame,
    estatisticas: dict[str, Any],
) -> None:
    garantir_schema(engine)
    momento = datetime.now().astimezone()

    with engine.begin() as conn:
        ids_por_codigo: dict[str, int] = {}
        for _, linha in df_clientes.iterrows():
            codigo = str(linha["codigo_cliente"])
            valores = {
                "nome": str(linha["nome_cliente"]),
                "segmento": str(linha["segmento"]),
                "lote_id": lote_id,
                "codigo_cliente": codigo,
                "nome_cliente": str(linha["nome_cliente"]),
                "consultor": str(linha["consultor"]),
                "nivel_cliente": str(linha["nivel_cliente"]),
                "faturamento_anual": float(linha["faturamento_anual"]),
                "cidade": str(linha["cidade"]) or None,
                "uf": str(linha["uf"]) or None,
                "data_contratacao": _data(linha["data_contratacao"]),
                "mes_contratacao": str(linha["mes_contratacao"]),
            }
            resultado = conn.execute(cliente.insert().values(**valores))
            ids_por_codigo[codigo] = int(resultado.inserted_primary_key[0])

        for _, linha in df_clientes.iterrows():
            cliente_id = ids_por_codigo[str(linha["codigo_cliente"])]
            for servico in linha["servicos_contratados"]:
                conn.execute(
                    contrato.insert().values(
                        cliente_id=cliente_id,
                        lote_id=lote_id,
                        servicos_contratados=servico,
                        data_contratacao=_data(linha["data_contratacao"]),
                    )
                )

        conn.execute(
            estatistica_lote.insert().values(
                lote_id=lote_id,
                gerado_em=momento,
                resultado=estatisticas,
            )
        )
        conn.execute(
            update(upload_lote)
            .where(upload_lote.c.id == lote_id)
            .values(status="PROCESSADO", mensagem=None)
        )
