package com.br.spassu.api.application.usecase.livro;

import com.br.spassu.api.domain.exceptions.EntityNotFoundException;
import com.br.spassu.api.domain.repository.LivroRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DeletarLivroUseCaseTest {

    @Mock
    private LivroRepository livroRepository;
    @InjectMocks
    private DeletarLivroUseCase deletarLivroUseCase;

    @Test
    void deveDeletarLivroComSucesso() {
        Integer id = 1;
        when(livroRepository.existsByCodigo(id)).thenReturn(true);
        assertThatCode(() -> deletarLivroUseCase.execute(id))
                .doesNotThrowAnyException();
    }

    @Test
    void deveLancarExcecaoQuandoLivroNaoEncontrado() {
        // given
        Integer id = 1;
        when(livroRepository.existsByCodigo(id)).thenReturn(false);

        // when/then
        assertThatThrownBy(() -> deletarLivroUseCase.execute(id))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessage("Livro não encontrado");
    }
}
