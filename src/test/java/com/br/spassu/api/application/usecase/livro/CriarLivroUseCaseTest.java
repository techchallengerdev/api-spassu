package com.br.spassu.api.application.usecase.livro;

import com.br.spassu.api.application.dto.LivroDTO;
import com.br.spassu.api.application.mapper.LivroMapper;
import com.br.spassu.api.domain.entity.Autor;
import com.br.spassu.api.domain.entity.Assunto;
import com.br.spassu.api.domain.entity.Livro;
import com.br.spassu.api.domain.repository.AutorRepository;
import com.br.spassu.api.domain.repository.AssuntoRepository;
import com.br.spassu.api.domain.repository.LivroRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Testes para Criar Livro Use Case")
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
    private Livro livro;
    private Autor autor;
    private Assunto assunto;

    @BeforeEach
    void setUp() {
        // Configurar DTO de entrada
        livroDTO = new LivroDTO();
        livroDTO.setId(1);
        livroDTO.setTitulo("Clean Code");
        livroDTO.setEditora("Alta Books");
        livroDTO.setNumeroEdicao(1);
        livroDTO.setAnoPublicacao("2008");
        livroDTO.setIdsAutores(Set.of(1));
        livroDTO.setIdsAssuntos(Set.of(1));

        // Configurar entidade de domínio
        livro = new Livro();
        livro.setCodigo(1);
        livro.setTitulo("Clean Code");
        livro.setEditora("Alta Books");
        livro.setEdicao(1);
        livro.setAnoPublicacao("2008");
        livro.setAutores(new HashSet<>());
        livro.setAssuntos(new HashSet<>());

        // Configurar autor
        autor = new Autor();
        autor.setCodigo(1);
        autor.setNome("Robert C. Martin");

        // Configurar assunto
        assunto = new Assunto();
        assunto.setCodigo(1);
        assunto.setDescricao("Programação");
    }

    @Test
    @DisplayName("Deve criar livro com sucesso")
    void deveCriarLivroComSucesso() {
        // given
        when(livroMapper.toDomain(livroDTO)).thenReturn(livro);
        when(autorRepository.findByCodigo(1)).thenReturn(Optional.of(autor));
        when(assuntoRepository.findByCodigo(1)).thenReturn(Optional.of(assunto));
        when(livroRepository.save(any(Livro.class))).thenReturn(livro);
        when(livroMapper.toDTO(livro)).thenReturn(livroDTO);

        // when
        LivroDTO resultado = criarLivroUseCase.execute(livroDTO);

        // then
        assertThat(resultado).isNotNull();
        assertThat(resultado.getId()).isEqualTo(1);
        assertThat(resultado.getTitulo()).isEqualTo("Clean Code");
        assertThat(resultado.getEditora()).isEqualTo("Alta Books");
        assertThat(resultado.getNumeroEdicao()).isEqualTo(1);
        assertThat(resultado.getAnoPublicacao()).isEqualTo("2008");

        // Verificar chamadas
        verify(livroMapper).toDomain(livroDTO);
        verify(autorRepository).findByCodigo(1);
        verify(assuntoRepository).findByCodigo(1);
        verify(livroRepository).save(any(Livro.class));
        verify(livroMapper).toDTO(livro);
    }

    @Test
    @DisplayName("Deve criar livro sem autores quando não encontrados")
    void deveCriarLivroSemAutoresQuandoNaoEncontrados() {
        // given
        when(livroMapper.toDomain(livroDTO)).thenReturn(livro);
        when(autorRepository.findByCodigo(1)).thenReturn(Optional.empty());
        when(assuntoRepository.findByCodigo(1)).thenReturn(Optional.of(assunto));
        when(livroRepository.save(any(Livro.class))).thenReturn(livro);
        when(livroMapper.toDTO(livro)).thenReturn(livroDTO);

        // when
        LivroDTO resultado = criarLivroUseCase.execute(livroDTO);

        // then
        assertThat(resultado).isNotNull();
        verify(autorRepository).findByCodigo(1);
        verify(livroRepository).save(any(Livro.class));
    }

    @Test
    @DisplayName("Deve criar livro sem assuntos quando não encontrados")
    void deveCriarLivroSemAssuntosQuandoNaoEncontrados() {
        // given
        when(livroMapper.toDomain(livroDTO)).thenReturn(livro);
        when(autorRepository.findByCodigo(1)).thenReturn(Optional.of(autor));
        when(assuntoRepository.findByCodigo(1)).thenReturn(Optional.empty());
        when(livroRepository.save(any(Livro.class))).thenReturn(livro);
        when(livroMapper.toDTO(livro)).thenReturn(livroDTO);

        // when
        LivroDTO resultado = criarLivroUseCase.execute(livroDTO);

        // then
        assertThat(resultado).isNotNull();
        verify(assuntoRepository).findByCodigo(1);
        verify(livroRepository).save(any(Livro.class));
    }

    @Test
    @DisplayName("Deve criar livro sem relacionamentos quando IDs não fornecidos")
    void deveCriarLivroSemRelacionamentosQuandoIdsNaoFornecidos() {
        // given
        livroDTO.setIdsAutores(null);
        livroDTO.setIdsAssuntos(null);

        when(livroMapper.toDomain(livroDTO)).thenReturn(livro);
        when(livroRepository.save(any(Livro.class))).thenReturn(livro);
        when(livroMapper.toDTO(livro)).thenReturn(livroDTO);

        // when
        LivroDTO resultado = criarLivroUseCase.execute(livroDTO);

        // then
        assertThat(resultado).isNotNull();
        verifyNoInteractions(autorRepository);
        verifyNoInteractions(assuntoRepository);
        verify(livroRepository).save(any(Livro.class));
    }
}