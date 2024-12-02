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
public class AssuntoDTO {
    private Integer codigo;

    @NotBlank(message = "Descrição é obrigatória")
    @Size(max = 20, message = "Descrição deve ter no máximo 20 caracteres")
    private String descricao;

    private List<Integer> livros;
}