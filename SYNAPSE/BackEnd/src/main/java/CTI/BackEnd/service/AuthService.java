package CTI.BackEnd.service;

import CTI.BackEnd.dto.UsuarioLoginResponseDto;

import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService {

    private final UsuarioService usuarioService;
    private final TokenService tokenService;

    public AuthService(UsuarioService usuarioService, TokenService tokenService) {
        this.usuarioService = usuarioService;
        this.tokenService = tokenService;
    }

    public Optional<UsuarioLoginResponseDto> login(String email, String senha) {
        return usuarioService.autenticar(email, senha)
                .map(usuario -> new UsuarioLoginResponseDto(
                        usuario.getId(),
                        usuario.getNome(),
                        usuario.getEmail(),
                        tokenService.emitir(usuario)
                ));
    }
}
