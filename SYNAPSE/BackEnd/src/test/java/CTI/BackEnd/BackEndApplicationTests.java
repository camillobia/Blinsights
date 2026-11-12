package CTI.BackEnd;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.security.oauth2.jwt.JwsHeader;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest(properties = {
		"spring.datasource.url=jdbc:h2:mem:backEndTests;MODE=PostgreSQL;DB_CLOSE_DELAY=-1",
		"spring.datasource.username=sa",
		"spring.datasource.password=",
		"spring.jpa.hibernate.ddl-auto=create-drop",
		"spring.sql.init.mode=always",
		"JWT_SECRET=test-only-secret-with-at-least-32-characters"
})
@AutoConfigureMockMvc
class BackEndApplicationTests {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private JwtEncoder jwtEncoder;

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Test
	void contextLoads() {
	}

	@Test
	void dashboardRequiresValidBearerToken() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/api/dashboard"))
				.andExpect(MockMvcResultMatchers.status().isUnauthorized());

		Instant agora = Instant.now();
		JwtClaimsSet claims = JwtClaimsSet.builder()
				.issuer("blinsights")
				.subject("test@example.com")
				.issuedAt(agora)
				.expiresAt(agora.plus(5, ChronoUnit.MINUTES))
				.build();
		String token = jwtEncoder.encode(
				JwtEncoderParameters.from(
						JwsHeader.with(MacAlgorithm.HS256).build(),
						claims
				)
		).getTokenValue();

		mockMvc.perform(MockMvcRequestBuilders.get("/api/dashboard")
						.header("Authorization", "Bearer " + token))
				.andExpect(MockMvcResultMatchers.status().isNotFound());

		jdbcTemplate.update(
				"""
				INSERT INTO upload_lote
					(arquivo_nome, status, total_linhas, linhas_validas)
				VALUES ('teste.xlsx', 'PROCESSADO', 1, 1)
				"""
		);
		Long loteId = jdbcTemplate.queryForObject(
				"SELECT MAX(id) FROM upload_lote",
				Long.class
		);
		jdbcTemplate.update(
				"""
				INSERT INTO estatistica_lote (lote_id, gerado_em, resultado)
				VALUES (?, CURRENT_TIMESTAMP, JSON '{"resumo":{"totalClientes":1,"media":1250.0},"insights":[]}')
				""",
				loteId
		);

		mockMvc.perform(MockMvcRequestBuilders.get("/api/dashboard")
						.param("loteId", loteId.toString())
						.header("Authorization", "Bearer " + token))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath(
						"$.estatisticas.resumo.media"
				).value(1250.0));
	}
}
