package com.br.spassu.api.application.usecase.livro;

import com.br.spassu.api.application.dto.LivroDTO;
import com.br.spassu.api.application.mapper.LivroMapper;
import com.br.spassu.api.domain.entity.Assunto;
import com.br.spassu.api.domain.entity.Autor;
import com.br.spassu.api.domain.entity.Livro;
import com.br.spassu.api.domain.exceptions.BusinessException;
import com.br.spassu.api.domain.repository.AssuntoRepository;
import com.br.spassu.api.domain.repository.AutorRepository;
import com.br.spassu.api.domain.repository.LivroRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CriarLivroUseCase {
    private final LivroRepository livroRepository;
    private final AutorRepository autorRepository;
    private final AssuntoRepository assuntoRepository;
    private final LivroMapper livroMapper;

    @Transactional
    public LivroDTO execute(LivroDTO dto) {
        validarDadosEntrada(dto);

        List<Autor> autores = buscarAutores(dto.getAutorCodAus());
        validarAutores(autores);

        List<Assunto> assuntos = buscarAssuntos(dto.getAssuntoCodAss());
        validarAssuntos(assuntos);

        Livro livro = criarLivro(dto, autores, assuntos);

        Livro livroSalvo = livroRepository.save(livro);
        return livroMapper.toDto(livroSalvo);
    }

    private Livro criarLivro(LivroDTO dto, List<Autor> autores, List<Assunto> assuntos) {
        return Livro.builder()
                .titulo(dto.getTitulo())
                .editora(dto.getEditora())
                .edicao(dto.getEdicao())
                .anoPublicacao(dto.getAnoPublicacao())
                .autores(autores)
                .assuntos(assuntos)
                .build();
    }

    void validarDadosEntrada(LivroDTO dto) {
        if (dto == null) {
            throw new BusinessException("Dados do livro não informados");
        }
        if (dto.getTitulo() == null || dto.getTitulo().trim().isEmpty()) {
            throw new BusinessException("Título do livro é obrigatório");
        }
        if (dto.getAutorCodAus() == null || dto.getAutorCodAus().isEmpty()) {
            throw new BusinessException("É necessário informar pelo menos um autor");
        }
        if (dto.getAssuntoCodAss() == null || dto.getAssuntoCodAss().isEmpty()) {
            throw new BusinessException("É necessário informar pelo menos um assunto");
        }
    }

    private List<Autor> buscarAutores(List<Integer> autorCodAus) {
        if (autorCodAus == null) return List.of();

        return autorCodAus.stream()
                .map(codigoAutor -> autorRepository.findByCodigo(codigoAutor)
                        .orElseThrow(() -> new BusinessException(
                                String.format("Autor com código %d não encontrado", codigoAutor))))
                .collect(Collectors.toList());
    }

    void validarAutores(List<Autor> autores) {
        if (autores.isEmpty()) {
            throw new BusinessException("É necessário informar pelo menos um autor válido");
        }
    }

    private List<Assunto> buscarAssuntos(List<Integer> assuntoCodAss) {
        if (assuntoCodAss == null) return List.of();

        return assuntoCodAss.stream()
                .map(codigoAssunto -> assuntoRepository.findByCodigo(codigoAssunto)
                        .orElseThrow(() -> new BusinessException(
                                String.format("Assunto com código %d não encontrado", codigoAssunto))))
                .collect(Collectors.toList());
    }

    void validarAssuntos(List<Assunto> assuntos) {
        if (assuntos.isEmpty()) {
            throw new BusinessException("É necessário informar pelo menos um assunto válido");
        }
    }
}