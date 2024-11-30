package com.br.spassu.api.application.dto;
import jakarta.validation.constraints.Size;
import lombok.Data;
import jakarta.validation.constraints.NotBlank;
import java.util.Set;

@Data
public class LivroDTO {
    private Integer id;

    @NotBlank(message = "Título é obrigatório")
    @Size(max = 40, message = "Título deve ter no máximo 40 caracteres")
    private String titulo;

    @Size(max = 40, message = "Editora deve ter no máximo 40 caracteres")
    private String editora;

    private Integer numeroEdicao;

    @Size(max = 4, message = "Ano de publicação deve ter no máximo 4 caracteres")
    private String anoPublicacao;

    private Set<Integer> idsAutores;
    private Set<Integer> idsAssuntos;
}
