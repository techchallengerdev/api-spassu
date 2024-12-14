package com.br.spassu.api.domain.entity;

import com.br.spassu.api.domain.exceptions.BusinessException;
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
        @DisplayName("Deve remover autor quando houver mais de um")
        void deveRemoverAutorQuandoHouverMaisDeUm() {
            Livro livro = new Livro();
            Autor autor1 = new Autor();
            Autor autor2 = new Autor();
            livro.adicionarAutor(autor1);
            livro.adicionarAutor(autor2);

            livro.removerAutor(autor1);

            assertFalse(livro.getAutores().contains(autor1));
            assertTrue(livro.getAutores().contains(autor2));
            assertFalse(autor1.getLivros().contains(livro));
            assertTrue(autor2.getLivros().contains(livro));
        }

        @Test
        @DisplayName("Não deve remover o único autor do livro")
        void naoDeveRemoverUnicoAutor() {
            Livro livro = new Livro();
            Autor autor = new Autor();
            livro.adicionarAutor(autor);

            assertThrows(BusinessException.class, () -> livro.removerAutor(autor));
            assertTrue(livro.getAutores().contains(autor));
            assertTrue(autor.getLivros().contains(livro));
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
            Livro livro = new Livro();
            Assunto assunto1 = new Assunto();
            Assunto assunto2 = new Assunto();
            livro.adicionarAssunto(assunto1);
            livro.adicionarAssunto(assunto2);

            livro.removerAssunto(assunto1);

            assertFalse(livro.getAssuntos().contains(assunto1));
            assertTrue(livro.getAssuntos().contains(assunto2));
            assertFalse(assunto1.getLivros().contains(livro));
            assertTrue(assunto2.getLivros().contains(livro));
            assertEquals(1, livro.getAssuntos().size());
            assertEquals(1, assunto2.getLivros().size());
        }

        @Test
        @DisplayName("Não deve remover o único assunto do livro")
        void naoDeveRemoverUnicoAssunto() {
            Livro livro = new Livro();
            Assunto assunto = new Assunto();
            livro.adicionarAssunto(assunto);

            assertThrows(BusinessException.class, () -> livro.removerAssunto(assunto));
            assertTrue(livro.getAssuntos().contains(assunto));
            assertTrue(assunto.getLivros().contains(livro));
        }
    }
}