package com.br.spassu.api.application.usecase.assunto;

import com.br.spassu.api.application.dto.AssuntoDTO;
import com.br.spassu.api.application.mapper.AssuntoMapper;
import com.br.spassu.api.domain.entity.Assunto;
import com.br.spassu.api.domain.exceptions.BusinessException;
import com.br.spassu.api.domain.exceptions.EntityNotFoundException;
import com.br.spassu.api.domain.repository.AssuntoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class BuscarAssuntoUseCase {
    private final AssuntoRepository assuntoRepository;
    private final AssuntoMapper assuntoMapper;

    @Transactional(readOnly = true)
    public AssuntoDTO execute(Integer codigo) {
        validarDadosEntrada(codigo);
        Assunto assunto = buscarAssunto(codigo);
        return assuntoMapper.toDto(assunto);
    }

    private void validarDadosEntrada(Integer codigo) {
        if (codigo == null) {
            throw new BusinessException("Código do assunto não informado");
        }
        if (codigo <= 0) {
            throw new BusinessException("Código do assunto deve ser maior que zero");
        }
    }

    private Assunto buscarAssunto(Integer codigo) {
        return assuntoRepository.findByCodigo(codigo)
                .orElseThrow(() -> new EntityNotFoundException(
                        String.format("Assunto com código %d não encontrado", codigo)));
    }
}
