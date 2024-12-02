package com.br.spassu.api.application.usecase.livro;

import com.br.spassu.api.application.dto.LivroDTO;
import com.br.spassu.api.domain.entity.Assunto;
import com.br.spassu.api.domain.entity.Autor;
import com.br.spassu.api.domain.entity.Livro;
import com.br.spassu.api.domain.exceptions.BusinessException;
import com.br.spassu.api.domain.repository.AssuntoRepository;
import com.br.spassu.api.domain.repository.AutorRepository;
import com.br.spassu.api.domain.repository.LivroRepository;
import com.br.spassu.api.application.mapper.LivroMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

class CriarLivroUseCaseTest {

    @Mock
    private LivroRepository livroRepository;

    @Mock
    private AutorRepository autorRepository;

    @Mock
    private AssuntoRepository assuntoRepository;

    @Mock
    private LivroMapper livroMapper;

    @InjectMocks
    private CriarLivroUseCase useCase;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Nested
    @DisplayName("Testes de sucesso")
    class SucessoTests {

        @Test
        @DisplayName("Deve criar um novo livro com sucesso")
        void deveCriarLivroComSucesso() {
            // Arrange
            LivroDTO livroDTO = LivroDTO.builder()
                    .titulo("Livro de Teste")
                    .editora("Editora de Teste")
                    .edicao(1)
                    .anoPublicacao("2023")
                    .autorCodAus(Arrays.asList(1, 2))
                    .assuntoCodAss(Arrays.asList(3, 4))
                    .build();

            Autor autor1 = Autor.builder().codigo(1).nome("Autor 1").build();
            Autor autor2 = Autor.builder().codigo(2).nome("Autor 2").build();
            Assunto assunto1 = Assunto.builder().codigo(3).descricao("Assunto 1").build();
            Assunto assunto2 = Assunto.builder().codigo(4).descricao("Assunto 2").build();

            Livro livro = Livro.builder()
                    .titulo("Livro de Teste")
                    .editora("Editora de Teste")
                    .edicao(1)
                    .anoPublicacao("2023")
                    .autores(Arrays.asList(autor1, autor2))
                    .assuntos(Arrays.asList(assunto1, assunto2))
                    .build();

            Mockito.when(autorRepository.findByCodigo(1)).thenReturn(Optional.of(autor1));
            Mockito.when(autorRepository.findByCodigo(2)).thenReturn(Optional.of(autor2));
            Mockito.when(assuntoRepository.findByCodigo(3)).thenReturn(Optional.of(assunto1));
            Mockito.when(assuntoRepository.findByCodigo(4)).thenReturn(Optional.of(assunto2));
            Mockito.when(livroRepository.save(Mockito.any(Livro.class))).thenReturn(livro);
            Mockito.when(livroMapper.toDto(Mockito.any(Livro.class))).thenReturn(livroDTO);

            // Act
            LivroDTO resultado = useCase.execute(livroDTO);

            // Assert
            Assertions.assertNotNull(resultado);
            Assertions.assertEquals(livroDTO.getTitulo(), resultado.getTitulo());
            Assertions.assertEquals(livroDTO.getEditora(), resultado.getEditora());
            Assertions.assertEquals(livroDTO.getEdicao(), resultado.getEdicao());
            Assertions.assertEquals(livroDTO.getAnoPublicacao(), resultado.getAnoPublicacao());
            Assertions.assertEquals(livroDTO.getAutorCodAus(), resultado.getAutorCodAus());
            Assertions.assertEquals(livroDTO.getAssuntoCodAss(), resultado.getAssuntoCodAss());
        }
    }

    @Nested
    @DisplayName("Testes de validação")
    class ValidacaoTests {

        @Test
        @DisplayName("Deve lançar exceção quando DTO for nulo")
        void deveLancarExcecaoQuandoDtoNulo() {
            Assertions.assertThrows(BusinessException.class, () -> useCase.execute(null));
        }

        @Test
        @DisplayName("Deve lançar exceção quando título for nulo ou vazio")
        void deveLancarExcecaoQuandoTituloNuloOuVazio() {
            LivroDTO livroDTO = LivroDTO.builder()
                    .autorCodAus(Arrays.asList(1, 2))
                    .assuntoCodAss(Arrays.asList(3, 4))
                    .build();

            Assertions.assertThrows(BusinessException.class, () -> useCase.execute(livroDTO));
        }

        @Test
        @DisplayName("Deve lançar exceção quando lista de autores for nula ou vazia")
        void deveLancarExcecaoQuandoListaDeAutoresNulaOuVazia() {
            LivroDTO livroDTO = LivroDTO.builder()
                    .titulo("Livro de Teste")
                    .assuntoCodAss(Arrays.asList(3, 4))
                    .build();

            Assertions.assertThrows(BusinessException.class, () -> useCase.execute(livroDTO));
        }

