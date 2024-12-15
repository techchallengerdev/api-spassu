package com.br.spassu.api.infrastructure.exception;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class JsonErrorHandler {
    private static final String TYPE_JSON_ERROR = "https://api.spassu.com.br/errors/json-invalid";
    private static final String TITLE_JSON_ERROR = "JSON Inválido";

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ApiError> handleHttpMessageNotReadable(HttpMessageNotReadableException ex) {
        String detail = "JSON enviado está mal formatado";

        Throwable cause = ex.getCause();
        if (cause instanceof JsonParseException) {
            detail = "Erro de sintaxe no JSON: " + cause.getMessage();
        } else if (cause instanceof JsonMappingException) {
            detail = "Erro ao mapear JSON para objeto: " + cause.getMessage();
        }

        ApiError error = ApiError.builder()
                .type(TYPE_JSON_ERROR)
                .title(TITLE_JSON_ERROR)
                .detail(detail)
                .status(HttpStatus.BAD_REQUEST.value())
                .timestamp(LocalDateTime.now())
                .build();

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(error);
    }
}
