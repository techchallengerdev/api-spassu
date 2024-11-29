package com.br.spassu.api.domain.entity;

import lombok.Getter;
import lombok.Setter;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
public class Autor {
    private Integer codigo;
    private String nome;
    private Set<Livro> livros = new HashSet<>();

    public void adicionarLivro(Livro livro) {
        this.livros.add(livro);
        livro.getAutores().add(this);
    }

    public void removerLivro(Livro livro) {
        this.livros.remove(livro);
        livro.getAutores().remove(this);
    }
}
