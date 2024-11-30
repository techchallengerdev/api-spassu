package com.br.spassu.api.application.mapper;

import com.br.spassu.api.domain.entity.Assunto;
import com.br.spassu.api.domain.entity.Autor;
import com.br.spassu.api.domain.entity.Livro;
import com.br.spassu.api.application.dto.LivroDTO;
import com.br.spassu.api.application.dto.LivroResumoDTO;
import org.springframework.stereotype.Component;
import java.util.HashSet;
import java.util.stream.Collectors;

@Component
public class LivroMapper {

    public LivroDTO toDTO(Livro livro) {
        if (livro == null) return null;

        LivroDTO dto = new LivroDTO();
        dto.setId(livro.getCodigo());
        dto.setTitulo(livro.getTitulo());
        dto.setEditora(livro.getEditora());
        dto.setNumeroEdicao(livro.getEdicao());
        dto.setAnoPublicacao(livro.getAnoPublicacao());

        dto.setIdsAutores(
                livro.getAutores().stream()
                        .map(Autor::getCodigo)
                        .collect(Collectors.toSet())
        );

        dto.setIdsAssuntos(
                livro.getAssuntos().stream()
                        .map(Assunto::getCodigo)
                        .collect(Collectors.toSet())
        );

        return dto;
    }

    public LivroResumoDTO toResumoDTO(Livro livro) {
        if (livro == null) return null;

        LivroResumoDTO resumo = new LivroResumoDTO();
        resumo.setId(livro.getCodigo());
        resumo.setTitulo(livro.getTitulo());
        resumo.setAnoPublicacao(livro.getAnoPublicacao());

        return resumo;
    }

    public Livro toEntity(LivroDTO dto) {
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
}