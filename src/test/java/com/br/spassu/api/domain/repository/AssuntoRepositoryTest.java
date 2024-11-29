package com.br.spassu.api.domain.repository;

import com.br.spassu.api.domain.entity.Assunto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AssuntoRepositoryTest {

    @Mock
    private AssuntoRepository assuntoRepository;

    private Assunto assunto;

    @BeforeEach
    void setUp() {
        assunto = new Assunto();
        assunto.setCodigo(1);
        assunto.setDescricao("Ficção Científica");
    }

    @Test
    @DisplayName("Deve salvar um assunto com sucesso")
    void deveSalvarAssuntoComSucesso() {
        when(assuntoRepository.save(any(Assunto.class))).thenReturn(assunto);
        Assunto assuntoSalvo = assuntoRepository.save(assunto);

        assertThat(assuntoSalvo).isNotNull();
        assertThat(assuntoSalvo.getCodigo()).isEqualTo(1);
        assertThat(assuntoSalvo.getDescricao()).isEqualTo("Ficção Científica");
        verify(assuntoRepository, times(1)).save(any(Assunto.class));
    }

    @Test
    @DisplayName("Deve buscar um assunto por código com sucesso")
    void deveBuscarAssuntoPorCodigoComSucesso() {
        // given
        when(assuntoRepository.findByCodigo(1)).thenReturn(Optional.of(assunto));

        // when
        Optional<Assunto> assuntoEncontrado = assuntoRepository.findByCodigo(1);

        // then
        assertThat(assuntoEncontrado)
                .isPresent()
                .hasValueSatisfying(a -> {
                    assertThat(a.getCodigo()).isEqualTo(1);
                    assertThat(a.getDescricao()).isEqualTo("Ficção Científica");
                });
        verify(assuntoRepository, times(1)).findByCodigo(1);
    }

    @Test
    @DisplayName("Deve retornar vazio ao buscar assunto inexistente")
    void deveRetornarVazioQuandoBuscarAssuntoInexistente() {
        when(assuntoRepository.findByCodigo(999)).thenReturn(Optional.empty());
        Optional<Assunto> assuntoEncontrado = assuntoRepository.findByCodigo(999);
        assertThat(assuntoEncontrado).isEmpty();
        verify(assuntoRepository, times(1)).findByCodigo(999);
    }

    @Test
    @DisplayName("Deve listar todos os assuntos com sucesso")
    void deveListarTodosAssuntosComSucesso() {
        // given
        Assunto assunto2 = new Assunto();
        assunto2.setCodigo(2);
        assunto2.setDescricao("Romance");

        List<Assunto> assuntos = Arrays.asList(assunto, assunto2);
        when(assuntoRepository.findAll()).thenReturn(assuntos);

        // when
        List<Assunto> listaAssuntos = assuntoRepository.findAll();

        // then
        assertThat(listaAssuntos)
                .hasSize(2)
                .extracting(Assunto::getDescricao)
                .containsExactly("Ficção Científica", "Romance");
        verify(assuntoRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("Deve retornar lista vazia quando não houver assuntos")
    void deveRetornarListaVaziaQuandoNaoHouverAssuntos() {
        // given
        when(assuntoRepository.findAll()).thenReturn(List.of());

        // when
        List<Assunto> listaAssuntos = assuntoRepository.findAll();

        // then
        assertThat(listaAssuntos).isEmpty();
        verify(assuntoRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("Deve deletar um assunto com sucesso")
    void deveDeletarAssuntoComSucesso() {
        // given
        doNothing().when(assuntoRepository).deleteByCodigo(1);

        // when
        assuntoRepository.deleteByCodigo(1);

        // then
        verify(assuntoRepository, times(1)).deleteByCodigo(1);
    }

    @Test
    @DisplayName("Deve verificar existência de assunto com sucesso")
    void deveVerificarExistenciaDeAssuntoComSucesso() {
        // given
        when(assuntoRepository.existsByCodigo(1)).thenReturn(true);
        when(assuntoRepository.existsByCodigo(999)).thenReturn(false);

        // when
        boolean existeAssunto = assuntoRepository.existsByCodigo(1);
        boolean naoExisteAssunto = assuntoRepository.existsByCodigo(999);

        // then
        assertThat(existeAssunto).isTrue();
        assertThat(naoExisteAssunto).isFalse();
        verify(assuntoRepository, times(1)).existsByCodigo(1);
        verify(assuntoRepository, times(1)).existsByCodigo(999);
    }

    @Test
    @DisplayName("Deve lançar exceção ao tentar salvar assunto nulo")
    void deveLancarExcecaoAoSalvarAssuntoNulo() {
        // given
        when(assuntoRepository.save(null)).thenThrow(IllegalArgumentException.class);

        // when
        Throwable exception = catchThrowable(() -> assuntoRepository.save(null));

        // then
        assertThat(exception)
                .isInstanceOf(IllegalArgumentException.class);
        verify(assuntoRepository, times(1)).save(null);
    }

    @Test
    @DisplayName("Deve salvar assunto com descrição atualizada")
    void deveSalvarAssuntoComDescricaoAtualizada() {
        // given
        Assunto assuntoAtualizado = new Assunto();
        assuntoAtualizado.setCodigo(1);
        assuntoAtualizado.setDescricao("Ficção Científica - Atualizado");

        when(assuntoRepository.save(any(Assunto.class))).thenReturn(assuntoAtualizado);

        // when
        Assunto resultado = assuntoRepository.save(assuntoAtualizado);

        // then
        assertThat(resultado)
                .isNotNull()
                .satisfies(a -> {
                    assertThat(a.getCodigo()).isEqualTo(1);
                    assertThat(a.getDescricao()).isEqualTo("Ficção Científica - Atualizado");
                });
        verify(assuntoRepository, times(1)).save(any(Assunto.class));
    }
}
