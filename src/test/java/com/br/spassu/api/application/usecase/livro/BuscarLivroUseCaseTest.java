package com.br.spassu.api.application.usecase.livro;

import com.br.spassu.api.application.dto.LivroDTO;
import com.br.spassu.api.application.mapper.LivroMapper;
import com.br.spassu.api.domain.entity.Livro;
import com.br.spassu.api.domain.exceptions.BusinessException;
import com.br.spassu.api.domain.exceptions.EntityNotFoundException;
import com.br.spassu.api.domain.repository.LivroRepository;
import org.junit.jupiter.api.*;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

class BuscarLivroUseCaseTest {

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

            Mockito.when(livroRepository.findByCodigo(1)).thenReturn(Optional.of(livro));
            Mockito.when(livroMapper.toDto(livro)).thenReturn(livroDTO);

            LivroDTO resultado = useCase.execute(1);

            Assertions.assertNotNull(resultado);
            Assertions.assertEquals(livroDTO.getCodigo(), resultado.getCodigo());
            Assertions.assertEquals(livroDTO.getTitulo(), resultado.getTitulo());
            Assertions.assertEquals(livroDTO.getEditora(), resultado.getEditora());
            Assertions.assertEquals(livroDTO.getEdicao(), resultado.getEdicao());
            Assertions.assertEquals(livroDTO.getAnoPublicacao(), resultado.getAnoPublicacao());
        }
    }

    @Nested
    @DisplayName("Testes de validação")
    class ValidacaoTests {

        @Test
        @DisplayName("Deve lançar BusinessException quando código for nulo")
        void deveLancarBusinessExceptionQuandoCodigoNull() {
            Assertions.assertThrows(BusinessException.class,
                    () -> useCase.execute(null));
        }

        @Test
        @DisplayName("Deve lançar BusinessException quando código for inválido")
        void deveLancarBusinessExceptionQuandoCodigoInvalido() {
            BusinessException exception = Assertions.assertThrows(BusinessException.class,
                    () -> useCase.execute(0));
            Assertions.assertEquals("Código do livro deve ser maior que zero", exception.getMessage());
        }

        @Test
        @DisplayName("Deve lançar EntityNotFoundException quando livro não for encontrado")
        void deveLancarEntityNotFoundExceptionQuandoLivroNaoEncontrado() {
            Mockito.when(livroRepository.findByCodigo(Mockito.any())).thenReturn(Optional.empty());

            EntityNotFoundException exception = Assertions.assertThrows(EntityNotFoundException.class,
                    () -> useCase.execute(1));
            Assertions.assertEquals("Livro com código 1 não encontrado", exception.getMessage());
        }
    }
}
