package CTI.BackEnd.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.method.annotation.HandlerMethodValidationException;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class ErroHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> tratarErros(
            MethodArgumentNotValidException ex) {

        Map<String, String> erros = new HashMap<>();

        ex.getBindingResult()
                .getFieldErrors()
                .forEach(erro ->
                        erros.put(
                                erro.getField(),
                                erro.getDefaultMessage()
                        )
                );

        return ResponseEntity.badRequest().body(erros);
    }

    @ExceptionHandler(HandlerMethodValidationException.class)
    public ResponseEntity<Map<String, String>> tratarErrosValidacao(
            HandlerMethodValidationException ex) {

        Map<String, String> erros = new HashMap<>();

        ex.getBeanResults()
                .forEach(resultado ->
                        resultado.getFieldErrors()
                                .forEach(erro ->
                                        erros.put(
                                                erro.getField(),
                                                erro.getDefaultMessage()
                                        )
                                )
                );

        return ResponseEntity.badRequest().body(erros);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<Map<String, String>> tratarJsonInvalido(
            HttpMessageNotReadableException ex) {

        Map<String, String> erro = new HashMap<>();
        erro.put("mensagem", "Dados inválidos");
        erro.put("erro", "JSON inválido ou data fora do formato ISO (AAAA-MM-DD).");
        return ResponseEntity.badRequest().body(erro);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Map<String, String>> tratarErroRegra(
            IllegalArgumentException ex) {

        Map<String, String> erro = new HashMap<>();

        erro.put("erro", ex.getMessage());

        return ResponseEntity.badRequest().body(erro);
    }
}