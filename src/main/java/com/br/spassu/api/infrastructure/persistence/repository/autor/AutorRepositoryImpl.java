package com.br.spassu.api.infrastructure.persistence.repository.autor;

import com.br.spassu.api.domain.entity.Autor;
import com.br.spassu.api.domain.repository.AutorRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AutorRepositoryImpl extends JpaRepository<Autor, Integer>, AutorRepository {

    @Override
    default Optional<Autor> findByCodigo(Integer codigo) {
        return findById(codigo);
    }

    @Override
    default void deleteByCodigo(Integer codigo) {
        deleteById(codigo);
    }

    @Override
    default boolean existsByCodigo(Integer codigo) {
        return existsById(codigo);
    }
}