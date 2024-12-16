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
import org.springframework.util.StringUtils;

@Service
@RequiredArgsConstructor
public class AtualizarAutorUseCase {
    private final AutorRepository autorRepository;
    private final AutorMapper autorMapper;

    private static final String SUCESSO_ATUALIZAR_AUTOR = "Autor atualizado com sucesso";

    @Transactional
    public ResponseWrapper<AutorDTO> execute(Integer codigo, AutorDTO autorDTO) {
        validarDadosAutor(autorDTO);
        validarCamposObrigatorios(autorDTO);

        Autor autorExistente = buscarAutorExistente(codigo);
        atualizarAutor(autorExistente, autorDTO);
        autorExistente.validar();

        Autor autorSalvo = autorRepository.save(autorExistente);

        return ResponseWrapper.<AutorDTO>builder()
                .message(SUCESSO_ATUALIZAR_AUTOR)
                .data(autorMapper.toDto(autorSalvo))
                .build();
    }

    private Autor buscarAutorExistente(Integer codigo) {
        if (codigo == null || codigo <= 0) {
            throw new InvalidAuthorDataException("Código do autor inválido");
        }
        return autorRepository.findByCodigo(codigo)
                .orElseThrow(() -> new AuthorNotFoundException(codigo));
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

    private void atualizarAutor(Autor autor, AutorDTO dto) {
        autor.setNome(dto.getNome().trim());
    }
}