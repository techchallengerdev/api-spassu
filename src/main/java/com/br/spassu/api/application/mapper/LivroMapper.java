package com.br.spassu.api.application.mapper;

import com.br.spassu.api.application.dto.LivroDTO;
import com.br.spassu.api.application.dto.LivroResumoDTO;
import com.br.spassu.api.domain.entity.Livro;
import com.br.spassu.api.infrastructure.persistence.entity.LivroEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class LivroMapper {

    private final AutorMapper autorMapper;
    private final AssuntoMapper assuntoMapper;

    // Domain -> DTO
    public LivroDTO toDTO(Livro livro) {
        if (livro == null) return null;

        LivroDTO dto = new LivroDTO();
        dto.setId(livro.getCodigo());
        dto.setTitulo(livro.getTitulo());
        dto.setEditora(livro.getEditora());
        dto.setNumeroEdicao(livro.getEdicao());
        dto.setAnoPublicacao(livro.getAnoPublicacao());

        if (livro.getAutores() != null) {
            dto.setIdsAutores(livro.getAutores().stream()
                    .map(autor -> autor.getCodigo())
                    .collect(Collectors.toSet()));
        } else {
            dto.setIdsAutores(new HashSet<>());
        }

        if (livro.getAssuntos() != null) {
            dto.setIdsAssuntos(livro.getAssuntos().stream()
                    .map(assunto -> assunto.getCodigo())
                    .collect(Collectors.toSet()));
        } else {
            dto.setIdsAssuntos(new HashSet<>());
        }

        return dto;
    }

    // DTO -> Domain
    public Livro toDomain(LivroDTO dto) {
        if (dto == null) return null;

        Livro livro = new Livro();
        livro.setCodigo(dto.getId());
        livro.setTitulo(dto.getTitulo());
        livro.setEditora(dto.getEditora());
        livro.setEdicao(dto.getNumeroEdicao());
        livro.setAnoPublicacao(dto.getAnoPublicacao());
        livro.setAutores(new HashSet<>());
        livro.setAssuntos(new HashSet<>());

        return livro;
    }

    // Entity -> Domain
    public Livro toDomain(LivroEntity entity) {
        if (entity == null) return null;

        Livro livro = new Livro();
        livro.setCodigo(entity.getCodigo());
        livro.setTitulo(entity.getTitulo());
        livro.setEditora(entity.getEditora());
        livro.setEdicao(entity.getEdicao());
        livro.setAnoPublicacao(entity.getAnoPublicacao());

        if (entity.getAutores() != null) {
            livro.setAutores(entity.getAutores().stream()
                    .map(autorMapper::toDomain)
                    .collect(Collectors.toSet()));
        } else {
            livro.setAutores(new HashSet<>());
        }

        if (entity.getAssuntos() != null) {
            livro.setAssuntos(entity.getAssuntos().stream()
                    .map(assuntoMapper::toDomain)
                    .collect(Collectors.toSet()));
        } else {
            livro.setAssuntos(new HashSet<>());
        }

        return livro;
    }

    // Domain -> Entity
    public LivroEntity toEntity(Livro livro) {
        if (livro == null) return null;

        LivroEntity entity = new LivroEntity();
        entity.setCodigo(livro.getCodigo());
        entity.setTitulo(livro.getTitulo());
        entity.setEditora(livro.getEditora());
        entity.setEdicao(livro.getEdicao());
        entity.setAnoPublicacao(livro.getAnoPublicacao());
        entity.setAutores(new HashSet<>());
        entity.setAssuntos(new HashSet<>());

        return entity;
    }

    public LivroResumoDTO toResumoDTO(Livro livro) {
        if (livro == null) return null;

        LivroResumoDTO resumo = new LivroResumoDTO();
        resumo.setId(livro.getCodigo());
        resumo.setTitulo(livro.getTitulo());
        resumo.setAnoPublicacao(livro.getAnoPublicacao());

        return resumo;
    }

    public void updateEntity(LivroEntity entity, Livro livro) {
        if (entity == null || livro == null) return;

        entity.setTitulo(livro.getTitulo());
        entity.setEditora(livro.getEditora());
        entity.setEdicao(livro.getEdicao());
        entity.setAnoPublicacao(livro.getAnoPublicacao());
    }
}