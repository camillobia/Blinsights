package CTI.BackEnd.service;

import CTI.BackEnd.model.Usuario;
import CTI.BackEnd.model.StatusUsuario;
import CTI.BackEnd.repository.UsuarioRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UsuarioServiceTest {

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UsuarioService usuarioService;

    @Test
    void autenticarRetornaUsuarioQuandoEmailESenhaConferem() {
        Usuario usuario = new Usuario();
        usuario.setEmail("pessoa@exemplo.com");
        usuario.setSenha("senha-cadastrada");
        usuario.setStatus(StatusUsuario.APROVADO);
        when(passwordEncoder.encode("senha-cadastrada"))
                .thenReturn("senha-criptografada");
        when(usuarioRepository.findByEmailIgnoreCase("pessoa@exemplo.com"))
                .thenReturn(Optional.of(usuario));
        Optional<Usuario> autenticado = usuarioService.autenticar(
                "  pessoa@exemplo.com  ",
                "senha-cadastrada"
        );

        assertEquals(usuario, autenticado.orElseThrow());
        verify(usuarioRepository).findByEmailIgnoreCase("pessoa@exemplo.com");
    }

    @Test
    void autenticarFalhaQuandoSenhaNaoConfere() {
        Usuario usuario = new Usuario();
        usuario.setSenha("senha-cadastrada");
        usuario.setStatus(StatusUsuario.APROVADO);
        when(usuarioRepository.findByEmailIgnoreCase("pessoa@exemplo.com"))
                .thenReturn(Optional.of(usuario));
        when(passwordEncoder.matches("outra-senha", "senha-cadastrada"))
                .thenReturn(false);

        assertTrue(usuarioService.autenticar("pessoa@exemplo.com", "outra-senha").isEmpty());
    }

    @Test
    void autenticarFalhaQuandoEmailEstaAusente() {
        assertTrue(usuarioService.autenticar(" ", "senha").isEmpty());
    }
}
