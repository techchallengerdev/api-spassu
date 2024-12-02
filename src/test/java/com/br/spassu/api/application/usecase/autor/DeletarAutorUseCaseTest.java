package com.br.spassu.api.application.usecase.autor;

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

class DeletarAutorUseCaseTest {

    @Mock
    private AutorRepository autorRepository;

    @InjectMocks
    private DeletarAutorUseCase useCase;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Nested
    @DisplayName("Testes de sucesso")
    class SucessoTests {

        @Test
        @DisplayName("Deve deletar um autor com sucesso")
        void deveDeletarAutorComSucesso() {
            // Arrange
            Autor autor = Autor.builder()
                    .codigo(1)
                    .nome("Autor de Teste")
                    .build();

            Mockito.when(autorRepository.findByCodigo(1)).thenReturn(Optional.of(autor));
            Mockito.doNothing().when(autorRepository).delete(1);

            // Act
            useCase.execute(1);

            // Assert
            Mockito.verify(autorRepository, Mockito.times(1)).delete(1);
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

        @Test
        @DisplayName("Deve lançar exceção quando erro ocorrer durante deleção")
        void deveLancarExcecaoQuandoOcorrerErroNaDeleicao() {
            Autor autor = Autor.builder()
                    .codigo(1)
                    .nome("Autor de Teste")
                    .build();

            Mockito.when(autorRepository.findByCodigo(1)).thenReturn(Optional.of(autor));
            Mockito.doThrow(new RuntimeException("Erro na deleção")).when(autorRepository).delete(1);

            Assertions.assertThrows(BusinessException.class, () -> useCase.execute(1));
        }
    }
}
