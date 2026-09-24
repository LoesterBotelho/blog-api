package com.api.blog_api.exception;

import java.time.Instant;
import java.util.stream.Collectors;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(RegistroNaoEncontradoException.class)
    public ResponseEntity<ErroResponseDto> tratarRegistroNaoEncontrado(
            RegistroNaoEncontradoException ex) {

        ErroResponseDto erro = new ErroResponseDto(
                HttpStatus.NOT_FOUND.value(),
                HttpStatus.NOT_FOUND.getReasonPhrase(),
                ex.getMessage(),
                Instant.now()
        );

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(erro);
    }

    @ExceptionHandler(RegraDeNegocioException.class)
    public ResponseEntity<ErroResponseDto> tratarRegraDeNegocio(
            RegraDeNegocioException ex) {

        ErroResponseDto erro = new ErroResponseDto(
                HttpStatus.BAD_REQUEST.value(),
                HttpStatus.BAD_REQUEST.getReasonPhrase(),
                ex.getMessage(),
                Instant.now()
        );

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(erro);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErroResponseDto> tratarErroValidacao(
            MethodArgumentNotValidException ex) {

        String mensagem = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(erro -> erro.getField() + ": " + erro.getDefaultMessage())
                .collect(Collectors.joining("; "));

        if (mensagem.isEmpty()) {
            mensagem = "Erro de validação de campos";
        }

        ErroResponseDto erro = new ErroResponseDto(
                HttpStatus.BAD_REQUEST.value(),
                HttpStatus.BAD_REQUEST.getReasonPhrase(),
                mensagem,
                Instant.now()
        );

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(erro);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErroResponseDto> tratarErroJsonInvalido(
            HttpMessageNotReadableException ex) {

        ErroResponseDto erro = new ErroResponseDto(
                HttpStatus.BAD_REQUEST.value(),
                HttpStatus.BAD_REQUEST.getReasonPhrase(),
                "O corpo da requisição contém um JSON inválido ou campos com tipos incompatíveis.",
                Instant.now()
        );

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(erro);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ErroResponseDto> tratarErroTipoParametro(
            MethodArgumentTypeMismatchException ex) {

        String mensagem = String.format("O parâmetro '%s' recebeu o valor '%s', que é inválido.", 
                ex.getName(), ex.getValue());

        ErroResponseDto erro = new ErroResponseDto(
                HttpStatus.BAD_REQUEST.value(),
                HttpStatus.BAD_REQUEST.getReasonPhrase(),
                mensagem,
                Instant.now()
        );

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(erro);
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<ErroResponseDto> tratarMetodoNaoSuportado(
            HttpRequestMethodNotSupportedException ex) {

        String mensagem = String.format("O método HTTP '%s' não é suportado para este endpoint.", 
                ex.getMethod());

        ErroResponseDto erro = new ErroResponseDto(
                HttpStatus.METHOD_NOT_ALLOWED.value(),
                HttpStatus.METHOD_NOT_ALLOWED.getReasonPhrase(),
                mensagem,
                Instant.now()
        );

        return ResponseEntity
                .status(HttpStatus.METHOD_NOT_ALLOWED)
                .body(erro);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ErroResponseDto> tratarViolacaoIntegridade(
            DataIntegrityViolationException ex) {

        ErroResponseDto erro = new ErroResponseDto(
                HttpStatus.CONFLICT.value(),
                HttpStatus.CONFLICT.getReasonPhrase(),
                "Ocorreu um conflito ao tentar salvar os dados. Registro já existente ou restrição violada.",
                Instant.now()
        );

        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(erro);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErroResponseDto> tratarErroInesperado(
            Exception ex) {

        ErroResponseDto erro = new ErroResponseDto(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(),
                "Ocorreu um erro interno no servidor.",
                Instant.now()
        );

        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(erro);
    }
}