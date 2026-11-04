from typing import Any, Dict, List


def brl(valor: float) -> str:
    return f"R$ {valor:,.2f}"


def _virg(valor: float) -> str:
    return f"{valor:,.0f}"


def gerar_insights(resumo: Dict[str, Any], dados: List[dict] | None = None) -> List[str]:
    insights: List[str] = []

    segmento = resumo.get("segmento_mais_frequente", {}).get("nome")
    qtd_segmento = resumo.get("segmento_mais_frequente", {}).get("quantidade", 0)
    if segmento:
        insights.append(f"O segmento {segmento} concentra {qtd_segmento} clientes atendidos.")

    nivel = resumo.get("nivel_mais_frequente", {}).get("nome")
    qtd_nivel = resumo.get("nivel_mais_frequente", {}).get("quantidade", 0)
    if nivel:
        insights.append(f"O nível {nivel} aparece como o mais frequente, com {qtd_nivel} registros.")

    media = resumo.get("media_faturamento", 0.0)
    if media:
        insights.append(f"A receita média por cliente está em {brl(media)}.")

    mediana = resumo.get("mediana_faturamento", 0.0)
    if mediana:
        insights.append(f"A mediana do faturamento é {brl(mediana)}.")

    servico = resumo.get("servico_mais_frequente", {}).get("nome")
    qtd_servico = resumo.get("servico_mais_frequente", {}).get("quantidade", 0)
    if servico:
        insights.append(f"O serviço {servico} é o mais contratado, com {qtd_servico} ocorrências.")

    corr = resumo.get("correlacao_servicos_faturamento", 0.0)
    if abs(corr) > 0:
        insights.append(f"A correlação entre quantidade de serviços e faturamento ficou em {corr:.2f}.")

    return insights
