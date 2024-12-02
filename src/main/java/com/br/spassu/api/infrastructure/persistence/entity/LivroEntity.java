package com.br.spassu.api.infrastructure.persistence.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
@Entity
@Table(name = "Livro")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LivroEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "codigo")
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
            joinColumns = @JoinColumn(name = "livro_codigo"),
            inverseJoinColumns = @JoinColumn(name = "autor_codigo")
    )
    @Builder.Default
    private List<AutorEntity> autores = new ArrayList<>();

    @ManyToMany
    @JoinTable(
            name = "Livro_Assunto",
            joinColumns = @JoinColumn(name = "livro_codigo"),
            inverseJoinColumns = @JoinColumn(name = "assunto_codigo")
    )
    @Builder.Default
    private List<AssuntoEntity> assuntos = new ArrayList<>();
}
