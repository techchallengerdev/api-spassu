package com.br.spassu.api.infrastructure.persistence.repository;

import com.br.spassu.api.application.mapper.AssuntoMapper;
import com.br.spassu.api.domain.entity.Assunto;
import com.br.spassu.api.infrastructure.persistence.entity.AssuntoEntity;
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
class AssuntoRepositoryImplTest {

    @Mock
    private SpringAssuntoRepository springRepository;

    @Mock
    private AssuntoMapper mapper;

    @InjectMocks
    private AssuntoRepositoryImpl assuntoRepository;

    private Assunto assunto;
    private AssuntoEntity assuntoEntity;

    @BeforeEach
    void setUp() {
        assunto = Assunto.builder()
                .codigo(1)
                .descricao("Programação")
                .build();

        assuntoEntity = AssuntoEntity.builder()
                .codigo(1)
                .descricao("Programação")
                .build();
    }

    @Nested
    @DisplayName("Testes de Save")
    class SaveTests {
        @Test
        @DisplayName("Deve salvar assunto com sucesso")
        void deveSalvarAssuntoComSucesso() {
            when(mapper.toEntity(any(Assunto.class))).thenReturn(assuntoEntity);
            when(springRepository.save(any(AssuntoEntity.class))).thenReturn(assuntoEntity);
            when(mapper.toDomain(any(AssuntoEntity.class))).thenReturn(assunto);

            Assunto resultado = assuntoRepository.save(assunto);

            assertNotNull(resultado);
            assertEquals(assunto.getCodigo(), resultado.getCodigo());
            verify(springRepository).save(any(AssuntoEntity.class));
        }
    }

    @Nested
    @DisplayName("Testes de Find")
    class FindTests {
        @Test
        @DisplayName("Deve encontrar assunto por código")
        void deveEncontrarAssuntoPorCodigo() {
            when(springRepository.findById(1)).thenReturn(Optional.of(assuntoEntity));
            when(mapper.toDomain(assuntoEntity)).thenReturn(assunto);

            Optional<Assunto> resultado = assuntoRepository.findByCodigo(1);

            assertTrue(resultado.isPresent());
            assertEquals(assunto.getDescricao(), resultado.get().getDescricao());
            verify(springRepository).findById(1);
        }

        @Test
        @DisplayName("Deve retornar lista de assuntos")
        void deveRetornarListaDeAssuntos() {
            when(springRepository.findAll()).thenReturn(List.of(assuntoEntity));
            when(mapper.toDomain(any(AssuntoEntity.class))).thenReturn(assunto);

            List<Assunto> resultado = assuntoRepository.findAll();

            assertFalse(resultado.isEmpty());
            assertEquals(1, resultado.size());
            verify(springRepository).findAll();
        }
    }

    @Nested
    @DisplayName("Testes de Delete")
    class DeleteTests {
        @Test
        @DisplayName("Deve deletar assunto com sucesso")
        void deveDeletarAssuntoComSucesso() {
            doNothing().when(springRepository).deleteById(1);

            assuntoRepository.delete(1);

            verify(springRepository).deleteById(1);
        }
    }
}
