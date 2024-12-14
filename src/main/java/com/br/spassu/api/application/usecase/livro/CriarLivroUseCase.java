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
    public LivroDTO execute(LivroDTO livroDTO) {
        if (livroDTO == null) {
            throw new BusinessException("Dados do livro não informados");
        }

        List<Autor> autores = buscarAutores(livroDTO.getAutorCodAus());
        List<Assunto> assuntos = buscarAssuntos(livroDTO.getAssuntoCodAss());

        Livro livro = criarLivroComRelacionamentos(livroDTO, autores, assuntos);
        livro.validar();

        Livro livroSalvo = livroRepository.save(livro);
        return livroMapper.toDto(livroSalvo);
    }

    private Livro criarLivroComRelacionamentos(LivroDTO dto, List<Autor> autores, List<Assunto> assuntos) {
        Livro livro = Livro.builder()
                .titulo(dto.getTitulo())
                .editora(dto.getEditora())
                .edicao(dto.getEdicao())
                .anoPublicacao(dto.getAnoPublicacao())
                .build();

        autores.forEach(livro::adicionarAutor);
        assuntos.forEach(livro::adicionarAssunto);

        return livro;
    }

    private List<Autor> buscarAutores(List<Integer> autorCodAus) {
        if (autorCodAus == null || autorCodAus.isEmpty()) {
            throw new BusinessException("É necessário informar pelo menos um autor");
        }

        return autorCodAus.stream()
                .map(codigoAutor -> autorRepository.findByCodigo(codigoAutor)
                        .orElseThrow(() -> new BusinessException(
                                String.format("Autor com código %d não encontrado", codigoAutor))))
                .collect(Collectors.toList());
    }

    private List<Assunto> buscarAssuntos(List<Integer> assuntoCodAss) {
        if (assuntoCodAss == null || assuntoCodAss.isEmpty()) {
            throw new BusinessException("É necessário informar pelo menos um assunto");
        }

        return assuntoCodAss.stream()
                .map(codigoAssunto -> assuntoRepository.findByCodigo(codigoAssunto)
                        .orElseThrow(() -> new BusinessException(
                                String.format("Assunto com código %d não encontrado", codigoAssunto))))
                .collect(Collectors.toList());
    }
}