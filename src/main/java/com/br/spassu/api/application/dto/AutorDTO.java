package com.br.spassu.api.application.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.Set;

@Data
public class AutorDTO {
    private Integer id;

    @NotBlank(message = "Nome é obrigatório")
    @Size(max = 40, message = "Nome deve ter no máximo 40 caracteres")
    private String nome;

    private Set<LivroResumoDTO> livros;
}
