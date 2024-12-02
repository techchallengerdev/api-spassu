package com.br.spassu.api.domain.repository;

import com.br.spassu.api.domain.entity.Assunto;
import com.br.spassu.api.domain.entity.Autor;
import com.br.spassu.api.domain.entity.Livro;
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
class LivroRepositoryTest {
    @Mock
    private LivroRepository repository;

    private Livro livro;
    private Autor autor;
    private Assunto assunto;

    @BeforeEach
    void setUp() {
        autor = Autor.builder()
                .codigo(1)
                .nome("Robert C. Martin")
                .build();

        assunto = Assunto.builder()
                .codigo(1)
                .descricao("Programação")
                .build();

        livro = Livro.builder()
                .codigo(1)
                .titulo("Clean Code")
                .editora("Alta Books")
                .edicao(1)
                .anoPublicacao("2008")
                .build();

        livro.adicionarAutor(autor);
        livro.adicionarAssunto(assunto);
    }

    @Nested
    @DisplayName("Testes de Save")
    class SaveTests {
        @Test
        @DisplayName("Deve salvar livro com sucesso")
        void deveSalvarLivroComSucesso() {
            when(repository.save(any(Livro.class))).thenReturn(livro);

            Livro livroSalvo = repository.save(livro);

            assertNotNull(livroSalvo);
            assertEquals(livro.getCodigo(), livroSalvo.getCodigo());
            verify(repository).save(livro);
        }
    }

    @Nested
    @DisplayName("Testes de Find")
    class FindTests {
        @Test
        @DisplayName("Deve encontrar livro por código")
        void deveEncontrarLivroPorCodigo() {
            when(repository.findByCodigo(1)).thenReturn(Optional.of(livro));

            Optional<Livro> resultado = repository.findByCodigo(1);

            assertTrue(resultado.isPresent());
            assertEquals(livro.getTitulo(), resultado.get().getTitulo());
            verify(repository).findByCodigo(1);
        }

        @Test
        @DisplayName("Deve retornar empty quando livro não encontrado")
        void deveRetornarEmptyQuandoLivroNaoEncontrado() {
            when(repository.findByCodigo(99)).thenReturn(Optional.empty());

            Optional<Livro> resultado = repository.findByCodigo(99);

            assertTrue(resultado.isEmpty());
            verify(repository).findByCodigo(99);
        }

        @Test
        @DisplayName("Deve encontrar todos os livros")
        void deveEncontrarTodosLivros() {
            when(repository.findAll()).thenReturn(List.of(livro));

            List<Livro> resultado = repository.findAll();

            assertFalse(resultado.isEmpty());
            assertEquals(1, resultado.size());
            verify(repository).findAll();
        }

        @Test
        @DisplayName("Deve encontrar livros por autor")
        void deveEncontrarLivrosPorAutor() {
            when(repository.findByAutor(autor)).thenReturn(List.of(livro));

            List<Livro> resultado = repository.findByAutor(autor);

            assertFalse(resultado.isEmpty());
            assertEquals(1, resultado.size());
            verify(repository).findByAutor(autor);
        }

        @Test
        @DisplayName("Deve encontrar livros por assunto")
        void deveEncontrarLivrosPorAssunto() {
            when(repository.findByAssunto(assunto)).thenReturn(List.of(livro));

            List<Livro> resultado = repository.findByAssunto(assunto);

            assertFalse(resultado.isEmpty());
            assertEquals(1, resultado.size());
            verify(repository).findByAssunto(assunto);
        }
    }

    @Nested
    @DisplayName("Testes de Delete")
    class DeleteTests {
        @Test
        @DisplayName("Deve deletar livro com sucesso")
        void deveDeletarLivroComSucesso() {
            doNothing().when(repository).delete(1);

            repository.delete(1);

            verify(repository).delete(1);
        }
    }
}
