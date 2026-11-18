package CTI.BackEnd.controller;

import CTI.BackEnd.dto.ClienteDTO;
import CTI.BackEnd.service.UploadService;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/clientes")
public class ClienteValidacaoController {

    private final UploadService uploadService;

    public ClienteValidacaoController(UploadService uploadService) {
        this.uploadService = uploadService;
    }

    @PostMapping("/validar")
    public ResponseEntity<Map<String, Object>> validar(
            @NotEmpty(message = "Envie ao menos um cliente.")
            @RequestBody List<@Valid ClienteDTO> clientes,
            @RequestHeader(name = "X-File-Name", required = false) String arquivoNome,
            @RequestHeader(name = "X-Total-Linhas", required = false) Integer totalLinhas
    ) {
        return ResponseEntity.ok(uploadService.processar(clientes, arquivoNome, totalLinhas));
    }
}
