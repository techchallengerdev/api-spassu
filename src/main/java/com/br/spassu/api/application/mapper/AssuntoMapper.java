package com.br.spassu.api.application.mapper;

import com.br.spassu.api.application.dto.AssuntoDTO;
import com.br.spassu.api.domain.entity.Assunto;
import com.br.spassu.api.domain.entity.Livro;
import com.br.spassu.api.infrastructure.persistence.entity.AssuntoEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.stream.Collectors;

@Component
public class AssuntoMapper {
    // Domain -> DTO
    public AssuntoDTO toDto(Assunto assunto) {
        if (assunto == null) return null;

        return AssuntoDTO.builder()
                .codigo(assunto.getCodigo())
                .descricao(assunto.getDescricao())
                .livros(assunto.getLivros().stream()
                        .map(Livro::getCodigo)
                        .collect(Collectors.toList()))
                .build();
    }

    // DTO -> Domain
    public Assunto toDomain(AssuntoDTO dto) {
        if (dto == null) return null;

        return Assunto.builder()
                .codigo(dto.getCodigo())
                .descricao(dto.getDescricao())
                .livros(new ArrayList<>())
                .build();
    }

    // Domain -> Entity
    public AssuntoEntity toEntity(Assunto assunto) {
        if (assunto == null) return null;

        return AssuntoEntity.builder()
                .codigo(assunto.getCodigo())
                .descricao(assunto.getDescricao())
                .build();
    }

    // Entity -> Domain
    public Assunto toDomain(AssuntoEntity entity) {
        if (entity == null) return null;

        return Assunto.builder()
                .codigo(entity.getCodigo())
                .descricao(entity.getDescricao())
                .livros(new ArrayList<>())
                .build();
    }

    public AssuntoEntity updateRelationships(AssuntoEntity entity, Assunto assunto) {
        if (entity == null || assunto == null) return entity;

        entity.setLivros(new ArrayList<>());
        return entity;
    }
}