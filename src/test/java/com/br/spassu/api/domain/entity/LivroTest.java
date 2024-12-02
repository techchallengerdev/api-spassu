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
        livro = new Livro();
        autor = new Autor();
        assunto = new Assunto();
    }

    @Nested
    @DisplayName("Testes de Builder")
    class BuilderTests {
        @Test
        @DisplayName("Deve criar livro usando builder")
        void deveCriarLivroBuilder() {
            livro = Livro.builder()
                    .codigo(1)
                    .titulo("Clean Code")
                    .editora("Alta Books")
                    .edicao(1)
                    .anoPublicacao("2008")
                    .build();

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

        @Test
        @DisplayName("Deve criar livro com listas vazias quando não especificadas")
        void deveCriarLivroComListasVazias() {
            livro = Livro.builder().build();
            assertNotNull(livro.getAutores());
            assertNotNull(livro.getAssuntos());
            assertTrue(livro.getAutores().isEmpty());
            assertTrue(livro.getAssuntos().isEmpty());
        }

        @Test
        @DisplayName("Deve permitir setters")
        void devePermitirSetters() {
            livro.setCodigo(1);
            livro.setTitulo("Clean Code");
            livro.setEditora("Alta Books");
            livro.setEdicao(1);
            livro.setAnoPublicacao("2008");

            assertEquals(1, livro.getCodigo());
            assertEquals("Clean Code", livro.getTitulo());
            assertEquals("Alta Books", livro.getEditora());
            assertEquals(1, livro.getEdicao());
            assertEquals("2008", livro.getAnoPublicacao());
        }
    }

    @Nested
    @DisplayName("Testes de Relacionamentos")
    class RelacionamentoTests {
        @Test
        @DisplayName("Deve adicionar autor")
        void deveAdicionarAutor() {
            livro.adicionarAutor(autor);

            assertTrue(livro.getAutores().contains(autor));
            assertTrue(autor.getLivros().contains(livro));
            assertEquals(1, livro.getAutores().size());
            assertEquals(1, autor.getLivros().size());
        }

        @Test
        @DisplayName("Deve remover autor")
        void deveRemoverAutor() {
            livro.adicionarAutor(autor);
            livro.removerAutor(autor);

            assertFalse(livro.getAutores().contains(autor));
            assertFalse(autor.getLivros().contains(livro));
            assertTrue(livro.getAutores().isEmpty());
            assertTrue(autor.getLivros().isEmpty());
        }

        @Test
        @DisplayName("Deve adicionar assunto")
        void deveAdicionarAssunto() {
            livro.adicionarAssunto(assunto);

            assertTrue(livro.getAssuntos().contains(assunto));
            assertTrue(assunto.getLivros().contains(livro));
            assertEquals(1, livro.getAssuntos().size());
            assertEquals(1, assunto.getLivros().size());
        }

        @Test
        @DisplayName("Deve remover assunto")
        void deveRemoverAssunto() {
            livro.adicionarAssunto(assunto);
            livro.removerAssunto(assunto);

            assertFalse(livro.getAssuntos().contains(assunto));
            assertFalse(assunto.getLivros().contains(livro));
            assertTrue(livro.getAssuntos().isEmpty());
            assertTrue(assunto.getLivros().isEmpty());
        }
    }
}