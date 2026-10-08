package CTI.BackEnd.controller;

import CTI.BackEnd.dto.ClienteDTO;
import CTI.BackEnd.service.ClienteValidacaoService;

import jakarta.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/clientes")
public class ClienteValidacaoController {

    private final ClienteValidacaoService clienteValidacaoService;

    public ClienteValidacaoController(ClienteValidacaoService clienteValidacaoService) {
        this.clienteValidacaoService = clienteValidacaoService;
    }

    @PostMapping("/validar")
    public ResponseEntity<List<ClienteDTO>> validar(
            @Valid @RequestBody List<ClienteDTO> clientes) {

        List<ClienteDTO> clientesValidados =
                clienteValidacaoService.validar(clientes);

        return ResponseEntity.ok(clientesValidados);
    }
}
