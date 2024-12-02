package com.br.spassu.api.infrastructure.persistence.entity;

import jakarta.persistence.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class LivroEntityTest {

    @Test
    @DisplayName("Deve criar livro com builder")
    void deveCriarLivroComBuilder() {
        LivroEntity livro = LivroEntity.builder()
                .codigo(1)
                .titulo("Clean Code")
                .editora("Alta Books")
                .edicao(1)
                .anoPublicacao(2008)
                .build();

        assertNotNull(livro);
        assertEquals(1, livro.getCodigo());
        assertEquals("Clean Code", livro.getTitulo());
        assertEquals("Alta Books", livro.getEditora());
        assertEquals(1, livro.getEdicao());
        assertEquals(2008, livro.getAnoPublicacao());
        assertNotNull(livro.getAutores());
        assertNotNull(livro.getAssuntos());
        assertTrue(livro.getAutores().isEmpty());
        assertTrue(livro.getAssuntos().isEmpty());
    }

    @Test
    @DisplayName("Deve verificar anotações JPA")
    void deveVerificarAnotacoesJPA() throws NoSuchFieldException {

        // Verifica anotação de entidade
        assertTrue(LivroEntity.class.isAnnotationPresent(Entity.class));
        assertTrue(LivroEntity.class.isAnnotationPresent(Table.class));
        assertEquals("Livro", LivroEntity.class.getAnnotation(Table.class).name());

        // Verifica ID
        var codigoField = getField("codigo");
        assert codigoField != null;
        assertTrue(codigoField.isAnnotationPresent(Id.class));
        assertTrue(codigoField.isAnnotationPresent(GeneratedValue.class));
        assertEquals(GenerationType.IDENTITY,
                codigoField.getAnnotation(GeneratedValue.class).strategy());

        // Verifica campos obrigatórios
        var tituloField = getField("titulo");
        assert tituloField != null;
        var tituloColumn = tituloField.getAnnotation(Column.class);
        assertTrue(tituloField.isAnnotationPresent(Column.class));
        assertEquals(40, tituloColumn.length());
        assertFalse(tituloColumn.nullable());
    }

    @Test
    @DisplayName("Deve verificar relacionamentos")
    void deveVerificarRelacionamentos() throws NoSuchFieldException {
        var autoresField = getField("autores");
        var assuntosField = getField("assuntos");

        // Verifica ManyToMany com Autor
        assert autoresField != null;
        assertTrue(autoresField.isAnnotationPresent(ManyToMany.class));
        assertTrue(autoresField.isAnnotationPresent(JoinTable.class));

        JoinTable autorJoinTable = autoresField.getAnnotation(JoinTable.class);
        assertEquals("Livro_Autor", autorJoinTable.name());
        assertEquals("livro_codigo", autorJoinTable.joinColumns()[0].name());
        assertEquals("autor_codigo", autorJoinTable.inverseJoinColumns()[0].name());

        // Verifica ManyToMany com Assunto
        assert assuntosField != null;
        assertTrue(assuntosField.isAnnotationPresent(ManyToMany.class));
        assertTrue(assuntosField.isAnnotationPresent(JoinTable.class));

        JoinTable assuntoJoinTable = assuntosField.getAnnotation(JoinTable.class);
        assertEquals("Livro_Assunto", assuntoJoinTable.name());
        assertEquals("livro_codigo", assuntoJoinTable.joinColumns()[0].name());
        assertEquals("assunto_codigo", assuntoJoinTable.inverseJoinColumns()[0].name());
    }

    private java.lang.reflect.Field getField(String fieldName) {
        try {
            return LivroEntity.class.getDeclaredField(fieldName);
        } catch (NoSuchFieldException e) {
            fail("Campo " + fieldName + " não encontrado");
            return null;
        }
    }
}
