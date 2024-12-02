package com.br.spassu.api.infrastructure.persistence.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Assunto")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AssuntoEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "codigo")
    private Integer codigo;

    @Column(name = "Descricao", length = 20)
    private String descricao;

    @ManyToMany(mappedBy = "assuntos")
    @Builder.Default
    private List<LivroEntity> livros = new ArrayList<>();
}
