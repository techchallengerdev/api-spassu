package com.br.spassu.api.application.usecase.livro;

import com.br.spassu.api.application.dto.LivroDTO;
import com.br.spassu.api.application.mapper.LivroMapper;
import com.br.spassu.api.domain.entity.Livro;
import com.br.spassu.api.domain.exceptions.BusinessException;
import com.br.spassu.api.domain.repository.LivroRepository;
import com.br.spassu.api.infrastructure.response.ResponseWrapper;
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

class ListarLivrosUseCaseTest {

    @Mock
    private LivroRepository livroRepository;

    @Mock
    private LivroMapper livroMapper;

    @InjectMocks
    private ListarLivrosUseCase useCase;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Nested
    @DisplayName("Testes de sucesso")
    class SucessoTests {

        @Test
        @DisplayName("Deve listar todos os livros com sucesso")
        void deveListarLivrosComSucesso() {

            Livro livro1 = Livro.builder()
                    .codigo(1)
                    .titulo("Livro de Teste 1")
                    .editora("Editora de Teste")
                    .edicao(1)
                    .anoPublicacao("2023")
                    .build();

            Livro livro2 = Livro.builder()
                    .codigo(2)
                    .titulo("Livro de Teste 2")
                    .editora("Editora de Teste")
                    .edicao(2)
                    .anoPublicacao("2024")
                    .build();

            List<Livro> livros = Arrays.asList(livro1, livro2);

            LivroDTO dto1 = LivroDTO.builder()
                    .codigo(1)
                    .titulo("Livro de Teste 1")
                    .editora("Editora de Teste")
                    .edicao(1)
                    .anoPublicacao("2023")
                    .build();

            LivroDTO dto2 = LivroDTO.builder()
                    .codigo(2)
                    .titulo("Livro de Teste 2")
                    .editora("Editora de Teste")
                    .edicao(2)
                    .anoPublicacao("2024")
                    .build();

            List<LivroDTO> dtos = Arrays.asList(dto1, dto2);

            Mockito.when(livroRepository.findAll()).thenReturn(livros);
            Mockito.when(livroMapper.toDto(livro1)).thenReturn(dto1);
            Mockito.when(livroMapper.toDto(livro2)).thenReturn(dto2);

            ResponseWrapper<List<LivroDTO>> resultado = useCase.execute();

            Assertions.assertNotNull(resultado);
            Assertions.assertEquals("Lista de livros recuperada com sucesso", resultado.getMessage());
            Assertions.assertEquals(dtos, resultado.getData());
        }
    }

    @Nested
    @DisplayName("Testes de validação")
    class ValidacaoTests {

        @Test
        @DisplayName("Deve lançar exceção quando ocorrer erro ao buscar livros")
        void deveLancarExcecaoQuandoOcorrerErroAoBuscarLivros() {
            Mockito.when(livroRepository.findAll()).thenThrow(new RuntimeException("Erro na busca"));

            BusinessException exception = Assertions.assertThrows(BusinessException.class, () -> useCase.execute());

            Assertions.assertEquals("Erro ao buscar livros: Erro na busca", exception.getMessage());
        }

        @Test
        @DisplayName("Deve retornar uma lista vazia quando não houver livros")
        void deveRetornarListaVaziaQuandoNaoHouverLivros() {
            Mockito.when(livroRepository.findAll()).thenReturn(Collections.emptyList());

            ResponseWrapper<List<LivroDTO>> resultado = useCase.execute();

            Assertions.assertNotNull(resultado);
            Assertions.assertEquals("Lista de livros recuperada com sucesso", resultado.getMessage());
            Assertions.assertTrue(resultado.getData().isEmpty());
        }
    }
}