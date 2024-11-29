package com.br.spassu.api.domain.repository;

import com.br.spassu.api.domain.entity.Assunto;

import java.util.List;
import java.util.Optional;

public interface AssuntoRepository {

    Assunto save(Assunto assunto);

    Optional<Assunto> findByCodigo(Integer codigo);

    List<Assunto> findAll();

    void deleteByCodigo(Integer codigo);

    boolean existsByCodigo(Integer codigo);
}
