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
import org.springframework.util.StringUtils;

@Service
@RequiredArgsConstructor
public class CriarAutorUseCase {
    private final AutorRepository autorRepository;
    private final AutorMapper autorMapper;

    private static final String SUCESSO_CRIAR_AUTOR = "Autor criado com sucesso";

    @Transactional
    public ResponseWrapper<AutorDTO> execute(AutorDTO autorDTO) {
        validarDadosAutor(autorDTO);
        validarCamposObrigatorios(autorDTO);

        Autor autor = criarAutor(autorDTO);
        autor.validar();

        Autor autorSalvo = autorRepository.save(autor);

        return ResponseWrapper.<AutorDTO>builder()
                .message(SUCESSO_CRIAR_AUTOR)
                .data(autorMapper.toDto(autorSalvo))
                .build();
    }

    private void validarDadosAutor(AutorDTO autorDTO) {
        if (autorDTO == null) {
            throw new InvalidAuthorDataException("Dados do autor não informados");
        }
    }

    private void validarCamposObrigatorios(AutorDTO autorDTO) {
        if (!StringUtils.hasText(autorDTO.getNome())) {
            throw new InvalidAuthorDataException("Nome não informado, campo obrigatório");
        }
    }

    private Autor criarAutor(AutorDTO autorDTO) {
        return Autor.builder()
                .nome(autorDTO.getNome().trim())
                .build();
    }
}