package com.br.spassu.api.domain.repository;

import com.br.spassu.api.domain.entity.Livro;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
public class LivroRepositoryTest {

    @Mock
    private LivroRepository livroRepository;

    @Test
    void deveSalvarLivroComSucesso() {
        Livro livro = new Livro();
        livro.setTitulo("Clean Code");

        Livro livroSalvo = new Livro();
        livroSalvo.setCodigo(1);
        livroSalvo.setTitulo("Clean Code");

        when(livroRepository.save(any(Livro.class))).thenReturn(livroSalvo);
        Livro resultado = livroRepository.save(livro);
        assertThat(resultado).isNotNull();
        assertThat(resultado.getCodigo()).isEqualTo(1);
        assertThat(resultado.getTitulo()).isEqualTo("Clean Code");
    }

    @Test
    void deveBuscarLivroPorCodigoComSucesso() {
        Livro livro = new Livro();
        livro.setCodigo(1);
        livro.setTitulo("Clean Code");

        when(livroRepository.findByCodigo(1)).thenReturn(Optional.of(livro));
        Optional<Livro> resultado = livroRepository.findByCodigo(1);

        assertThat(resultado).isPresent();
        assertThat(resultado.get().getCodigo()).isEqualTo(1);
        assertThat(resultado.get().getTitulo()).isEqualTo("Clean Code");
    }

    @Test
    void deveRetornarListaVaziaQuandoNaoHouverLivros() {
        // given
        when(livroRepository.findAll()).thenReturn(List.of());

        // when
        List<Livro> resultado = livroRepository.findAll();

        // then
        assertThat(resultado).isEmpty();
    }
}
