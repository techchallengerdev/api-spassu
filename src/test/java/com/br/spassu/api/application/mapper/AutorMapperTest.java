package com.br.spassu.api.application.mapper;

import com.br.spassu.api.domain.entity.Autor;
import com.br.spassu.api.domain.entity.Livro;
import com.br.spassu.api.application.dto.AutorDTO;
import com.br.spassu.api.application.dto.LivroResumoDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AutorMapperTest {

    @Mock
    private LivroMapper livroMapper;

    private AutorMapper autorMapper;

    @BeforeEach
    void setUp() {
        autorMapper = new AutorMapper(livroMapper);
    }

    @Test
    void deveMappearAutorParaDTO() {
        Autor autor = criarAutor();
        Livro livro = new Livro();
        livro.setCodigo(1);
        autor.setLivros(Set.of(livro));

        LivroResumoDTO livroResumoDTO = new LivroResumoDTO();
        livroResumoDTO.setId(1);
        when(livroMapper.toResumoDTO(livro)).thenReturn(livroResumoDTO);

        AutorDTO dto = autorMapper.toDTO(autor);

        assertThat(dto).isNotNull();
        assertThat(dto.getId()).isEqualTo(1);
        assertThat(dto.getNome()).isEqualTo("Robert C. Martin");
        assertThat(dto.getLivros()).hasSize(1);
    }

    @Test
    void deveMappearDTOParaAutor() {
        AutorDTO dto = criarAutorDTO();
        Autor autor = autorMapper.toEntity(dto);
        assertThat(autor).isNotNull();
        assertThat(autor.getCodigo()).isEqualTo(1);
        assertThat(autor.getNome()).isEqualTo("Robert C. Martin");
    }

    @Test
    void deveRetornarNullQuandoAutorForNull() {
        assertThat(autorMapper.toDTO(null)).isNull();
        assertThat(autorMapper.toEntity(null)).isNull();
    }

    private Autor criarAutor() {
        Autor autor = new Autor();
        autor.setCodigo(1);
        autor.setNome("Robert C. Martin");
        return autor;
    }

    private AutorDTO criarAutorDTO() {
        AutorDTO dto = new AutorDTO();
        dto.setId(1);
        dto.setNome("Robert C. Martin");
        return dto;
    }
}
