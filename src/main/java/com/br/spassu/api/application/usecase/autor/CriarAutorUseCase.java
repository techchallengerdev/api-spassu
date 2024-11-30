package com.br.spassu.api.application.usecase.autor;

import com.br.spassu.api.application.dto.AutorDTO;
import com.br.spassu.api.application.mapper.AutorMapper;
import com.br.spassu.api.domain.entity.Autor;
import com.br.spassu.api.domain.repository.AutorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CriarAutorUseCase {
    private final AutorRepository autorRepository;
    private final AutorMapper autorMapper;

    @Transactional
    public AutorDTO execute(AutorDTO dto) {
        Autor autor = autorMapper.toEntity(dto);
        return autorMapper.toDTO(autorRepository.save(autor));
    }
}
