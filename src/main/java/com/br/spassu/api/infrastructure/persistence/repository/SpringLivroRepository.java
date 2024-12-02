package com.br.spassu.api.infrastructure.persistence.repository;

import com.br.spassu.api.infrastructure.persistence.entity.LivroEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

interface SpringLivroRepository extends JpaRepository<LivroEntity, Integer> {
    List<LivroEntity> findByAutoresCodAu(Integer codAu);
    List<LivroEntity> findByAssuntosCodAs(Integer codAs);
}
