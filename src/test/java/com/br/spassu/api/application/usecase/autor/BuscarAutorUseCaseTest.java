package com.br.spassu.api.application.usecase.autor;

import com.br.spassu.api.application.dto.AutorDTO;
import com.br.spassu.api.application.mapper.AutorMapper;
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
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BuscarAutorUseCaseTest {

    @Mock
    private AutorRepository autorRepository;

    @Mock
    private AutorMapper autorMapper;

    @InjectMocks
    private BuscarAutorUseCase buscarAutorUseCase;

    @Nested
    @DisplayName("Testes de sucesso")
    class TestesComSucesso {

        @Test
        @DisplayName("Deve buscar autor com sucesso")
        void deveBuscarAutorComSucesso() {
            Integer codigo = 1;
            Autor autor = Autor.builder().codigo(codigo).nome("John Doe").build();
            AutorDTO autorDTO = AutorDTO.builder().codigo(codigo).nome("John Doe").build();

            when(autorRepository.findByCodigo(codigo)).thenReturn(Optional.of(autor));
            when(autorMapper.toDto(autor)).thenReturn(autorDTO);

            ResponseWrapper<AutorDTO> response = buscarAutorUseCase.execute(codigo);

            assertThat(response).isNotNull();
            assertThat(response.getMessage()).isEqualTo("Autor encontrado com sucesso");
            assertThat(response.getData()).isEqualTo(autorDTO);
            assertThat(response.getData().getCodigo()).isEqualTo(codigo);
            assertThat(response.getData().getNome()).isEqualTo("John Doe");
        }
    }

    @Nested
    @DisplayName("Testes de validação")
    class TestesValidacao {

        @Test
        @DisplayName("Deve lançar exceção quando código for nulo")
        void deveLancarExcecaoQuandoCodigoNulo() {
            assertThatThrownBy(() -> buscarAutorUseCase.execute(null))
                    .isInstanceOf(InvalidAuthorDataException.class)
                    .hasMessage("Código do autor inválido");
        }

        @Test
        @DisplayName("Deve lançar exceção quando código for menor ou igual a zero")
        void deveLancarExcecaoQuandoCodigoMenorIgualZero() {
            assertThatThrownBy(() -> buscarAutorUseCase.execute(0))
                    .isInstanceOf(InvalidAuthorDataException.class)
                    .hasMessage("Código do autor inválido");
        }

        @Test
        @DisplayName("Deve lançar exceção quando autor não for encontrado")
        void deveLancarExcecaoQuandoAutorNaoEncontrado() {
            // Arrange
            Integer codigo = 1;
            when(autorRepository.findByCodigo(codigo)).thenReturn(Optional.empty());

            // Act & Assert
            assertThatThrownBy(() -> buscarAutorUseCase.execute(codigo))
                    .isInstanceOf(AuthorNotFoundException.class)
                    .hasMessage("Autor com código 1 não encontrado");
        }
    }
}