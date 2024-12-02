package com.br.spassu.api.infrastructure.persistence.entity;

import jakarta.persistence.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import static org.junit.jupiter.api.Assertions.*;

class AssuntoEntityTest {

    @Test
    @DisplayName("Deve criar assunto com builder")
    void deveCriarAssuntoComBuilder() {
        AssuntoEntity assunto = AssuntoEntity.builder()
                .codigo(1)
                .descricao("Programação")
                .build();

        assertNotNull(assunto);
        assertEquals(1, assunto.getCodigo());
        assertEquals("Programação", assunto.getDescricao());
        assertNotNull(assunto.getLivros());
        assertTrue(assunto.getLivros().isEmpty());
    }

    @Test
    @DisplayName("Deve verificar anotações JPA")
    void deveVerificarAnotacoesJPA() {
        assertTrue(AssuntoEntity.class.isAnnotationPresent(Entity.class));
        assertTrue(AssuntoEntity.class.isAnnotationPresent(Table.class));
        assertEquals("Assunto", AssuntoEntity.class.getAnnotation(Table.class).name());

        var codigoField = getField("codigo");
        assert codigoField != null;
        assertTrue(codigoField.isAnnotationPresent(Id.class));
        assertTrue(codigoField.isAnnotationPresent(GeneratedValue.class));
        assertEquals(GenerationType.IDENTITY,
                codigoField.getAnnotation(GeneratedValue.class).strategy());

        var descricaoField = getField("descricao");
        assert descricaoField != null;
        var descricaoColumn = descricaoField.getAnnotation(Column.class);
        assertTrue(descricaoField.isAnnotationPresent(Column.class));
        assertEquals("Descricao", descricaoColumn.name());
        assertEquals(20, descricaoColumn.length());
    }

    @Test
    @DisplayName("Deve verificar relacionamento com Livro")
    void deveVerificarRelacionamentoComLivro() {
        var livrosField = getField("livros");
        assert livrosField != null;
        assertTrue(livrosField.isAnnotationPresent(ManyToMany.class));

        ManyToMany manyToMany = livrosField.getAnnotation(ManyToMany.class);
        assertEquals("assuntos", manyToMany.mappedBy());
    }

    private java.lang.reflect.Field getField(String fieldName) {
        try {
            return AssuntoEntity.class.getDeclaredField(fieldName);
        } catch (NoSuchFieldException e) {
            fail("Campo " + fieldName + " não encontrado");
            return null;
        }
    }
}
