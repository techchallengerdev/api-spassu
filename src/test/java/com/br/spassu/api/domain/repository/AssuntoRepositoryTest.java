package com.br.spassu.api.domain.repository;

import com.br.spassu.api.domain.entity.Assunto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AssuntoRepositoryTest {
    @Mock
    private AssuntoRepository repository;

    private Assunto assunto;

    @BeforeEach
    void setUp() {
        assunto = Assunto.builder()
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
            when(repository.save(any(Assunto.class))).thenReturn(assunto);

            Assunto assuntoSalvo = repository.save(assunto);

            assertNotNull(assuntoSalvo);
            assertEquals(assunto.getCodigo(), assuntoSalvo.getCodigo());
            verify(repository).save(assunto);
        }
    }

    @Nested
    @DisplayName("Testes de Find")
    class FindTests {
        @Test
        @DisplayName("Deve encontrar assunto por código")
        void deveEncontrarAssuntoPorCodigo() {
            when(repository.findByCodigo(1)).thenReturn(Optional.of(assunto));

            Optional<Assunto> resultado = repository.findByCodigo(1);

            assertTrue(resultado.isPresent());
            assertEquals(assunto.getDescricao(), resultado.get().getDescricao());
            verify(repository).findByCodigo(1);
        }

        @Test
        @DisplayName("Deve retornar empty quando assunto não encontrado")
        void deveRetornarEmptyQuandoAssuntoNaoEncontrado() {
            when(repository.findByCodigo(99)).thenReturn(Optional.empty());

            Optional<Assunto> resultado = repository.findByCodigo(99);

            assertTrue(resultado.isEmpty());
            verify(repository).findByCodigo(99);
        }

        @Test
        @DisplayName("Deve encontrar todos os assuntos")
        void deveEncontrarTodosAssuntos() {
            when(repository.findAll()).thenReturn(List.of(assunto));

            List<Assunto> resultado = repository.findAll();

            assertFalse(resultado.isEmpty());
            assertEquals(1, resultado.size());
            verify(repository).findAll();
        }
    }

    @Nested
    @DisplayName("Testes de Delete")
    class DeleteTests {
        @Test
        @DisplayName("Deve deletar assunto com sucesso")
        void deveDeletarAssuntoComSucesso() {
            doNothing().when(repository).delete(1);

            repository.delete(1);

            verify(repository).delete(1);
        }
    }
}