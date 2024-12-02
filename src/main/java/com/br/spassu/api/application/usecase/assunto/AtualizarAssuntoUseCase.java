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
public class AtualizarAssuntoUseCase {
    private final AssuntoRepository assuntoRepository;
    private final AssuntoMapper assuntoMapper;

    @Transactional
    public AssuntoDTO execute(Integer codigo, AssuntoDTO dto) {
        validarDadosEntrada(codigo, dto);
        Assunto assuntoExistente = buscarAssunto(codigo);
        atualizarAssunto(assuntoExistente, dto);
        Assunto assuntoAtualizado = assuntoRepository.save(assuntoExistente);
        return assuntoMapper.toDto(assuntoAtualizado);
    }

    private void validarDadosEntrada(Integer codigo, AssuntoDTO dto) {
        if (codigo == null) {
            throw new BusinessException("Código do assunto não informado");
        }
        if (codigo <= 0) {
            throw new BusinessException("Código do assunto deve ser maior que zero");
        }
        if (dto == null) {
            throw new BusinessException("Dados do assunto não informados");
        }
        if (dto.getDescricao() == null || dto.getDescricao().trim().isEmpty()) {
            throw new BusinessException("Descrição do assunto é obrigatória");
        }
    }

    private Assunto buscarAssunto(Integer codigo) {
        return assuntoRepository.findByCodigo(codigo)
                .orElseThrow(() -> new EntityNotFoundException(
                        String.format("Assunto com código %d não encontrado", codigo)));
    }

    private void atualizarAssunto(Assunto assunto, AssuntoDTO dto) {
        assunto.setDescricao(dto.getDescricao());
    }
}