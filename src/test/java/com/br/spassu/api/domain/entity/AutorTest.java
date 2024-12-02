package com.br.spassu.api.domain.entity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class AutorTest {
    private Autor autor;
    private Livro livro;

    @BeforeEach
    void setUp() {
        autor = new Autor();
        livro = new Livro();
    }

    @Nested
    @DisplayName("Testes de Builder")
    class BuilderTests {
        @Test
        @DisplayName("Deve criar autor usando builder")
        void deveCriarAutorBuilder() {
            autor = Autor.builder()
                    .codigo(1)
                    .nome("Robert C. Martin")
                    .livros(List.of(builderLivro()))
                    .build();

            assertEquals(1, autor.getCodigo());
            assertEquals("Robert C. Martin", autor.getNome());
            assertNotNull(autor.getLivros());
            assertFalse(autor.getLivros().isEmpty());
        }

        @Test
        @DisplayName("Deve criar autor com lista vazia quando não especificada")
        void deveCriarAutorComListaVazia() {
            autor = Autor.builder().build();
            assertNotNull(autor.getLivros());
            assertTrue(autor.getLivros().isEmpty());
        }

        @Test
        @DisplayName("Deve permitir setters")
        void devePermitirSetters() {
            autor.setCodigo(1);
            autor.setNome("Robert C. Martin");

            assertEquals(1, autor.getCodigo());
            assertEquals("Robert C. Martin", autor.getNome());
        }
    }

    @Nested
    @DisplayName("Testes de Relacionamentos")
    class RelacionamentoTests {
        @Test
        @DisplayName("Deve adicionar livro")
        void deveAdicionarLivro() {
            autor.adicionarLivro(livro);

            assertTrue(autor.getLivros().contains(livro));
            assertTrue(livro.getAutores().contains(autor));
            assertEquals(1, autor.getLivros().size());
            assertEquals(1, livro.getAutores().size());
        }

        @Test
        @DisplayName("Deve remover livro")
        void deveRemoverLivro() {
            autor.adicionarLivro(livro);
            autor.removerLivro(livro);

            assertFalse(autor.getLivros().contains(livro));
            assertFalse(livro.getAutores().contains(autor));
            assertTrue(autor.getLivros().isEmpty());
            assertTrue(livro.getAutores().isEmpty());
        }
    }

    private Livro builderLivro(){
        return livro = Livro.builder()
                .codigo(1)
                .titulo("Clean Code")
                .editora("Alta Books")
                .edicao(1)
                .anoPublicacao("2008")
                .build();
    }
}