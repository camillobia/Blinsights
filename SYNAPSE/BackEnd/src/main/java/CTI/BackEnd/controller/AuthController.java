package CTI.BackEnd.controller;

import CTI.BackEnd.dto.UsuarioLoginDto;
import CTI.BackEnd.dto.UsuarioLoginResponseDto;
import CTI.BackEnd.dto.UsuarioDto;
import CTI.BackEnd.model.Usuario;
import CTI.BackEnd.service.AuthService;
import CTI.BackEnd.service.UsuarioService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;
    private final UsuarioService usuarioService;

    public AuthController(AuthService authService, UsuarioService usuarioService) {
        this.authService = authService;
        this.usuarioService = usuarioService;
    }

    @PostMapping("/login")
    public ResponseEntity<UsuarioLoginResponseDto> login(@RequestBody UsuarioLoginDto dto) {
        return authService.login(dto.getEmail(), dto.getSenha())
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.UNAUTHORIZED).build());
    }

    @PostMapping("/register")
    public ResponseEntity<Usuario> register(@RequestBody UsuarioDto dto) {
        Usuario usuario = new Usuario();
        usuario.setNome(dto.getNome());
        usuario.setEmail(dto.getEmail());
        usuario.setSenha(dto.getSenha());
        usuario.setMatricula(dto.getMatricula());
        usuario.setTurma(dto.getTurma());

        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(usuarioService.cadastrar(usuario));
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }

    /** Dados do usuário dono do token (usado pelo front para recarregar a sessão). */
    @GetMapping("/me")
    public ResponseEntity<Usuario> me(@AuthenticationPrincipal Jwt jwt) {
        return usuarioService.buscarPorEmail(jwt.getSubject())
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.UNAUTHORIZED).build());
    }
}