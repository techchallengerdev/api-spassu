package com.br.spassu.api.application.usecase.assunto;

import com.br.spassu.api.domain.entity.Assunto;
import com.br.spassu.api.domain.exceptions.BusinessException;
import com.br.spassu.api.domain.exceptions.EntityNotFoundException;
import com.br.spassu.api.domain.repository.AssuntoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class DeletarAssuntoUseCase {
    private final AssuntoRepository assuntoRepository;

    @Transactional
    public void execute(Integer codigo) {
        validarDadosEntrada(codigo);
        Assunto assunto = buscarAssunto(codigo);
        deletarAssunto(assunto);
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

    private void deletarAssunto(Assunto assunto) {
        try {
            assuntoRepository.delete(assunto.getCodigo());
        } catch (Exception e) {
            throw new BusinessException(
                    String.format("Erro ao excluir assunto com código %d: %s",
                            assunto.getCodigo(), e.getMessage()));
        }
    }
}
