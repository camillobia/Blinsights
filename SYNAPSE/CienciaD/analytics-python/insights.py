from typing import Any


def brl(valor: float) -> str:
    formatado = f"{valor:,.2f}".replace(",", "X").replace(".", ",").replace("X", ".")
    return f"R$ {formatado}"


def gerar_insights(estatisticas: dict[str, Any]) -> list[str]:
    resumo = estatisticas["resumo"]
    insights: list[str] = []

    if resumo["totalClientes"]:
        insights.append(
            f"Foram analisados {resumo['totalClientes']} clientes, "
            f"com faturamento total de {brl(resumo['totalFaturamento'])}."
        )
        insights.append(
            f"O faturamento médio anual por cliente é {brl(resumo['media'])}."
        )

    if estatisticas["porSegmento"]:
        segmento = estatisticas["porSegmento"][0]
        insights.append(
            f"O segmento {segmento['segmento']} concentra "
            f"{segmento['clientes']} clientes."
        )

    if estatisticas["porNivel"]:
        nivel = estatisticas["porNivel"][0]
        insights.append(
            f"O nível {nivel['nivel']} é o mais frequente, "
            f"com {nivel['clientes']} clientes."
        )

    if estatisticas["porServico"]:
        servico = estatisticas["porServico"][0]
        insights.append(
            f"O serviço {servico['servico']} aparece em "
            f"{servico['clientes']} contratos."
        )

    return insights
