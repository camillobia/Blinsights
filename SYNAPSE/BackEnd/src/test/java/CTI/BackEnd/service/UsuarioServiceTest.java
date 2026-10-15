package CTI.BackEnd.service;

import CTI.BackEnd.model.Usuario;
import CTI.BackEnd.repository.UsuarioRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UsuarioServiceTest {

    @Mock
    private UsuarioRepository usuarioRepository;

    @InjectMocks
    private UsuarioService usuarioService;

    @Test
    void autenticarRetornaUsuarioQuandoEmailESenhaConferem() {
        Usuario usuario = new Usuario();
        usuario.setEmail("pessoa@exemplo.com");
        usuario.setSenha("senha-cadastrada");
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
        when(usuarioRepository.findByEmailIgnoreCase("pessoa@exemplo.com"))
                .thenReturn(Optional.of(usuario));

        assertTrue(usuarioService.autenticar("pessoa@exemplo.com", "outra-senha").isEmpty());
    }

    @Test
    void autenticarFalhaQuandoEmailEstaAusente() {
        assertTrue(usuarioService.autenticar(" ", "senha").isEmpty());
    }
}
