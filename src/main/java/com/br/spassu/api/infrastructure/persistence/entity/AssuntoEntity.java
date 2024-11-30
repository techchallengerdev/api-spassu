package com.br.spassu.api.infrastructure.persistence.entity;

import jakarta.persistence.*;

import java.util.Set;

import jakarta.persistence.*;
import lombok.Data;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "Assunto")
@Data
public class AssuntoEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "codAs")
    private Integer codigo;

    @Column(name = "Descricao", length = 20)
    private String descricao;

    @ManyToMany(mappedBy = "assuntos")
    private Set<LivroEntity> livros = new HashSet<>();
}
