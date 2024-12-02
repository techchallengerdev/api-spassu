package com.br.spassu.api.application.usecase.livro;

import com.br.spassu.api.application.dto.LivroDTO;
import com.br.spassu.api.application.mapper.LivroMapper;
import com.br.spassu.api.domain.entity.Livro;
import com.br.spassu.api.domain.exceptions.BusinessException;
import com.br.spassu.api.domain.repository.LivroRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ListarLivrosUseCase {
    private final LivroRepository livroRepository;
    private final LivroMapper livroMapper;

    @Transactional(readOnly = true)
    public List<LivroDTO> execute() {
        List<Livro> livros = buscarLivros();
        validarResultado(livros);
        return converterParaDTO(livros);
    }

    private List<Livro> buscarLivros() {
        try {
            return livroRepository.findAll();
        } catch (Exception e) {
            throw new BusinessException("Erro ao buscar livros: " + e.getMessage());
        }
    }

    private void validarResultado(List<Livro> livros) {
        if (livros == null) {
            throw new BusinessException("Erro ao recuperar a lista de livros");
        }
    }

    private List<LivroDTO> converterParaDTO(List<Livro> livros) {
        return livros.stream()
                .map(livroMapper::toDto)
                .collect(Collectors.toList());
    }
}