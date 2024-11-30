package com.br.spassu.api.application.mapper;

import com.br.spassu.api.domain.entity.Livro;
import com.br.spassu.api.domain.entity.Autor;
import com.br.spassu.api.domain.entity.Assunto;
import com.br.spassu.api.application.dto.LivroDTO;
import com.br.spassu.api.application.dto.LivroResumoDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

class LivroMapperTest {

    private LivroMapper livroMapper;

    @BeforeEach
    void setUp() {
        livroMapper = new LivroMapper();
    }

    @Test
    void deveMappearLivroParaDTO() {
        // given
        Livro livro = criarLivro();

        // when
        LivroDTO dto = livroMapper.toDTO(livro);

        // then
        assertThat(dto).isNotNull();
        assertThat(dto.getId()).isEqualTo(1);
        assertThat(dto.getTitulo()).isEqualTo("Clean Code");
        assertThat(dto.getEditora()).isEqualTo("Alta Books");
        assertThat(dto.getNumeroEdicao()).isEqualTo(1);
        assertThat(dto.getAnoPublicacao()).isEqualTo("2008");
        assertThat(dto.getIdsAutores()).containsExactly(1);
        assertThat(dto.getIdsAssuntos()).containsExactly(1);
    }

    @Test
    void deveMappearDTOParaLivro() {
        // given
        LivroDTO dto = criarLivroDTO();

        // when
        Livro livro = livroMapper.toEntity(dto);

        // then
        assertThat(livro).isNotNull();
        assertThat(livro.getCodigo()).isEqualTo(1);
        assertThat(livro.getTitulo()).isEqualTo("Clean Code");
        assertThat(livro.getEditora()).isEqualTo("Alta Books");
        assertThat(livro.getEdicao()).isEqualTo(1);
        assertThat(livro.getAnoPublicacao()).isEqualTo("2008");
        assertThat(livro.getAutores()).isEmpty();
        assertThat(livro.getAssuntos()).isEmpty();
    }

    @Test
    void deveMappearLivroParaResumoDTO() {
        // given
        Livro livro = criarLivro();

        // when
        LivroResumoDTO resumoDTO = livroMapper.toResumoDTO(livro);

        // then
        assertThat(resumoDTO).isNotNull();
        assertThat(resumoDTO.getId()).isEqualTo(1);
        assertThat(resumoDTO.getTitulo()).isEqualTo("Clean Code");
        assertThat(resumoDTO.getAnoPublicacao()).isEqualTo("2008");
    }

    @Test
    void deveRetornarNullQuandoLivroForNull() {
        assertThat(livroMapper.toDTO(null)).isNull();
        assertThat(livroMapper.toEntity(null)).isNull();
        assertThat(livroMapper.toResumoDTO(null)).isNull();
    }

    private Livro criarLivro() {
        Livro livro = new Livro();
        livro.setCodigo(1);
        livro.setTitulo("Clean Code");
        livro.setEditora("Alta Books");
        livro.setEdicao(1);
        livro.setAnoPublicacao("2008");

        Autor autor = new Autor();
        autor.setCodigo(1);

        Assunto assunto = new Assunto();
        assunto.setCodigo(1);

        livro.setAutores(Set.of(autor));
        livro.setAssuntos(Set.of(assunto));

        return livro;
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
}
