package com.br.spassu.api.infrastructure.persistence.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "Livro")
@Data
public class LivroEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Livro_Cod")
    private Integer codigo;

    @Column(name = "Titulo", length = 40, nullable = false)
    private String titulo;

    @Column(name = "Editora", length = 40)
    private String editora;

    @Column(name = "Edicao")
    private Integer edicao;

    @Column(name = "AnoPublicacao", length = 4)
    private String anoPublicacao;

    @ManyToMany
    @JoinTable(
            name = "Livro_Autor",
            joinColumns = @JoinColumn(name = "Livro_Cod"),
            inverseJoinColumns = @JoinColumn(name = "Autor_CodAu")
    )
    private Set<AutorEntity> autores = new HashSet<>();

    @ManyToMany
    @JoinTable(
            name = "Livro_Assunto",
            joinColumns = @JoinColumn(name = "Livro_Cod"),
            inverseJoinColumns = @JoinColumn(name = "Assunto_codAs")
    )
    private Set<AssuntoEntity> assuntos = new HashSet<>();
}
