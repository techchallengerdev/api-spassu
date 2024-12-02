package com.br.spassu.api.application.usecase.livro;

import com.br.spassu.api.application.dto.LivroDTO;
import com.br.spassu.api.application.mapper.LivroMapper;
import com.br.spassu.api.domain.entity.Assunto;
import com.br.spassu.api.domain.entity.Autor;
import com.br.spassu.api.domain.entity.Livro;
import com.br.spassu.api.domain.exceptions.BusinessException;
import com.br.spassu.api.domain.exceptions.EntityNotFoundException;
import com.br.spassu.api.domain.repository.AssuntoRepository;
import com.br.spassu.api.domain.repository.AutorRepository;
import com.br.spassu.api.domain.repository.LivroRepository;
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
class AtualizarLivroUseCaseTest {

    @Mock
    private LivroRepository livroRepository;

    @Mock
    private AutorRepository autorRepository;

    @Mock
    private AssuntoRepository assuntoRepository;

    @Mock
    private LivroMapper livroMapper;

    @InjectMocks
    private AtualizarLivroUseCase useCase;

    private LivroDTO livroDTO;
    private Livro livro;
    private Autor autor;
    private Assunto assunto;

    @BeforeEach
    void setUp() {
        // Configuração dos objetos de teste
        livroDTO = LivroDTO.builder()
                .codigo(1)
                .titulo("Clean Code")
                .editora("Alta Books")
                .edicao(1)
                .anoPublicacao("2008")
                .autorCodAus(List.of(1))
                .assuntoCodAss(List.of(1))
                .build();

        autor = Autor.builder()
                .codigo(1)
                .nome("Robert C. Martin")
                .livros(List.of())
                .build();

        assunto = Assunto.builder()
                .codigo(1)
                .descricao("Programação")
                .livros(List.of())
                .build();

        livro = Livro.builder()
                .codigo(1)
                .titulo("Clean Code")
                .editora("Alta Books")
                .edicao(1)
                .anoPublicacao("2008")
                .autores(List.of(autor))
                .assuntos(List.of(assunto))
                .build();
    }

    @Nested
    @DisplayName("Testes de sucesso")
    class SucessoTests {

        @Test
        @DisplayName("Deve atualizar um livro com sucesso")
        void deveAtualizarLivroComSucesso() {
            // Arrange
            when(livroRepository.findByCodigo(1)).thenReturn(Optional.of(livro));
            when(autorRepository.findByCodigo(1)).thenReturn(Optional.of(autor));
            when(assuntoRepository.findByCodigo(1)).thenReturn(Optional.of(assunto));
            when(livroRepository.save(any(Livro.class))).thenReturn(livro);
            when(livroMapper.toDto(any(Livro.class))).thenReturn(livroDTO);

            // Act
            LivroDTO resultado = useCase.execute(1, livroDTO);

            // Assert
            assertNotNull(resultado);
            assertEquals(livroDTO.getCodigo(), resultado.getCodigo());
            assertEquals(livroDTO.getTitulo(), resultado.getTitulo());
            verify(livroRepository).save(any(Livro.class));
        }
    }

    @Nested
    @DisplayName("Testes de validação")
    class ValidacaoTests {

        @Test
        @DisplayName("Deve lançar exceção quando código for nulo")
        void deveLancarExcecaoQuandoCodigoNulo() {
            BusinessException exception = assertThrows(BusinessException.class,
                    () -> useCase.execute(null, livroDTO));
            assertEquals("Código do livro não informado", exception.getMessage());
        }

        @Test
        @DisplayName("Deve lançar exceção quando código for menor ou igual a zero")
        void deveLancarExcecaoQuandoCodigoInvalido() {
            BusinessException exception = assertThrows(BusinessException.class,
                    () -> useCase.execute(0, livroDTO));
            assertEquals("Código do livro deve ser maior que zero", exception.getMessage());
        }

        @Test
        @DisplayName("Deve lançar exceção quando DTO for nulo")
        void deveLancarExcecaoQuandoDtoNulo() {
            BusinessException exception = assertThrows(BusinessException.class,
                    () -> useCase.execute(1, null));
            assertEquals("Dados do livro não informados", exception.getMessage());
        }

        @Test
        @DisplayName("Deve lançar exceção quando título for nulo")
        void deveLancarExcecaoQuandoTituloNulo() {
            livroDTO.setTitulo(null);
            BusinessException exception = assertThrows(BusinessException.class,
                    () -> useCase.execute(1, livroDTO));
            assertEquals("Título do livro é obrigatório", exception.getMessage());
        }
    }

    @Nested
    @DisplayName("Testes de busca")
    class BuscaTests {

        @Test
        @DisplayName("Deve lançar exceção quando livro não encontrado")
        void deveLancarExcecaoQuandoLivroNaoEncontrado() {
            when(livroRepository.findByCodigo(any())).thenReturn(Optional.empty());

            EntityNotFoundException exception = assertThrows(EntityNotFoundException.class,
                    () -> useCase.execute(1, livroDTO));
            assertEquals("Livro com código 1 não encontrado", exception.getMessage());
        }

        @Test
        @DisplayName("Deve lançar exceção quando autor não encontrado")
        void deveLancarExcecaoQuandoAutorNaoEncontrado() {
            when(livroRepository.findByCodigo(1)).thenReturn(Optional.of(livro));
            when(autorRepository.findByCodigo(any())).thenReturn(Optional.empty());

            BusinessException exception = assertThrows(BusinessException.class,
                    () -> useCase.execute(1, livroDTO));
            assertEquals("Autor com código 1 não encontrado", exception.getMessage());
        }

        @Test
        @DisplayName("Deve lançar exceção quando assunto não encontrado")
        void deveLancarExcecaoQuandoAssuntoNaoEncontrado() {
            when(livroRepository.findByCodigo(1)).thenReturn(Optional.of(livro));
            when(autorRepository.findByCodigo(1)).thenReturn(Optional.of(autor));
            when(assuntoRepository.findByCodigo(any())).thenReturn(Optional.empty());

            BusinessException exception = assertThrows(BusinessException.class,
                    () -> useCase.execute(1, livroDTO));
            assertEquals("Assunto com código 1 não encontrado", exception.getMessage());
        }
    }
}

