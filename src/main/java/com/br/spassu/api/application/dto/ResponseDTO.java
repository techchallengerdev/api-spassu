package com.br.spassu.api.application.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseDTO {
    @JsonProperty("message")
    private String message;
    @JsonProperty("livros")
    private String livros;

    public ResponseDTO(String message, String livros) {
        this.message = message;
        this.livros = livros;
    }
}
