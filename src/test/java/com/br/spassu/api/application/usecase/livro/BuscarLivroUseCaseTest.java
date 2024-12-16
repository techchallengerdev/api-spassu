package com.br.spassu.api.application.usecase.livro;

import com.br.spassu.api.application.dto.LivroDTO;
import com.br.spassu.api.application.mapper.LivroMapper;
import com.br.spassu.api.domain.entity.Livro;
import com.br.spassu.api.domain.exceptions.BusinessException;
import com.br.spassu.api.domain.repository.LivroRepository;
import com.br.spassu.api.infrastructure.response.ResponseWrapper;
import org.junit.jupiter.api.*;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

class BuscarLivroUseCaseTest {

    private static final String MENSAGEM_SUCESSO = "Livro encontrado com sucesso";

    @Mock
    private LivroRepository livroRepository;

    @Mock
    private LivroMapper livroMapper;

    @InjectMocks
    private BuscarLivroUseCase useCase;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Nested
    @DisplayName("Testes de sucesso")
    class SucessoTests {

        @Test
        @DisplayName("Deve buscar um livro com sucesso")
        void deveBuscarLivroComSucesso() {
            Livro livro = Livro.builder()
                    .codigo(1)
                    .titulo("Clean Code")
                    .editora("Alta Books")
                    .edicao(1)
                    .anoPublicacao("2008")
                    .build();

            LivroDTO livroDTO = LivroDTO.builder()
                    .codigo(1)
                    .titulo("Clean Code")
                    .editora("Alta Books")
                    .edicao(1)
                    .anoPublicacao("2008")
                    .build();

            when(livroRepository.findByCodigo(1)).thenReturn(Optional.of(livro));
            when(livroMapper.toDto(livro)).thenReturn(livroDTO);

            ResponseWrapper<LivroDTO> resultado = useCase.execute(1);

            assertThat(resultado).isNotNull();
            assertThat(resultado.getMessage()).isEqualTo(MENSAGEM_SUCESSO);
            assertThat(resultado.getData()).isNotNull();
            assertThat(resultado.getData())
                    .extracting(
                            LivroDTO::getCodigo,
                            LivroDTO::getTitulo,
                            LivroDTO::getEditora,
                            LivroDTO::getEdicao,
                            LivroDTO::getAnoPublicacao
                    )
                    .containsExactly(
                            livroDTO.getCodigo(),
                            livroDTO.getTitulo(),
                            livroDTO.getEditora(),
                            livroDTO.getEdicao(),
                            livroDTO.getAnoPublicacao()
                    );

            verify(livroRepository).findByCodigo(1);
            verify(livroMapper).toDto(livro);
        }
    }

    @Nested
    @DisplayName("Testes de validação")
    class ValidacaoTests {

        @Test
        @DisplayName("Deve lançar BusinessException quando código for nulo")
        void deveLancarBusinessExceptionQuandoCodigoNull() {
            assertThatThrownBy(() -> useCase.execute(null))
                    .isInstanceOf(BusinessException.class)
                    .hasMessage("Código do livro não pode ser nulo");

            verify(livroRepository, never()).findByCodigo(any());
        }

        @Test
        @DisplayName("Deve lançar BusinessException quando código for inválido")
        void deveLancarBusinessExceptionQuandoCodigoInvalido() {
            assertThatThrownBy(() -> useCase.execute(0))
                    .isInstanceOf(BusinessException.class)
                    .hasMessage("Código do livro deve ser maior que zero");

            verify(livroRepository, never()).findByCodigo(any());
        }

        @Test
        @DisplayName("Deve lançar BusinessException quando livro não for encontrado")
        void deveLancarBusinessExceptionQuandoLivroNaoEncontrado() {
            when(livroRepository.findByCodigo(1)).thenReturn(Optional.empty());

            assertThatThrownBy(() -> useCase.execute(1))
                    .isInstanceOf(BusinessException.class)
                    .hasMessage("Livro com código 1 não encontrado");

            verify(livroRepository).findByCodigo(1);
            verify(livroMapper, never()).toDto(any());
        }
    }
}