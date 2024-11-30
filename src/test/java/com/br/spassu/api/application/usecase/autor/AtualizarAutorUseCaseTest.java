package com.br.spassu.api.application.usecase.autor;

import com.br.spassu.api.application.dto.AutorDTO;
import com.br.spassu.api.application.mapper.AutorMapper;
import com.br.spassu.api.domain.entity.Autor;
import com.br.spassu.api.domain.exceptions.EntityNotFoundException;
import com.br.spassu.api.domain.repository.AutorRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DisplayName("Testes para Atualizar Autor Use Case")
class AtualizarAutorUseCaseTest {

    private static final Integer AUTOR_ID = 1;
    private static final String NOME_ORIGINAL = "Robert C. Martin";
    private static final String NOME_ATUALIZADO = "Uncle Bob";

    @Mock
    private AutorRepository autorRepository;

    @Mock
    private AutorMapper autorMapper;

    @InjectMocks
    private AtualizarAutorUseCase atualizarAutorUseCase;

    @Nested
    @DisplayName("Testes de sucesso")
    class SucessoTests {
        @Test
        @DisplayName("Deve atualizar autor com sucesso quando dados válidos")
        void deveAtualizarAutorComSucesso() {
            var autorExistente = criarAutor();
            var autorDTO = criarAutorDTO(NOME_ATUALIZADO);
            configurarMocksParaSucesso(autorExistente, autorDTO);

            var resultado = atualizarAutorUseCase.execute(AUTOR_ID, autorDTO);

            assertResultadoSucesso(resultado);
            verificarChamadasRepository(autorExistente);
        }

        @Test
        @DisplayName("Deve permitir atualização com campos opcionais nulos")
        void devePermitirAtualizacaoComCamposOpcionais() {
            var autorExistente = criarAutor();
            var autorDTO = new AutorDTO();

            when(autorRepository.findByCodigo(AUTOR_ID)).thenReturn(Optional.of(autorExistente));
            when(autorRepository.save(any(Autor.class))).thenReturn(autorExistente);
            when(autorMapper.toDTO(any(Autor.class))).thenReturn(autorDTO);

            var resultado = atualizarAutorUseCase.execute(AUTOR_ID, autorDTO);

            assertThat(resultado).isNotNull();
            verify(autorRepository).findByCodigo(AUTOR_ID);
            verify(autorRepository).save(autorExistente);
            verify(autorMapper).toDTO(autorExistente);
        }
    }

    @Nested
    @DisplayName("Testes de falha")
    class FalhaTests {
        @Test
        @DisplayName("Deve lançar exceção quando autor não existe")
        void deveLancarExcecaoQuandoAutorInexistente() {
            var autorDTO = criarAutorDTO(NOME_ATUALIZADO);
            when(autorRepository.findByCodigo(AUTOR_ID)).thenReturn(Optional.empty());

            assertThatThrownBy(() -> atualizarAutorUseCase.execute(AUTOR_ID, autorDTO))
                    .isInstanceOf(EntityNotFoundException.class)
                    .hasMessage("Autor não encontrado");
        }
    }

    private void configurarMocksParaSucesso(Autor autorExistente, AutorDTO autorDTO) {
        when(autorRepository.findByCodigo(AUTOR_ID)).thenReturn(Optional.of(autorExistente));
        when(autorRepository.save(any(Autor.class))).thenReturn(autorExistente);
        when(autorMapper.toDTO(any(Autor.class))).thenReturn(autorDTO);
    }

    private void assertResultadoSucesso(AutorDTO resultado) {
        assertThat(resultado)
                .isNotNull()
                .satisfies(dto -> {
                    assertThat(dto.getId()).isEqualTo(AUTOR_ID);
                    assertThat(dto.getNome()).isEqualTo(NOME_ATUALIZADO);
                });
    }

    private void verificarChamadasRepository(Autor autorExistente) {
        verify(autorRepository).findByCodigo(AUTOR_ID);
        verify(autorRepository).save(autorExistente);
        verify(autorMapper).toDTO(autorExistente);
    }

    private AutorDTO criarAutorDTO(String nome) {
        var dto = new AutorDTO();
        dto.setId(AUTOR_ID);
        dto.setNome(nome);
        return dto;
    }

    private Autor criarAutor() {
        var autor = new Autor();
        autor.setCodigo(AUTOR_ID);
        autor.setNome(NOME_ORIGINAL);
        return autor;
    }
}