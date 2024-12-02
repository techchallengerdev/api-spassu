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
public class AtualizarAutorUseCase {
    private final AutorRepository autorRepository;
    private final AutorMapper autorMapper;

    @Transactional
    public AutorDTO execute(Integer codigo, AutorDTO dto) {
        validarDadosEntrada(codigo, dto);
        Autor autorExistente = buscarAutor(codigo);
        atualizarAutor(autorExistente, dto);
        Autor autorAtualizado = autorRepository.save(autorExistente);
        return autorMapper.toDto(autorAtualizado);
    }

    private void validarDadosEntrada(Integer codigo, AutorDTO dto) {
        if (codigo == null) {
            throw new BusinessException("Código do autor não informado");
        }
        if (codigo <= 0) {
            throw new BusinessException("Código do autor deve ser maior que zero");
        }
        if (dto == null) {
            throw new BusinessException("Dados do autor não informados");
        }
        if (dto.getNome() == null || dto.getNome().trim().isEmpty()) {
            throw new BusinessException("Nome do autor é obrigatório");
        }
    }

    private Autor buscarAutor(Integer codigo) {
        return autorRepository.findByCodigo(codigo)
                .orElseThrow(() -> new EntityNotFoundException(
                        String.format("Autor com código %d não encontrado", codigo)));
    }

    private void atualizarAutor(Autor autor, AutorDTO dto) {
        autor.setNome(dto.getNome());
    }
}