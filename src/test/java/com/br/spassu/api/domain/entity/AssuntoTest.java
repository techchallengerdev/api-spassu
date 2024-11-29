package com.br.spassu.api.domain.entity;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.assertj.core.api.Assertions.assertThat;

class AssuntoTest {
    private Assunto assunto;
    private Livro livro;

    @BeforeEach
    void setUp() {
        assunto = new Assunto();
        assunto.setCodigo(1);
        assunto.setDescricao("Programação");

        livro = new Livro();
        livro.setCodigo(1);
        livro.setTitulo("Clean Code");
    }

    @Test
    void deveInicializarAssuntoCorretamente() {
        assertThat(assunto.getCodigo()).isEqualTo(1);
        assertThat(assunto.getDescricao()).isEqualTo("Programação");
        assertThat(assunto.getLivros()).isEmpty();
    }

    @Test
    void deveAdicionarLivroCorretamente() {
        assunto.adicionarLivro(livro);
        assertThat(assunto.getLivros()).hasSize(1);
        assertThat(assunto.getLivros()).contains(livro);
        assertThat(livro.getAssuntos()).contains(assunto);
    }

    @Test
    void deveRemoverLivroCorretamente() {
        assunto.adicionarLivro(livro);
        assunto.removerLivro(livro);
        assertThat(assunto.getLivros()).isEmpty();
        assertThat(livro.getAssuntos()).isEmpty();
    }
}
