package com.br.spassu.api.infrastructure.persistence.entity;

import jakarta.persistence.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import static org.junit.jupiter.api.Assertions.*;

class AutorEntityTest {

    @Test
    @DisplayName("Deve criar autor com builder")
    void deveCriarAutorComBuilder() {
        AutorEntity autor = AutorEntity.builder()
                .codigo(1)
                .nome("Robert C. Martin")
                .build();

        assertNotNull(autor);
        assertEquals(1, autor.getCodigo());
        assertEquals("Robert C. Martin", autor.getNome());
        assertNotNull(autor.getLivros());
        assertTrue(autor.getLivros().isEmpty());
    }

    @Test
    @DisplayName("Deve verificar anotações JPA")
    void deveVerificarAnotacoesJPA() {
        assertTrue(AutorEntity.class.isAnnotationPresent(Entity.class));
        assertTrue(AutorEntity.class.isAnnotationPresent(Table.class));
        assertEquals("Autor", AutorEntity.class.getAnnotation(Table.class).name());

        // Verifica ID
        var codigoField = getField("codigo");
        assert codigoField != null;
        assertTrue(codigoField.isAnnotationPresent(Id.class));
        assertTrue(codigoField.isAnnotationPresent(GeneratedValue.class));
        assertEquals(GenerationType.IDENTITY,
                codigoField.getAnnotation(GeneratedValue.class).strategy());

        // Verifica nome
        var nomeField = getField("nome");
        assert nomeField != null;
        var nomeColumn = nomeField.getAnnotation(Column.class);
        assertTrue(nomeField.isAnnotationPresent(Column.class));
        assertEquals(40, nomeColumn.length());
    }

    @Test
    @DisplayName("Deve verificar relacionamento com Livro")
    void deveVerificarRelacionamentoComLivro() {
        var livrosField = getField("livros");
        assert livrosField != null;
        assertTrue(livrosField.isAnnotationPresent(ManyToMany.class));

        ManyToMany manyToMany = livrosField.getAnnotation(ManyToMany.class);
        assertEquals("autores", manyToMany.mappedBy());
    }

    private java.lang.reflect.Field getField(String fieldName) {
        try {
            return AutorEntity.class.getDeclaredField(fieldName);
        } catch (NoSuchFieldException e) {
            fail("Campo " + fieldName + " não encontrado");
            return null;
        }
    }
}
