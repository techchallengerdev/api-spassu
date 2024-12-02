package com.br.spassu.api.application.usecase.autor;

import com.br.spassu.api.application.dto.AutorDTO;
import com.br.spassu.api.application.mapper.AutorMapper;
import com.br.spassu.api.domain.entity.Autor;
import com.br.spassu.api.domain.exceptions.BusinessException;
import com.br.spassu.api.domain.repository.AutorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CriarAutorUseCase {
    private final AutorRepository autorRepository;
    private final AutorMapper autorMapper;

    @Transactional
    public AutorDTO execute(AutorDTO dto) {
        validarDadosEntrada(dto);
        Autor autor = criarAutor(dto);
        Autor autorSalvo = autorRepository.save(autor);
        return autorMapper.toDto(autorSalvo);
    }

    private void validarDadosEntrada(AutorDTO dto) {
        if (dto == null) {
            throw new BusinessException("Dados do autor não informados");
        }
        if (dto.getNome() == null || dto.getNome().trim().isEmpty()) {
            throw new BusinessException("Nome é obrigatório");
        }
    }

    private Autor criarAutor(AutorDTO dto) {
        return Autor.builder()
                .nome(dto.getNome())
                .build();
    }
}
