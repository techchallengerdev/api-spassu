package com.br.spassu.api.infrastructure.persistence.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
@Entity
@Table(name = "Livro_Autor")
public class AutorEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "codAs")
    private Integer codigo;

    @Column(length = 40)
    private String nome;

    @ManyToMany(mappedBy = "autores")
    private List<LivroEntity> livros;
}
