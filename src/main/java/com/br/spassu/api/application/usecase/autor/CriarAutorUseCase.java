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
        // Converter DTO para Domain
        Autor autor = autorMapper.toDomain(dto);

        // Salvar e converter resultado para DTO
        Autor autorSalvo = autorRepository.save(autor);
        return autorMapper.toDTO(autorSalvo);
    }
}
