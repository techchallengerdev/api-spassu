package com.br.spassu.api.application.usecase.autor;

import com.br.spassu.api.application.dto.AutorDTO;
import com.br.spassu.api.application.mapper.AutorMapper;
import com.br.spassu.api.domain.entity.Autor;
import com.br.spassu.api.domain.exceptions.InvalidAuthorDataException;
import com.br.spassu.api.domain.repository.AutorRepository;
import com.br.spassu.api.infrastructure.response.ResponseWrapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ListarAutoresUseCase {
    private final AutorRepository autorRepository;
    private final AutorMapper autorMapper;

    private static final String SUCESSO_LISTAR_AUTORES = "Lista de autores recuperada com sucesso";

    @Transactional(readOnly = true)
    public ResponseWrapper<List<AutorDTO>> execute() {
        try {
            List<Autor> autores = autorRepository.findAll();
            return ResponseWrapper.<List<AutorDTO>>builder()
                    .message(SUCESSO_LISTAR_AUTORES)
                    .data(autores.stream()
                            .map(autorMapper::toDto)
                            .collect(Collectors.toList()))
                    .build();
        } catch (Exception e) {
            throw new InvalidAuthorDataException("Erro ao buscar autores: " + e.getMessage());
        }
    }
}