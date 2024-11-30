package com.br.spassu.api.application.usecase.livro;

import com.br.spassu.api.application.dto.LivroDTO;
import com.br.spassu.api.application.mapper.LivroMapper;
import com.br.spassu.api.domain.repository.LivroRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ListarLivrosUseCase {
    private final LivroRepository livroRepository;
    private final LivroMapper livroMapper;

    public List<LivroDTO> execute() {
        return livroRepository.findAll().stream()
                .map(livroMapper::toDTO)
                .collect(Collectors.toList());
    }
}
