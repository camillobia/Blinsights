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
	void healthIsPublic() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/api/health"))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$.status").value("ok"));
	}

	@Test
	void unauthorizedReturnsJsonError() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/api/auth/me"))
				.andExpect(MockMvcResultMatchers.status().isUnauthorized())
				.andExpect(MockMvcResultMatchers.jsonPath("$.erro").exists());
	}

	@Test
	void meReturnsLoggedUserAndAdminRoutesNeedAdmin() throws Exception {
		String login = mockMvc.perform(MockMvcRequestBuilders.post("/api/auth/login")
						.contentType("application/json")
						.content("{\"email\":\"admin@senai.com\",\"senha\":\"admin123\"}"))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andReturn().getResponse().getContentAsString();
		String tokenAdmin = login.replaceAll(".*\"token\":\"([^\"]+)\".*", "$1");

		mockMvc.perform(MockMvcRequestBuilders.get("/api/auth/me")
						.header("Authorization", "Bearer " + tokenAdmin))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$.perfil").value("ADMIN"))
				.andExpect(MockMvcResultMatchers.jsonPath("$.senha").doesNotExist());

		mockMvc.perform(MockMvcRequestBuilders.get("/api/admin/usuarios")
						.header("Authorization", "Bearer " + tokenAdmin))
				.andExpect(MockMvcResultMatchers.status().isOk());

		mockMvc.perform(MockMvcRequestBuilders.get("/api/admin/usuarios")
						.header("Authorization", "Bearer " + tokenDe("comum@example.com", "USER")))
				.andExpect(MockMvcResultMatchers.status().isForbidden())
				.andExpect(MockMvcResultMatchers.jsonPath("$.erro").exists());
	}

	@Test
	void cadastroPendenteAprovacaoEPromocao() throws Exception {
		String tokenAdmin = loginToken("admin@senai.com", "admin123");

		String criado = mockMvc.perform(MockMvcRequestBuilders.post("/api/auth/register")
						.contentType("application/json")
						.content("{\"nome\":\"Novo\",\"email\":\"novo@example.com\",\"senha\":\"segredo1\"}"))
				.andExpect(MockMvcResultMatchers.status().isCreated())
				.andExpect(MockMvcResultMatchers.jsonPath("$.status").value("PENDENTE"))
				.andReturn().getResponse().getContentAsString();
		String id = criado.replaceAll(".*\"id\":(\\d+).*", "$1");

		// Pendente não entra.
		mockMvc.perform(MockMvcRequestBuilders.post("/api/auth/login")
						.contentType("application/json")
						.content("{\"email\":\"novo@example.com\",\"senha\":\"segredo1\"}"))
				.andExpect(MockMvcResultMatchers.status().isUnauthorized());

		mockMvc.perform(MockMvcRequestBuilders.patch("/api/admin/usuarios/" + id + "/status")
						.param("status", "APROVADO")
						.header("Authorization", "Bearer " + tokenAdmin))
				.andExpect(MockMvcResultMatchers.status().isOk());

		mockMvc.perform(MockMvcRequestBuilders.patch("/api/admin/usuarios/" + id + "/perfil")
						.param("perfil", "ADMIN")
						.header("Authorization", "Bearer " + tokenAdmin))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$.perfil").value("ADMIN"));

		// Aprovado entra, já como ADMIN.
		mockMvc.perform(MockMvcRequestBuilders.post("/api/auth/login")
						.contentType("application/json")
						.content("{\"email\":\"novo@example.com\",\"senha\":\"segredo1\"}"))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$.perfil").value("ADMIN"));

		// O admin não pode se bloquear nem se rebaixar.
		String meuId = mockMvc.perform(MockMvcRequestBuilders.get("/api/auth/me")
						.header("Authorization", "Bearer " + tokenAdmin))
				.andReturn().getResponse().getContentAsString().replaceAll(".*\"id\":(\\d+).*", "$1");
		mockMvc.perform(MockMvcRequestBuilders.patch("/api/admin/usuarios/" + meuId + "/status")
						.param("status", "BLOQUEADO")
						.header("Authorization", "Bearer " + tokenAdmin))
				.andExpect(MockMvcResultMatchers.status().isForbidden())
				.andExpect(MockMvcResultMatchers.jsonPath("$.erro").exists());
		mockMvc.perform(MockMvcRequestBuilders.patch("/api/admin/usuarios/" + meuId + "/perfil")
						.param("perfil", "USER")
						.header("Authorization", "Bearer " + tokenAdmin))
				.andExpect(MockMvcResultMatchers.status().isForbidden());
	}

	private String loginToken(String email, String senha) throws Exception {
		String resposta = mockMvc.perform(MockMvcRequestBuilders.post("/api/auth/login")
						.contentType("application/json")
						.content("{\"email\":\"" + email + "\",\"senha\":\"" + senha + "\"}"))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andReturn().getResponse().getContentAsString();
		return resposta.replaceAll(".*\"token\":\"([^\"]+)\".*", "$1");
	}

	private String tokenDe(String email, String scope) {
		Instant agora = Instant.now();
		JwtClaimsSet claims = JwtClaimsSet.builder()
				.issuer("blinsights")
				.subject(email)
				.issuedAt(agora)
				.expiresAt(agora.plus(5, ChronoUnit.MINUTES))
				.claim("scope", scope)
				.build();
		return jwtEncoder.encode(JwtEncoderParameters.from(
				JwsHeader.with(MacAlgorithm.HS256).build(), claims)).getTokenValue();
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
