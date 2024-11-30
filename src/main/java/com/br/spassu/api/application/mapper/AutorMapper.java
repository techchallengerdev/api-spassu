package com.br.spassu.api.application.mapper;

import com.br.spassu.api.application.dto.AutorDTO;
import com.br.spassu.api.domain.entity.Autor;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class AutorMapper {

    private final LivroMapper livroMapper;

    public AutorMapper(LivroMapper livroMapper) {
        this.livroMapper = livroMapper;
    }

    public AutorDTO toDTO(Autor autor) {
        if (autor == null) return null;

        AutorDTO dto = new AutorDTO();
        dto.setId(autor.getCodigo());
        dto.setNome(autor.getNome());

        dto.setLivros(
                autor.getLivros().stream()
                        .map(livroMapper::toResumoDTO)
                        .collect(Collectors.toSet())
        );

        return dto;
    }

    public Autor toEntity(AutorDTO dto) {
        if (dto == null) return null;

        Autor autor = new Autor();
        autor.setCodigo(dto.getId());
        autor.setNome(dto.getNome());
        return autor;
    }
}
