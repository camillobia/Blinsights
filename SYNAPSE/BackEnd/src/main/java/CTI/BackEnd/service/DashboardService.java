package CTI.BackEnd.service;

import java.nio.charset.StandardCharsets;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import tools.jackson.core.JacksonException;
import tools.jackson.databind.ObjectMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;

@Service
public class DashboardService {

    private static final String DASHBOARD_SQL = """
            SELECT l.id, l.arquivo_nome, l.enviado_em, l.total_linhas,
                   l.linhas_validas, e.gerado_em, e.resultado
            FROM upload_lote l
            JOIN estatistica_lote e ON e.lote_id = l.id
            WHERE l.status = 'PROCESSADO'
            """;

    private final JdbcTemplate jdbcTemplate;
    private final ObjectMapper objectMapper;

    public DashboardService(JdbcTemplate jdbcTemplate, ObjectMapper objectMapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.objectMapper = objectMapper;
    }

    public Map<String, Object> obter(Long loteId) {
        String sql = DASHBOARD_SQL
                + (loteId == null
                        ? " ORDER BY l.id DESC LIMIT 1"
                        : " AND l.id = ? LIMIT 1");

        List<Map<String, Object>> linhas = loteId == null
                ? jdbcTemplate.queryForList(sql)
                : jdbcTemplate.queryForList(sql, loteId);

        if (linhas.isEmpty()) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Nenhum lote processado foi encontrado."
            );
        }

        Map<String, Object> linha = linhas.get(0);
        Map<String, Object> resposta = new LinkedHashMap<>();
        Map<String, Object> lote = new LinkedHashMap<>();
        lote.put("id", linha.get("id"));
        lote.put("arquivoNome", linha.get("arquivo_nome"));
        lote.put("enviadoEm", linha.get("enviado_em"));
        lote.put("totalLinhas", linha.get("total_linhas"));
        lote.put("linhasValidas", linha.get("linhas_validas"));

        resposta.put("lote", lote);
        resposta.put("geradoEm", linha.get("gerado_em"));
        resposta.put("estatisticas", lerJson(linha.get("resultado")));
        return resposta;
    }

    public List<Map<String, Object>> listarLotes() {
        return jdbcTemplate.queryForList("""
                SELECT id, arquivo_nome, enviado_em, total_linhas, linhas_validas
                FROM upload_lote
                WHERE status = 'PROCESSADO'
                ORDER BY id DESC
                LIMIT 20
                """);
    }

    private Object lerJson(Object resultado) {
        try {
            String json = resultado instanceof byte[] bytes
                    ? new String(bytes, StandardCharsets.UTF_8)
                    : resultado.toString();
            return objectMapper.readValue(json, Object.class);
        } catch (JacksonException ex) {
            throw new IllegalStateException(
                    "O resultado estatístico armazenado no banco não é um JSON válido.",
                    ex
            );
        }
    }
}
