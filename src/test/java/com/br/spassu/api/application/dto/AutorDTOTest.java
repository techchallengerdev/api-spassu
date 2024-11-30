package com.br.spassu.api.application.dto;

import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

class AutorDTOTest {
    private Validator validator;

    @BeforeEach
    void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void deveCriarAutorDTOValido() {
        // given
        AutorDTO autorDTO = new AutorDTO();
        autorDTO.setId(1);
        autorDTO.setNome("Robert C. Martin");
        autorDTO.setLivros(Set.of(
                criarLivroResumo(1, "Clean Code", "2008"),
                criarLivroResumo(2, "Clean Architecture", "2017")
        ));

        // when
        var violations = validator.validate(autorDTO);

        // then
        assertThat(violations).isEmpty();
        assertThat(autorDTO.getId()).isEqualTo(1);
        assertThat(autorDTO.getNome()).isEqualTo("Robert C. Martin");
        assertThat(autorDTO.getLivros()).hasSize(2);
    }

    @Test
    void deveValidarNomeObrigatorio() {
        // given
        AutorDTO autorDTO = new AutorDTO();
        autorDTO.setId(1);

        // when
        var violations = validator.validate(autorDTO);

        // then
        assertThat(violations).hasSize(1);
        assertThat(violations.iterator().next().getMessage())
                .isEqualTo("Nome é obrigatório");
    }

    private LivroResumoDTO criarLivroResumo(Integer id, String titulo, String ano) {
        LivroResumoDTO resumo = new LivroResumoDTO();
        resumo.setId(id);
        resumo.setTitulo(titulo);
        resumo.setAnoPublicacao(ano);
        return resumo;
    }
}
