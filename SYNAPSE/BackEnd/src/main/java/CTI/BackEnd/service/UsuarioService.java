package CTI.BackEnd.service;

import CTI.BackEnd.model.Usuario;
import CTI.BackEnd.model.PerfilUsuario;
import CTI.BackEnd.model.StatusUsuario;
import CTI.BackEnd.repository.UsuarioRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    public UsuarioService(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public List<Usuario> listarTodos() {
        return usuarioRepository.findAll();
    }

    public Optional<Usuario> buscarPorId(Long id) {
        return usuarioRepository.findById(id);
    }

    public Optional<Usuario> buscarPorEmail(String email) {
        if (email == null || email.isBlank()) {
            return Optional.empty();
        }
        return usuarioRepository.findByEmailIgnoreCase(email.trim());
    }

    public Usuario salvar(Usuario usuario) {
        usuario.setPerfil(usuario.getPerfil() == null ? PerfilUsuario.USER : usuario.getPerfil());
        usuario.setStatus(usuario.getStatus() == null ? StatusUsuario.PENDENTE : usuario.getStatus());
        return usuarioRepository.save(usuario);
    }

    public Usuario cadastrar(Usuario usuario) {
        if (usuarioRepository.findByEmailIgnoreCase(usuario.getEmail()).isPresent()) {
            throw new IllegalArgumentException("Já existe uma conta com este e-mail.");
        }
        usuario.setSenha(passwordEncoder.encode(usuario.getSenha()));
        usuario.setPerfil(PerfilUsuario.USER);
        usuario.setStatus(StatusUsuario.PENDENTE);
        return usuarioRepository.save(usuario);
    }

    public Optional<Usuario> autenticar(String email, String senha) {
        if (email == null || email.isBlank() || senha == null) {
            return Optional.empty();
        }

        return usuarioRepository.findByEmailIgnoreCase(email.trim())
                .filter(usuario -> usuario.getStatus() == null
                        || usuario.getStatus() == StatusUsuario.APROVADO)
                .filter(usuario -> {
                    if (senha.equals(usuario.getSenha())) {
                        usuario.setSenha(passwordEncoder.encode(senha));
                        usuarioRepository.save(usuario);
                        return true;
                    }
                    return passwordEncoder.matches(senha, usuario.getSenha());
                });
    }

    public void deletar(Long id) {
        usuarioRepository.deleteById(id);
    }
}