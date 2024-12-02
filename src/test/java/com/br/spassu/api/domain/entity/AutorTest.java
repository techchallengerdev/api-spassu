package com.br.spassu.api.domain.entity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AutorTest {
    private Autor autor;
    private Livro livro;

    @BeforeEach
    void setUp() {
        autor = Autor.builder()
                .codigo(1)
                .nome("Robert C. Martin")
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
            autor.adicionarLivro(livro);

            assertTrue(autor.getLivros().contains(livro));
            assertTrue(livro.getAutores().contains(autor));
            assertEquals(1, autor.getLivros().size());
            assertEquals(1, livro.getAutores().size());
        }

        @Test
        @DisplayName("Deve remover livro corretamente")
        void deveRemoverLivroComSucesso() {
            autor.adicionarLivro(livro);

            autor.removerLivro(livro);

            assertFalse(autor.getLivros().contains(livro));
            assertFalse(livro.getAutores().contains(autor));
            assertTrue(autor.getLivros().isEmpty());
            assertTrue(livro.getAutores().isEmpty());
        }
    }

    @Nested
    @DisplayName("Testes de construção")
    class ConstrucaoTests {

        @Test
        @DisplayName("Deve criar autor com builder corretamente")
        void deveCriarAutorComBuilder() {

            assertEquals(1, autor.getCodigo());
            assertEquals("Robert C. Martin", autor.getNome());
            assertNotNull(autor.getLivros());
            assertTrue(autor.getLivros().isEmpty());
        }
    }
}
