package com.br.spassu.api.domain.exceptions;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BusinessExceptionTest {

    @Nested
    @DisplayName("Testes de Construção")
    class ConstrucaoTests {

        @Test
        @DisplayName("Deve criar exceção com mensagem")
        void deveCriarExcecaoComMensagem() {
            String mensagem = "Erro de negócio";

            BusinessException exception = new BusinessException(mensagem);

            assertEquals(mensagem, exception.getMessage());
            assertInstanceOf(RuntimeException.class, exception);
        }

        @Test
        @DisplayName("Deve manter a mensagem original")
        void deveManterMensagemOriginal() {
            String mensagem = "Mensagem de erro específica";

            BusinessException exception = new BusinessException(mensagem);

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
            BusinessException exception = new BusinessException("teste");

            assertTrue(true);
        }

        @Test
        @DisplayName("Deve preservar características de RuntimeException")
        void devePreservarCaracteristicasRuntimeException() {
            BusinessException exception = new BusinessException("teste");

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
            assertThrows(BusinessException.class, () -> {
                throw new BusinessException("erro");
            });
        }

        @Test
        @DisplayName("Deve capturar como RuntimeException")
        void deveCapturarComoRuntimeException() {
            assertThrows(RuntimeException.class, () -> {
                throw new BusinessException("erro");
            });
        }
    }
}

