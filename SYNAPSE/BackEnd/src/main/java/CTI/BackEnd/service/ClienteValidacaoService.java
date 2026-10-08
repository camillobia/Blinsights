package CTI.BackEnd.service;

import CTI.BackEnd.dto.ClienteDTO;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class ClienteValidacaoService {

    public List<ClienteDTO> validar(List<ClienteDTO> clientes) {

        Set<String> codigos = new HashSet<>();
        List<ClienteDTO> clientesPadronizados = new ArrayList<>();

        for (ClienteDTO cliente : clientes) {

            if (!codigos.add(cliente.codigoCliente().trim())) {
                throw new IllegalArgumentException(
                        "Código de cliente duplicado: " + cliente.codigoCliente().trim()
                );
            }

            ClienteDTO clientePadronizado = new ClienteDTO(
                    cliente.codigoCliente().trim(),
                    cliente.nomeCliente().trim(),
                    formatarNome(cliente.consultor()),
                    padronizarSegmento(cliente.segmento()),
                    cliente.nivelCliente().trim().toUpperCase(),
                    cliente.faturamentoAnual(),
                    cliente.servicosContratados().trim(),
                    cliente.dataContratacao(),
                    cliente.cidade() == null ? "" : cliente.cidade().trim(),
                    cliente.uf() == null ? "" : cliente.uf().trim().toUpperCase()
            );

            clientesPadronizados.add(clientePadronizado);
        }

        return clientesPadronizados;
    }

    private String padronizarSegmento(String segmento) {

        String s = segmento.trim().toLowerCase();

        if (s.equals("ind.") || s.equals("industria") || s.equals("indústria")) {
            return "Indústria";
        }

        if (s.equals("comercio") || s.equals("comércio")) {
            return "Comércio";
        }

        if (s.equals("servicos") || s.equals("serviços")) {
            return "Serviços";
        }

        return segmento.trim();
    }

    private String formatarNome(String nome) {

        String texto = nome.trim().toLowerCase();

        return texto.substring(0, 1).toUpperCase() + texto.substring(1);
    }
}