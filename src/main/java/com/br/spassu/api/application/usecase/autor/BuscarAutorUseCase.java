package com.br.spassu.api.application.usecase.autor;

import com.br.spassu.api.application.dto.AutorDTO;
import com.br.spassu.api.application.mapper.AutorMapper;
import com.br.spassu.api.domain.entity.Autor;
import com.br.spassu.api.domain.exceptions.BusinessException;
import com.br.spassu.api.domain.exceptions.EntityNotFoundException;
import com.br.spassu.api.domain.repository.AutorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class BuscarAutorUseCase {
    private final AutorRepository autorRepository;
    private final AutorMapper autorMapper;

    @Transactional(readOnly = true)
    public AutorDTO execute(Integer codigo) {
        validarDadosEntrada(codigo);
        Autor autor = buscarAutor(codigo);
        return autorMapper.toDto(autor);
    }

    private void validarDadosEntrada(Integer codigo) {
        if (codigo == null) {
            throw new BusinessException("Código do autor não informado");
        }
        if (codigo <= 0) {
            throw new BusinessException("Código do autor deve ser maior que zero");
        }
    }

    private Autor buscarAutor(Integer codigo) {
        return autorRepository.findByCodigo(codigo)
                .orElseThrow(() -> new EntityNotFoundException(
                        String.format("Autor com código %d não encontrado", codigo)));
    }
}