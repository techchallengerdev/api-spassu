package com.br.spassu.api.application.usecase.livro;

import com.br.spassu.api.domain.entity.Livro;
import com.br.spassu.api.domain.exceptions.EntityNotFoundException;
import com.br.spassu.api.domain.repository.LivroRepository;
import com.br.spassu.api.domain.repository.AutorRepository;
import com.br.spassu.api.domain.repository.AssuntoRepository;
import com.br.spassu.api.application.dto.LivroDTO;
import com.br.spassu.api.application.mapper.LivroMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CriarLivroUseCase {
    private final LivroRepository livroRepository;
    private final AutorRepository autorRepository;
    private final AssuntoRepository assuntoRepository;
    private final LivroMapper livroMapper;

    @Transactional
    public LivroDTO execute(LivroDTO dto) {
        Livro livro = livroMapper.toEntity(dto);

        dto.getIdsAutores().forEach(autorId ->
                autorRepository.findByCodigo(autorId)
                        .ifPresent(livro::adicionarAutor));

        dto.getIdsAssuntos().forEach(assuntoId ->
                assuntoRepository.findByCodigo(assuntoId)
                        .ifPresent(livro::adicionarAssunto));

        return livroMapper.toDTO(livroRepository.save(livro));
    }
}