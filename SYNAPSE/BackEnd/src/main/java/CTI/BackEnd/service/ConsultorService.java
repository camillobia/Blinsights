package CTI.BackEnd.service;

import CTI.BackEnd.model.Consultor;
import CTI.BackEnd.repository.ConsultorRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ConsultorService {

    private final ConsultorRepository repository;

    public ConsultorService(ConsultorRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public Consultor criar(Consultor consultor) {

        if (repository.findByEmail(consultor.getEmail()).isPresent()) {
            throw new RuntimeException("E-mail já cadastrado");
        }

        return repository.save(consultor);
    }

    public Consultor login(String email, String senha) {

        Consultor consultor = repository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Consultor não encontrado"));

        if (!consultor.getSenha().equals(senha)) {
            throw new RuntimeException("Senha inválida");
        }

        return consultor;
    }

    public List<Consultor> listar() {
        return repository.findAll();
    }

    public Consultor buscarPorId(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Consultor não encontrado"));
    }

    @Transactional
    public Consultor atualizar(Long id, Consultor dados) {

        Consultor consultor = buscarPorId(id);

        consultor.setNome(dados.getNome());
        consultor.setEmail(dados.getEmail());
        consultor.setSenha(dados.getSenha());

        return repository.save(consultor);
    }

    @Transactional
    public void excluir(Long id) {

        Consultor consultor = buscarPorId(id);

        repository.deleteById(consultor.getId());
    }
}