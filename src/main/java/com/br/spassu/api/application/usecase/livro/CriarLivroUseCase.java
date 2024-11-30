package com.br.spassu.api.application.usecase.livro;

import com.br.spassu.api.application.dto.LivroDTO;
import com.br.spassu.api.application.mapper.LivroMapper;
import com.br.spassu.api.domain.entity.Livro;
import com.br.spassu.api.domain.repository.AssuntoRepository;
import com.br.spassu.api.domain.repository.AutorRepository;
import com.br.spassu.api.domain.repository.LivroRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CriarLivroUseCase {
    private final LivroRepository livroRepository;
    private final AutorRepository autorRepository;
    private final AssuntoRepository assuntoRepository;
    private final LivroMapper livroMapper;

    @Transactional
    public LivroDTO execute(LivroDTO dto) {

        // Converter DTO para Domain
        Livro livro = livroMapper.toDomain(dto);

        // Adicionar autores
        if (dto.getIdsAutores() != null) {
            dto.getIdsAutores().forEach(autorId ->
                    autorRepository.findByCodigo(autorId)
                            .ifPresent(livro::adicionarAutor));
        }

        // Adicionar assuntos
        if (dto.getIdsAssuntos() != null) {
            dto.getIdsAssuntos().forEach(assuntoId ->
                    assuntoRepository.findByCodigo(assuntoId)
                            .ifPresent(livro::adicionarAssunto));
        }

        // Salvar e converter resultado para DTO
        Livro livroSalvo = livroRepository.save(livro);
        return livroMapper.toDTO(livroSalvo);
    }
}
