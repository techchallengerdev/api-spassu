package com.br.spassu.api.application.dto;

import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.*;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class LivroDTOTest {
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
        @DisplayName("Deve criar LivroDTO completo")
        void deveCriarLivroDTOCompleto() {
            LivroDTO dto = LivroDTO.builder()
                    .codigo(1)
                    .titulo("Clean Code")
                    .editora("Alta Books")
                    .edicao(1)
                    .anoPublicacao("2008")
                    .autorCodAus(List.of(1))
                    .assuntoCodAss(List.of(1))
                    .build();

            assertNotNull(dto);
            assertEquals(1, dto.getCodigo());
            assertEquals("Clean Code", dto.getTitulo());
            assertEquals("Alta Books", dto.getEditora());
            assertEquals(1, dto.getEdicao());
            assertEquals("2008", dto.getAnoPublicacao());
            assertEquals(1, dto.getAutorCodAus().get(0));
            assertEquals(1, dto.getAssuntoCodAss().get(0));
        }

        @Test
        @DisplayName("Deve criar LivroDTO vazio")
        void deveCriarLivroDTOVazio() {
            LivroDTO dto = new LivroDTO();
            assertNotNull(dto);
        }
    }

    @Nested
    @DisplayName("Testes de Validação")
    class ValidacaoTests {
        @Test
        @DisplayName("Deve validar título obrigatório")
        void deveValidarTituloObrigatorio() {
            LivroDTO dto = LivroDTO.builder().build();
            var violations = validator.validate(dto);
            assertFalse(violations.isEmpty());
            assertTrue(violations.stream()
                    .anyMatch(v -> v.getMessage().equals("Título é obrigatório")));
        }

        @Test
        @DisplayName("Deve validar tamanho máximo do título")
        void deveValidarTamanhoMaximoTitulo() {
            LivroDTO dto = LivroDTO.builder()
                    .titulo("A".repeat(41))
                    .build();
            var violations = validator.validate(dto);
            assertTrue(violations.stream()
                    .anyMatch(v -> v.getMessage().equals("Título deve ter no máximo 40 caracteres")));
        }

        @Test
        @DisplayName("Deve validar tamanho máximo da editora")
        void deveValidarTamanhoMaximoEditora() {
            LivroDTO dto = LivroDTO.builder()
                    .titulo("Clean Code")
                    .editora("A".repeat(41))
                    .build();
            var violations = validator.validate(dto);
            assertTrue(violations.stream()
                    .anyMatch(v -> v.getMessage().equals("Editora deve ter no máximo 40 caracteres")));
        }
    }
}
