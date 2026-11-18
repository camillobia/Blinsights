
package CTI.BackEnd.config;

import CTI.BackEnd.model.PerfilUsuario;
import CTI.BackEnd.model.StatusUsuario;
import CTI.BackEnd.model.Usuario;
import CTI.BackEnd.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class UsuarioConfig {

    @Bean
    ApplicationRunner criarAdministradorInicial(
            UsuarioRepository usuarioRepository,
            PasswordEncoder passwordEncoder,
            @Value("${ADMIN_EMAIL:admin@senai.com}") String email,
            @Value("${ADMIN_PASSWORD:admin123}") String senha
    ) {
        return args -> usuarioRepository.findByEmailIgnoreCase(email)
                .map(admin -> {
                    admin.setPerfil(PerfilUsuario.ADMIN);
                    if (admin.getStatus() == null) {
                        admin.setStatus(StatusUsuario.APROVADO);
                    }
                    return usuarioRepository.save(admin);
                })
                .orElseGet(() -> {
                    Usuario admin = new Usuario();
                    admin.setNome("Administrador");
                    admin.setEmail(email);
                    admin.setSenha(passwordEncoder.encode(senha));
                    admin.setPerfil(PerfilUsuario.ADMIN);
                    admin.setStatus(StatusUsuario.APROVADO);
                    return usuarioRepository.save(admin);
                });
    }
}
