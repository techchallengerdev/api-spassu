package com.br.spassu.api.domain.repository;

import com.br.spassu.api.domain.entity.Autor;
import org.junit.jupiter.api.BeforeEach;
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
class AutorRepositoryTest {

    @Mock
    private AutorRepository autorRepository;

    private Autor autor;

    @BeforeEach
    void setUp() {
        autor = new Autor();
        autor.setCodigo(1);
        autor.setNome("Carlos Drummond");
    }

    @Test
    void deveSalvarAutorComSucesso() {
        // given
        when(autorRepository.save(any(Autor.class))).thenReturn(autor);

        // when
        Autor autorSalvo = autorRepository.save(autor);

        // then
        assertThat(autorSalvo).isNotNull();
        assertThat(autorSalvo.getCodigo()).isEqualTo(1);
        assertThat(autorSalvo.getNome()).isEqualTo("Carlos Drummond");
        verify(autorRepository, times(1)).save(any(Autor.class));
    }

    @Test
    void deveBuscarAutorPorCodigoComSucesso() {
        when(autorRepository.findByCodigo(1)).thenReturn(Optional.of(autor));
        Optional<Autor> autorEncontrado = autorRepository.findByCodigo(1);
        assertThat(autorEncontrado).isPresent();
        assertThat(autorEncontrado.get().getCodigo()).isEqualTo(1);
        assertThat(autorEncontrado.get().getNome()).isEqualTo("Carlos Drummond");
        verify(autorRepository, times(1)).findByCodigo(1);
    }

    @Test
    void deveRetornarVazioQuandoBuscarAutorInexistente() {
        when(autorRepository.findByCodigo(999)).thenReturn(Optional.empty());
        Optional<Autor> autorEncontrado = autorRepository.findByCodigo(999);
        assertThat(autorEncontrado).isEmpty();
        verify(autorRepository, times(1)).findByCodigo(999);
    }

    @Test
    void deveListarTodosAutoresComSucesso() {
        Autor autor2 = new Autor();
        autor2.setCodigo(2);
        autor2.setNome("Machado de Assis");

        List<Autor> autores = Arrays.asList(autor, autor2);
        when(autorRepository.findAll()).thenReturn(autores);

        List<Autor> listaAutores = autorRepository.findAll();
        assertThat(listaAutores).hasSize(2);
        assertThat(listaAutores).extracting(Autor::getNome)
                .containsExactly("Carlos Drummond", "Machado de Assis");
        verify(autorRepository, times(1)).findAll();
    }

    @Test
    void deveRetornarListaVaziaQuandoNaoHouverAutores() {
        when(autorRepository.findAll()).thenReturn(List.of());
        List<Autor> listaAutores = autorRepository.findAll();
        assertThat(listaAutores).isEmpty();
        verify(autorRepository, times(1)).findAll();
    }

    @Test
    void deveDeletarAutorComSucesso() {
        doNothing().when(autorRepository).deleteByCodigo(1);
        autorRepository.deleteByCodigo(1);
        verify(autorRepository, times(1)).deleteByCodigo(1);
    }

    @Test
    void deveVerificarExistenciaDeAutorComSucesso() {
        // given
        when(autorRepository.existsByCodigo(1)).thenReturn(true);
        when(autorRepository.existsByCodigo(999)).thenReturn(false);

        // when
        boolean existeAutor = autorRepository.existsByCodigo(1);
        boolean naoExisteAutor = autorRepository.existsByCodigo(999);

        // then
        assertThat(existeAutor).isTrue();
        assertThat(naoExisteAutor).isFalse();
        verify(autorRepository, times(1)).existsByCodigo(1);
        verify(autorRepository, times(1)).existsByCodigo(999);
    }

    @Test
    void deveLancarExcecaoAoSalvarAutorNulo() {
        when(autorRepository.save(null)).thenThrow(IllegalArgumentException.class);
        assertThat(catchThrowable(() -> autorRepository.save(null)))
                .isInstanceOf(IllegalArgumentException.class);
        verify(autorRepository, times(1)).save(null);
    }
}
