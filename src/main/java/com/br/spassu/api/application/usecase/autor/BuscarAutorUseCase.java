package com.br.spassu.api.application.usecase.autor;

import com.br.spassu.api.application.dto.AutorDTO;
import com.br.spassu.api.application.mapper.AutorMapper;
import com.br.spassu.api.domain.entity.Autor;
import com.br.spassu.api.domain.exceptions.AuthorNotFoundException;
import com.br.spassu.api.domain.exceptions.InvalidAuthorDataException;
import com.br.spassu.api.domain.repository.AutorRepository;
import com.br.spassu.api.infrastructure.response.ResponseWrapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class BuscarAutorUseCase {
    private final AutorRepository autorRepository;
    private final AutorMapper autorMapper;

    private static final String SUCESSO_BUSCAR_AUTOR = "Autor encontrado com sucesso";

    @Transactional(readOnly = true)
    public ResponseWrapper<AutorDTO> execute(Integer codigo) {
        validarCodigo(codigo);

        Autor autor = autorRepository.findByCodigo(codigo)
                .orElseThrow(() -> new AuthorNotFoundException(codigo));

        return ResponseWrapper.<AutorDTO>builder()
                .message(SUCESSO_BUSCAR_AUTOR)
                .data(autorMapper.toDto(autor))
                .build();
    }

    private void validarCodigo(Integer codigo) {
        if (codigo == null || codigo <= 0) {
            throw new InvalidAuthorDataException("Código do autor inválido");
        }
    }
}