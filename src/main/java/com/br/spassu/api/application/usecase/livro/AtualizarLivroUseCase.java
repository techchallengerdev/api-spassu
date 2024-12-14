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

import java.util.ArrayList;
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
        validarDadosEntrada(dto);

        Livro livroExistente = buscarLivro(codigo);

        List<Autor> autores = buscarAutores(dto.getAutorCodAus());
        validarAutores(autores);

        List<Assunto> assuntos = buscarAssuntos(dto.getAssuntoCodAss());
        validarAssuntos(assuntos);

        atualizarLivro(livroExistente, dto, autores, assuntos);

        Livro livroAtualizado = livroRepository.save(livroExistente);
        return livroMapper.toDto(livroAtualizado);
    }

    void validarDadosEntrada(LivroDTO livroDTO) {
        List<String> erros = new ArrayList<>();

        if (livroDTO.getCodigo() == null || livroDTO.getCodigo() <= 0) {
            erros.add("O código do livro deve ser maior que zero");
        }

        if (livroDTO.getTitulo() == null || livroDTO.getTitulo().isEmpty()) {
            erros.add("O título do livro é obrigatório");
        }

        if (livroDTO.getEditora() == null || livroDTO.getEditora().isEmpty()) {
            erros.add("A editora do livro é obrigatória");
        }

        if (livroDTO.getEdicao() == null || livroDTO.getEdicao() < 0) {
            erros.add("A edição do livro deve ser maior ou igual a zero");
        }

        if (livroDTO.getAnoPublicacao() == null || livroDTO.getAnoPublicacao().isEmpty()) {
            erros.add("O ano de publicação do livro é obrigatório");
        } else {
            try {
                Integer.parseInt(livroDTO.getAnoPublicacao());
            } catch (NumberFormatException e) {
                erros.add("O ano de publicação do livro deve ser um número válido");
            }
        }

        if (livroDTO.getAutorCodAus() == null || livroDTO.getAutorCodAus().isEmpty()) {
            erros.add("O livro deve ter pelo menos um autor");
        }

        if (livroDTO.getAssuntoCodAss() == null || livroDTO.getAssuntoCodAss().isEmpty()) {
            erros.add("O livro deve ter pelo menos um assunto");
        }

        if (!erros.isEmpty()) {
            throw new BusinessException(String.join(", ", erros));
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