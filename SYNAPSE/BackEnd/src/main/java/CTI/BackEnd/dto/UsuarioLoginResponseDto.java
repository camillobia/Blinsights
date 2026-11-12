package CTI.BackEnd.dto;

public class UsuarioLoginResponseDto {

    private final Long id;
    private final String nome;
    private final String email;
    private final String token;

    public UsuarioLoginResponseDto(Long id, String nome, String email, String token) {
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.token = token;
    }

    public Long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getEmail() {
        return email;
    }

    public String getToken() {
        return token;
    }
}
