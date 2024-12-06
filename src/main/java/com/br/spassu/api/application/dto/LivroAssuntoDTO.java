package com.br.spassu.api.application.dto;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LivroAssuntoDTO {
    private Integer codigo;
    private String descricao;
}
