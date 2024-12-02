package com.br.spassu.api.application.usecase.autor;

import com.br.spassu.api.application.dto.AutorDTO;
import com.br.spassu.api.application.mapper.AutorMapper;
import com.br.spassu.api.domain.entity.Autor;
import com.br.spassu.api.domain.exceptions.BusinessException;
import com.br.spassu.api.domain.repository.AutorRepository;
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

class ListarAutoresUseCaseTest {

    @Mock
    private AutorRepository autorRepository;

    @Mock
    private AutorMapper autorMapper;

    @InjectMocks
    private ListarAutoresUseCase useCase;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Nested
    @DisplayName("Testes de sucesso")
    class SucessoTests {

        @Test
        @DisplayName("Deve listar todos os autores com sucesso")
        void deveListarAutoresComSucesso() {
            // Arrange
            Autor autor1 = Autor.builder()
                    .codigo(1)
                    .nome("Autor 1")
                    .build();

            Autor autor2 = Autor.builder()
                    .codigo(2)
                    .nome("Autor 2")
                    .build();

            List<Autor> autores = Arrays.asList(autor1, autor2);

            AutorDTO dto1 = AutorDTO.builder()
                    .codigo(1)
                    .nome("Autor 1")
                    .build();

            AutorDTO dto2 = AutorDTO.builder()
                    .codigo(2)
                    .nome("Autor 2")
                    .build();

            List<AutorDTO> dtos = Arrays.asList(dto1, dto2);

            Mockito.when(autorRepository.findAll()).thenReturn(autores);
            Mockito.when(autorMapper.toDto(Mockito.any(Autor.class))).thenReturn(dto1, dto2);

            // Act
            List<AutorDTO> resultado = useCase.execute();

            // Assert
            Assertions.assertNotNull(resultado);
            Assertions.assertEquals(dtos.size(), resultado.size());
            Assertions.assertEquals(dtos, resultado);
        }
    }

    @Nested
    @DisplayName("Testes de validação")
    class ValidacaoTests {

        @Test
        @DisplayName("Deve lançar exceção quando ocorrer erro ao buscar autores")
        void deveLancarExcecaoQuandoOcorrerErroAoBuscarAutores() {
            Mockito.when(autorRepository.findAll()).thenThrow(new RuntimeException("Erro na busca"));

            BusinessException exception = Assertions.assertThrows(BusinessException.class, () -> useCase.execute());

            Assertions.assertEquals("Erro ao buscar autores: Erro na busca", exception.getMessage());
        }

        @Test
        @DisplayName("Deve lançar exceção quando a lista de autores for nula")
        void deveLancarExcecaoQuandoListaAutoresForNula() {
            Mockito.when(autorRepository.findAll()).thenReturn(null);

            BusinessException exception = Assertions.assertThrows(BusinessException.class, () -> useCase.execute());

            Assertions.assertEquals("Erro ao recuperar a lista de autores", exception.getMessage());
        }

        @Test
        @DisplayName("Deve retornar uma lista vazia quando não houver autores")
        void deveRetornarListaVaziaQuandoNaoHouverAutores() {
            Mockito.when(autorRepository.findAll()).thenReturn(Collections.emptyList());

            List<AutorDTO> resultado = useCase.execute();

            Assertions.assertNotNull(resultado);
            Assertions.assertTrue(resultado.isEmpty());
        }
    }
}
