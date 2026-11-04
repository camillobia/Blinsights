from typing import Any, List

from fastapi import FastAPI

from main import processar_json

app = FastAPI(title="ETL Analytics API", version="1.0.0")


@app.get("/")
def raiz() -> dict[str, str]:
    return {"status": "ok", "mensagem": "API ETL pronta para receber dados do backend."}


@app.post("/etl")
async def receber_etl(payload: List[dict[str, Any]] | dict[str, Any]) -> dict[str, Any]:
    dados = payload if isinstance(payload, list) else payload.get("dados", [])
    return processar_json(dados)
