package com.br.spassu.api.application.dto;

import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

class AssuntoDTOTest {
    private Validator validator;

    @BeforeEach
    void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void deveCriarAssuntoDTOValido() {
        AssuntoDTO assuntoDTO = new AssuntoDTO();
        assuntoDTO.setId(1);
        assuntoDTO.setDescricao("Programação");
        assuntoDTO.setLivros(Set.of(
                criarLivroResumo(1, "Clean Code", "2008")
        ));

        var violations = validator.validate(assuntoDTO);

        assertThat(violations).isEmpty();
        assertThat(assuntoDTO.getId()).isEqualTo(1);
        assertThat(assuntoDTO.getDescricao()).isEqualTo("Programação");
        assertThat(assuntoDTO.getLivros()).hasSize(1);
    }

    @Test
    void deveValidarDescricaoObrigatoria() {

        AssuntoDTO assuntoDTO = new AssuntoDTO();
        assuntoDTO.setId(1);

        var violations = validator.validate(assuntoDTO);

        assertThat(violations).hasSize(1);
        assertThat(violations.iterator().next().getMessage())
                .isEqualTo("Descrição é obrigatória");
    }

    private LivroResumoDTO criarLivroResumo(Integer id, String titulo, String ano) {
        LivroResumoDTO resumo = new LivroResumoDTO();
        resumo.setId(id);
        resumo.setTitulo(titulo);
        resumo.setAnoPublicacao(ano);
        return resumo;
    }
}
