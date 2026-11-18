package CTI.BackEnd.controller;

import CTI.BackEnd.model.PerfilUsuario;
import CTI.BackEnd.model.Usuario;
import CTI.BackEnd.model.StatusUsuario;
import CTI.BackEnd.service.UsuarioService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * Administração de contas (somente perfil ADMIN — regra em SecurityConfig).
 * Contas novas são criadas apenas por /api/auth/register, que criptografa a senha.
 * O administrador não pode bloquear, rebaixar ou excluir a própria conta (evita ficar sem acesso).
 */
@RestController
@RequestMapping("/api/admin/usuarios")
public class AdminUsuarioController {

    private final UsuarioService usuarioService;

    public AdminUsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @GetMapping
    public List<Usuario> listarTodos(@RequestParam(required = false) String status) {
        if (status == null || status.isBlank()) {
            return usuarioService.listarTodos();
        }
        try {
            StatusUsuario statusUsuario = StatusUsuario.valueOf(status.toUpperCase());
            return usuarioService.listarTodos().stream()
                    .filter(usuario -> usuario.getStatus() == statusUsuario)
                    .toList();
        } catch (IllegalArgumentException ex) {
            return List.of();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Usuario> buscarPorId(@PathVariable Long id) {
        return usuarioService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletar(@PathVariable Long id, @AuthenticationPrincipal Jwt jwt) {
        if (ehOProprio(id, jwt)) {
            return proibido("Você não pode excluir a própria conta.");
        }
        usuarioService.deletar(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<?> alterarStatus(
            @PathVariable Long id,
            @RequestParam String status,
            @AuthenticationPrincipal Jwt jwt
    ) {
        StatusUsuario novoStatus;
        try {
            novoStatus = StatusUsuario.valueOf(status.toUpperCase());
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.badRequest().body(Map.of("erro", "Status inválido."));
        }
        if (ehOProprio(id, jwt) && novoStatus != StatusUsuario.APROVADO) {
            return proibido("Você não pode bloquear a própria conta.");
        }
        return usuarioService.buscarPorId(id)
                .<ResponseEntity<?>>map(usuario -> {
                    usuario.setStatus(novoStatus);
                    return ResponseEntity.ok(usuarioService.salvar(usuario));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @PatchMapping("/{id}/perfil")
    public ResponseEntity<?> alterarPerfil(
            @PathVariable Long id,
            @RequestParam String perfil,
            @AuthenticationPrincipal Jwt jwt
    ) {
        PerfilUsuario novoPerfil;
        try {
            novoPerfil = PerfilUsuario.valueOf(perfil.toUpperCase());
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.badRequest().body(Map.of("erro", "Perfil inválido."));
        }
        if (ehOProprio(id, jwt) && novoPerfil != PerfilUsuario.ADMIN) {
            return proibido("Você não pode remover o próprio acesso de administrador.");
        }
        return usuarioService.buscarPorId(id)
                .<ResponseEntity<?>>map(usuario -> {
                    usuario.setPerfil(novoPerfil);
                    return ResponseEntity.ok(usuarioService.salvar(usuario));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    private boolean ehOProprio(Long id, Jwt jwt) {
        return jwt != null && usuarioService.buscarPorId(id)
                .map(u -> u.getEmail() != null && u.getEmail().equalsIgnoreCase(jwt.getSubject()))
                .orElse(false);
    }

    private static ResponseEntity<Map<String, String>> proibido(String mensagem) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(Map.of("erro", mensagem));
    }
}
