package com.br.spassu.api.application.usecase.assunto;

import com.br.spassu.api.application.dto.AssuntoDTO;
import com.br.spassu.api.application.mapper.AssuntoMapper;
import com.br.spassu.api.domain.entity.Assunto;
import com.br.spassu.api.domain.repository.AssuntoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CriarAssuntoUseCase {
    private final AssuntoRepository assuntoRepository;
    private final AssuntoMapper assuntoMapper;

    @Transactional
    public AssuntoDTO execute(AssuntoDTO dto) {
        // Converter DTO para Domain
        Assunto assunto = assuntoMapper.toDomain(dto);

        // Salvar e converter resultado para DTO
        Assunto assuntoSalvo = assuntoRepository.save(assunto);
        return assuntoMapper.toDTO(assuntoSalvo);
    }
}
