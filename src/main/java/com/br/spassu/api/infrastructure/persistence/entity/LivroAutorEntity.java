package com.br.spassu.api.infrastructure.persistence.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "Livro_Autor")
@Data
public class LivroAutorEntity {

    @Id
    @Column(name = "Livro_Cod")
    private Integer livroCodigo;

    @Id
    @Column(name = "Autor_CodAu")
    private Integer autorCodigo;
}
