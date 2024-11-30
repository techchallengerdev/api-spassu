package com.br.spassu.api.application.mapper;

import com.br.spassu.api.domain.entity.Assunto;
import com.br.spassu.api.domain.entity.Livro;
import com.br.spassu.api.application.dto.AssuntoDTO;
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
class AssuntoMapperTest {

    @Mock
    private LivroMapper livroMapper;

    private AssuntoMapper assuntoMapper;

    @BeforeEach
    void setUp() {
        assuntoMapper = new AssuntoMapper(livroMapper);
    }

    @Test
    void deveMappearAssuntoParaDTO() {

        Assunto assunto = criarAssunto();
        Livro livro = new Livro();
        livro.setCodigo(1);
        assunto.setLivros(Set.of(livro));

        LivroResumoDTO livroResumoDTO = new LivroResumoDTO();
        livroResumoDTO.setId(1);
        when(livroMapper.toResumoDTO(livro)).thenReturn(livroResumoDTO);

        AssuntoDTO dto = assuntoMapper.toDTO(assunto);

        assertThat(dto).isNotNull();
        assertThat(dto.getId()).isEqualTo(1);
        assertThat(dto.getDescricao()).isEqualTo("Programação");
        assertThat(dto.getLivros()).hasSize(1);
    }

    @Test
    void deveMappearDTOParaAssunto() {
        AssuntoDTO dto = criarAssuntoDTO();
        Assunto assunto = assuntoMapper.toEntity(dto);
        assertThat(assunto).isNotNull();
        assertThat(assunto.getCodigo()).isEqualTo(1);
        assertThat(assunto.getDescricao()).isEqualTo("Programação");
    }

    @Test
    void deveRetornarNullQuandoAssuntoForNull() {
        assertThat(assuntoMapper.toDTO(null)).isNull();
        assertThat(assuntoMapper.toEntity(null)).isNull();
    }

    private Assunto criarAssunto() {
        Assunto assunto = new Assunto();
        assunto.setCodigo(1);
        assunto.setDescricao("Programação");
        return assunto;
    }

    private AssuntoDTO criarAssuntoDTO() {
        AssuntoDTO dto = new AssuntoDTO();
        dto.setId(1);
        dto.setDescricao("Programação");
        return dto;
    }
}
