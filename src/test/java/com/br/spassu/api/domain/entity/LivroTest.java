package com.br.spassu.api.domain.entity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LivroTest {
    private Livro livro;
    private Autor autor;
    private Assunto assunto;

    @BeforeEach
    void setUp() {
        livro = Livro.builder()
                .codigo(1)
                .titulo("Clean Code")
                .editora("Alta Books")
                .edicao(1)
                .anoPublicacao("2008")
                .build();

        autor = Autor.builder()
                .codigo(1)
                .nome("Robert C. Martin")
                .build();

        assunto = Assunto.builder()
                .codigo(1)
                .descricao("Programação")
                .build();
    }

    @Nested
    @DisplayName("Testes de relacionamento com Autor")
    class RelacionamentoAutorTests {

        @Test
        @DisplayName("Deve adicionar autor corretamente")
        void deveAdicionarAutorComSucesso() {
            // Act
            livro.adicionarAutor(autor);

            // Assert
            assertTrue(livro.getAutores().contains(autor));
            assertTrue(autor.getLivros().contains(livro));
            assertEquals(1, livro.getAutores().size());
            assertEquals(1, autor.getLivros().size());
        }

        @Test
        @DisplayName("Deve remover autor corretamente")
        void deveRemoverAutorComSucesso() {
            // Arrange
            livro.adicionarAutor(autor);

            // Act
            livro.removerAutor(autor);

            // Assert
            assertFalse(livro.getAutores().contains(autor));
            assertFalse(autor.getLivros().contains(livro));
            assertTrue(livro.getAutores().isEmpty());
            assertTrue(autor.getLivros().isEmpty());
        }
    }

    @Nested
    @DisplayName("Testes de relacionamento com Assunto")
    class RelacionamentoAssuntoTests {

        @Test
        @DisplayName("Deve adicionar assunto corretamente")
        void deveAdicionarAssuntoComSucesso() {
            // Act
            livro.adicionarAssunto(assunto);

            // Assert
            assertTrue(livro.getAssuntos().contains(assunto));
            assertTrue(assunto.getLivros().contains(livro));
            assertEquals(1, livro.getAssuntos().size());
            assertEquals(1, assunto.getLivros().size());
        }

        @Test
        @DisplayName("Deve remover assunto corretamente")
        void deveRemoverAssuntoComSucesso() {
            // Arrange
            livro.adicionarAssunto(assunto);

            // Act
            livro.removerAssunto(assunto);

            // Assert
            assertFalse(livro.getAssuntos().contains(assunto));
            assertFalse(assunto.getLivros().contains(livro));
            assertTrue(livro.getAssuntos().isEmpty());
            assertTrue(assunto.getLivros().isEmpty());
        }
    }

    @Nested
    @DisplayName("Testes de construção")
    class ConstrucaoTests {

        @Test
        @DisplayName("Deve criar livro com builder corretamente")
        void deveCriarLivroComBuilder() {
            // Assert
            assertEquals(1, livro.getCodigo());
            assertEquals("Clean Code", livro.getTitulo());
            assertEquals("Alta Books", livro.getEditora());
            assertEquals(1, livro.getEdicao());
            assertEquals("2008", livro.getAnoPublicacao());
            assertNotNull(livro.getAutores());
            assertNotNull(livro.getAssuntos());
            assertTrue(livro.getAutores().isEmpty());
            assertTrue(livro.getAssuntos().isEmpty());
        }
    }
}