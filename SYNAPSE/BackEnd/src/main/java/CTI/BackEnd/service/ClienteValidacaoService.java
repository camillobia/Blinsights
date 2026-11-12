package CTI.BackEnd.service;

import CTI.BackEnd.dto.ClienteDTO;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Locale;

@Service
public class ClienteValidacaoService {

    public List<ClienteDTO> validar(List<ClienteDTO> clientes) {

        Set<String> codigos = new HashSet<>();
        List<ClienteDTO> clientesPadronizados = new ArrayList<>();

        for (ClienteDTO cliente : clientes) {

            if (!codigos.add(cliente.codigoCliente().trim().toUpperCase(Locale.ROOT))) {
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

        String s = segmento.trim().toLowerCase(Locale.ROOT);

        return java.util.Arrays.stream(s.split("\\s*/\\s*"))
                .map(this::padronizarSegmentoIndividual)
                .collect(java.util.stream.Collectors.joining(" / "));
    }

    private String padronizarSegmentoIndividual(String segmento) {
        return switch (segmento) {
            case "ind.", "industria", "indústria" -> "Indústria";
            case "comercio", "comércio" -> "Comércio";
            case "servicos", "serviços" -> "Serviços";
            case "saude", "saúde" -> "Saúde";
            case "educacao", "educação" -> "Educação";
            case "tecnologia" -> "Tecnologia";
            default -> segmento.isEmpty()
                    ? segmento
                    : segmento.substring(0, 1).toUpperCase(Locale.ROOT) + segmento.substring(1);
        };
    }

    private String formatarNome(String nome) {

        return java.util.Arrays.stream(nome.trim().toLowerCase(Locale.ROOT).split("\\s+"))
                .map(parte -> parte.substring(0, 1).toUpperCase(Locale.ROOT) + parte.substring(1))
                .collect(java.util.stream.Collectors.joining(" "));
    }
}