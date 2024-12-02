package com.br.spassu.api.infrastructure.persistence.repository;

import com.br.spassu.api.infrastructure.persistence.entity.AssuntoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

interface SpringAssuntoRepository extends JpaRepository<AssuntoEntity, Integer> {
    Optional<AssuntoEntity> findByCodigo(Integer codigo);
}
