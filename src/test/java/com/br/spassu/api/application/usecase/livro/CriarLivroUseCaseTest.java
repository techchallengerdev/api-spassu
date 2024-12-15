package com.br.spassu.api.application.usecase.livro;

import com.br.spassu.api.application.dto.LivroDTO;
import com.br.spassu.api.application.mapper.LivroMapper;
import com.br.spassu.api.domain.entity.Assunto;
import com.br.spassu.api.domain.entity.Autor;
import com.br.spassu.api.domain.entity.Livro;
import com.br.spassu.api.domain.exceptions.BusinessException;
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

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CriarLivroUseCaseTest {

    private static final String MENSAGEM_SUCESSO = "Livro criado com sucesso";

    @Mock
    private LivroRepository livroRepository;
    @Mock
    private AutorRepository autorRepository;
    @Mock
    private AssuntoRepository assuntoRepository;
    @Mock
    private LivroMapper livroMapper;

    @InjectMocks
    private CriarLivroUseCase criarLivroUseCase;

    private LivroDTO livroDTO;
    private Autor robertMartin;
    private Autor martinFowler;
    private Assunto cleanCode;
    private Assunto arquiteturaSoftware;
    private Livro livro;

    @BeforeEach
    void setUp() {
        robertMartin = Autor.builder()
                .codigo(1)
                .nome("Robert C. Martin")
                .build();

        martinFowler = Autor.builder()
                .codigo(2)
                .nome("Martin Fowler")
                .build();

        cleanCode = Assunto.builder()
                .codigo(1)
                .descricao("Clean Code")
                .build();

        arquiteturaSoftware = Assunto.builder()
                .codigo(2)
                .descricao("Arquitetura de Software")
                .build();

        livroDTO = LivroDTO.builder()
                .titulo("Clean Architecture")
                .editora("Alta Books")
                .edicao(1)
                .anoPublicacao("2020")
                .autorCodAus(List.of(1))
                .assuntoCodAss(List.of(1))
                .build();

        livro = Livro.builder()
                .titulo("Clean Architecture")
                .editora("Alta Books")
                .edicao(1)
                .anoPublicacao("2020")
                .build();
    }

    @Test
    @DisplayName("Deve criar livro Clean Architecture com sucesso")
    void deveCriarLivroCleanArchitectureComSucesso() {
        when(autorRepository.findByCodigo(1)).thenReturn(Optional.of(robertMartin));
        when(assuntoRepository.findByCodigo(1)).thenReturn(Optional.of(cleanCode));
        when(livroRepository.save(any(Livro.class))).thenReturn(livro);
        when(livroMapper.toDto(any(Livro.class))).thenReturn(livroDTO);

        ResponseWrapper<LivroDTO> resultado = criarLivroUseCase.execute(livroDTO);

        assertThat(resultado).isNotNull();
        assertThat(resultado.getMessage()).isEqualTo(MENSAGEM_SUCESSO);
        assertThat(resultado.getData()).isNotNull();
        assertThat(resultado.getData().getTitulo()).isEqualTo("Clean Architecture");
        verify(livroRepository, times(1)).save(any(Livro.class));
    }

    @Test
    @DisplayName("Deve criar livro Refactoring com múltiplos autores e assuntos")
    void deveCriarLivroRefactoringComMultiplosAutoresEAssuntos() {

        livroDTO.setTitulo("Refactoring: Improving the Design of Existing Code");
        livroDTO.setAutorCodAus(List.of(1, 2)); // Uncle Bob e Martin Fowler
        livroDTO.setAssuntoCodAss(List.of(1, 2)); // Clean Code e Arquitetura

        when(autorRepository.findByCodigo(1)).thenReturn(Optional.of(robertMartin));
        when(autorRepository.findByCodigo(2)).thenReturn(Optional.of(martinFowler));
        when(assuntoRepository.findByCodigo(1)).thenReturn(Optional.of(cleanCode));
        when(assuntoRepository.findByCodigo(2)).thenReturn(Optional.of(arquiteturaSoftware));
        when(livroRepository.save(any(Livro.class))).thenReturn(livro);
        when(livroMapper.toDto(any(Livro.class))).thenReturn(livroDTO);

        ResponseWrapper<LivroDTO> resultado = criarLivroUseCase.execute(livroDTO);

        assertThat(resultado).isNotNull();
        assertThat(resultado.getMessage()).isEqualTo(MENSAGEM_SUCESSO);
        assertThat(resultado.getData().getAutorCodAus()).hasSize(2);
        assertThat(resultado.getData().getAssuntoCodAss()).hasSize(2);
        verify(livroRepository, times(1)).save(any(Livro.class));
    }

    @Test
    @DisplayName("Deve lançar exceção quando dados do livro não forem informados")
    void deveLancarExcecaoQuandoDadosLivroNaoInformados() {
        assertThatThrownBy(() -> criarLivroUseCase.execute(null))
                .isInstanceOf(BusinessException.class)
                .hasMessage("Dados do livro não informados");

        verify(livroRepository, never()).save(any());
    }

    @Test
    @DisplayName("Deve lançar exceção quando autor não for encontrado")
    void deveLancarExcecaoQuandoAutorNaoEncontrado() {
        when(autorRepository.findByCodigo(1)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> criarLivroUseCase.execute(livroDTO))
                .isInstanceOf(BusinessException.class)
                .hasMessage("Autor com código 1 não encontrado");

        verify(livroRepository, never()).save(any());
    }

    @Test
    @DisplayName("Deve lançar exceção quando título não for informado")
    void deveLancarExcecaoQuandoTituloNaoInformado() {
        livroDTO.setTitulo("");

        assertThatThrownBy(() -> criarLivroUseCase.execute(livroDTO))
                .isInstanceOf(BusinessException.class)
                .hasMessage("Título não informado, campo obrigatório");

        verify(livroRepository, never()).save(any());
    }

    @Test
    @DisplayName("Deve lançar exceção quando editora não for informada")
    void deveLancarExcecaoQuandoEditoraNaoInformada() {
        livroDTO.setEditora("");

        assertThatThrownBy(() -> criarLivroUseCase.execute(livroDTO))
                .isInstanceOf(BusinessException.class)
                .hasMessage("Editora não informada, campo obrigatório");

        verify(livroRepository, never()).save(any());
    }

    @Test
    @DisplayName("Deve lançar exceção quando lista de assuntos estiver vazia")
    void deveLancarExcecaoQuandoListaAssuntosVazia() {
        livroDTO.setAssuntoCodAss(List.of());

        assertThatThrownBy(() -> criarLivroUseCase.execute(livroDTO))
                .isInstanceOf(BusinessException.class)
                .hasMessage("Lista de assuntos não informada, campo obrigatório");

        verify(livroRepository, never()).save(any());
    }
}