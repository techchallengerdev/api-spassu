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

public class AutorDTOTest {
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
        @DisplayName("Deve criar AutorDTO completo")
        void deveCriarAutorDTOCompleto() {
            AutorDTO dto = AutorDTO.builder()
                    .codigo(1)
                    .nome("Robert C. Martin")
                    .livros(List.of(1))
                    .build();

            assertNotNull(dto);
            assertEquals(1, dto.getCodigo());
            assertEquals("Robert C. Martin", dto.getNome());
            assertEquals(1, dto.getLivros().get(0));
        }
    }

    @Nested
    @DisplayName("Testes de Validação")
    class ValidacaoTests {
        @Test
        @DisplayName("Deve validar nome obrigatório")
        void deveValidarNomeObrigatorio() {
            AutorDTO dto = AutorDTO.builder().build();
            var violations = validator.validate(dto);
            assertFalse(violations.isEmpty());
            assertTrue(violations.stream()
                    .anyMatch(v -> v.getMessage().equals("Nome é obrigatório")));
        }

        @Test
        @DisplayName("Deve validar tamanho máximo do nome")
        void deveValidarTamanhoMaximoNome() {
            AutorDTO dto = AutorDTO.builder()
                    .nome("A".repeat(41))
                    .build();
            var violations = validator.validate(dto);
            assertTrue(violations.stream()
                    .anyMatch(v -> v.getMessage().equals("Nome deve ter no máximo 40 caracteres")));
        }
    }
}
