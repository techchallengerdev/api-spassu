package com.br.spassu.api.domain.entity;

import lombok.Getter;
import lombok.Setter;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
public class Livro {
    private Integer codigo;
    private String titulo;
    private String editora;
    private Integer edicao;
    private String anoPublicacao;
    private Set<Autor> autores = new HashSet<>();
    private Set<Assunto> assuntos = new HashSet<>();

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
