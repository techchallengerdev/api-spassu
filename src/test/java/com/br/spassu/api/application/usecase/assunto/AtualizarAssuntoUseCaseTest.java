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

class AtualizarAssuntoUseCaseTest {

    @Mock
    private AssuntoRepository assuntoRepository;

    @Mock
    private AssuntoMapper assuntoMapper;

    @InjectMocks
    private AtualizarAssuntoUseCase useCase;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Nested
    @DisplayName("Testes de sucesso")
    class SucessoTests {

        @Test
        @DisplayName("Deve atualizar um assunto com sucesso")
        void deveAtualizarAssuntoComSucesso() {
            Integer id = 1;
            AssuntoDTO assuntoDTO = AssuntoDTO.builder()
                    .codigo(id)
                    .descricao("Assunto de Teste Atualizado")
                    .build();

            Assunto assunto = Assunto.builder()
                    .codigo(id)
                    .descricao("Assunto de Teste Atualizado")
                    .build();

            Mockito.when(assuntoRepository.findByCodigo(id)).thenReturn(Optional.of(assunto));
            Mockito.when(assuntoRepository.save(Mockito.any(Assunto.class))).thenReturn(assunto);
            Mockito.when(assuntoMapper.toDto(Mockito.any(Assunto.class))).thenReturn(assuntoDTO);

            AssuntoDTO resultado = useCase.execute(id, assuntoDTO);

            Assertions.assertNotNull(resultado);
            Assertions.assertEquals(id, resultado.getCodigo());
            Assertions.assertEquals("Assunto de Teste Atualizado", resultado.getDescricao());
        }
    }

    @Nested
    @DisplayName("Testes de validação")
    class ValidacaoTests {

        @Test
        @DisplayName("Deve lançar exceção quando DTO for nulo")
        void deveLancarExcecaoQuandoDtoNulo() {
            Assertions.assertThrows(BusinessException.class, () -> useCase.execute(1, null));
        }

        @Test
        @DisplayName("Deve lançar exceção quando descrição for nula ou vazia")
        void deveLancarExcecaoQuandoDescricaoNulaOuVazia() {
            AssuntoDTO assuntoDTO = AssuntoDTO.builder()
                    .descricao(null)
                    .build();

            Assertions.assertThrows(BusinessException.class, () -> useCase.execute(1, assuntoDTO));
        }

        @Test
        @DisplayName("Deve lançar exceção quando assunto não for encontrado")
        void deveLancarExcecaoQuandoAssuntoNaoEncontrado() {
            Integer id = 1;
            AssuntoDTO assuntoDTO = AssuntoDTO.builder()
                    .descricao("Assunto de Teste Atualizado")
                    .build();

            Mockito.when(assuntoRepository.findByCodigo(id)).thenReturn(Optional.empty());

            Assertions.assertThrows(EntityNotFoundException.class, () -> useCase.execute(id, assuntoDTO));
        }
    }
}
