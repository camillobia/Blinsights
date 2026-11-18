package CTI.BackEnd.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

class PythonEtlServiceTest {

    @Test
    void normalizaErrosComunsDeConfiguracao() {
        assertEquals("https://etl.onrender.com", PythonEtlService.normalizarUrl("https://etl.onrender.com"));
        assertEquals("https://etl.onrender.com", PythonEtlService.normalizarUrl("  https://etl.onrender.com/  "));
        assertEquals("https://etl.onrender.com", PythonEtlService.normalizarUrl("\"https://etl.onrender.com\""));
        assertEquals("https://etl.onrender.com", PythonEtlService.normalizarUrl("etl.onrender.com"));
        assertEquals("https://etl.onrender.com", PythonEtlService.normalizarUrl("https://etl.onrender.com/etl"));
        assertEquals("http://localhost:8000", PythonEtlService.normalizarUrl(""));
        assertEquals("http://localhost:8000", PythonEtlService.normalizarUrl("http://localhost:8000"));
    }

    @Test
    void urlInvalidaDaMensagemClara() {
        IllegalStateException erro = assertThrows(IllegalStateException.class,
                () -> PythonEtlService.normalizarUrl("https://meu etl.com"));
        assertEquals(true, erro.getMessage().contains("ETL_URL inválida"));
    }
}
