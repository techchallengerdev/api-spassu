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
    private Autor autor;
    private Assunto assunto;
    private Livro livro;

    @BeforeEach
    void setUp() {
        autor = Autor.builder()
                .codigo(1)
                .nome("Autor Teste")
                .build();

        assunto = Assunto.builder()
                .codigo(1)
                .descricao("Assunto Teste")
                .build();

        livroDTO = LivroDTO.builder()
                .titulo("Livro Teste")
                .editora("Editora Teste")
                .edicao(1)
                .anoPublicacao("2024")
                .autorCodAus(List.of(1))
                .assuntoCodAss(List.of(1))
                .build();

        livro = Livro.builder()
                .titulo("Livro Teste")
                .editora("Editora Teste")
                .edicao(1)
                .anoPublicacao("2024")
                .build();
    }

    @Test
    @DisplayName("Deve criar um livro com sucesso")
    void deveCriarLivroComSucesso() {

        when(autorRepository.findByCodigo(1)).thenReturn(Optional.of(autor));
        when(assuntoRepository.findByCodigo(1)).thenReturn(Optional.of(assunto));
        when(livroRepository.save(any(Livro.class))).thenReturn(livro);
        when(livroMapper.toDto(any(Livro.class))).thenReturn(livroDTO);

        LivroDTO resultado = criarLivroUseCase.execute(livroDTO);

        assertThat(resultado).isNotNull();
        assertThat(resultado.getTitulo()).isEqualTo(livroDTO.getTitulo());
        verify(livroRepository, times(1)).save(any(Livro.class));
    }

    @Test
    @DisplayName("Deve lançar exceção quando dto for nulo")
    void deveLancarExcecaoQuandoDtoNulo() {

        assertThatThrownBy(() -> criarLivroUseCase.execute(null))
                .isInstanceOf(BusinessException.class)
                .hasMessage("Dados do livro não informados");
    }

    @Test
    @DisplayName("Deve lançar exceção quando lista de autores for vazia")
    void deveLancarExcecaoQuandoListaAutoresVazia() {

        livroDTO.setAutorCodAus(List.of());

        assertThatThrownBy(() -> criarLivroUseCase.execute(livroDTO))
                .isInstanceOf(BusinessException.class)
                .hasMessage("É necessário informar pelo menos um autor");
    }

    @Test
    @DisplayName("Deve lançar exceção quando lista de assuntos for vazia")
    void deveLancarExcecaoQuandoListaAssuntosVazia() {

        livroDTO.setAssuntoCodAss(List.of());

        assertThatThrownBy(() -> criarLivroUseCase.execute(livroDTO))
                .isInstanceOf(BusinessException.class)
                .hasMessage("Autor com código 1 não encontrado");
    }

    @Test
    @DisplayName("Deve lançar exceção quando autor não for encontrado")
    void deveLancarExcecaoQuandoAutorNaoEncontrado() {

        when(autorRepository.findByCodigo(1)).thenReturn(Optional.empty());


        assertThatThrownBy(() -> criarLivroUseCase.execute(livroDTO))
                .isInstanceOf(BusinessException.class)
                .hasMessage("Autor com código 1 não encontrado");
    }

    @Test
    @DisplayName("Deve lançar exceção quando assunto não for encontrado")
    void deveLancarExcecaoQuandoAssuntoNaoEncontrado() {

        when(autorRepository.findByCodigo(1)).thenReturn(Optional.of(autor));
        when(assuntoRepository.findByCodigo(1)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> criarLivroUseCase.execute(livroDTO))
                .isInstanceOf(BusinessException.class)
                .hasMessage("Assunto com código 1 não encontrado");
    }

    @Test
    @DisplayName("Deve lançar exceção quando validação do domínio falhar")
    void deveLancarExcecaoQuandoValidacaoDominioFalhar() {

        when(autorRepository.findByCodigo(1)).thenReturn(Optional.of(autor));
        when(assuntoRepository.findByCodigo(1)).thenReturn(Optional.of(assunto));
        livroDTO.setTitulo("");

        assertThatThrownBy(() -> criarLivroUseCase.execute(livroDTO))
                .isInstanceOf(BusinessException.class)
                .hasMessage("Título é obrigatório");
    }

    @Test
    @DisplayName("Deve criar livro com múltiplos autores e assuntos")
    void deveCriarLivroComMultiplosAutoresEAssuntos() {

        Autor autor2 = Autor.builder().codigo(2).nome("Autor 2").build();
        Assunto assunto2 = Assunto.builder().codigo(2).descricao("Assunto 2").build();

        livroDTO.setAutorCodAus(List.of(1, 2));
        livroDTO.setAssuntoCodAss(List.of(1, 2));

        when(autorRepository.findByCodigo(1)).thenReturn(Optional.of(autor));
        when(autorRepository.findByCodigo(2)).thenReturn(Optional.of(autor2));
        when(assuntoRepository.findByCodigo(1)).thenReturn(Optional.of(assunto));
        when(assuntoRepository.findByCodigo(2)).thenReturn(Optional.of(assunto2));
        when(livroRepository.save(any(Livro.class))).thenReturn(livro);
        when(livroMapper.toDto(any(Livro.class))).thenReturn(livroDTO);

        LivroDTO resultado = criarLivroUseCase.execute(livroDTO);

        assertThat(resultado).isNotNull();
        assertThat(resultado.getAutorCodAus()).hasSize(2);
        assertThat(resultado.getAssuntoCodAss()).hasSize(2);
        verify(livroRepository, times(1)).save(any(Livro.class));
    }
}