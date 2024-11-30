package com.br.spassu.api.application.usecase.livro;

import com.br.spassu.api.application.dto.LivroDTO;
import com.br.spassu.api.application.mapper.LivroMapper;
import com.br.spassu.api.domain.entity.Livro;
import com.br.spassu.api.domain.exceptions.EntityNotFoundException;
import com.br.spassu.api.domain.repository.LivroRepository;
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
class BuscarLivroUseCaseTest {

    @Mock
    private LivroRepository livroRepository;
    @Mock
    private LivroMapper livroMapper;
    @InjectMocks
    private BuscarLivroUseCase buscarLivroUseCase;

    @Test
    void deveBuscarLivroComSucesso() {
        Integer id = 1;
        Livro livro = new Livro();
        LivroDTO livroDTO = new LivroDTO();
        livroDTO.setId(id);

        when(livroRepository.findByCodigo(id)).thenReturn(Optional.of(livro));
        when(livroMapper.toDTO(livro)).thenReturn(livroDTO);

        LivroDTO resultado = buscarLivroUseCase.execute(id);

        assertThat(resultado).isNotNull();
        assertThat(resultado.getId()).isEqualTo(id);
    }

    @Test
    void deveLancarExcecaoQuandoLivroNaoEncontrado() {

        Integer id = 1;
        when(livroRepository.findByCodigo(id)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> buscarLivroUseCase.execute(id))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessage("Livro não encontrado");
    }
}
