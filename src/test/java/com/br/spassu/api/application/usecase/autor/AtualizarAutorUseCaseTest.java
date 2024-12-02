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

class AtualizarAutorUseCaseTest {

    @Mock
    private AutorRepository autorRepository;

    @Mock
    private AutorMapper autorMapper;

    @InjectMocks
    private AtualizarAutorUseCase useCase;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Nested
    @DisplayName("Testes de sucesso")
    class SucessoTests {

        @Test
        @DisplayName("Deve atualizar um autor com sucesso")
        void deveAtualizarAutorComSucesso() {
            // Arrange
            Integer id = 1;
            AutorDTO autorDTO = AutorDTO.builder()
                    .codigo(id)
                    .nome("Autor de Teste Atualizado")
                    .build();

            Autor autor = Autor.builder()
                    .codigo(id)
                    .nome("Autor de Teste Atualizado")
                    .build();

            Mockito.when(autorRepository.findByCodigo(id)).thenReturn(Optional.of(autor));
            Mockito.when(autorRepository.save(Mockito.any(Autor.class))).thenReturn(autor);
            Mockito.when(autorMapper.toDto(Mockito.any(Autor.class))).thenReturn(autorDTO);

            // Act
            AutorDTO resultado = useCase.execute(id, autorDTO);

            // Assert
            Assertions.assertNotNull(resultado);
            Assertions.assertEquals(id, resultado.getCodigo());
            Assertions.assertEquals("Autor de Teste Atualizado", resultado.getNome());
        }
    }

    @Nested
    @DisplayName("Testes de validação")
    class ValidacaoTests {

        @Test
        @DisplayName("Deve lançar exceção quando código for nulo")
        void deveLancarExcecaoQuandoCodigoNulo() {
            AutorDTO autorDTO = AutorDTO.builder()
                    .nome("Autor de Teste Atualizado")
                    .build();

            Assertions.assertThrows(BusinessException.class, () -> useCase.execute(null, autorDTO));
        }

        @Test
        @DisplayName("Deve lançar exceção quando código for inválido")
        void deveLancarExcecaoQuandoCodigoInvalido() {
            AutorDTO autorDTO = AutorDTO.builder()
                    .nome("Autor de Teste Atualizado")
                    .build();

            Assertions.assertThrows(BusinessException.class, () -> useCase.execute(0, autorDTO));
        }

        @Test
        @DisplayName("Deve lançar exceção quando DTO for nulo")
        void deveLancarExcecaoQuandoDtoNulo() {
            Assertions.assertThrows(BusinessException.class, () -> useCase.execute(1, null));
        }

        @Test
        @DisplayName("Deve lançar exceção quando nome for nulo ou vazio")
        void deveLancarExcecaoQuandoNomeNuloOuVazio() {
            AutorDTO autorDTO = AutorDTO.builder()
                    .nome(null)
                    .build();

            Assertions.assertThrows(BusinessException.class, () -> useCase.execute(1, autorDTO));
        }

        @Test
        @DisplayName("Deve lançar exceção quando autor não for encontrado")
        void deveLancarExcecaoQuandoAutorNaoEncontrado() {
            Integer id = 1;
            AutorDTO autorDTO = AutorDTO.builder()
                    .nome("Autor de Teste Atualizado")
                    .build();

            Mockito.when(autorRepository.findByCodigo(id)).thenReturn(Optional.empty());

            Assertions.assertThrows(EntityNotFoundException.class, () -> useCase.execute(id, autorDTO));
        }
    }
}