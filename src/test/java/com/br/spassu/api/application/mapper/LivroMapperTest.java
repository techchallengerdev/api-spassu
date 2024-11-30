package com.br.spassu.api.application.mapper;

import com.br.spassu.api.application.dto.LivroDTO;
import com.br.spassu.api.application.dto.LivroResumoDTO;
import com.br.spassu.api.domain.entity.Livro;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashSet;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
@DisplayName("Testes do LivroMapper")
class LivroMapperTest {

    @Mock
    private AutorMapper autorMapper;

    @Mock
    private AssuntoMapper assuntoMapper;

    @InjectMocks
    private LivroMapper livroMapper;

    @Nested
    @DisplayName("Testes de toDTO")
    class ToDTOTests {

        @Test
        @DisplayName("Deve converter Livro para DTO com sucesso")
        void deveConverterLivroParaDTO() {
            // given
            Livro livro = new Livro();
            livro.setCodigo(1);
            livro.setTitulo("Clean Code");
            livro.setEditora("Alta Books");
            livro.setEdicao(1);
            livro.setAnoPublicacao("2008");
            livro.setAutores(new HashSet<>());
            livro.setAssuntos(new HashSet<>());

            // when
            LivroDTO dto = livroMapper.toDTO(livro);

            // then
            assertThat(dto)
                    .isNotNull()
                    .satisfies(d -> {
                        assertThat(d.getId()).isEqualTo(1);
                        assertThat(d.getTitulo()).isEqualTo("Clean Code");
                        assertThat(d.getEditora()).isEqualTo("Alta Books");
                        assertThat(d.getNumeroEdicao()).isEqualTo(1);
                        assertThat(d.getAnoPublicacao()).isEqualTo("2008");
                        assertThat(d.getIdsAutores()).isEmpty();
                        assertThat(d.getIdsAssuntos()).isEmpty();
                    });
        }
    }

    @Test
    @DisplayName("Deve converter para ResumoDTO com sucesso")
    void deveConverterParaResumoDTOComSucesso() {
        // given
        Livro livro = new Livro();
        livro.setCodigo(1);
        livro.setTitulo("Clean Code");
        livro.setAnoPublicacao("2008");

        // when
        LivroResumoDTO resumo = livroMapper.toResumoDTO(livro);

        // then
        assertThat(resumo)
                .isNotNull()
                .satisfies(r -> {
                    assertThat(r.getId()).isEqualTo(1);
                    assertThat(r.getTitulo()).isEqualTo("Clean Code");
                    assertThat(r.getAnoPublicacao()).isEqualTo("2008");
                });
    }
}