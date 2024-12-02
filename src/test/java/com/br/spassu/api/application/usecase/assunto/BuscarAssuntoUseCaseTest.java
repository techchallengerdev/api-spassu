package com.br.spassu.api.application.usecase.assunto;

import com.br.spassu.api.application.dto.AssuntoDTO;
import com.br.spassu.api.application.mapper.AssuntoMapper;
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

class BuscarAssuntoUseCaseTest {

    @Mock
    private AssuntoRepository assuntoRepository;

    @Mock
    private AssuntoMapper assuntoMapper;

    @InjectMocks
    private BuscarAssuntoUseCase useCase;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Nested
    @DisplayName("Testes de sucesso")
    class SucessoTests {

        @Test
        @DisplayName("Deve buscar um assunto com sucesso")
        void deveBuscarAssuntoComSucesso() {
            Assunto assunto = Assunto.builder()
                    .codigo(1)
                    .descricao("Assunto de Teste")
                    .build();

            AssuntoDTO assuntoDTO = AssuntoDTO.builder()
                    .codigo(1)
                    .descricao("Assunto de Teste")
                    .build();

            Mockito.when(assuntoRepository.findByCodigo(1)).thenReturn(Optional.of(assunto));
            Mockito.when(assuntoMapper.toDto(Mockito.any(Assunto.class))).thenReturn(assuntoDTO);

            AssuntoDTO resultado = useCase.execute(1);

            Assertions.assertNotNull(resultado);
            Assertions.assertEquals(assunto.getCodigo(), resultado.getCodigo());
            Assertions.assertEquals(assunto.getDescricao(), resultado.getDescricao());
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
    }
}
