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

class CriarAutorUseCaseTest {

    @Mock
    private AutorRepository autorRepository;

    @Mock
    private AutorMapper autorMapper;

    @InjectMocks
    private CriarAutorUseCase useCase;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Nested
    @DisplayName("Testes de sucesso")
    class SucessoTests {

        @Test
        @DisplayName("Deve criar um novo autor com sucesso")
        void deveCriarAutorComSucesso() {
            // Arrange
            AutorDTO autorDTO = AutorDTO.builder()
                    .nome("Autor de Teste")
                    .build();

            Autor autor = Autor.builder()
                    .nome("Autor de Teste")
                    .build();

            Mockito.when(autorRepository.save(Mockito.any(Autor.class))).thenReturn(autor);
            Mockito.when(autorMapper.toDto(Mockito.any(Autor.class))).thenReturn(autorDTO);

            // Act
            AutorDTO resultado = useCase.execute(autorDTO);

            // Assert
            Assertions.assertNotNull(resultado);
            Assertions.assertEquals(autorDTO.getNome(), resultado.getNome());
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
        @DisplayName("Deve lançar exceção quando nome for nulo ou vazio")
        void deveLancarExcecaoQuandoNomeNuloOuVazio() {
            AutorDTO autorDTO = AutorDTO.builder()
                    .nome(null)
                    .build();

            Assertions.assertThrows(BusinessException.class, () -> useCase.execute(autorDTO));
        }
    }
}