package com.br.spassu.api.application.mapper;

import com.br.spassu.api.application.dto.AssuntoDTO;
import com.br.spassu.api.domain.entity.Assunto;
import com.br.spassu.api.infrastructure.persistence.entity.AssuntoEntity;
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
@DisplayName("Testes do AssuntoMapper")
class AssuntoMapperTest {

    @Mock
    private LivroMapper livroMapper;

    @InjectMocks
    private AssuntoMapper assuntoMapper;

    @Nested
    @DisplayName("Testes de toDTO")
    class ToDTOTests {

        @Test
        @DisplayName("Deve converter Assunto para DTO com sucesso")
        void deveConverterAssuntoParaDTO() {
            Assunto assunto = new Assunto();
            assunto.setCodigo(1);
            assunto.setDescricao("Programação");
            assunto.setLivros(new HashSet<>());

            AssuntoDTO dto = assuntoMapper.toDTO(assunto);

            assertThat(dto)
                    .isNotNull()
                    .satisfies(d -> {
                        assertThat(d.getId()).isEqualTo(1);
                        assertThat(d.getDescricao()).isEqualTo("Programação");
                        assertThat(d.getLivros()).isEmpty();
                    });
        }

        @Test
        @DisplayName("Deve retornar null quando Assunto for null")
        void deveRetornarNullQuandoAssuntoNull() {
            assertThat(assuntoMapper.toDTO(null)).isNull();
        }
    }

    @Nested
    @DisplayName("Testes de toDomain")
    class ToDomainTests {

        @Test
        @DisplayName("Deve converter DTO para Domain com sucesso")
        void deveConverterDTOParaDomain() {
            // given
            AssuntoDTO dto = new AssuntoDTO();
            dto.setId(1);
            dto.setDescricao("Programação");

            // when
            Assunto domain = assuntoMapper.toDomain(dto);

            // then
            assertThat(domain)
                    .isNotNull()
                    .satisfies(d -> {
                        assertThat(d.getCodigo()).isEqualTo(1);
                        assertThat(d.getDescricao()).isEqualTo("Programação");
                        assertThat(d.getLivros()).isEmpty();
                    });
        }

        @Test
        @DisplayName("Deve converter Entity para Domain com sucesso")
        void deveConverterEntityParaDomain() {
            // given
            AssuntoEntity entity = new AssuntoEntity();
            entity.setCodigo(1);
            entity.setDescricao("Programação");
            entity.setLivros(new HashSet<>());

            // when
            Assunto domain = assuntoMapper.toDomain(entity);

            // then
            assertThat(domain)
                    .isNotNull()
                    .satisfies(d -> {
                        assertThat(d.getCodigo()).isEqualTo(1);
                        assertThat(d.getDescricao()).isEqualTo("Programação");
                        assertThat(d.getLivros()).isEmpty();
                    });
        }
    }

    @Nested
    @DisplayName("Testes de toEntity")
    class ToEntityTests {

        @Test
        @DisplayName("Deve converter Domain para Entity com sucesso")
        void deveConverterDomainParaEntity() {
            // given
            Assunto domain = new Assunto();
            domain.setCodigo(1);
            domain.setDescricao("Programação");

            // when
            AssuntoEntity entity = assuntoMapper.toEntity(domain);

            // then
            assertThat(entity)
                    .isNotNull()
                    .satisfies(e -> {
                        assertThat(e.getCodigo()).isEqualTo(1);
                        assertThat(e.getDescricao()).isEqualTo("Programação");
                        assertThat(e.getLivros()).isEmpty();
                    });
        }
    }

    @Test
    @DisplayName("Deve atualizar Entity com sucesso")
    void deveAtualizarEntityComSucesso() {
        // given
        AssuntoEntity entity = new AssuntoEntity();
        entity.setDescricao("Old");

        Assunto domain = new Assunto();
        domain.setDescricao("New");

        // when
        assuntoMapper.updateEntity(entity, domain);

        // then
        assertThat(entity.getDescricao()).isEqualTo("New");
    }
}
