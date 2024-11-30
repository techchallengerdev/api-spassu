package com.br.spassu.api.application.dto;

import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import static org.assertj.core.api.Assertions.assertThat;


@DisplayName("Testes para AssuntoDTO")
class AssuntoDTOTest {

    private Validator validator;

    @BeforeEach
    void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    @DisplayName("Deve criar AssuntoDTO válido")
    void deveCriarAssuntoDTOValido() {
        AssuntoDTO dto = new AssuntoDTO();
        dto.setId(1);
        dto.setDescricao("Programação");
        dto.setLivros(new HashSet<>());

        var violations = validator.validate(dto);

        assertThat(violations).isEmpty();
    }

    @Test
    @DisplayName("Deve validar descrição obrigatória")
    void deveValidarDescricaoObrigatoria() {
        // given
        AssuntoDTO dto = new AssuntoDTO();
        dto.setId(1);

        // when
        var violations = validator.validate(dto);

        // then
        assertThat(violations)
                .hasSize(1)
                .anySatisfy(violation -> {
                    assertThat(violation.getPropertyPath().toString()).isEqualTo("descricao");
                    assertThat(violation.getMessage()).isEqualTo("Descrição é obrigatória");
                });
    }

    @Test
    @DisplayName("Deve validar tamanho máximo da descrição")
    void deveValidarTamanhoMaximoDescricao() {
        // given
        AssuntoDTO dto = new AssuntoDTO();
        dto.setId(1);
        dto.setDescricao("a".repeat(21)); // 21 caracteres

        // when
        var violations = validator.validate(dto);

        // then
        assertThat(violations)
                .hasSize(1)
                .anySatisfy(violation -> {
                    assertThat(violation.getPropertyPath().toString()).isEqualTo("descricao");
                    assertThat(violation.getMessage()).isEqualTo("Descrição deve ter no máximo 20 caracteres");
                });
    }
}