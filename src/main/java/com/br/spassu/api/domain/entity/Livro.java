package com.br.spassu.api.domain.entity;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Livro {
    private Integer codigo;
    private String titulo;
    private String editora;
    private Integer edicao;
    private String anoPublicacao;
    @Builder.Default
    private List<Autor> autores = new ArrayList<>();
    @Builder.Default
    private List<Assunto> assuntos = new ArrayList<>();

    public void adicionarAutor(Autor autor) {
        this.autores.add(autor);
        autor.getLivros().add(this);
    }

    public void removerAutor(Autor autor) {
        this.autores.remove(autor);
        autor.getLivros().remove(this);
    }

    public void adicionarAssunto(Assunto assunto) {
        this.assuntos.add(assunto);
        assunto.getLivros().add(this);
    }

    public void removerAssunto(Assunto assunto) {
        this.assuntos.remove(assunto);
        assunto.getLivros().remove(this);
    }
}
