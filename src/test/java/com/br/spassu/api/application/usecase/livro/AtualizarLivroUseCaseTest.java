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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AtualizarLivroUseCaseTest {

    @Mock
    private LivroRepository livroRepository;
    @Mock
    private LivroMapper livroMapper;
    @InjectMocks
    private AtualizarLivroUseCase atualizarLivroUseCase;

    @Test
    void deveAtualizarLivroComSucesso() {
        Integer id = 1;
        Livro livro = new Livro();
        LivroDTO dto = criarLivroDTO();

        when(livroRepository.findByCodigo(id)).thenReturn(Optional.of(livro));
        when(livroRepository.save(any(Livro.class))).thenReturn(livro);
        when(livroMapper.toDTO(livro)).thenReturn(dto);

        LivroDTO resultado = atualizarLivroUseCase.execute(id, dto);

        assertThat(resultado).isNotNull();
        assertThat(resultado.getId()).isEqualTo(id);
        assertThat(resultado.getTitulo()).isEqualTo(dto.getTitulo());
    }

    @Test
    void deveLancarExcecaoQuandoLivroNaoEncontrado() {

        Integer id = 1;
        LivroDTO dto = criarLivroDTO();
        when(livroRepository.findByCodigo(id)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> atualizarLivroUseCase.execute(id, dto))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessage("Livro não encontrado");
    }

    private LivroDTO criarLivroDTO() {
        LivroDTO dto = new LivroDTO();
        dto.setId(1);
        dto.setTitulo("Clean Code Updated");
        return dto;
    }
}
