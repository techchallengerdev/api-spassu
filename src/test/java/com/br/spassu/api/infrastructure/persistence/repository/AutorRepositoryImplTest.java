package com.br.spassu.api.infrastructure.persistence.repository;

import com.br.spassu.api.application.mapper.AutorMapper;
import com.br.spassu.api.domain.entity.Autor;
import com.br.spassu.api.infrastructure.persistence.entity.AutorEntity;
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
class AutorRepositoryImplTest {

    @Mock
    private SpringAutorRepository springRepository;

    @Mock
    private AutorMapper mapper;

    @InjectMocks
    private AutorRepositoryImpl autorRepository;

    private Autor autor;
    private AutorEntity autorEntity;

    @BeforeEach
    void setUp() {
        autor = Autor.builder()
                .codigo(1)
                .nome("Robert C. Martin")
                .build();

        autorEntity = AutorEntity.builder()
                .codigo(1)
                .nome("Robert C. Martin")
                .build();
    }

    @Nested
    @DisplayName("Testes de Save")
    class SaveTests {
        @Test
        @DisplayName("Deve salvar autor com sucesso")
        void deveSalvarAutorComSucesso() {
            when(mapper.toEntity(any(Autor.class))).thenReturn(autorEntity);
            when(springRepository.save(any(AutorEntity.class))).thenReturn(autorEntity);
            when(mapper.toDomain(any(AutorEntity.class))).thenReturn(autor);

            Autor resultado = autorRepository.save(autor);

            assertNotNull(resultado);
            assertEquals(autor.getCodigo(), resultado.getCodigo());
            verify(springRepository).save(any(AutorEntity.class));
        }
    }

    @Nested
    @DisplayName("Testes de Find")
    class FindTests {
        @Test
        @DisplayName("Deve encontrar autor por código")
        void deveEncontrarAutorPorCodigo() {
            when(springRepository.findById(1)).thenReturn(Optional.of(autorEntity));
            when(mapper.toDomain(autorEntity)).thenReturn(autor);

            Optional<Autor> resultado = autorRepository.findByCodigo(1);

            assertTrue(resultado.isPresent());
            assertEquals(autor.getNome(), resultado.get().getNome());
            verify(springRepository).findById(1);
        }

        @Test
        @DisplayName("Deve retornar lista de autores")
        void deveRetornarListaDeAutores() {
            when(springRepository.findAll()).thenReturn(List.of(autorEntity));
            when(mapper.toDomain(any(AutorEntity.class))).thenReturn(autor);

            List<Autor> resultado = autorRepository.findAll();

            assertFalse(resultado.isEmpty());
            assertEquals(1, resultado.size());
            verify(springRepository).findAll();
        }
    }

    @Nested
    @DisplayName("Testes de Delete")
    class DeleteTests {
        @Test
        @DisplayName("Deve deletar autor com sucesso")
        void deveDeletarAutorComSucesso() {
            doNothing().when(springRepository).deleteById(1);

            autorRepository.delete(1);

            verify(springRepository).deleteById(1);
        }
    }
}
