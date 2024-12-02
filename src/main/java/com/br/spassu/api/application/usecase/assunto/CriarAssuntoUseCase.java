package com.br.spassu.api.application.usecase.assunto;

import com.br.spassu.api.application.dto.AssuntoDTO;
import com.br.spassu.api.application.mapper.AssuntoMapper;
import com.br.spassu.api.domain.entity.Assunto;
import com.br.spassu.api.domain.exceptions.BusinessException;
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
        validarDadosEntrada(dto);
        Assunto assunto = criarAssunto(dto);
        Assunto assuntoSalvo = assuntoRepository.save(assunto);
        return assuntoMapper.toDto(assuntoSalvo);
    }

    private void validarDadosEntrada(AssuntoDTO dto) {
        if (dto == null) {
            throw new BusinessException("Dados do assunto não informados");
        }
        if (dto.getDescricao() == null || dto.getDescricao().trim().isEmpty()) {
            throw new BusinessException("Descrição é obrigatória");
        }
    }

    private Assunto criarAssunto(AssuntoDTO dto) {
        return Assunto.builder()
                .descricao(dto.getDescricao())
                .build();
    }
}
