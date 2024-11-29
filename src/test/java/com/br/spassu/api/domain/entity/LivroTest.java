package com.br.spassu.api.domain.entity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

class LivroTest {
    private Livro livro;
    private Autor autor;
    private Assunto assunto;

    @BeforeEach
    void setUp() {
        livro = new Livro();
        livro.setCodigo(1);
        livro.setTitulo("Clean Code");
        livro.setEditora("Alta Books");
        livro.setEdicao(1);
        livro.setAnoPublicacao("2008");

        autor = new Autor();
        autor.setCodigo(1);
        autor.setNome("Robert C. Martin");

        assunto = new Assunto();
        assunto.setCodigo(1);
        assunto.setDescricao("Programação");
    }

    @Test
    void deveInicializarLivroCorretamente() {
        assertThat(livro.getCodigo()).isEqualTo(1);
        assertThat(livro.getTitulo()).isEqualTo("Clean Code");
        assertThat(livro.getEditora()).isEqualTo("Alta Books");
        assertThat(livro.getEdicao()).isEqualTo(1);
        assertThat(livro.getAnoPublicacao()).isEqualTo("2008");
        assertThat(livro.getAutores()).isEmpty();
        assertThat(livro.getAssuntos()).isEmpty();
    }

    @Test
    void deveAdicionarAutorCorretamente() {
        livro.adicionarAutor(autor);
        assertThat(livro.getAutores()).hasSize(1);
        assertThat(livro.getAutores()).contains(autor);
        assertThat(autor.getLivros()).contains(livro);
    }

    @Test
    void deveRemoverAutorCorretamente() {
        livro.adicionarAutor(autor);
        livro.removerAutor(autor);
        assertThat(livro.getAutores()).isEmpty();
        assertThat(autor.getLivros()).isEmpty();
    }

    @Test
    void deveAdicionarAssuntoCorretamente() {
        livro.adicionarAssunto(assunto);
        assertThat(livro.getAssuntos()).hasSize(1);
        assertThat(livro.getAssuntos()).contains(assunto);
        assertThat(assunto.getLivros()).contains(livro);
    }

    @Test
    void deveRemoverAssuntoCorretamente() {
        livro.adicionarAssunto(assunto);
        livro.removerAssunto(assunto);
        assertThat(livro.getAssuntos()).isEmpty();
        assertThat(assunto.getLivros()).isEmpty();
    }
}
