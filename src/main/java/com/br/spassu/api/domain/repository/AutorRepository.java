package com.br.spassu.api.domain.repository;

import com.br.spassu.api.domain.entity.Autor;

import java.util.List;
import java.util.Optional;

public interface AutorRepository {

    Autor save(Autor autor);

    Optional<Autor> findByCodigo(Integer codigo);

    List<Autor> findAll();

    void deleteByCodigo(Integer codigo);

    boolean existsByCodigo(Integer codigo);
}
