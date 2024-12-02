package com.br.spassu.api.domain.entity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class AssuntoTest {
    private Assunto assunto;
    private Livro livro;

    @BeforeEach
    void setUp() {
        assunto = new Assunto();
        livro = new Livro();
    }

    @Nested
    @DisplayName("Testes de Builder")
    class BuilderTests {
        @Test
        @DisplayName("Deve criar assunto usando builder")
        void deveCriarAssuntoBuilder() {
            assunto = Assunto.builder()
                    .codigo(1)
                    .descricao("Programação")
                    .livros(List.of(builderLivro()))
                    .build();

            assertEquals(1, assunto.getCodigo());
            assertEquals("Programação", assunto.getDescricao());
            assertNotNull(assunto.getLivros());
            assertFalse(assunto.getLivros().isEmpty());
        }

        @Test
        @DisplayName("Deve criar assunto com lista vazia quando não especificada")
        void deveCriarAssuntoComListaVazia() {
            assunto = Assunto.builder().build();
            assertNotNull(assunto.getLivros());
            assertTrue(assunto.getLivros().isEmpty());
        }

        @Test
        @DisplayName("Deve permitir setters")
        void devePermitirSetters() {
            assunto.setCodigo(1);
            assunto.setDescricao("Programação");

            assertEquals(1, assunto.getCodigo());
            assertEquals("Programação", assunto.getDescricao());
        }
    }

    @Nested
    @DisplayName("Testes de Relacionamentos")
    class RelacionamentoTests {
        @Test
        @DisplayName("Deve adicionar livro")
        void deveAdicionarLivro() {
            assunto.adicionarLivro(livro);

            assertTrue(assunto.getLivros().contains(livro));
            assertTrue(livro.getAssuntos().contains(assunto));
            assertEquals(1, assunto.getLivros().size());
            assertEquals(1, livro.getAssuntos().size());
        }

        @Test
        @DisplayName("Deve remover livro")
        void deveRemoverLivro() {
            assunto.adicionarLivro(livro);
            assunto.removerLivro(livro);

            assertFalse(assunto.getLivros().contains(livro));
            assertFalse(livro.getAssuntos().contains(assunto));
            assertTrue(assunto.getLivros().isEmpty());
            assertTrue(livro.getAssuntos().isEmpty());
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