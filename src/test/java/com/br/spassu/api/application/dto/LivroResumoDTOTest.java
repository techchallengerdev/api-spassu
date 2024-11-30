package com.br.spassu.api.application.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

class LivroResumoDTOTest {

    @Test
    void deveCriarLivroResumoDTOValido() {

        LivroResumoDTO resumoDTO = new LivroResumoDTO();
        resumoDTO.setId(1);
        resumoDTO.setTitulo("Clean Code");
        resumoDTO.setAnoPublicacao("2008");

        assertThat(resumoDTO.getId()).isEqualTo(1);
        assertThat(resumoDTO.getTitulo()).isEqualTo("Clean Code");
        assertThat(resumoDTO.getAnoPublicacao()).isEqualTo("2008");
    }
}
