package com.br.spassu.api.application.usecase.livro;

import com.br.spassu.api.application.dto.LivroDTO;
import com.br.spassu.api.application.mapper.LivroMapper;
import com.br.spassu.api.domain.entity.Livro;
import com.br.spassu.api.domain.exceptions.BookNotFoundException;
import com.br.spassu.api.domain.exceptions.BusinessException;
import com.br.spassu.api.domain.repository.LivroRepository;
import com.br.spassu.api.infrastructure.response.ResponseWrapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BuscarLivroUseCase {
    private final LivroRepository livroRepository;
    private final LivroMapper livroMapper;

    private static final String SUCESSO_BUSCAR_LIVRO = "Livro encontrado com sucesso";

    public ResponseWrapper<LivroDTO> execute(Integer codigo) {
        validarCodigo(codigo);

        Livro livro = livroRepository.findByCodigo(codigo)
                .orElseThrow(() -> new BookNotFoundException(codigo));

        return ResponseWrapper.<LivroDTO>builder()
                .message(SUCESSO_BUSCAR_LIVRO)
                .data(livroMapper.toDto(livro))
                .build();
    }

    private void validarCodigo(Integer codigo) {
        if (codigo == null) {
            throw new BusinessException("Código do livro não pode ser nulo");
        }
        if (codigo <= 0) {
            throw new BusinessException("Código do livro deve ser maior que zero");
        }
    }
}