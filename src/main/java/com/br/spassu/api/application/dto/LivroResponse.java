package com.br.spassu.api.application.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LivroResponse {
    private Integer codigo;
    private String titulo;
    private String editora;
    private Integer edicao;
    private String anoPublicacao;
    private List<AutorResponse> autores;
    private List<AssuntoResponse> assuntos;
}