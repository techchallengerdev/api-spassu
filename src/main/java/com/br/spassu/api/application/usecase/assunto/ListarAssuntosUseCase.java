package com.br.spassu.api.application.usecase.assunto;

import com.br.spassu.api.application.dto.AssuntoDTO;
import com.br.spassu.api.application.mapper.AssuntoMapper;
import com.br.spassu.api.domain.entity.Assunto;
import com.br.spassu.api.domain.exceptions.BusinessException;
import com.br.spassu.api.domain.repository.AssuntoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ListarAssuntosUseCase {
    private final AssuntoRepository assuntoRepository;
    private final AssuntoMapper assuntoMapper;

    @Transactional(readOnly = true)
    public List<AssuntoDTO> execute() {
        List<Assunto> assuntos = buscarAssuntos();
        validarResultado(assuntos);
        return converterParaDTO(assuntos);
    }

    private List<Assunto> buscarAssuntos() {
        try {
            return assuntoRepository.findAll();
        } catch (Exception e) {
            throw new BusinessException("Erro ao buscar assuntos: " + e.getMessage());
        }
    }

    private void validarResultado(List<Assunto> assuntos) {
        if (assuntos == null) {
            throw new BusinessException("Erro ao recuperar a lista de assuntos");
        }
    }

    private List<AssuntoDTO> converterParaDTO(List<Assunto> assuntos) {
        return assuntos.stream()
                .map(assuntoMapper::toDto)
                .collect(Collectors.toList());
    }
}