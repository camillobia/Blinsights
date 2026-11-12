import json
import os
import unittest
from pathlib import Path
from unittest.mock import patch

from sqlalchemy import create_engine, text

from api import saude, testar
from banco import reservar_lote
from main import processar_json, processar_lote


ROOT = Path(__file__).resolve().parents[1]


def amostra():
    return json.loads(
        (ROOT / "amostras" / "clientes_exemplo.json").read_text(encoding="utf-8")
    )


class PipelineTest(unittest.TestCase):
    def test_api_de_saude_e_teste_nao_persistente(self):
        self.assertEqual(saude(), {"status": "ok"})
        with patch.dict(os.environ, {"ETL_API_KEY": "chave-de-teste"}):
            resultado = testar(amostra(), "chave-de-teste")
        self.assertEqual(resultado["resumo"]["totalClientes"], 3)

    def test_processa_dados_para_o_contrato_do_dashboard(self):
        resultado = processar_json(amostra())

        self.assertEqual(resultado["resumo"]["totalClientes"], 3)
        self.assertAlmostEqual(resultado["resumo"]["totalFaturamento"], 5_130_000)
        self.assertEqual(len(resultado["porSegmento"]), 3)
        self.assertEqual(resultado["porNivel"][0]["nivel"], "A")
        self.assertEqual(resultado["porServico"][0]["servico"], "Firewall")
        self.assertEqual(resultado["porMes"][0]["mes"], "2025-01")
        self.assertTrue(resultado["insights"])
        self.assertEqual(resultado["topClientes"][0]["codigoCliente"], "CTI003")

    def test_processa_e_persiste_lote_completo(self):
        engine = create_engine("sqlite+pysqlite:///:memory:")
        try:
            lote_id = reservar_lote(engine, "clientes.xlsx", 4, 3)
            estatisticas = processar_lote(lote_id, amostra(), engine)

            with engine.connect() as conn:
                status = conn.execute(
                    text("SELECT status FROM upload_lote WHERE id = :id"),
                    {"id": lote_id},
                ).scalar_one()
                clientes = conn.execute(
                    text("SELECT COUNT(*) FROM cliente WHERE lote_id = :id"),
                    {"id": lote_id},
                ).scalar_one()
                contratos = conn.execute(
                    text("SELECT COUNT(*) FROM contrato WHERE lote_id = :id"),
                    {"id": lote_id},
                ).scalar_one()
                media = conn.execute(
                    text(
                        "SELECT json_extract(resultado, '$.resumo.media') "
                        "FROM estatistica_lote WHERE lote_id = :id"
                    ),
                    {"id": lote_id},
                ).scalar_one()

            self.assertEqual(status, "PROCESSADO")
            self.assertEqual(clientes, 3)
            self.assertEqual(contratos, 7)
            self.assertEqual(media, estatisticas["resumo"]["media"])
        finally:
            engine.dispose()


if __name__ == "__main__":
    unittest.main()
