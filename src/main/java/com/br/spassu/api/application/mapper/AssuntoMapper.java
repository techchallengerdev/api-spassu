package com.br.spassu.api.application.mapper;

import com.br.spassu.api.application.dto.AssuntoDTO;
import com.br.spassu.api.domain.entity.Assunto;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class AssuntoMapper {

    private final LivroMapper livroMapper;

    public AssuntoMapper(LivroMapper livroMapper) {
        this.livroMapper = livroMapper;
    }

    public AssuntoDTO toDTO(Assunto assunto) {
        if (assunto == null) return null;

        AssuntoDTO dto = new AssuntoDTO();
        dto.setId(assunto.getCodigo());
        dto.setDescricao(assunto.getDescricao());

        dto.setLivros(
                assunto.getLivros().stream()
                        .map(livroMapper::toResumoDTO)
                        .collect(Collectors.toSet())
        );

        return dto;
    }

    public Assunto toEntity(AssuntoDTO dto) {
        if (dto == null) return null;

        Assunto assunto = new Assunto();
        assunto.setCodigo(dto.getId());
        assunto.setDescricao(dto.getDescricao());
        return assunto;
    }
}
