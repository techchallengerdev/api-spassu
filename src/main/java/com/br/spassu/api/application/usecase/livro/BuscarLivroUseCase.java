package com.br.spassu.api.application.usecase.livro;

import com.br.spassu.api.application.dto.LivroDTO;
import com.br.spassu.api.application.mapper.LivroMapper;
import com.br.spassu.api.domain.exceptions.EntityNotFoundException;
import com.br.spassu.api.domain.repository.LivroRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BuscarLivroUseCase {
    private final LivroRepository livroRepository;
    private final LivroMapper livroMapper;

    public LivroDTO execute(Integer id) {
        return livroRepository.findByCodigo(id)
                .map(livroMapper::toDTO)
                .orElseThrow(() -> new EntityNotFoundException("Livro não encontrado"));
    }
}
