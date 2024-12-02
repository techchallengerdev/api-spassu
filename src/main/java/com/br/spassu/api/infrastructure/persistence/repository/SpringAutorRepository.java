package com.br.spassu.api.infrastructure.persistence.repository;

import com.br.spassu.api.infrastructure.persistence.entity.AutorEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

interface SpringAutorRepository extends JpaRepository<AutorEntity, Integer> {
    Optional<AutorEntity> findByCodigo(Integer codigo);
}
