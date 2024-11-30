package com.br.spassu.api.application.dto;

import lombok.Data;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.Set;

@Data
public class AssuntoDTO {
    private Integer id;

    @NotBlank(message = "Descrição é obrigatória")
    @Size(max = 20, message = "Descrição deve ter no máximo 20 caracteres")
    private String descricao;

    private Set<LivroResumoDTO> livros;
}