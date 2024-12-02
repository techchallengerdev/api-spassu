package com.br.spassu.api.application.mapper;

import com.br.spassu.api.application.dto.AssuntoDTO;
import com.br.spassu.api.domain.entity.Assunto;
import com.br.spassu.api.domain.entity.Livro;
import com.br.spassu.api.infrastructure.persistence.entity.AssuntoEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class AssuntoMapper {
    private final LivroMapper livroMapper;

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
                .livros(new ArrayList<>())  // Inicializa lista vazia
                .build();
    }

    // Domain -> Entity
    public AssuntoEntity toEntity(Assunto assunto) {
        if (assunto == null) return null;

        return AssuntoEntity.builder()
                .codigo(assunto.getCodigo())
                .descricao(assunto.getDescricao())
                .livros(assunto.getLivros().stream()
                        .map(livroMapper::toEntity)
                        .collect(Collectors.toList()))
                .build();
    }

    // Entity -> Domain
    public Assunto toDomain(AssuntoEntity entity) {
        if (entity == null) return null;

        return Assunto.builder()
                .codigo(entity.getCodigo())
                .descricao(entity.getDescricao())
                .livros(entity.getLivros().stream()
                        .map(livroMapper::toDomain)
                        .collect(Collectors.toList()))
                .build();
    }
}