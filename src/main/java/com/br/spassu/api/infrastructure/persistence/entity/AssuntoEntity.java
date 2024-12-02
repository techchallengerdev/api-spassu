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
@Table(name = "Assunto")
public class AssuntoEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "codAs")
    private Integer codigo;

    @Column(name = "Descricao", length = 20)
    private String descricao;

    @ManyToMany(mappedBy = "assuntos")
    private List<LivroEntity> livros;
}
