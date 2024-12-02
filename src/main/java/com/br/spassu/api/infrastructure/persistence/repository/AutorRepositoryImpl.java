package com.br.spassu.api.infrastructure.persistence.repository;

import com.br.spassu.api.application.mapper.AutorMapper;
import com.br.spassu.api.domain.entity.Autor;
import com.br.spassu.api.domain.repository.AutorRepository;
import com.br.spassu.api.infrastructure.persistence.entity.AutorEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AutorRepositoryImpl implements AutorRepository {
    private final SpringAutorRepository autorRepository;
    private final AutorMapper mapper;

    @Override
    @Transactional
    public Autor save(Autor autor) {
        AutorEntity entity = mapper.toEntity(autor);
        AutorEntity savedEntity = autorRepository.save(entity);
        return mapper.toDomain(savedEntity);
    }

    @Override
    public Optional<Autor> findByCodigo(Integer codigo) {
        return autorRepository.findById(codigo)
                .map(mapper::toDomain);
    }

    @Override
    public List<Autor> findAll() {
        return autorRepository.findAll().stream()
                .map(mapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void delete(Integer codigo) {
        autorRepository.deleteById(codigo);
    }
}