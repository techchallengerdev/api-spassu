package com.br.spassu.api.application.mapper;

import com.br.spassu.api.application.dto.LivroDTO;
import com.br.spassu.api.domain.entity.Assunto;
import com.br.spassu.api.domain.entity.Autor;
import com.br.spassu.api.domain.entity.Livro;
import com.br.spassu.api.infrastructure.persistence.entity.LivroEntity;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class LivroMapper {
    private final AutorMapper autorMapper;
    private final AssuntoMapper assuntoMapper;
    public LivroMapper(AutorMapper autorMapper, AssuntoMapper assuntoMapper) {
        this.autorMapper = autorMapper;
        this.assuntoMapper = assuntoMapper;
    }

    // Domain -> DTO
    public LivroDTO toDto(Livro livro) {
        return LivroDTO.builder()
                .codigo(livro.getCodigo())
                .titulo(livro.getTitulo())
                .editora(livro.getEditora())
                .edicao(livro.getEdicao())
                .anoPublicacao(livro.getAnoPublicacao())
                .autorCodAus(livro.getAutores().stream()
                        .map(Autor::getCodigo)
                        .collect(Collectors.toList()))
                .assuntoCodAss(livro.getAssuntos().stream()
                        .map(Assunto::getCodigo)
                        .collect(Collectors.toList()))
                .build();
    }

    // Domain -> Entity
    public LivroEntity toEntity(Livro livro) {
        return LivroEntity.builder()
                .codigo(livro.getCodigo())
                .titulo(livro.getTitulo())
                .editora(livro.getEditora())
                .edicao(livro.getEdicao())
                .anoPublicacao(Integer.valueOf(livro.getAnoPublicacao()))
                .autores(livro.getAutores().stream()
                        .map(autorMapper::toEntity)
                        .collect(Collectors.toList()))
                .assuntos(livro.getAssuntos().stream()
                        .map(assuntoMapper::toEntity)
                        .collect(Collectors.toList()))
                .build();
    }

    // Entity -> Domain
    public Livro toDomain(LivroEntity entity) {
        return Livro.builder()
                .codigo(entity.getCodigo())
                .titulo(entity.getTitulo())
                .editora(entity.getEditora())
                .edicao(entity.getEdicao())
                .anoPublicacao(String.valueOf(entity.getAnoPublicacao()))
                .autores(entity.getAutores().stream()
                        .map(autorMapper::toDomain)
                        .collect(Collectors.toList()))
                .assuntos(entity.getAssuntos().stream()
                        .map(assuntoMapper::toDomain)
                        .collect(Collectors.toList()))
                .build();
    }
}