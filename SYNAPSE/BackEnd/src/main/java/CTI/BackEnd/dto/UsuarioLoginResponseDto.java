package CTI.BackEnd.dto;

public class UsuarioLoginResponseDto {

    private final Long id;
    private final String nome;
    private final String email;
    private final String token;
    private final String perfil;
    private final String status;

    public UsuarioLoginResponseDto(Long id, String nome, String email, String token,
                                   String perfil, String status) {
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.token = token;
        this.perfil = perfil;
        this.status = status;
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

    public String getPerfil() {
        return perfil;
    }

    public String getStatus() {
        return status;
    }
}
