package com.br.spassu.api.infrastructure.persistence.repository;

import com.br.spassu.api.application.mapper.LivroMapper;
import com.br.spassu.api.domain.entity.Assunto;
import com.br.spassu.api.domain.entity.Autor;
import com.br.spassu.api.domain.entity.Livro;
import com.br.spassu.api.infrastructure.persistence.entity.LivroEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class LivroRepositoryImplTest {

    @Mock
    private SpringLivroRepository springRepository;

    @Mock
    private LivroMapper mapper;

    @InjectMocks
    private LivroRepositoryImpl livroRepository;

    private Livro livro;
    private LivroEntity livroEntity;

    @BeforeEach
    void setUp() {
        livro = Livro.builder()
                .codigo(1)
                .titulo("Clean Code")
                .editora("Alta Books")
                .edicao(1)
                .anoPublicacao("2008")
                .build();

        livroEntity = LivroEntity.builder()
                .codigo(1)
                .titulo("Clean Code")
                .editora("Alta Books")
                .edicao(1)
                .anoPublicacao(2008)
                .build();
    }

    @Nested
    @DisplayName("Testes de Save")
    class SaveTests {
        @Test
        @DisplayName("Deve salvar livro com sucesso")
        void deveSalvarLivroComSucesso() {
            when(mapper.toEntity(any(Livro.class))).thenReturn(livroEntity);
            when(springRepository.save(any(LivroEntity.class))).thenReturn(livroEntity);
            when(mapper.toDomain(any(LivroEntity.class))).thenReturn(livro);

            Livro resultado = livroRepository.save(livro);

            assertNotNull(resultado);
            assertEquals(livro.getCodigo(), resultado.getCodigo());
            verify(springRepository).save(any(LivroEntity.class));
            verify(mapper).toEntity(any(Livro.class));
            verify(mapper).toDomain(any(LivroEntity.class));
        }
    }

    @Nested
    @DisplayName("Testes de Find")
    class FindTests {
        @Test
        @DisplayName("Deve encontrar livro por código")
        void deveEncontrarLivroPorCodigo() {
            when(springRepository.findById(1)).thenReturn(Optional.of(livroEntity));
            when(mapper.toDomain(livroEntity)).thenReturn(livro);

            Optional<Livro> resultado = livroRepository.findByCodigo(1);

            assertTrue(resultado.isPresent());
            assertEquals(livro.getTitulo(), resultado.get().getTitulo());
            verify(springRepository).findById(1);
        }

        @Test
        @DisplayName("Deve retornar lista de livros")
        void deveRetornarListaDeLivros() {
            when(springRepository.findAll()).thenReturn(List.of(livroEntity));
            when(mapper.toDomain(any(LivroEntity.class))).thenReturn(livro);

            List<Livro> resultado = livroRepository.findAll();

            assertFalse(resultado.isEmpty());
            assertEquals(1, resultado.size());
            verify(springRepository).findAll();
        }

        @Test
        @DisplayName("Deve buscar livros por autor")
        void deveBuscarLivrosPorAutor() {
            Autor autor = Autor.builder().codigo(1).build();
            when(springRepository.findByAutorCodigo(1)).thenReturn(List.of(livroEntity));
            when(mapper.toDomain(any(LivroEntity.class))).thenReturn(livro);

            List<Livro> resultado = livroRepository.findByAutor(autor);

            assertFalse(resultado.isEmpty());
            assertEquals(1, resultado.size());
            verify(springRepository).findByAutorCodigo(1);
        }

        @Test
        @DisplayName("Deve buscar livros por autor")
        void deveBuscarLivrosPorAssunto() {
            Assunto assunto = Assunto.builder().codigo(1).build();
            when(springRepository.findByAssuntoCodigo(1)).thenReturn(List.of(livroEntity));
            when(mapper.toDomain(any(LivroEntity.class))).thenReturn(livro);

            List<Livro> resultado = livroRepository.findByAssunto(assunto);

            assertFalse(resultado.isEmpty());
            assertEquals(1, resultado.size());
            verify(springRepository).findByAssuntoCodigo(1);
        }
    }

    @Nested
    @DisplayName("Testes de Delete")
    class DeleteTests {
        @Test
        @DisplayName("Deve deletar livro com sucesso")
        void deveDeletarLivroComSucesso() {
            doNothing().when(springRepository).deleteById(1);

            livroRepository.delete(1);

            verify(springRepository).deleteById(1);
        }
    }
}
