import hmac
import os
from typing import Any

from fastapi import FastAPI, Header, HTTPException
from pydantic import BaseModel, ConfigDict, Field

from main import processar_json, processar_lote

app = FastAPI(title="Synapse ETL Analytics API", version="1.0.0")


class LoteEntrada(BaseModel):
    model_config = ConfigDict(populate_by_name=True)

    lote_id: int = Field(alias="loteId", gt=0)
    recebidas: int = Field(ge=0)
    linhas: list[dict[str, Any]] = Field(min_length=1)


def validar_chave_etl(x_etl_key: str | None) -> None:
    chave_configurada = os.getenv("ETL_API_KEY", "")
    if not chave_configurada:
        raise HTTPException(
            status_code=503,
            detail="ETL_API_KEY não está configurada no serviço Python.",
        )
    if not x_etl_key or not hmac.compare_digest(x_etl_key, chave_configurada):
        raise HTTPException(status_code=401, detail="Chave de integração inválida.")


@app.get("/saude")
@app.get("/")
def saude() -> dict[str, str]:
    return {"status": "ok"}


@app.post("/testar")
def testar(
    linhas: list[dict[str, Any]],
    x_etl_key: str | None = Header(default=None, alias="X-ETL-Key"),
) -> dict[str, Any]:
    validar_chave_etl(x_etl_key)
    return processar_json(linhas)


@app.post("/etl")
def receber_etl(
    payload: LoteEntrada,
    x_etl_key: str | None = Header(default=None, alias="X-ETL-Key"),
) -> dict[str, Any]:
    validar_chave_etl(x_etl_key)
    estatisticas = processar_lote(payload.lote_id, payload.linhas)
    return {
        "loteId": payload.lote_id,
        "estatisticas": estatisticas,
    }
