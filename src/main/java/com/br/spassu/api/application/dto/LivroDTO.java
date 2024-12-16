package com.br.spassu.api.application.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.List;

@Data
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class LivroDTO {

    @JsonProperty("codigo")
    private Integer codigo;

    @NotBlank(message = "Título é obrigatório")
    @NotNull(message = "Título não pode ser nulo")
    @Size(max = 40, message = "Título deve ter no máximo 40 caracteres")
    @JsonProperty("titulo")
    private String titulo;

    @NotBlank(message = "Editora não informada, campo obrigatório")
    @Size(max = 40, message = "Editora deve ter no máximo 40 caracteres")
    @JsonProperty("editora")
    private String editora;

    @JsonProperty("edicao")
    private Integer edicao;

    @Size(max = 4, message = "Ano de publicação deve ter no máximo 4 caracteres")
    @JsonProperty("anoPublicacao")
    private String anoPublicacao;

    @JsonProperty("autorCodAus")
    @NotEmpty(message = "Lista de autores não informada, campo obrigatório")
    private List<Integer> autorCodAus;

    @JsonProperty("assuntoCodAss")
    @NotEmpty(message = "Lista de assuntos não informada, campo obrigatório")
    private List<Integer> assuntoCodAss;

    @JsonProperty("autores")
    private List<LivroAutorDTO> autores;

    @JsonProperty("assuntos")
    private List<LivroAssuntoDTO> assuntos;
}
