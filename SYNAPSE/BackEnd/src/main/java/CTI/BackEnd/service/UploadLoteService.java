package CTI.BackEnd.service;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service
public class UploadLoteService {

    private final JdbcTemplate jdbcTemplate;

    public UploadLoteService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Long iniciar(String arquivoNome, int totalLinhas, int linhasValidas) {
        return jdbcTemplate.queryForObject(
                """
                INSERT INTO upload_lote
                    (arquivo_nome, status, total_linhas, linhas_validas)
                VALUES (?, 'PROCESSANDO', ?, ?)
                RETURNING id
                """,
                Long.class,
                limitar(arquivoNome, 255),
                totalLinhas,
                linhasValidas
        );
    }

    public void marcarErro(Long loteId, String mensagem) {
        jdbcTemplate.update(
                "UPDATE upload_lote SET status = 'ERRO', mensagem = ? WHERE id = ?",
                limitar(mensagem, 2000),
                loteId
        );
    }

    private String limitar(String valor, int tamanho) {
        if (valor == null || valor.isBlank()) {
            return "upload";
        }
        return valor.substring(0, Math.min(valor.length(), tamanho));
    }
}
