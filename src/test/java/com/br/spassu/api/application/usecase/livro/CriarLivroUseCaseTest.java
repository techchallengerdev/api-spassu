package com.br.spassu.api.application.usecase.livro;

import com.br.spassu.api.application.dto.LivroDTO;
import com.br.spassu.api.application.mapper.LivroMapper;
import com.br.spassu.api.domain.entity.Autor;
import com.br.spassu.api.domain.entity.Assunto;
import com.br.spassu.api.domain.entity.Livro;
import com.br.spassu.api.domain.repository.AutorRepository;
import com.br.spassu.api.domain.repository.AssuntoRepository;
import com.br.spassu.api.domain.repository.LivroRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

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

    @Test
    void deveCriarLivroComSucesso() {
        // given
        LivroDTO dto = criarLivroDTO();
        Livro livro = criarLivro();
        Autor autor = criarAutor();
        Assunto assunto = criarAssunto();

        when(livroMapper.toEntity(dto)).thenReturn(livro);
        when(autorRepository.findByCodigo(1)).thenReturn(Optional.of(autor));
        when(assuntoRepository.findByCodigo(1)).thenReturn(Optional.of(assunto));
        when(livroRepository.save(any(Livro.class))).thenReturn(livro);
        when(livroMapper.toDTO(livro)).thenReturn(dto);

        // when
        LivroDTO resultado = criarLivroUseCase.execute(dto);

        // then
        assertThat(resultado).isNotNull();
        assertThat(resultado.getId()).isEqualTo(1);
        assertThat(resultado.getTitulo()).isEqualTo("Clean Code");
    }

    private LivroDTO criarLivroDTO() {
        LivroDTO dto = new LivroDTO();
        dto.setId(1);
        dto.setTitulo("Clean Code");
        dto.setEditora("Alta Books");
        dto.setNumeroEdicao(1);
        dto.setAnoPublicacao("2008");
        dto.setIdsAutores(Set.of(1));
        dto.setIdsAssuntos(Set.of(1));
        return dto;
    }

    private Livro criarLivro() {
        Livro livro = new Livro();
        livro.setCodigo(1);
        livro.setTitulo("Clean Code");
        return livro;
    }

    private Autor criarAutor() {
        Autor autor = new Autor();
        autor.setCodigo(1);
        return autor;
    }

    private Assunto criarAssunto() {
        Assunto assunto = new Assunto();
        assunto.setCodigo(1);
        return assunto;
    }
}
