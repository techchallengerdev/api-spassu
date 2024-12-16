package com.br.spassu.api.application.usecase.autor;

import com.br.spassu.api.domain.entity.Autor;
import com.br.spassu.api.domain.exceptions.AuthorNotFoundException;
import com.br.spassu.api.domain.exceptions.InvalidAuthorDataException;
import com.br.spassu.api.domain.repository.AutorRepository;
import com.br.spassu.api.infrastructure.response.ResponseWrapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DeletarAutorUseCaseTest {

    @Mock
    private AutorRepository autorRepository;

    @InjectMocks
    private DeletarAutorUseCase deletarAutorUseCase;

    @Nested
    @DisplayName("Testes de sucesso")
    class TestesComSucesso {

        @Test
        @DisplayName("Deve deletar autor com sucesso")
        void deveDeletarAutorComSucesso() {
            Integer codigo = 1;
            Autor autor = Autor.builder()
                    .codigo(codigo)
                    .nome("John Doe")
                    .build();

            when(autorRepository.findByCodigo(codigo)).thenReturn(Optional.of(autor));
            doNothing().when(autorRepository).delete(codigo);

            ResponseWrapper<Void> response = deletarAutorUseCase.execute(codigo);

            assertThat(response).isNotNull();
            assertThat(response.getMessage()).isEqualTo("Autor deletado com sucesso");
            assertThat(response.getData()).isNull();
            verify(autorRepository).delete(codigo);
        }
    }

    @Nested
    @DisplayName("Testes de validação")
    class TestesValidacao {

        @Test
        @DisplayName("Deve lançar exceção quando código for nulo")
        void deveLancarExcecaoQuandoCodigoNulo() {
            assertThatThrownBy(() -> deletarAutorUseCase.execute(null))
                    .isInstanceOf(InvalidAuthorDataException.class)
                    .hasMessage("Código do autor inválido");
        }

        @Test
        @DisplayName("Deve lançar exceção quando código for menor ou igual a zero")
        void deveLancarExcecaoQuandoCodigoMenorIgualZero() {
            assertThatThrownBy(() -> deletarAutorUseCase.execute(0))
                    .isInstanceOf(InvalidAuthorDataException.class)
                    .hasMessage("Código do autor inválido");

            assertThatThrownBy(() -> deletarAutorUseCase.execute(-1))
                    .isInstanceOf(InvalidAuthorDataException.class)
                    .hasMessage("Código do autor inválido");
        }

        @Test
        @DisplayName("Deve lançar exceção quando autor não for encontrado")
        void deveLancarExcecaoQuandoAutorNaoEncontrado() {
            Integer codigo = 1;
            when(autorRepository.findByCodigo(codigo)).thenReturn(Optional.empty());

            assertThatThrownBy(() -> deletarAutorUseCase.execute(codigo))
                    .isInstanceOf(AuthorNotFoundException.class)
                    .hasMessage("Autor com código 1 não encontrado");
        }

        @Test
        @DisplayName("Deve lançar exceção quando ocorrer erro na deleção")
        void deveLancarExcecaoQuandoErroDeletar() {
            Integer codigo = 1;
            Autor autor = Autor.builder()
                    .codigo(codigo)
                    .nome("John Doe")
                    .build();

            when(autorRepository.findByCodigo(codigo)).thenReturn(Optional.of(autor));
            doThrow(new RuntimeException("Erro ao deletar"))
                    .when(autorRepository).delete(codigo);

            assertThatThrownBy(() -> deletarAutorUseCase.execute(codigo))
                    .isInstanceOf(InvalidAuthorDataException.class)
                    .hasMessageContaining("Erro ao excluir autor com código 1");
        }
    }
}