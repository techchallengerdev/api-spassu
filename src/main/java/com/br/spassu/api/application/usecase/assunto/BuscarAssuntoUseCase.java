package com.br.spassu.api.application.usecase.assunto;

import com.br.spassu.api.application.dto.AssuntoDTO;
import com.br.spassu.api.application.mapper.AssuntoMapper;
import com.br.spassu.api.domain.exceptions.EntityNotFoundException;
import com.br.spassu.api.domain.repository.AssuntoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BuscarAssuntoUseCase {
    private final AssuntoRepository assuntoRepository;
    private final AssuntoMapper assuntoMapper;

    public AssuntoDTO execute(Integer id) {
        return assuntoRepository.findByCodigo(id)
                .map(assuntoMapper::toDTO)
                .orElseThrow(() -> new EntityNotFoundException("Assunto não encontrado"));
    }
}
