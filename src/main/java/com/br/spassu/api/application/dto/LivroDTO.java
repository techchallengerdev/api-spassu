package com.br.spassu.api.application.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class LivroDTO {

    @JsonProperty("codigo")
    private Integer codigo;

    @NotBlank(message = "Título é obrigatório")
    @Size(max = 40, message = "Título deve ter no máximo 40 caracteres")
    @JsonProperty("titulo")
    private String titulo;

    @Size(max = 40, message = "Editora deve ter no máximo 40 caracteres")
    @JsonProperty("editora")
    private String editora;

    @JsonProperty("edicao")
    private Integer edicao;

    @Size(max = 4, message = "Ano de publicação deve ter no máximo 4 caracteres")
    @JsonProperty("anoPublicacao")
    private String anoPublicacao;

    @JsonProperty("autorCodAus")
    private List<Integer> autorCodAus;

    @JsonProperty("assuntoCodAss")
    private List<Integer> assuntoCodAss;

    @JsonProperty("autores")
    private List<LivroAutorDTO> autores;

    @JsonProperty("assuntos")
    private List<LivroAssuntoDTO> assuntos;
}
