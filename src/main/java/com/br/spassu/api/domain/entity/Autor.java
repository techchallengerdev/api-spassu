package com.br.spassu.api.domain.entity;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Autor {
    private Integer codigo;
    private String nome;
    @Builder.Default
    private List<Livro> livros = new ArrayList<>();

    public void adicionarLivro(Livro livro) {
        this.livros.add(livro);
        livro.getAutores().add(this);
    }

    public void removerLivro(Livro livro) {
        this.livros.remove(livro);
        livro.getAutores().remove(this);
    }
}
