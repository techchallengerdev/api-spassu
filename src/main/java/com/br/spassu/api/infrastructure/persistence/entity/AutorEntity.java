package com.br.spassu.api.infrastructure.persistence.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Autor")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AutorEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "codigo")
    private Integer codigo;

    @Column(length = 40)
    private String nome;

    @ManyToMany(mappedBy = "autores")
    @Builder.Default
    private List<LivroEntity> livros = new ArrayList<>();
}
