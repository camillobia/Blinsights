import json
from pathlib import Path

from analise import calcular
from banco import salvar_dados
from insights import gerar_insights
from limpeza import tratar


def processar_json(dados_brutos: list[dict]) -> dict:
    df_limpo, avisos = tratar(dados_brutos)
    resumo = calcular(df_limpo, linhas_recebidas=len(dados_brutos))
    frases = gerar_insights(resumo, dados_brutos)

    payload = {
        "resumo": resumo,
        "insights": frases,
        "avisos": avisos,
    }
    return payload


def main() -> None:
    arquivo = Path(__file__).resolve().parent / "amostras" / "clientes_exemplo.json"
    with arquivo.open("r", encoding="utf-8") as f:
        dados = json.load(f)

    resultado = processar_json(dados)
    print(json.dumps(resultado, ensure_ascii=False, indent=2))


if __name__ == "__main__":
    main()
