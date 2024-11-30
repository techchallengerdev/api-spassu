package com.br.spassu.api.application.mapper;

import com.br.spassu.api.application.dto.AutorDTO;
import com.br.spassu.api.domain.entity.Autor;
import com.br.spassu.api.infrastructure.persistence.entity.AutorEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class AutorMapper {

    private final LivroMapper livroMapper;

    // Domain -> DTO
    public AutorDTO toDTO(Autor autor) {
        if (autor == null) return null;

        AutorDTO dto = new AutorDTO();
        dto.setId(autor.getCodigo());
        dto.setNome(autor.getNome());

        if (autor.getLivros() != null) {
            dto.setLivros(
                    autor.getLivros().stream()
                            .map(livroMapper::toResumoDTO)
                            .collect(Collectors.toSet())
            );
        } else {
            dto.setLivros(new HashSet<>());
        }

        return dto;
    }

    // DTO -> Domain
    public Autor toDomain(AutorDTO dto) {
        if (dto == null) return null;

        Autor autor = new Autor();
        autor.setCodigo(dto.getId());
        autor.setNome(dto.getNome());
        autor.setLivros(new HashSet<>());

        return autor;
    }

    // Entity -> Domain
    public Autor toDomain(AutorEntity entity) {
        if (entity == null) return null;

        Autor autor = new Autor();
        autor.setCodigo(entity.getCodigo());
        autor.setNome(entity.getNome());

        if (entity.getLivros() != null) {
            autor.setLivros(
                    entity.getLivros().stream()
                            .map(livroMapper::toDomain)
                            .collect(Collectors.toSet())
            );
        } else {
            autor.setLivros(new HashSet<>());
        }

        return autor;
    }

    // Domain -> Entity
    public AutorEntity toEntity(Autor autor) {
        if (autor == null) return null;

        AutorEntity entity = new AutorEntity();
        entity.setCodigo(autor.getCodigo());
        entity.setNome(autor.getNome());
        entity.setLivros(new HashSet<>());

        return entity;
    }

    public void updateEntity(AutorEntity entity, Autor autor) {
        if (entity == null || autor == null) return;
        entity.setNome(autor.getNome());
    }
}