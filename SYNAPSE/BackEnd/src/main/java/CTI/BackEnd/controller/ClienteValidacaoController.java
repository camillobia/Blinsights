package CTI.BackEnd.controller;

import CTI.BackEnd.dto.ClienteDTO;
import CTI.BackEnd.service.ClienteValidacaoService;
import CTI.BackEnd.service.PythonEtlException;
import CTI.BackEnd.service.PythonEtlService;
import CTI.BackEnd.service.UploadLoteService;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;

import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/clientes")
public class ClienteValidacaoController {

    private final ClienteValidacaoService clienteValidacaoService;
    private final UploadLoteService uploadLoteService;
    private final PythonEtlService pythonEtlService;

    public ClienteValidacaoController(
            ClienteValidacaoService clienteValidacaoService,
            UploadLoteService uploadLoteService,
            PythonEtlService pythonEtlService
    ) {
        this.clienteValidacaoService = clienteValidacaoService;
        this.uploadLoteService = uploadLoteService;
        this.pythonEtlService = pythonEtlService;
    }

    @PostMapping("/validar")
    public ResponseEntity<Map<String, Object>> validar(
            @NotEmpty(message = "Envie ao menos um cliente.")
            @RequestBody List<@Valid ClienteDTO> clientes,
            @RequestHeader(name = "X-File-Name", required = false) String arquivoNome,
            @RequestHeader(name = "X-Total-Linhas", required = false) Integer totalLinhas
    ) {

        List<ClienteDTO> clientesValidados =
                clienteValidacaoService.validar(clientes);

        int recebidas = totalLinhas == null
                ? clientesValidados.size()
                : Math.max(totalLinhas, clientesValidados.size());
        Long loteId = uploadLoteService.iniciar(
                arquivoNome == null ? "upload" : arquivoNome,
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
            resposta.put("arquivoNome", arquivoNome == null ? "upload" : arquivoNome);
            resposta.put("totalLinhas", recebidas);
            resposta.put("linhasValidas", clientesValidados.size());
            resposta.putAll(resultado);
            return ResponseEntity.ok(resposta);
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
