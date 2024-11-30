package com.br.spassu.api.application.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

class LivroAutorDTOTest {

    @Test
    void deveCriarLivroAutorDTOValido() {

        LivroAutorDTO livroAutorDTO = new LivroAutorDTO();
        livroAutorDTO.setLivro_Cod(1);
        livroAutorDTO.setAutor_CodAu(1);

        assertThat(livroAutorDTO.getLivro_Cod()).isEqualTo(1);
        assertThat(livroAutorDTO.getAutor_CodAu()).isEqualTo(1);
    }
}
