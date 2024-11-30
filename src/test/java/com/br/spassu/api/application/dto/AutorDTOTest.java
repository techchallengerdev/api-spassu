package com.br.spassu.api.application.dto;

import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashSet;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Testes para AutorDTO")
class AutorDTOTest {

    private Validator validator;

    @BeforeEach
    void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    @DisplayName("Deve criar AutorDTO válido")
    void deveCriarAutorDTOValido() {

        AutorDTO dto = new AutorDTO();
        dto.setId(1);
        dto.setNome("Robert C. Martin");
        dto.setLivros(new HashSet<>());

        var violations = validator.validate(dto);

        assertThat(violations).isEmpty();
    }

    @Test
    @DisplayName("Deve validar nome obrigatório")
    void deveValidarNomeObrigatorio() {

        AutorDTO dto = new AutorDTO();
        dto.setId(1);

        var violations = validator.validate(dto);

        assertThat(violations)
                .hasSize(1)
                .anySatisfy(violation -> {
                    assertThat(violation.getPropertyPath().toString()).isEqualTo("nome");
                    assertThat(violation.getMessage()).isEqualTo("Nome é obrigatório");
                });
    }
}
