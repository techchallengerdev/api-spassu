package com.br.spassu.api.infrastructure.persistence.repository;

import com.br.spassu.api.application.mapper.AssuntoMapper;
import com.br.spassu.api.domain.entity.Assunto;
import com.br.spassu.api.domain.repository.AssuntoRepository;
import com.br.spassu.api.infrastructure.persistence.entity.AssuntoEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AssuntoRepositoryImpl implements AssuntoRepository {
    private final SpringAssuntoRepository assuntoRepository;
    private final AssuntoMapper mapper;

    @Override
    @Transactional
    public Assunto save(Assunto assunto) {
        AssuntoEntity entity = mapper.toEntity(assunto);
        AssuntoEntity savedEntity = assuntoRepository.save(entity);
        return mapper.toDomain(savedEntity);
    }

    @Override
    public Optional<Assunto> findByCodigo(Integer codigo) {
        return assuntoRepository.findById(codigo)
                .map(mapper::toDomain);
    }

    @Override
    public List<Assunto> findAll() {
        return assuntoRepository.findAll().stream()
                .map(mapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void delete(Integer codigo) {
        assuntoRepository.deleteById(codigo);
    }
}