package com.br.spassu.api.domain.entity;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Assunto {
    private Integer codigo;
    private String descricao;
    @Builder.Default
    private List<Livro> livros = new ArrayList<>();

    public void adicionarLivro(Livro livro) {
        this.livros.add(livro);
        livro.getAssuntos().add(this);
    }

    public void removerLivro(Livro livro) {
        this.livros.remove(livro);
        livro.getAssuntos().remove(this);
    }
}
