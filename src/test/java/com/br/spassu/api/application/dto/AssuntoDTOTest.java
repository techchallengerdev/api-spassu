package com.br.spassu.api.application.dto;

import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class AssuntoDTOTest {

    private static Validator validator;

    @BeforeAll
    static void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Nested
    @DisplayName("Testes de Builder")
    class BuilderTests {
        @Test
        @DisplayName("Deve criar AssuntoDTO completo")
        void deveCriarAssuntoDTOCompleto() {
            AssuntoDTO dto = AssuntoDTO.builder()
                    .codigo(1)
                    .descricao("Programação")
                    .livros(List.of(1))
                    .build();

            assertNotNull(dto);
            assertEquals(1, dto.getCodigo());
            assertEquals("Programação", dto.getDescricao());
            assertEquals(1, dto.getLivros().get(0));
        }
    }

    @Nested
    @DisplayName("Testes de Validação")
    class ValidacaoTests {
        @Test
        @DisplayName("Deve validar descrição obrigatória")
        void deveValidarDescricaoObrigatoria() {
            AssuntoDTO dto = AssuntoDTO.builder().build();
            var violations = validator.validate(dto);
            assertFalse(violations.isEmpty());
            assertTrue(violations.stream()
                    .anyMatch(v -> v.getMessage().equals("Descrição é obrigatória")));
        }

        @Test
        @DisplayName("Deve validar tamanho máximo da descrição")
        void deveValidarTamanhoMaximoDescricao() {
            AssuntoDTO dto = AssuntoDTO.builder()
                    .descricao("A".repeat(21))
                    .build();
            var violations = validator.validate(dto);
            assertTrue(violations.stream()
                    .anyMatch(v -> v.getMessage().equals("Descrição deve ter no máximo 20 caracteres")));
        }
    }
}
