package com.br.spassu.api.application.mapper;

import com.br.spassu.api.application.dto.AutorDTO;
import com.br.spassu.api.domain.entity.Autor;
import com.br.spassu.api.domain.entity.Livro;
import com.br.spassu.api.infrastructure.persistence.entity.AutorEntity;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

class AutorMapperTest {

    private AutorMapper autorMapper;

    @BeforeEach
    void setup() {
        autorMapper = new AutorMapper();
    }

    @Test
    void testToDto() {
        Livro livro1 = Livro.builder().codigo(1).titulo("Livro 1").build();
        Livro livro2 = Livro.builder().codigo(2).titulo("Livro 2").build();

        Autor autor = Autor.builder()
                .codigo(1)
                .nome("Autor de Teste")
                .livros(Arrays.asList(livro1, livro2))
                .build();

        AutorDTO autorDTO = autorMapper.toDto(autor);

        Assertions.assertEquals(autor.getCodigo(), autorDTO.getCodigo());
        Assertions.assertEquals(autor.getNome(), autorDTO.getNome());
        Assertions.assertEquals(Arrays.asList(1, 2), autorDTO.getLivros());
    }

    @Test
    void testToDomain() {
        AutorEntity autorEntity = AutorEntity.builder()
                .codigo(1)
                .nome("Autor de Teste")
                .build();

        Autor autor = autorMapper.toDomain(autorEntity);

        Assertions.assertEquals(autorEntity.getCodigo(), autor.getCodigo());
        Assertions.assertEquals(autorEntity.getNome(), autor.getNome());
    }

    @Test
    void testToEntity() {
        Autor autor = Autor.builder()
                .codigo(1)
                .nome("Autor de Teste")
                .build();

        AutorEntity autorEntity = autorMapper.toEntity(autor);

        Assertions.assertEquals(autor.getCodigo(), autorEntity.getCodigo());
        Assertions.assertEquals(autor.getNome(), autorEntity.getNome());
    }
}
