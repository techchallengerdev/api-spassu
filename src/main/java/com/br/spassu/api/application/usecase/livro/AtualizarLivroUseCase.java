package com.br.spassu.api.application.usecase.livro;

import com.br.spassu.api.application.dto.LivroDTO;
import com.br.spassu.api.application.mapper.LivroMapper;
import com.br.spassu.api.domain.exceptions.EntityNotFoundException;
import com.br.spassu.api.domain.repository.LivroRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AtualizarLivroUseCase {
    private final LivroRepository livroRepository;
    private final LivroMapper livroMapper;

    @Transactional
    public LivroDTO execute(Integer id, LivroDTO dto) {
        return livroRepository.findByCodigo(id)
                .map(livro -> {
                    livro.setTitulo(dto.getTitulo());
                    livro.setEditora(dto.getEditora());
                    livro.setEdicao(dto.getNumeroEdicao());
                    livro.setAnoPublicacao(dto.getAnoPublicacao());
                    return livroMapper.toDTO(livroRepository.save(livro));
                })
                .orElseThrow(() -> new EntityNotFoundException("Livro não encontrado"));
    }
}
