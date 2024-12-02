package com.br.spassu.api.application.mapper;

import com.br.spassu.api.application.dto.AutorDTO;
import com.br.spassu.api.domain.entity.Autor;
import com.br.spassu.api.domain.entity.Livro;
import com.br.spassu.api.infrastructure.persistence.entity.AutorEntity;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.stream.Collectors;

@Component
public class AutorMapper {

    // Domain -> DTO
    public AutorDTO toDto(Autor autor) {
        if (autor == null) return null;

        return AutorDTO.builder()
                .codigo(autor.getCodigo())
                .nome(autor.getNome())
                .livros(autor.getLivros().stream()
                        .map(Livro::getCodigo)
                        .collect(Collectors.toList()))
                .build();
    }

    // DTO -> Domain
    public Autor toDomain(AutorDTO dto) {
        if (dto == null) return null;

        return Autor.builder()
                .codigo(dto.getCodigo())
                .nome(dto.getNome())
                .livros(new ArrayList<>())
                .build();
    }

    // Domain -> Entity
    public AutorEntity toEntity(Autor autor) {
        if (autor == null) return null;

        return AutorEntity.builder()
                .codigo(autor.getCodigo())
                .nome(autor.getNome())
                .build();
    }

    // Entity -> Domain
    public Autor toDomain(AutorEntity entity) {
        if (entity == null) return null;

        return Autor.builder()
                .codigo(entity.getCodigo())
                .nome(entity.getNome())
                .livros(new ArrayList<>())
                .build();
    }

    public AutorEntity updateRelationships(AutorEntity entity, Autor autor) {
        if (entity == null || autor == null) return entity;

        entity.setLivros(new ArrayList<>());
        return entity;
    }
}