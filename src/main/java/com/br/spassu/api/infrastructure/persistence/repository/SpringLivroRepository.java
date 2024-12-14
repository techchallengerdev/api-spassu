package com.br.spassu.api.infrastructure.persistence.repository;

import com.br.spassu.api.infrastructure.persistence.entity.LivroEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

interface SpringLivroRepository extends JpaRepository<LivroEntity, Integer> {
    Optional<LivroEntity> findByCodigo(Integer codigo);

    @Query("SELECT l FROM LivroEntity l JOIN l.autores a WHERE a.codigo = :autorCodigo")
    List<LivroEntity> findByAutorCodigo(@Param("autorCodigo") Integer autorCodigo);

    @Query("SELECT l FROM LivroEntity l JOIN l.assuntos a WHERE a.codigo = :assuntoCodigo")
    List<LivroEntity> findByAssuntoCodigo(@Param("assuntoCodigo") Integer assuntoCodigo);
}
