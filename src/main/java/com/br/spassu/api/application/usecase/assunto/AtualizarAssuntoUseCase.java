package com.br.spassu.api.application.usecase.assunto;

import com.br.spassu.api.application.dto.AssuntoDTO;
import com.br.spassu.api.application.mapper.AssuntoMapper;
import com.br.spassu.api.domain.exceptions.EntityNotFoundException;
import com.br.spassu.api.domain.repository.AssuntoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AtualizarAssuntoUseCase {
    private final AssuntoRepository assuntoRepository;
    private final AssuntoMapper assuntoMapper;

    @Transactional
    public AssuntoDTO execute(Integer id, AssuntoDTO dto) {
        return assuntoRepository.findByCodigo(id)
                .map(assunto -> {
                    assunto.setDescricao(dto.getDescricao());
                    return assuntoMapper.toDTO(assuntoRepository.save(assunto));
                })
                .orElseThrow(() -> new EntityNotFoundException("Assunto não encontrado"));
    }
}
