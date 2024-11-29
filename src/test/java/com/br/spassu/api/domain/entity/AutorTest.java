package com.br.spassu.api.domain.entity;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.assertj.core.api.Assertions.assertThat;

class AutorTest {
    private Autor autor;
    private Livro livro;

    @BeforeEach
    void setUp() {
        autor = new Autor();
        autor.setCodigo(1);
        autor.setNome("Robert C. Martin");

        livro = new Livro();
        livro.setCodigo(1);
        livro.setTitulo("Clean Code");
    }

    @Test
    void deveInicializarAutorCorretamente() {
        assertThat(autor.getCodigo()).isEqualTo(1);
        assertThat(autor.getNome()).isEqualTo("Robert C. Martin");
        assertThat(autor.getLivros()).isEmpty();
    }

    @Test
    void deveAdicionarLivroCorretamente() {
        autor.adicionarLivro(livro);
        assertThat(autor.getLivros()).hasSize(1);
        assertThat(autor.getLivros()).contains(livro);
        assertThat(livro.getAutores()).contains(autor);
    }

    @Test
    void deveRemoverLivroCorretamente() {
        autor.adicionarLivro(livro);
        autor.removerLivro(livro);
        assertThat(autor.getLivros()).isEmpty();
        assertThat(livro.getAutores()).isEmpty();
    }
}
