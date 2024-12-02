package com.br.spassu.api.application.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LivroDTO {
    private Integer codigo;

    @NotBlank(message = "Título é obrigatório")
    @Size(max = 40, message = "Título deve ter no máximo 40 caracteres")
    private String titulo;

    @Size(max = 40, message = "Editora deve ter no máximo 40 caracteres")
    private String editora;

    private Integer edicao;

    @Size(max = 4, message = "Ano de publicação deve ter no máximo 4 caracteres")
    private String anoPublicacao;

    private List<Integer> autorCodAus;
    private List<Integer> assuntoCodAss;
}
