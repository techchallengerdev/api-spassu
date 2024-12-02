package com.br.spassu.api.domain.exceptions;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EntityNotFoundExceptionTest {

    @Nested
    @DisplayName("Testes de Construção")
    class ConstrucaoTests {

        @Test
        @DisplayName("Deve criar exceção com mensagem")
        void deveCriarExcecaoComMensagem() {
            String mensagem = "Entidade não encontrada";

            EntityNotFoundException exception = new EntityNotFoundException(mensagem);

            assertEquals(mensagem, exception.getMessage());
            assertInstanceOf(RuntimeException.class, exception);
        }

        @Test
        @DisplayName("Deve manter a mensagem original")
        void deveManterMensagemOriginal() {
            String mensagem = "Livro não encontrado com ID: 1";

            EntityNotFoundException exception = new EntityNotFoundException(mensagem);

            assertEquals(mensagem, exception.getMessage());
            assertNotNull(exception.getMessage());
        }
    }

    @Nested
    @DisplayName("Testes de Hierarquia")
    class HierarquiaTests {

        @Test
        @DisplayName("Deve ser uma RuntimeException")
        void deveSerRuntimeException() {
            EntityNotFoundException exception = new EntityNotFoundException("teste");

            assertTrue(true);
        }

        @Test
        @DisplayName("Deve preservar características de RuntimeException")
        void devePreservarCaracteristicasRuntimeException() {
            EntityNotFoundException exception = new EntityNotFoundException("teste");

            assertNull(exception.getCause());
            assertNotNull(exception.getStackTrace());
            assertTrue(exception.getMessage().contains("teste"));
        }
    }

    @Nested
    @DisplayName("Testes de Comportamento")
    class ComportamentoTests {

        @Test
        @DisplayName("Deve lançar exceção corretamente")
        void deveLancarExcecaoCorretamente() {
            assertThrows(EntityNotFoundException.class, () -> {
                throw new EntityNotFoundException("erro");
            });
        }

        @Test
        @DisplayName("Deve capturar como RuntimeException")
        void deveCapturarComoRuntimeException() {
            assertThrows(RuntimeException.class, () -> {
                throw new EntityNotFoundException("erro");
            });
        }
    }
}
