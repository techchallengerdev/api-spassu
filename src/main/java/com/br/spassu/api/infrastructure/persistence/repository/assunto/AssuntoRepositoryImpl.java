package com.br.spassu.api.infrastructure.persistence.repository.assunto;

import com.br.spassu.api.application.mapper.AssuntoMapper;
import com.br.spassu.api.domain.entity.Assunto;
import com.br.spassu.api.domain.repository.AssuntoRepository;
import com.br.spassu.api.infrastructure.persistence.entity.AssuntoEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class AssuntoRepositoryImpl implements AssuntoRepository {

    private final AssuntoJpaRepository jpaRepository;
    private final AssuntoMapper assuntoMapper;

    @Override
    public Assunto save(Assunto assunto) {
        AssuntoEntity entity = assuntoMapper.toEntity(assunto);
        entity = jpaRepository.save(entity);
        return assuntoMapper.toDomain(entity);
    }

    @Override
    public Optional<Assunto> findByCodigo(Integer codigo) {
        return jpaRepository.findById(codigo)
                .map(assuntoMapper::toDomain);
    }

    @Override
    public List<Assunto> findAll() {
        return jpaRepository.findAll().stream()
                .map(assuntoMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteByCodigo(Integer codigo) {
        jpaRepository.deleteById(codigo);
    }

    @Override
    public boolean existsByCodigo(Integer codigo) {
        return jpaRepository.existsById(codigo);
    }
}