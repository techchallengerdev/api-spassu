package com.br.spassu.api.domain.entity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AssuntoTest {
    private Assunto assunto;
    private Livro livro;

    @BeforeEach
    void setUp() {
        assunto = Assunto.builder()
                .codigo(1)
                .descricao("Programação")
                .build();

        livro = Livro.builder()
                .codigo(1)
                .titulo("Clean Code")
                .editora("Alta Books")
                .edicao(1)
                .anoPublicacao("2008")
                .build();
    }

    @Nested
    @DisplayName("Testes de relacionamento com Livro")
    class RelacionamentoLivroTests {

        @Test
        @DisplayName("Deve adicionar livro corretamente")
        void deveAdicionarLivroComSucesso() {
            // Act
            assunto.adicionarLivro(livro);

            // Assert
            assertTrue(assunto.getLivros().contains(livro));
            assertTrue(livro.getAssuntos().contains(assunto));
            assertEquals(1, assunto.getLivros().size());
            assertEquals(1, livro.getAssuntos().size());
        }

        @Test
        @DisplayName("Deve remover livro corretamente")
        void deveRemoverLivroComSucesso() {
            // Arrange
            assunto.adicionarLivro(livro);

            // Act
            assunto.removerLivro(livro);

            // Assert
            assertFalse(assunto.getLivros().contains(livro));
            assertFalse(livro.getAssuntos().contains(assunto));
            assertTrue(assunto.getLivros().isEmpty());
            assertTrue(livro.getAssuntos().isEmpty());
        }
    }

    @Nested
    @DisplayName("Testes de construção")
    class ConstrucaoTests {

        @Test
        @DisplayName("Deve criar assunto com builder corretamente")
        void deveCriarAssuntoComBuilder() {
            // Assert
            assertEquals(1, assunto.getCodigo());
            assertEquals("Programação", assunto.getDescricao());
            assertNotNull(assunto.getLivros());
            assertTrue(assunto.getLivros().isEmpty());
        }
    }
}
