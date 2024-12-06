package com.br.spassu.api.application.dto;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LivroAutorDTO {
    private Integer codigo;
    private String nome;
}
