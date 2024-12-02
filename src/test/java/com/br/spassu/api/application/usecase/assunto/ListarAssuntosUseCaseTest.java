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

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

class ListarAssuntosUseCaseTest {

    @Mock
    private AssuntoRepository assuntoRepository;

    @Mock
    private AssuntoMapper assuntoMapper;

    @InjectMocks
    private ListarAssuntosUseCase useCase;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Nested
    @DisplayName("Testes de sucesso")
    class SucessoTests {

        @Test
        @DisplayName("Deve listar todos os assuntos com sucesso")
        void deveListarAssuntosComSucesso() {
            Assunto assunto1 = Assunto.builder()
                    .codigo(1)
                    .descricao("Assunto 1")
                    .build();

            Assunto assunto2 = Assunto.builder()
                    .codigo(2)
                    .descricao("Assunto 2")
                    .build();

            List<Assunto> assuntos = Arrays.asList(assunto1, assunto2);

            AssuntoDTO dto1 = AssuntoDTO.builder()
                    .codigo(1)
                    .descricao("Assunto 1")
                    .build();

            AssuntoDTO dto2 = AssuntoDTO.builder()
                    .codigo(2)
                    .descricao("Assunto 2")
                    .build();

            List<AssuntoDTO> dtos = Arrays.asList(dto1, dto2);

            Mockito.when(assuntoRepository.findAll()).thenReturn(assuntos);
            Mockito.when(assuntoMapper.toDto(Mockito.any(Assunto.class))).thenReturn(dto1, dto2);

            List<AssuntoDTO> resultado = useCase.execute();

            Assertions.assertNotNull(resultado);
            Assertions.assertEquals(dtos.size(), resultado.size());
            Assertions.assertEquals(dtos, resultado);
        }
    }

    @Nested
    @DisplayName("Testes de validação")
    class ValidacaoTests {

        @Test
        @DisplayName("Deve lançar exceção quando ocorrer erro ao buscar assuntos")
        void deveLancarExcecaoQuandoOcorrerErroAoBuscarAssuntos() {
            Mockito.when(assuntoRepository.findAll()).thenThrow(new RuntimeException("Erro na busca"));

            BusinessException exception = Assertions.assertThrows(BusinessException.class, () -> useCase.execute());

            Assertions.assertEquals("Erro ao buscar assuntos: Erro na busca", exception.getMessage());
        }

        @Test
        @DisplayName("Deve lançar exceção quando a lista de assuntos for nula")
        void deveLancarExcecaoQuandoListaAssuntosForNula() {
            Mockito.when(assuntoRepository.findAll()).thenReturn(null);

            BusinessException exception = Assertions.assertThrows(BusinessException.class, () -> useCase.execute());

            Assertions.assertEquals("Erro ao recuperar a lista de assuntos", exception.getMessage());
        }

        @Test
        @DisplayName("Deve retornar uma lista vazia quando não houver assuntos")
        void deveRetornarListaVaziaQuandoNaoHouverAssuntos() {
            Mockito.when(assuntoRepository.findAll()).thenReturn(Collections.emptyList());

            List<AssuntoDTO> resultado = useCase.execute();

            Assertions.assertNotNull(resultado);
            Assertions.assertTrue(resultado.isEmpty());
        }
    }
}
