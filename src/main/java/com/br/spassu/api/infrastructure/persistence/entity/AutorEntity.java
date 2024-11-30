package com.br.spassu.api.infrastructure.persistence.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "Autor")
@Data
public class AutorEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CodAu")
    private Integer codigo;

    @Column(name = "Nome", length = 40, nullable = false)
    private String nome;

    @ManyToMany(mappedBy = "autores")
    private Set<LivroEntity> livros = new HashSet<>();
}
