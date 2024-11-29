package com.br.spassu.api.domain.repository;

import com.br.spassu.api.domain.entity.Livro;

import java.util.List;
import java.util.Optional;

public interface LivroRepository {

    Livro save(Livro livro);

    Optional<Livro> findByCodigo(Integer codigo);

    List<Livro> findAll();

    void deleteByCodigo(Integer codigo);

    List<Livro> findByAutorCodigo(Integer autorCodigo);

    List<Livro> findByAssuntoCodigo(Integer assuntoCodigo);

    boolean existsByCodigo(Integer codigo);
}
