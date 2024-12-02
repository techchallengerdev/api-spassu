package com.br.spassu.api.application.usecase.autor;

import com.br.spassu.api.application.dto.AutorDTO;
import com.br.spassu.api.application.mapper.AutorMapper;
import com.br.spassu.api.domain.entity.Autor;
import com.br.spassu.api.domain.exceptions.BusinessException;
import com.br.spassu.api.domain.repository.AutorRepository;
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

    @Transactional(readOnly = true)
    public List<AutorDTO> execute() {
        List<Autor> autores = buscarAutores();
        validarResultado(autores);
        return converterParaDTO(autores);
    }

    private List<Autor> buscarAutores() {
        try {
            return autorRepository.findAll();
        } catch (Exception e) {
            throw new BusinessException("Erro ao buscar autores: " + e.getMessage());
        }
    }

    private void validarResultado(List<Autor> autores) {
        if (autores == null) {
            throw new BusinessException("Erro ao recuperar a lista de autores");
        }
    }

    private List<AutorDTO> converterParaDTO(List<Autor> autores) {
        return autores.stream()
                .map(autorMapper::toDto)
                .collect(Collectors.toList());
    }
}