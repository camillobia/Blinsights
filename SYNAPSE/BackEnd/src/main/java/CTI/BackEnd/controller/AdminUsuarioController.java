package CTI.BackEnd.controller;

import CTI.BackEnd.dto.UsuarioDto;
import CTI.BackEnd.model.Usuario;
import CTI.BackEnd.model.StatusUsuario;
import CTI.BackEnd.service.UsuarioService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping({"/usuarios", "/api/admin/usuarios"})
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

    @PostMapping
    public Usuario salvar(@RequestBody UsuarioDto dto) {

        Usuario usuario = new Usuario();

        usuario.setNome(dto.getNome());
        usuario.setEmail(dto.getEmail());
        usuario.setSenha(dto.getSenha());

        return usuarioService.salvar(usuario);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Usuario> atualizar(
            @PathVariable Long id,
            @RequestBody Usuario usuario) {

        return usuarioService.buscarPorId(id)
                .map(usuarioExistente -> {

                    usuarioExistente.setNome(usuario.getNome());
                    usuarioExistente.setEmail(usuario.getEmail());
                    usuarioExistente.setSenha(usuario.getSenha());

                    Usuario atualizado = usuarioService.salvar(usuarioExistente);

                    return ResponseEntity.ok(atualizado);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {

        usuarioService.deletar(id);

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/status/{status}")
    public List<Usuario> listarPorStatus(@PathVariable String status) {
        StatusUsuario statusUsuario = StatusUsuario.valueOf(status.toUpperCase());
        return usuarioService.listarTodos().stream()
                .filter(usuario -> usuario.getStatus() == statusUsuario)
                .toList();
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<Usuario> alterarStatus(
            @PathVariable Long id,
            @RequestParam String status
    ) {
        try {
            StatusUsuario novoStatus = StatusUsuario.valueOf(status.toUpperCase());
            return usuarioService.buscarPorId(id)
                    .map(usuario -> {
                        usuario.setStatus(novoStatus);
                        return ResponseEntity.ok(usuarioService.salvar(usuario));
                    })
                    .orElse(ResponseEntity.notFound().build());
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.badRequest().build();
        }
    }
}