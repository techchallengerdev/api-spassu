package com.br.spassu.api.application.usecase.livro;

import com.br.spassu.api.application.dto.LivroDTO;
import com.br.spassu.api.application.mapper.LivroMapper;
import com.br.spassu.api.domain.entity.Livro;
import com.br.spassu.api.domain.exceptions.BusinessException;
import com.br.spassu.api.domain.repository.LivroRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class BuscarLivroUseCase {
    private final LivroRepository livroRepository;
    private final LivroMapper livroMapper;

    @Transactional(readOnly = true)
    public LivroDTO execute(Integer codigo) {
        validarDadosEntrada(codigo);
        Livro livro = buscarLivro(codigo);
        return livroMapper.toDto(livro);
    }

    private void validarDadosEntrada(Integer codigo) {
        if (codigo == null) {
            throw new BusinessException("Código do livro não informado");
        }
        if (codigo <= 0) {
            throw new BusinessException("Código do livro deve ser maior que zero");
        }
    }

    private Livro buscarLivro(Integer codigo) {
        return livroRepository.findByCodigo(codigo)
                .orElseThrow(() -> new BusinessException(
                        String.format("Livro com código %d não encontrado", codigo)));
    }
}