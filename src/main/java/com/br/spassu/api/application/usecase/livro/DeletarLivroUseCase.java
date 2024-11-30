package com.br.spassu.api.application.usecase.livro;

import com.br.spassu.api.domain.exceptions.EntityNotFoundException;
import com.br.spassu.api.domain.repository.LivroRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class DeletarLivroUseCase {
    private final LivroRepository livroRepository;

    @Transactional
    public void execute(Integer id) {
        if (!livroRepository.existsByCodigo(id)) {
            throw new EntityNotFoundException("Livro não encontrado");
        }
        livroRepository.deleteByCodigo(id);
    }
}
