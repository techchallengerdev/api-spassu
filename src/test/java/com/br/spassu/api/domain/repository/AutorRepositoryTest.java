package com.br.spassu.api.domain.repository;

import com.br.spassu.api.domain.entity.Autor;
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
class AutorRepositoryTest {
    @Mock
    private AutorRepository repository;

    private Autor autor;

    @BeforeEach
    void setUp() {
        autor = Autor.builder()
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
            when(repository.save(any(Autor.class))).thenReturn(autor);

            Autor autorSalvo = repository.save(autor);

            assertNotNull(autorSalvo);
            assertEquals(autor.getCodigo(), autorSalvo.getCodigo());
            verify(repository).save(autor);
        }
    }

    @Nested
    @DisplayName("Testes de Find")
    class FindTests {
        @Test
        @DisplayName("Deve encontrar autor por código")
        void deveEncontrarAutorPorCodigo() {
            when(repository.findByCodigo(1)).thenReturn(Optional.of(autor));

            Optional<Autor> resultado = repository.findByCodigo(1);

            assertTrue(resultado.isPresent());
            assertEquals(autor.getNome(), resultado.get().getNome());
            verify(repository).findByCodigo(1);
        }

        @Test
        @DisplayName("Deve retornar empty quando autor não encontrado")
        void deveRetornarEmptyQuandoAutorNaoEncontrado() {
            when(repository.findByCodigo(99)).thenReturn(Optional.empty());

            Optional<Autor> resultado = repository.findByCodigo(99);

            assertTrue(resultado.isEmpty());
            verify(repository).findByCodigo(99);
        }

        @Test
        @DisplayName("Deve encontrar todos os autores")
        void deveEncontrarTodosAutores() {
            when(repository.findAll()).thenReturn(List.of(autor));

            List<Autor> resultado = repository.findAll();

            assertFalse(resultado.isEmpty());
            assertEquals(1, resultado.size());
            verify(repository).findAll();
        }
    }

    @Nested
    @DisplayName("Testes de Delete")
    class DeleteTests {
        @Test
        @DisplayName("Deve deletar autor com sucesso")
        void deveDeletarAutorComSucesso() {
            doNothing().when(repository).delete(1);

            repository.delete(1);

            verify(repository).delete(1);
        }
    }
}
