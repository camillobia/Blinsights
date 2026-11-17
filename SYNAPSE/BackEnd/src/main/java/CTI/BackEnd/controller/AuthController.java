package CTI.BackEnd.controller;

import CTI.BackEnd.dto.UsuarioLoginDto;
import CTI.BackEnd.dto.UsuarioLoginResponseDto;
import CTI.BackEnd.service.AuthService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/usuarios")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<UsuarioLoginResponseDto> login(@RequestBody UsuarioLoginDto dto) {
        return authService.login(dto.getEmail(), dto.getSenha())
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.UNAUTHORIZED).build());
    }
}