package com.br.spassu.api.application.usecase.assunto;

import com.br.spassu.api.domain.entity.Assunto;
import com.br.spassu.api.domain.exceptions.BusinessException;
import com.br.spassu.api.domain.exceptions.EntityNotFoundException;
import com.br.spassu.api.domain.repository.AssuntoRepository;
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

class DeletarAssuntoUseCaseTest {

    @Mock
    private AssuntoRepository assuntoRepository;

    @InjectMocks
    private DeletarAssuntoUseCase useCase;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Nested
    @DisplayName("Testes de sucesso")
    class SucessoTests {

        @Test
        @DisplayName("Deve deletar um assunto com sucesso")
        void deveDeletarAssuntoComSucesso() {
            // Arrange
            Assunto assunto = Assunto.builder()
                    .codigo(1)
                    .descricao("Assunto de Teste")
                    .build();

            Mockito.when(assuntoRepository.findByCodigo(1)).thenReturn(Optional.of(assunto));
            Mockito.doNothing().when(assuntoRepository).delete(1);

            // Act
            useCase.execute(1);

            // Assert
            Mockito.verify(assuntoRepository, Mockito.times(1)).delete(1);
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
        @DisplayName("Deve lançar exceção quando assunto não for encontrado")
        void deveLancarExcecaoQuandoAssuntoNaoEncontrado() {
            Mockito.when(assuntoRepository.findByCodigo(1)).thenReturn(Optional.empty());

            Assertions.assertThrows(EntityNotFoundException.class, () -> useCase.execute(1));
        }

        @Test
        @DisplayName("Deve lançar exceção quando erro ocorrer durante deleção")
        void deveLancarExcecaoQuandoOcorrerErroNaDeleicao() {
            Assunto assunto = Assunto.builder()
                    .codigo(1)
                    .descricao("Assunto de Teste")
                    .build();

            Mockito.when(assuntoRepository.findByCodigo(1)).thenReturn(Optional.of(assunto));
            Mockito.doThrow(new RuntimeException("Erro na deleção")).when(assuntoRepository).delete(1);

            Assertions.assertThrows(BusinessException.class, () -> useCase.execute(1));
        }
    }
}
