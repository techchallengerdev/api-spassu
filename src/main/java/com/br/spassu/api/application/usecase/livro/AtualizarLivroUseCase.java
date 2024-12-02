package com.br.spassu.api.application.usecase.livro;

import com.br.spassu.api.application.dto.LivroDTO;
import com.br.spassu.api.application.mapper.LivroMapper;
import com.br.spassu.api.domain.entity.Assunto;
import com.br.spassu.api.domain.entity.Autor;
import com.br.spassu.api.domain.entity.Livro;
import com.br.spassu.api.domain.exceptions.BusinessException;
import com.br.spassu.api.domain.exceptions.EntityNotFoundException;
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
public class AtualizarLivroUseCase {
    private final LivroRepository livroRepository;
    private final AutorRepository autorRepository;
    private final AssuntoRepository assuntoRepository;
    private final LivroMapper livroMapper;

    @Transactional
    public LivroDTO execute(Integer codigo, LivroDTO dto) {
        validarDadosEntrada(codigo, dto);

        Livro livroExistente = buscarLivro(codigo);

        List<Autor> autores = buscarAutores(dto.getAutorCodAus());
        validarAutores(autores);

        List<Assunto> assuntos = buscarAssuntos(dto.getAssuntoCodAss());
        validarAssuntos(assuntos);

        atualizarLivro(livroExistente, dto, autores, assuntos);

        Livro livroAtualizado = livroRepository.save(livroExistente);
        return livroMapper.toDto(livroAtualizado);
    }

    void validarDadosEntrada(Integer codigo, LivroDTO dto) {
        if (codigo == null) {
            throw new BusinessException("Código do livro não informado");
        }
        if (codigo <= 0) {
            throw new BusinessException("Código do livro deve ser maior que zero");
        }
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

    private Livro buscarLivro(Integer codigo) {
        return livroRepository.findByCodigo(codigo)
                .orElseThrow(() -> new EntityNotFoundException(
                        String.format("Livro com código %d não encontrado", codigo)));
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

    private void atualizarLivro(Livro livro, LivroDTO dto, List<Autor> autores, List<Assunto> assuntos) {
        livro.setTitulo(dto.getTitulo());
        livro.setEditora(dto.getEditora());
        livro.setEdicao(dto.getEdicao());
        livro.setAnoPublicacao(dto.getAnoPublicacao());
        livro.setAutores(autores);
        livro.setAssuntos(assuntos);
    }
}