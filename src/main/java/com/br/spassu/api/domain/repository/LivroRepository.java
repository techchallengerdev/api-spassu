package com.br.spassu.api.domain.repository;

import com.br.spassu.api.domain.entity.Assunto;
import com.br.spassu.api.domain.entity.Autor;
import com.br.spassu.api.domain.entity.Livro;

import java.util.List;
import java.util.Optional;

public interface LivroRepository {
    Livro save(Livro livro);
    Optional<Livro> findByCodigo(Integer codigo);
    List<Livro> findAll();
    void delete(Integer codigo);
    List<Livro> findByAutor(Autor autor);
    List<Livro> findByAssunto(Assunto assunto);
}
