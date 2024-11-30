package com.br.spassu.api.application.usecase.livro;

import com.br.spassu.api.application.dto.LivroDTO;
import com.br.spassu.api.application.mapper.LivroMapper;
import com.br.spassu.api.domain.entity.Livro;
import com.br.spassu.api.domain.repository.LivroRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ListarLivrosUseCaseTest {

    @Mock
    private LivroRepository livroRepository;
    @Mock
    private LivroMapper livroMapper;
    @InjectMocks
    private ListarLivrosUseCase listarLivrosUseCase;

    @Test
    void deveListarLivrosComSucesso() {
        List<Livro> livros = List.of(new Livro(), new Livro());
        List<LivroDTO> livrosDTO = List.of(new LivroDTO(), new LivroDTO());

        when(livroRepository.findAll()).thenReturn(livros);
        when(livroMapper.toDTO(any(Livro.class))).thenReturn(new LivroDTO());

        List<LivroDTO> resultado = listarLivrosUseCase.execute();
        assertThat(resultado).hasSize(2);
    }

    @Test
    void deveRetornarListaVaziaQuandoNaoHouverLivros() {
        when(livroRepository.findAll()).thenReturn(Collections.emptyList());
        List<LivroDTO> resultado = listarLivrosUseCase.execute();
        assertThat(resultado).isEmpty();
    }
}
