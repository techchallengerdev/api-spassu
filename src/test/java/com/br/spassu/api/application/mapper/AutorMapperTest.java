package com.br.spassu.api.application.mapper;

import com.br.spassu.api.application.dto.AutorDTO;
import com.br.spassu.api.domain.entity.Autor;
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
@DisplayName("Testes do AutorMapper")
class AutorMapperTest {

    @Mock
    private LivroMapper livroMapper;

    @InjectMocks
    private AutorMapper autorMapper;

    @Nested
    @DisplayName("Testes de toDTO")
    class ToDTOTests {

        @Test
        @DisplayName("Deve converter Autor para DTO com sucesso")
        void deveConverterAutorParaDTO() {
            Autor autor = new Autor();
            autor.setCodigo(1);
            autor.setNome("Robert C. Martin");
            autor.setLivros(new HashSet<>());

            AutorDTO dto = autorMapper.toDTO(autor);

            assertThat(dto)
                    .isNotNull()
                    .satisfies(d -> {
                        assertThat(d.getId()).isEqualTo(1);
                        assertThat(d.getNome()).isEqualTo("Robert C. Martin");
                        assertThat(d.getLivros()).isEmpty();
                    });
        }
    }

    @Nested
    @DisplayName("Testes de toDomain")
    class ToDomainTests {

        @Test
        @DisplayName("Deve converter DTO para Domain com sucesso")
        void deveConverterDTOParaDomain() {
            AutorDTO dto = new AutorDTO();
            dto.setId(1);
            dto.setNome("Robert C. Martin");

            Autor domain = autorMapper.toDomain(dto);

            assertThat(domain)
                    .isNotNull()
                    .satisfies(d -> {
                        assertThat(d.getCodigo()).isEqualTo(1);
                        assertThat(d.getNome()).isEqualTo("Robert C. Martin");
                        assertThat(d.getLivros()).isEmpty();
                    });
        }
    }
}
