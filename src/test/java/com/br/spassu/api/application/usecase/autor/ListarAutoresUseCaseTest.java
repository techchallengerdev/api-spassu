package com.br.spassu.api.application.usecase.autor;

import com.br.spassu.api.application.dto.AutorDTO;
import com.br.spassu.api.application.mapper.AutorMapper;
import com.br.spassu.api.domain.entity.Autor;
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

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ListarAutoresUseCaseTest {

    @Mock
    private AutorRepository autorRepository;

    @Mock
    private AutorMapper autorMapper;

    @InjectMocks
    private ListarAutoresUseCase listarAutoresUseCase;

    @Nested
    @DisplayName("Testes de sucesso")
    class TestesComSucesso {

        @Test
        @DisplayName("Deve listar autores com sucesso")
        void deveListarAutoresComSucesso() {
            List<Autor> autores = Arrays.asList(
                    Autor.builder().codigo(1).nome("John Doe").build(),
                    Autor.builder().codigo(2).nome("Jane Doe").build()
            );

            List<AutorDTO> autoresDTO = Arrays.asList(
                    AutorDTO.builder().codigo(1).nome("John Doe").build(),
                    AutorDTO.builder().codigo(2).nome("Jane Doe").build()
            );

            when(autorRepository.findAll()).thenReturn(autores);
            when(autorMapper.toDto(any(Autor.class))).thenReturn(autoresDTO.get(0), autoresDTO.get(1));

            ResponseWrapper<List<AutorDTO>> response = listarAutoresUseCase.execute();

            assertThat(response).isNotNull();
            assertThat(response.getMessage()).isEqualTo("Lista de autores recuperada com sucesso");
            assertThat(response.getData()).hasSize(2);
            assertThat(response.getData().get(0).getNome()).isEqualTo("John Doe");
            assertThat(response.getData().get(1).getNome()).isEqualTo("Jane Doe");
            verify(autorRepository).findAll();
            verify(autorMapper, times(2)).toDto(any(Autor.class));
        }

        @Test
        @DisplayName("Deve retornar lista vazia quando não houver autores")
        void deveRetornarListaVaziaQuandoNaoHouverAutores() {
            when(autorRepository.findAll()).thenReturn(Collections.emptyList());

            ResponseWrapper<List<AutorDTO>> response = listarAutoresUseCase.execute();

            assertThat(response).isNotNull();
            assertThat(response.getMessage()).isEqualTo("Lista de autores recuperada com sucesso");
            assertThat(response.getData()).isEmpty();
            verify(autorRepository).findAll();
            verify(autorMapper, never()).toDto(any(Autor.class));
        }
    }

    @Nested
    @DisplayName("Testes de erro")
    class TestesErro {

        @Test
        @DisplayName("Deve lançar exceção quando ocorrer erro ao buscar autores")
        void deveLancarExcecaoQuandoErroBuscarAutores() {
            when(autorRepository.findAll()).thenThrow(new RuntimeException("Erro ao buscar no banco"));

            assertThatThrownBy(() -> listarAutoresUseCase.execute())
                    .isInstanceOf(InvalidAuthorDataException.class)
                    .hasMessageContaining("Erro ao buscar autores");
        }
    }
}