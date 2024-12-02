package com.br.spassu.api.application.mapper;

import com.br.spassu.api.application.dto.LivroDTO;
import com.br.spassu.api.domain.entity.Assunto;
import com.br.spassu.api.domain.entity.Autor;
import com.br.spassu.api.domain.entity.Livro;
import com.br.spassu.api.infrastructure.persistence.entity.LivroEntity;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

class LivroMapperTest {

    private LivroMapper livroMapper;
    private AutorMapper autorMapper;
    private AssuntoMapper assuntoMapper;

    @BeforeEach
    void setup() {
        autorMapper = new AutorMapper();
        assuntoMapper = new AssuntoMapper();
        livroMapper = new LivroMapper(autorMapper, assuntoMapper);
    }

    @Test
    void testToDto() {
        Autor autor1 = Autor.builder().codigo(1).nome("Autor 1").build();
        Autor autor2 = Autor.builder().codigo(2).nome("Autor 2").build();
        Assunto assunto1 = Assunto.builder().codigo(1).descricao("Assunto 1").build();
        Assunto assunto2 = Assunto.builder().codigo(2).descricao("Assunto 2").build();

        Livro livro = Livro.builder()
                .codigo(1)
                .titulo("Livro de Teste")
                .editora("Editora de Teste")
                .edicao(1)
                .anoPublicacao("2022")
                .autores(Arrays.asList(autor1, autor2))
                .assuntos(Arrays.asList(assunto1, assunto2))
                .build();

        LivroDTO livroDTO = livroMapper.toDto(livro);

        Assertions.assertEquals(livro.getCodigo(), livroDTO.getCodigo());
        Assertions.assertEquals(livro.getTitulo(), livroDTO.getTitulo());
        Assertions.assertEquals(livro.getEditora(), livroDTO.getEditora());
        Assertions.assertEquals(livro.getEdicao(), livroDTO.getEdicao());
        Assertions.assertEquals(livro.getAnoPublicacao(), livroDTO.getAnoPublicacao());
        Assertions.assertEquals(Arrays.asList(1, 2), livroDTO.getAutorCodAus());
        Assertions.assertEquals(Arrays.asList(1, 2), livroDTO.getAssuntoCodAss());
    }

    @Test
    void testToEntity() {
        Autor autor1 = Autor.builder().codigo(1).nome("Autor 1").build();
        Autor autor2 = Autor.builder().codigo(2).nome("Autor 2").build();
        Assunto assunto1 = Assunto.builder().codigo(1).descricao("Assunto 1").build();
        Assunto assunto2 = Assunto.builder().codigo(2).descricao("Assunto 2").build();

        Livro livro = Livro.builder()
                .codigo(1)
                .titulo("Livro de Teste")
                .editora("Editora de Teste")
                .edicao(1)
                .anoPublicacao("2022")
                .autores(Arrays.asList(autor1, autor2))
                .assuntos(Arrays.asList(assunto1, assunto2))
                .build();

        LivroEntity livroEntity = livroMapper.toEntity(livro);

        Assertions.assertEquals(livro.getCodigo(), livroEntity.getCodigo());
        Assertions.assertEquals(livro.getTitulo(), livroEntity.getTitulo());
        Assertions.assertEquals(livro.getEditora(), livroEntity.getEditora());
        Assertions.assertEquals(livro.getEdicao(), livroEntity.getEdicao());
        Assertions.assertEquals(Integer.valueOf(livro.getAnoPublicacao()), livroEntity.getAnoPublicacao());
        Assertions.assertEquals(2, livroEntity.getAutores().size());
        Assertions.assertEquals(2, livroEntity.getAssuntos().size());
    }

    @Test
    void testToDomain() {
        LivroEntity livroEntity = LivroEntity.builder()
                .codigo(1)
                .titulo("Livro de Teste")
                .editora("Editora de Teste")
                .edicao(1)
                .anoPublicacao(2022)
                .autores(Arrays.asList(
                        autorMapper.toEntity(Autor.builder().codigo(1).nome("Autor 1").build()),
                        autorMapper.toEntity(Autor.builder().codigo(2).nome("Autor 2").build())))
                .assuntos(Arrays.asList(
                        assuntoMapper.toEntity(Assunto.builder().codigo(1).descricao("Assunto 1").build()),
                        assuntoMapper.toEntity(Assunto.builder().codigo(2).descricao("Assunto 2").build())))
                .build();

        Livro livro = livroMapper.toDomain(livroEntity);

        Assertions.assertEquals(livroEntity.getCodigo(), livro.getCodigo());
        Assertions.assertEquals(livroEntity.getTitulo(), livro.getTitulo());
        Assertions.assertEquals(livroEntity.getEditora(), livro.getEditora());
        Assertions.assertEquals(livroEntity.getEdicao(), livro.getEdicao());
        Assertions.assertEquals(String.valueOf(livroEntity.getAnoPublicacao()), livro.getAnoPublicacao());
        Assertions.assertEquals(2, livro.getAutores().size());
        Assertions.assertEquals(2, livro.getAssuntos().size());
    }
}
