package com.br.spassu.api.infrastructure.persistence.repository.livro;

import com.br.spassu.api.domain.entity.Livro;
import com.br.spassu.api.domain.repository.LivroRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LivroRepositoryImpl extends JpaRepository<Livro, Integer>, LivroRepository {

    @Override
    default Optional<Livro> findByCodigo(Integer codigo) {
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
