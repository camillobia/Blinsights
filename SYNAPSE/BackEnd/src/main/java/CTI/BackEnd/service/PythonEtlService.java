package CTI.BackEnd.service;

import java.util.List;
import java.util.Map;

import CTI.BackEnd.dto.ClienteDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientException;

@Service
public class PythonEtlService {

    private final RestClient restClient;
    private final String apiKey;

    public PythonEtlService(
            @Value("${ETL_URL:http://localhost:8000}") String etlUrl,
            @Value("${ETL_API_KEY:}") String apiKey
    ) {
        this.restClient = RestClient.create(etlUrl);
        this.apiKey = apiKey;
    }

    public Map<String, Object> processar(
            Long loteId,
            int recebidas,
            List<ClienteDTO> clientes
    ) {
        if (apiKey.isBlank()) {
            throw new PythonEtlException("ETL_API_KEY não está configurada no backend.");
        }

        Map<String, Object> payload = Map.of(
                "loteId", loteId,
                "recebidas", recebidas,
                "linhas", clientes
        );

        try {
            Map<String, Object> resposta = restClient.post()
                    .uri("/etl")
                    .header("X-ETL-Key", apiKey)
                    .body(payload)
                    .retrieve()
                    .body(new ParameterizedTypeReference<>() { });

            if (resposta == null || !resposta.containsKey("estatisticas")) {
                throw new PythonEtlException("O serviço Python retornou uma resposta incompleta.");
            }
            return resposta;
        } catch (RestClientException ex) {
            throw new PythonEtlException("Não foi possível falar com o serviço Python.", ex);
        }
    }
}
