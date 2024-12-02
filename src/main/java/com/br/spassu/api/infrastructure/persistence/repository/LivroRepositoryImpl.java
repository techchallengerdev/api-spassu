package com.br.spassu.api.infrastructure.persistence.repository;

import com.br.spassu.api.application.mapper.LivroMapper;
import com.br.spassu.api.domain.entity.Assunto;
import com.br.spassu.api.domain.entity.Autor;
import com.br.spassu.api.domain.entity.Livro;
import com.br.spassu.api.domain.repository.LivroRepository;
import com.br.spassu.api.infrastructure.persistence.entity.LivroEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class LivroRepositoryImpl implements LivroRepository {
    private final SpringLivroRepository livroRepository;
    private final LivroMapper mapper;

    @Override
    @Transactional
    public Livro save(Livro livro) {
        LivroEntity entity = mapper.toEntity(livro);
        LivroEntity savedEntity = livroRepository.save(entity);
        return mapper.toDomain(savedEntity);
    }

    @Override
    public Optional<Livro> findByCodigo(Integer codigo) {
        return livroRepository.findById(codigo)
                .map(mapper::toDomain);
    }

    @Override
    public List<Livro> findAll() {
        return livroRepository.findAll().stream()
                .map(mapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void delete(Integer codI) {
        livroRepository.deleteById(codI);
    }

    @Override
    public List<Livro> findByAutor(Autor autor) {
        return livroRepository.findByAutoresCodAu(autor.getCodigo()).stream()
                .map(mapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<Livro> findByAssunto(Assunto assunto) {
        return livroRepository.findByAssuntosCodAs(assunto.getCodigo()).stream()
                .map(mapper::toDomain)
                .collect(Collectors.toList());
    }
}