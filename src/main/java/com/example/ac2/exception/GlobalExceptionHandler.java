package com.example.ac2.exception;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<?> tratarNaoEncontrado(
            ResourceNotFoundException ex) {

        return resposta(HttpStatus.NOT_FOUND, ex.getMessage());
    }

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<?> tratarRegraNegocio(
            BusinessException ex) {

        return resposta(HttpStatus.BAD_REQUEST, ex.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> tratarValidacao(
            MethodArgumentNotValidException ex) {

        String erros = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(e -> e.getField() + ": " + e.getDefaultMessage())
                .collect(Collectors.joining("; "));

        return resposta(HttpStatus.BAD_REQUEST, erros);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<?> tratarIntegridade(
            DataIntegrityViolationException ex) {

        return resposta(
                HttpStatus.CONFLICT,
                "Não foi possível excluir ou cadastrar o registro. "
                + "Verifique se existem dados relacionados ou duplicados."
        );
    }

    private ResponseEntity<Map<String, Object>> resposta(
            HttpStatus status, String mensagem) {

        Map<String, Object> corpo = Map.of(
                "data", LocalDateTime.now(),
                "status", status.value(),
                "erro", status.getReasonPhrase(),
                "mensagem", mensagem
        );

        return ResponseEntity.status(status).body(corpo);
    }
}
