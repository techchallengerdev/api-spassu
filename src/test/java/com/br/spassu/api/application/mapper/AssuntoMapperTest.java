package com.br.spassu.api.application.mapper;

import com.br.spassu.api.application.dto.AssuntoDTO;
import com.br.spassu.api.domain.entity.Assunto;
import com.br.spassu.api.domain.entity.Livro;
import com.br.spassu.api.infrastructure.persistence.entity.AssuntoEntity;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

class AssuntoMapperTest {

    private AssuntoMapper assuntoMapper;

    @BeforeEach
    void setup() {
        assuntoMapper = new AssuntoMapper();
    }

    @Test
    void testToDto() {
        Livro livro1 = Livro.builder().codigo(1).titulo("Livro 1").build();
        Livro livro2 = Livro.builder().codigo(2).titulo("Livro 2").build();

        Assunto assunto = Assunto.builder()
                .codigo(1)
                .descricao("Assunto de Teste")
                .livros(Arrays.asList(livro1, livro2))
                .build();

        AssuntoDTO assuntoDTO = assuntoMapper.toDto(assunto);

        Assertions.assertEquals(assunto.getCodigo(), assuntoDTO.getCodigo());
        Assertions.assertEquals(assunto.getDescricao(), assuntoDTO.getDescricao());
        Assertions.assertEquals(Arrays.asList(1, 2), assuntoDTO.getLivros());
    }

    @Test
    void testToDomain() {
        AssuntoEntity assuntoEntity = AssuntoEntity.builder()
                .codigo(1)
                .descricao("Assunto de Teste")
                .build();

        Assunto assunto = assuntoMapper.toDomain(assuntoEntity);

        Assertions.assertEquals(assuntoEntity.getCodigo(), assunto.getCodigo());
        Assertions.assertEquals(assuntoEntity.getDescricao(), assunto.getDescricao());
    }

    @Test
    void testToEntity() {
        Assunto assunto = Assunto.builder()
                .codigo(1)
                .descricao("Assunto de Teste")
                .build();

        AssuntoEntity assuntoEntity = assuntoMapper.toEntity(assunto);

        Assertions.assertEquals(assunto.getCodigo(), assuntoEntity.getCodigo());
        Assertions.assertEquals(assunto.getDescricao(), assuntoEntity.getDescricao());
    }
}
