package CTI.BackEnd.service;

import CTI.BackEnd.dto.ClienteDTO;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
public class UploadService {

    private final ClienteValidacaoService clienteValidacaoService;
    private final UploadLoteService uploadLoteService;
    private final PythonEtlService pythonEtlService;

    public UploadService(
            ClienteValidacaoService clienteValidacaoService,
            UploadLoteService uploadLoteService,
            PythonEtlService pythonEtlService
    ) {
        this.clienteValidacaoService = clienteValidacaoService;
        this.uploadLoteService = uploadLoteService;
        this.pythonEtlService = pythonEtlService;
    }

    public Map<String, Object> processar(
            List<ClienteDTO> clientes,
            String arquivoNome,
            Integer totalLinhas
    ) {
        String nomeArquivo = arquivoNome == null ? "upload" : arquivoNome;

        List<ClienteDTO> clientesValidados =
                clienteValidacaoService.validar(clientes);

        int recebidas = totalLinhas == null
                ? clientesValidados.size()
                : Math.max(totalLinhas, clientesValidados.size());

        Long loteId = uploadLoteService.iniciar(
                nomeArquivo,
                recebidas,
                clientesValidados.size()
        );

        try {
            Map<String, Object> resultado = pythonEtlService.processar(
                    loteId,
                    recebidas,
                    clientesValidados
            );

            Map<String, Object> resposta = new LinkedHashMap<>();
            resposta.put("loteId", loteId);
            resposta.put("arquivoNome", nomeArquivo);
            resposta.put("totalLinhas", recebidas);
            resposta.put("linhasValidas", clientesValidados.size());
            resposta.putAll(resultado);
            return resposta;
        } catch (PythonEtlException ex) {
            uploadLoteService.marcarErro(loteId, ex.getMessage());
            throw new ResponseStatusException(
                    HttpStatus.BAD_GATEWAY,
                    ex.getMessage(),
                    ex
            );
        }
    }
}