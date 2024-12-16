package com.br.spassu.api.application.usecase.livro;

import com.br.spassu.api.application.dto.LivroDTO;
import com.br.spassu.api.application.mapper.LivroMapper;
import com.br.spassu.api.domain.entity.Assunto;
import com.br.spassu.api.domain.entity.Autor;
import com.br.spassu.api.domain.entity.Livro;
import com.br.spassu.api.domain.exceptions.*;
import com.br.spassu.api.domain.repository.AssuntoRepository;
import com.br.spassu.api.domain.repository.AutorRepository;
import com.br.spassu.api.domain.repository.LivroRepository;
import com.br.spassu.api.infrastructure.response.ResponseWrapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AtualizarLivroUseCaseTest {

    private static final Integer LIVRO_ID = 1;
    private static final String MENSAGEM_SUCESSO = "Livro atualizado com sucesso";

    @Mock
    private LivroRepository livroRepository;
    @Mock
    private AutorRepository autorRepository;
    @Mock
    private AssuntoRepository assuntoRepository;
    @Mock
    private LivroMapper livroMapper;

    @InjectMocks
    private AtualizarLivroUseCase useCase;

    private LivroDTO livroDTO;
    private Livro livro;
    private Autor autor;
    private Assunto assunto;

    @BeforeEach
    void setUp() {
        autor = Autor.builder()
                .codigo(1)
                .nome("Robert C. Martin")
                .build();

        assunto = Assunto.builder()
                .codigo(1)
                .descricao("Clean Code")
                .build();

        livroDTO = LivroDTO.builder()
                .codigo(LIVRO_ID)
                .titulo("Clean Architecture")
                .editora("Alta Books")
                .edicao(1)
                .anoPublicacao("2020")
                .autorCodAus(List.of(1))
                .assuntoCodAss(List.of(1))
                .build();

        livro = Livro.builder()
                .codigo(LIVRO_ID)
                .titulo("Clean Architecture")
                .editora("Alta Books")
                .edicao(1)
                .anoPublicacao("2020")
                .build();
    }

    @Test
    @DisplayName("Deve atualizar livro com sucesso")
    void deveAtualizarLivroComSucesso() {
        when(livroRepository.findByCodigo(LIVRO_ID)).thenReturn(Optional.of(livro));
        when(autorRepository.findByCodigo(1)).thenReturn(Optional.of(autor));
        when(assuntoRepository.findByCodigo(1)).thenReturn(Optional.of(assunto));
        when(livroRepository.save(any(Livro.class))).thenReturn(livro);
        when(livroMapper.toDto(any(Livro.class))).thenReturn(livroDTO);

        ResponseWrapper<LivroDTO> resultado = useCase.execute(LIVRO_ID, livroDTO);

        assertThat(resultado).isNotNull();
        assertThat(resultado.getMessage()).isEqualTo(MENSAGEM_SUCESSO);
        assertThat(resultado.getData()).isNotNull();
        assertThat(resultado.getData().getTitulo()).isEqualTo(livroDTO.getTitulo());
        verify(livroRepository).save(any(Livro.class));
    }

    @Test
    @DisplayName("Deve lançar exceção quando livro não for encontrado")
    void deveLancarExcecaoQuandoLivroNaoEncontrado() {
        when(livroRepository.findByCodigo(LIVRO_ID)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> useCase.execute(LIVRO_ID, livroDTO))
                .isInstanceOf(BookNotFoundException.class)
                .hasMessage("Livro com código " + LIVRO_ID + " não encontrado");

        verify(livroRepository, never()).save(any());
    }

    @Test
    @DisplayName("Deve lançar exceção quando autor não for encontrado")
    void deveLancarExcecaoQuandoAutorNaoEncontrado() {
        when(livroRepository.findByCodigo(LIVRO_ID)).thenReturn(Optional.of(livro));
        when(autorRepository.findByCodigo(1)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> useCase.execute(LIVRO_ID, livroDTO))
                .isInstanceOf(AuthorNotFoundException.class)
                .hasMessage("Autor com código 1 não encontrado");

        verify(livroRepository, never()).save(any());
    }

    @Test
    @DisplayName("Deve lançar exceção quando assunto não for encontrado")
    void deveLancarExcecaoQuandoAssuntoNaoEncontrado() {
        when(livroRepository.findByCodigo(LIVRO_ID)).thenReturn(Optional.of(livro));
        when(autorRepository.findByCodigo(1)).thenReturn(Optional.of(autor));
        when(assuntoRepository.findByCodigo(1)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> useCase.execute(LIVRO_ID, livroDTO))
                .isInstanceOf(SubjectNotFoundException.class)
                .hasMessage("Assunto com código 1 não encontrado");

        verify(livroRepository, never()).save(any());
    }

    @Test
    @DisplayName("Deve lançar exceção quando título não informado")
    void deveLancarExcecaoQuandoTituloNaoInformado() {
        LivroDTO dto = LivroDTO.builder()
                .editora("Editora")
                .autorCodAus(List.of(1))
                .assuntoCodAss(List.of(1))
                .build();

        assertThatThrownBy(() -> useCase.execute(1, dto))
                .isInstanceOf(InvalidBookDataException.class)
                .hasMessage("Título não informado, campo obrigatório");

        verify(livroRepository, never()).findByCodigo(anyInt());
    }

    @Test
    @DisplayName("Deve lançar exceção quando editora não informada")
    void deveLancarExcecaoQuandoEditoraNaoInformada() {
        LivroDTO dto = LivroDTO.builder()
                .titulo("Título")
                .autorCodAus(List.of(1))
                .assuntoCodAss(List.of(1))
                .build();

        assertThatThrownBy(() -> useCase.execute(1, dto))
                .isInstanceOf(InvalidBookDataException.class)
                .hasMessage("Editora não informada, campo obrigatório");
    }

    @Test
    @DisplayName("Deve lançar exceção quando lista de autores vazia")
    void deveLancarExcecaoQuandoListaAutoresVazia() {
        LivroDTO dto = LivroDTO.builder()
                .titulo("Título")
                .editora("Editora")
                .assuntoCodAss(List.of(1))
                .build();

        assertThatThrownBy(() -> useCase.execute(1, dto))
                .isInstanceOf(InvalidBookDataException.class)
                .hasMessage("Lista de autores não informada, campo obrigatório");
    }

    @Test
    @DisplayName("Deve lançar exceção quando lista de assuntos vazia")
    void deveLancarExcecaoQuandoListaAssuntosVazia() {
        LivroDTO dto = LivroDTO.builder()
                .titulo("Título")
                .editora("Editora")
                .autorCodAus(List.of(1))
                .build();

        assertThatThrownBy(() -> useCase.execute(1, dto))
                .isInstanceOf(InvalidBookDataException.class)
                .hasMessage("Lista de assuntos não informada, campo obrigatório");
    }
}