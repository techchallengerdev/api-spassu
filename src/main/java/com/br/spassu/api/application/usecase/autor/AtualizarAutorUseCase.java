package com.br.spassu.api.application.usecase.autor;

import com.br.spassu.api.application.dto.AutorDTO;
import com.br.spassu.api.application.mapper.AutorMapper;
import com.br.spassu.api.domain.exceptions.EntityNotFoundException;
import com.br.spassu.api.domain.repository.AutorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AtualizarAutorUseCase {
    private final AutorRepository autorRepository;
    private final AutorMapper autorMapper;

    @Transactional
    public AutorDTO execute(Integer id, AutorDTO dto) {
        return autorRepository.findByCodigo(id)
                .map(autor -> {
                    autor.setNome(dto.getNome());
                    return autorMapper.toDTO(autorRepository.save(autor));
                })
                .orElseThrow(() -> new EntityNotFoundException("Autor não encontrado"));
    }
}
