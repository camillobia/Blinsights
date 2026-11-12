import json
from pathlib import Path
from typing import Any

from analise import calcular
from banco import get_engine, marcar_erro, salvar_dados
from insights import gerar_insights
from limpeza import tratar


def processar_json(dados_brutos: list[dict[str, Any]]) -> dict[str, Any]:
    df_limpo, avisos = tratar(dados_brutos)
    estatisticas = calcular(df_limpo, linhas_recebidas=len(dados_brutos))
    estatisticas["insights"] = gerar_insights(estatisticas)
    estatisticas["avisos"] = avisos
    return estatisticas


def processar_lote(
    lote_id: int,
    dados_brutos: list[dict[str, Any]],
    engine=None,
) -> dict[str, Any]:
    banco = engine or get_engine()
    try:
        df_limpo, avisos = tratar(dados_brutos)
        estatisticas = calcular(df_limpo, linhas_recebidas=len(dados_brutos))
        estatisticas["insights"] = gerar_insights(estatisticas)
        estatisticas["avisos"] = avisos
        salvar_dados(banco, lote_id, df_limpo, estatisticas)
        return estatisticas
    except Exception as erro:
        marcar_erro(banco, lote_id, str(erro))
        raise
    finally:
        if engine is None:
            banco.dispose()


def main() -> None:
    arquivo = Path(__file__).resolve().parent / "amostras" / "clientes_exemplo.json"
    with arquivo.open("r", encoding="utf-8") as f:
        dados = json.load(f)

    resultado = processar_json(dados)
    print(json.dumps(resultado, ensure_ascii=False, indent=2))


if __name__ == "__main__":
    main()
