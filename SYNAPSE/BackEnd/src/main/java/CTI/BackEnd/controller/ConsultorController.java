package CTI.BackEnd.controller;

import CTI.BackEnd.model.Consultor;
import CTI.BackEnd.service.ConsultorService;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/consultores")
public class ConsultorController {

    private final ConsultorService service;

    public ConsultorController(ConsultorService service) {
        this.service = service;
    }

    @PostMapping
    public Consultor criar(@RequestBody Consultor consultor) {
        return service.criar(consultor);
    }

    @PostMapping("/login")
    public Consultor login(@RequestBody Consultor consultor) {
        return service.login(
                consultor.getEmail(),
                consultor.getSenha()
        );
    }

    @GetMapping
    public List<Consultor> listar() {
        return service.listar();
    }

    @GetMapping("/{id}")
    public Consultor buscar(@PathVariable Long id) {
        return service.buscarPorId(id);
    }

    @PutMapping("/{id}")
    public Consultor atualizar(
            @PathVariable Long id,
            @RequestBody Consultor consultor
    ) {
        return service.atualizar(id, consultor);
    }

    @DeleteMapping("/{id}")
    public void excluir(@PathVariable Long id) {
        service.excluir(id);
    }
}