package com.br.spassu.api.application.usecase.autor;

import com.br.spassu.api.application.dto.AutorDTO;
import com.br.spassu.api.application.mapper.AutorMapper;
import com.br.spassu.api.domain.exceptions.EntityNotFoundException;
import com.br.spassu.api.domain.repository.AutorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BuscarAutorUseCase {
    private final AutorRepository autorRepository;
    private final AutorMapper autorMapper;

    public AutorDTO execute(Integer id) {
        return autorRepository.findByCodigo(id)
                .map(autorMapper::toDTO)
                .orElseThrow(() -> new EntityNotFoundException("Autor não encontrado"));
    }
}
