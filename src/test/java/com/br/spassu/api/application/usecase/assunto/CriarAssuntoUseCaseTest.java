package com.br.spassu.api.application.usecase.assunto;

import com.br.spassu.api.application.dto.AssuntoDTO;
import com.br.spassu.api.application.mapper.AssuntoMapper;
import com.br.spassu.api.domain.entity.Assunto;
import com.br.spassu.api.domain.exceptions.BusinessException;
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

class CriarAssuntoUseCaseTest {

    @Mock
    private AssuntoRepository assuntoRepository;

    @Mock
    private AssuntoMapper assuntoMapper;

    @InjectMocks
    private CriarAssuntoUseCase useCase;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Nested
    @DisplayName("Testes de sucesso")
    class SucessoTests {

        @Test
        @DisplayName("Deve criar um novo assunto com sucesso")
        void deveCriarAssuntoComSucesso() {
            AssuntoDTO assuntoDTO = AssuntoDTO.builder()
                    .descricao("Assunto de Teste")
                    .build();

            Assunto assunto = Assunto.builder()
                    .descricao("Assunto de Teste")
                    .build();

            Mockito.when(assuntoRepository.save(Mockito.any(Assunto.class))).thenReturn(assunto);
            Mockito.when(assuntoMapper.toDto(Mockito.any(Assunto.class))).thenReturn(assuntoDTO);

            AssuntoDTO resultado = useCase.execute(assuntoDTO);

            Assertions.assertNotNull(resultado);
            Assertions.assertEquals(assuntoDTO.getDescricao(), resultado.getDescricao());
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
        @DisplayName("Deve lançar exceção quando descrição for nula ou vazia")
        void deveLancarExcecaoQuandoDescricaoNulaOuVazia() {
            AssuntoDTO assuntoDTO = AssuntoDTO.builder()
                    .descricao(null)
                    .build();

            Assertions.assertThrows(BusinessException.class, () -> useCase.execute(assuntoDTO));
        }
    }
}
