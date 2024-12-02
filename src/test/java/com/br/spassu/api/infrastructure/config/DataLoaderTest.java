package com.br.spassu.api.infrastructure.config;

import com.br.spassu.api.domain.entity.Assunto;
import com.br.spassu.api.domain.entity.Autor;
import com.br.spassu.api.domain.entity.Livro;
import com.br.spassu.api.domain.repository.AssuntoRepository;
import com.br.spassu.api.domain.repository.AutorRepository;
import com.br.spassu.api.domain.repository.LivroRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

@SpringBootTest
class DataLoaderTest {

    @Autowired
    LivroRepository livroRepository;

    @Autowired
    AutorRepository autorRepository;

    @Autowired
    AssuntoRepository assuntoRepository;

    @Test
    void testLoadData() {
        assertAutoresCarregados();
        assertAssuntosCarregados();
        assertLivrosCarregados();
    }

    private void assertAutoresCarregados() {
        Optional<Autor> robertMartin = autorRepository.findByCodigo(1);
        Assertions.assertTrue(robertMartin.isPresent());
        Assertions.assertEquals("Robert C. Martin", robertMartin.get().getNome());

        Optional<Autor> martinFowler = autorRepository.findByCodigo(2);
        Assertions.assertTrue(martinFowler.isPresent());
        Assertions.assertEquals("Martin Fowler", martinFowler.get().getNome());

        Optional<Autor> ericEvans = autorRepository.findByCodigo(3);
        Assertions.assertTrue(ericEvans.isPresent());
        Assertions.assertEquals("Eric Evans", ericEvans.get().getNome());
    }

    private void assertAssuntosCarregados() {
        Optional<Assunto> cleanCode = assuntoRepository.findByCodigo(1);
        Assertions.assertTrue(cleanCode.isPresent());
        Assertions.assertEquals("Clean Code", cleanCode.get().getDescricao());

        Optional<Assunto> kubernetes = assuntoRepository.findByCodigo(2);
        Assertions.assertTrue(kubernetes.isPresent());
        Assertions.assertEquals("Kubernetes", kubernetes.get().getDescricao());

        Optional<Assunto> devops = assuntoRepository.findByCodigo(3);
        Assertions.assertTrue(devops.isPresent());
        Assertions.assertEquals("Devops nativo", devops.get().getDescricao());
    }

    private void assertLivrosCarregados() {
        Optional<Livro> cleanCodeBook = livroRepository.findByCodigo(1);
        Assertions.assertTrue(cleanCodeBook.isPresent());
        Assertions.assertEquals("Clean Code", cleanCodeBook.get().getTitulo());
        Assertions.assertEquals(1, cleanCodeBook.get().getAutores().size());
        Assertions.assertEquals(1, cleanCodeBook.get().getAssuntos().size());

        Optional<Livro> refactoring = livroRepository.findByCodigo(2);
        Assertions.assertTrue(refactoring.isPresent());
        Assertions.assertEquals("Refactoring", refactoring.get().getTitulo());
        Assertions.assertEquals(1, refactoring.get().getAutores().size());
        Assertions.assertEquals(2, refactoring.get().getAssuntos().size());

        Optional<Livro> dddBook = livroRepository.findByCodigo(3);
        Assertions.assertTrue(dddBook.isPresent());
        Assertions.assertEquals("Domain-Driven", dddBook.get().getTitulo());
        Assertions.assertEquals(1, dddBook.get().getAutores().size());
        Assertions.assertEquals(2, dddBook.get().getAssuntos().size());
    }
}
