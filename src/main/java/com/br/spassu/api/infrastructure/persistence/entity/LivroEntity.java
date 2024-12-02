package com.br.spassu.api.infrastructure.persistence.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
@Getter
@Setter
@Entity
@Builder
@Table(name = "Livro")
public class LivroEntity {
    @Id
    @Column(name = "CodI")
    private Integer codigo;

    @Column(name = "Titulo", length = 40, nullable = false)
    private String titulo;

    @Column(name = "Editora", length = 40)
    private String editora;

    @Column(name = "Edicao")
    private Integer edicao;

    @Column(name = "AnoPublicacao", length = 4)
    private Integer anoPublicacao;

    @ManyToMany
    @JoinTable(
            name = "Livro_Autor",
            joinColumns = @JoinColumn(name = "Livro_CodI"),
            inverseJoinColumns = @JoinColumn(name = "Autor_CodAu")
    )
    private List<AutorEntity> autores;

    @ManyToMany
    @JoinTable(
            name = "Livro_Assunto",
            joinColumns = @JoinColumn(name = "Livro_CodI"),
            inverseJoinColumns = @JoinColumn(name = "Assunto_codAs")
    )
    private List<AssuntoEntity> assuntos;
}
