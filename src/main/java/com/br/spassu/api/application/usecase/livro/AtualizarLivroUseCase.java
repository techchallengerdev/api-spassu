package com.br.spassu.api.application.usecase.livro;

import com.br.spassu.api.application.dto.LivroDTO;
import com.br.spassu.api.application.mapper.LivroMapper;
import com.br.spassu.api.domain.entity.Assunto;
import com.br.spassu.api.domain.entity.Autor;
import com.br.spassu.api.domain.entity.Livro;
import com.br.spassu.api.domain.exceptions.AuthorNotFoundException;
import com.br.spassu.api.domain.exceptions.BookNotFoundException;
import com.br.spassu.api.domain.exceptions.InvalidBookDataException;
import com.br.spassu.api.domain.exceptions.SubjectNotFoundException;
import com.br.spassu.api.domain.repository.AssuntoRepository;
import com.br.spassu.api.domain.repository.AutorRepository;
import com.br.spassu.api.domain.repository.LivroRepository;
import com.br.spassu.api.infrastructure.response.ResponseWrapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AtualizarLivroUseCase {

    private final LivroRepository livroRepository;
    private final AutorRepository autorRepository;
    private final AssuntoRepository assuntoRepository;
    private final LivroMapper livroMapper;

    private static final String SUCESSO_ATUALIZAR_LIVRO = "Livro atualizado com sucesso";

    @Transactional
    public ResponseWrapper<LivroDTO> execute(Integer codigo, LivroDTO livroDTO) {
        validarDadosLivro(livroDTO);
        validarCamposObrigatorios(livroDTO);

        Livro livroExistente = buscarLivroExistente(codigo);
        List<Autor> autores = buscarAutores(livroDTO.getAutorCodAus());
        List<Assunto> assuntos = buscarAssuntos(livroDTO.getAssuntoCodAss());

        atualizarLivro(livroExistente, livroDTO, autores, assuntos);
        livroExistente.validar();

        Livro livroSalvo = livroRepository.save(livroExistente);

        return ResponseWrapper.<LivroDTO>builder()
                .message(SUCESSO_ATUALIZAR_LIVRO)
                .data(livroMapper.toDto(livroSalvo))
                .build();
    }

    private Livro buscarLivroExistente(Integer codigo) {
        return livroRepository.findByCodigo(codigo)
                .orElseThrow(() -> new BookNotFoundException(codigo));
    }

    private void validarDadosLivro(LivroDTO livroDTO) {
        if (livroDTO == null) {
            throw new InvalidBookDataException("Dados do livro não informados");
        }
    }

    private void validarCamposObrigatorios(LivroDTO livroDTO) {
        if (!StringUtils.hasText(livroDTO.getTitulo())) {
            throw new InvalidBookDataException("Título não informado, campo obrigatório");
        }

        if (!StringUtils.hasText(livroDTO.getEditora())) {
            throw new InvalidBookDataException("Editora não informada, campo obrigatório");
        }
    }

    private void atualizarLivro(Livro livro, LivroDTO dto, List<Autor> autores, List<Assunto> assuntos) {
        livro.setTitulo(dto.getTitulo().trim());
        livro.setEditora(dto.getEditora().trim());
        livro.setEdicao(dto.getEdicao());
        livro.setAnoPublicacao(dto.getAnoPublicacao());

        livro.limparAutores();
        livro.limparAssuntos();

        autores.forEach(livro::adicionarAutor);
        assuntos.forEach(livro::adicionarAssunto);
    }

    private List<Autor> buscarAutores(List<Integer> autorCodAus) {
        if (autorCodAus == null || autorCodAus.isEmpty()) {
            throw new InvalidBookDataException("Lista de autores não informada, campo obrigatório");
        }

        return autorCodAus.stream()
                .map(codigoAutor -> autorRepository.findByCodigo(codigoAutor)
                        .orElseThrow(() -> new AuthorNotFoundException(codigoAutor)))
                .collect(Collectors.toList());
    }

    private List<Assunto> buscarAssuntos(List<Integer> assuntoCodAss) {
        if (assuntoCodAss == null || assuntoCodAss.isEmpty()) {
            throw new InvalidBookDataException("Lista de assuntos não informada, campo obrigatório");
        }

        return assuntoCodAss.stream()
                .map(codigoAssunto -> assuntoRepository.findByCodigo(codigoAssunto)
                        .orElseThrow(() -> new SubjectNotFoundException(codigoAssunto)))
                .collect(Collectors.toList());
    }
}