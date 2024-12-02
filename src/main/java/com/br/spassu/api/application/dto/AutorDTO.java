package com.br.spassu.api.application.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Data
@Builder
public class AutorDTO {
    private Integer codigo;

    @NotBlank(message = "Nome é obrigatório")
    @Size(max = 40, message = "Nome deve ter no máximo 40 caracteres")
    private String nome;

    private List<Integer> livros;
}
