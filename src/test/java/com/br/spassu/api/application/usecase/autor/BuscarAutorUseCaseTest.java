package com.br.spassu.api.application.usecase.autor;

import com.br.spassu.api.application.dto.AutorDTO;
import com.br.spassu.api.application.mapper.AutorMapper;
import com.br.spassu.api.domain.entity.Autor;
import com.br.spassu.api.domain.exceptions.BusinessException;
import com.br.spassu.api.domain.exceptions.EntityNotFoundException;
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

import java.util.Optional;

class BuscarAutorUseCaseTest {

    @Mock
    private AutorRepository autorRepository;

    @Mock
    private AutorMapper autorMapper;

    @InjectMocks
    private BuscarAutorUseCase useCase;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Nested
    @DisplayName("Testes de sucesso")
    class SucessoTests {

        @Test
        @DisplayName("Deve buscar um autor com sucesso")
        void deveBuscarAutorComSucesso() {
            // Arrange
            Autor autor = Autor.builder()
                    .codigo(1)
                    .nome("Autor de Teste")
                    .build();

            AutorDTO autorDTO = AutorDTO.builder()
                    .codigo(1)
                    .nome("Autor de Teste")
                    .build();

            Mockito.when(autorRepository.findByCodigo(1)).thenReturn(Optional.of(autor));
            Mockito.when(autorMapper.toDto(Mockito.any(Autor.class))).thenReturn(autorDTO);

            // Act
            AutorDTO resultado = useCase.execute(1);

            // Assert
            Assertions.assertNotNull(resultado);
            Assertions.assertEquals(autor.getCodigo(), resultado.getCodigo());
            Assertions.assertEquals(autor.getNome(), resultado.getNome());
        }
    }

    @Nested
    @DisplayName("Testes de validação")
    class ValidacaoTests {

        @Test
        @DisplayName("Deve lançar exceção quando código for nulo")
        void deveLancarExcecaoQuandoCodigoNulo() {
            Assertions.assertThrows(BusinessException.class, () -> useCase.execute(null));
        }

        @Test
        @DisplayName("Deve lançar exceção quando código for inválido")
        void deveLancarExcecaoQuandoCodigoInvalido() {
            Assertions.assertThrows(BusinessException.class, () -> useCase.execute(0));
        }

        @Test
        @DisplayName("Deve lançar exceção quando autor não for encontrado")
        void deveLancarExcecaoQuandoAutorNaoEncontrado() {
            Mockito.when(autorRepository.findByCodigo(1)).thenReturn(Optional.empty());

            Assertions.assertThrows(EntityNotFoundException.class, () -> useCase.execute(1));
        }
    }
}