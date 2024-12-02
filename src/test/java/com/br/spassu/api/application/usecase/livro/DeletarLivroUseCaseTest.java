package com.br.spassu.api.application.usecase.livro;

import com.br.spassu.api.domain.entity.Livro;
import com.br.spassu.api.domain.exceptions.BusinessException;
import com.br.spassu.api.domain.exceptions.EntityNotFoundException;
import com.br.spassu.api.domain.repository.LivroRepository;
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

class DeletarLivroUseCaseTest {

    @Mock
    private LivroRepository livroRepository;

    @InjectMocks
    private DeletarLivroUseCase useCase;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Nested
    @DisplayName("Testes de sucesso")
    class SucessoTests {

        @Test
        @DisplayName("Deve deletar um livro com sucesso")
        void deveDeletarLivroComSucesso() {
            Livro livro = Livro.builder()
                    .codigo(1)
                    .titulo("Livro de Teste")
                    .editora("Editora de Teste")
                    .edicao(1)
                    .anoPublicacao("2023")
                    .build();

            Mockito.when(livroRepository.findByCodigo(1)).thenReturn(Optional.of(livro));
            Mockito.doNothing().when(livroRepository).delete(1);

            useCase.execute(1);

            Mockito.verify(livroRepository, Mockito.times(1)).delete(1);
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
        @DisplayName("Deve lançar exceção quando livro não for encontrado")
        void deveLancarExcecaoQuandoLivroNaoEncontrado() {
            Mockito.when(livroRepository.findByCodigo(1)).thenReturn(Optional.empty());

            Assertions.assertThrows(EntityNotFoundException.class, () -> useCase.execute(1));
        }

        @Test
        @DisplayName("Deve lançar exceção quando erro ocorrer durante deleção")
        void deveLancarExcecaoQuandoOcorrerErroNaDeleicao() {
            Livro livro = Livro.builder()
                    .codigo(1)
                    .titulo("Livro de Teste")
                    .editora("Editora de Teste")
                    .edicao(1)
                    .anoPublicacao("2023")
                    .build();

            Mockito.when(livroRepository.findByCodigo(1)).thenReturn(Optional.of(livro));
            Mockito.doThrow(new RuntimeException("Erro na deleção")).when(livroRepository).delete(1);

            Assertions.assertThrows(BusinessException.class, () -> useCase.execute(1));
        }
    }
}