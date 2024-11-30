package com.br.spassu.api.application.mapper;

import com.br.spassu.api.application.dto.AssuntoDTO;
import com.br.spassu.api.domain.entity.Assunto;
import com.br.spassu.api.infrastructure.persistence.entity.AssuntoEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class AssuntoMapper {

    private final LivroMapper livroMapper;

    public AssuntoDTO toDTO(Assunto assunto) {
        if (assunto == null) return null;

        AssuntoDTO dto = new AssuntoDTO();
        dto.setId(assunto.getCodigo());
        dto.setDescricao(assunto.getDescricao());

        if (assunto.getLivros() != null) {
            dto.setLivros(
                    assunto.getLivros().stream()
                            .map(livroMapper::toResumoDTO)
                            .collect(Collectors.toSet())
            );
        } else {
            dto.setLivros(new HashSet<>());
        }

        return dto;
    }

    public Assunto toDomain(AssuntoDTO dto) {
        if (dto == null) return null;

        Assunto assunto = new Assunto();
        assunto.setCodigo(dto.getId());
        assunto.setDescricao(dto.getDescricao());
        assunto.setLivros(new HashSet<>());

        return assunto;
    }

    public Assunto toDomain(AssuntoEntity entity) {
        if (entity == null) return null;

        Assunto assunto = new Assunto();
        assunto.setCodigo(entity.getCodigo());
        assunto.setDescricao(entity.getDescricao());

        if (entity.getLivros() != null) {
            assunto.setLivros(
                    entity.getLivros().stream()
                            .map(livroMapper::toDomain)
                            .collect(Collectors.toSet())
            );
        } else {
            assunto.setLivros(new HashSet<>());
        }

        return assunto;
    }

    // Domain -> Entity
    public AssuntoEntity toEntity(Assunto assunto) {
        if (assunto == null) return null;

        AssuntoEntity entity = new AssuntoEntity();
        entity.setCodigo(assunto.getCodigo());
        entity.setDescricao(assunto.getDescricao());
        entity.setLivros(new HashSet<>());

        return entity;
    }

    public void updateEntity(AssuntoEntity entity, Assunto assunto) {
        if (entity == null || assunto == null) return;

        entity.setDescricao(assunto.getDescricao());
    }
}