        @Test
        @DisplayName("Deve lançar exceção quando lista de assuntos for nula ou vazia")
        void deveLancarExcecaoQuandoListaDeAssuntosNulaOuVazia() {
            LivroDTO livroDTO = LivroDTO.builder()
                    .titulo("Livro de Teste")
                    .autorCodAus(Arrays.asList(1, 2))
                    .build();

            Assertions.assertThrows(BusinessException.class, () -> useCase.execute(livroDTO));
        }

        @Test
        @DisplayName("Deve lançar exceção quando autor não for encontrado")
        void deveLancarExcecaoQuandoAutorNaoEncontrado() {
            LivroDTO livroDTO = LivroDTO.builder()
                    .titulo("Livro de Teste")
                    .autorCodAus(Arrays.asList(1, 2))
                    .assuntoCodAss(Arrays.asList(3, 4))
                    .build();

            Mockito.when(autorRepository.findByCodigo(1)).thenReturn(Optional.of(Autor.builder().codigo(1).nome("Autor 1").build()));
            Mockito.when(autorRepository.findByCodigo(2)).thenReturn(Optional.empty());

            Assertions.assertThrows(BusinessException.class, () -> useCase.execute(livroDTO));
        }

        @Test
        @DisplayName("Deve lançar exceção quando assunto não for encontrado")
        void deveLancarExcecaoQuandoAssuntoNaoEncontrado() {
            LivroDTO livroDTO = LivroDTO.builder()
                    .titulo("Livro de Teste")
                    .autorCodAus(Arrays.asList(1, 2))
                    .assuntoCodAss(Arrays.asList(3, 4))
                    .build();

            Mockito.when(autorRepository.findByCodigo(Mockito.anyInt())).thenReturn(Optional.of(Autor.builder().codigo(1).nome("Autor 1").build()));
            Mockito.when(assuntoRepository.findByCodigo(3)).thenReturn(Optional.of(Assunto.builder().codigo(3).descricao("Assunto 1").build()));
            Mockito.when(assuntoRepository.findByCodigo(4)).thenReturn(Optional.empty());

            Assertions.assertThrows(BusinessException.class, () -> useCase.execute(livroDTO));
        }

        @Test
        @DisplayName("Deve lançar exceção quando a lista de autores estiver vazia")
        void testeValidarAutores_QuandoListaDeAutoresEstaVazia() {
            List<Autor> autores = Collections.emptyList();

            Assertions.assertThrows(BusinessException.class, () -> useCase.validarAutores(autores));
        }

        @Test
        @DisplayName("Deve lançar exceção quando a lista de assuntos estiver vazia")
        void testeValidarAssuntos_QuandoListaDeAssuntosEstaVazia() {
            List<Assunto> assuntos = Collections.emptyList();

            Assertions.assertThrows(BusinessException.class, () -> useCase.validarAssuntos(assuntos));
        }

        @Test
        @DisplayName("Deve lançar exceção quando o DTO for nulo")
        void testeValidarDadosEntrada_QuandoDtoForNulo() {
            Assertions.assertThrows(BusinessException.class, () -> useCase.validarDadosEntrada(null));
        }

        @Test
        @DisplayName("Deve lançar exceção quando o título for nulo ou vazio")
        void testeValidarDadosEntrada_QuandoTituloForNuloOuVazio() {
            LivroDTO livroDTO = LivroDTO.builder()
                    .autorCodAus(Arrays.asList(1, 2))
                    .assuntoCodAss(Arrays.asList(3, 4))
                    .build();

            Assertions.assertThrows(BusinessException.class, () -> useCase.validarDadosEntrada(livroDTO));
        }

        @Test
        @DisplayName("Deve lançar exceção quando a lista de autores for nula ou vazia")
        void testeValidarDadosEntrada_QuandoListaDeAutoresForNulaOuVazia() {
            LivroDTO livroDTO = LivroDTO.builder()
                    .titulo("Livro de Teste")
                    .assuntoCodAss(Arrays.asList(3, 4))
                    .build();

            Assertions.assertThrows(BusinessException.class, () -> useCase.validarDadosEntrada(livroDTO));
        }

        @Test
        @DisplayName("Deve lançar exceção quando a lista de assuntos for nula ou vazia")
        void testeValidarDadosEntrada_QuandoListaDeAssuntosForNulaOuVazia() {
            LivroDTO livroDTO = LivroDTO.builder()
                    .titulo("Livro de Teste")
                    .autorCodAus(Arrays.asList(1, 2))
                    .build();

            Assertions.assertThrows(BusinessException.class, () -> useCase.validarDadosEntrada(livroDTO));
        }
    }
}
