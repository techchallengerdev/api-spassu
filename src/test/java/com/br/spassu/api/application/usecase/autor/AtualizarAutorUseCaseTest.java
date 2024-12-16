package com.br.spassu.api.application.usecase.autor;

import com.br.spassu.api.application.dto.AutorDTO;
import com.br.spassu.api.application.mapper.AutorMapper;
import com.br.spassu.api.domain.entity.Autor;
import com.br.spassu.api.domain.exceptions.InvalidAuthorDataException;
import com.br.spassu.api.domain.repository.AutorRepository;
import com.br.spassu.api.infrastructure.response.ResponseWrapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AtualizarAutorUseCaseTest {

    @Mock
    private AutorRepository autorRepository;

    @Mock
    private AutorMapper autorMapper;

    @InjectMocks
    private AtualizarAutorUseCase atualizarAutorUseCase;

    @Nested
    @DisplayName("Testes de sucesso")
    class TestesComSucesso {

        @Test
        @DisplayName("Deve atualizar autor com sucesso")
        void deveAtualizarAutorComSucesso() {

            Integer codigo = 1;
            AutorDTO inputDto = AutorDTO.builder().nome("John Doe Updated").build();
            Autor autorExistente = Autor.builder().codigo(codigo).nome("John Doe").build();
            Autor autorAtualizado = Autor.builder().codigo(codigo).nome("John Doe Updated").build();
            AutorDTO outputDto = AutorDTO.builder().codigo(codigo).nome("John Doe Updated").build();

            when(autorRepository.findByCodigo(codigo)).thenReturn(Optional.of(autorExistente));
            when(autorRepository.save(any(Autor.class))).thenReturn(autorAtualizado);
            when(autorMapper.toDto(autorAtualizado)).thenReturn(outputDto);

            ResponseWrapper<AutorDTO> response = atualizarAutorUseCase.execute(codigo, inputDto);

            assertThat(response).isNotNull();
            assertThat(response.getMessage()).isEqualTo("Autor atualizado com sucesso");
            assertThat(response.getData()).isEqualTo(outputDto);
            assertThat(response.getData().getNome()).isEqualTo("John Doe Updated");
            verify(autorRepository).save(any(Autor.class));
        }

        @Test
        @DisplayName("Deve atualizar autor removendo espaços em branco do nome")
        void deveAtualizarAutorRemovendoEspacosEmBranco() {

            Integer codigo = 1;
            AutorDTO inputDto = AutorDTO.builder().nome("  John Doe Updated  ").build();
            Autor autorExistente = Autor.builder().codigo(codigo).nome("John Doe").build();
            Autor autorAtualizado = Autor.builder().codigo(codigo).nome("John Doe Updated").build();
            AutorDTO outputDto = AutorDTO.builder().codigo(codigo).nome("John Doe Updated").build();

            when(autorRepository.findByCodigo(codigo)).thenReturn(Optional.of(autorExistente));
            when(autorRepository.save(any(Autor.class))).thenReturn(autorAtualizado);
            when(autorMapper.toDto(autorAtualizado)).thenReturn(outputDto);

            ResponseWrapper<AutorDTO> response = atualizarAutorUseCase.execute(codigo, inputDto);

            assertThat(response.getData().getNome()).isEqualTo("John Doe Updated");
        }
    }

    @Nested
    @DisplayName("Testes de validação")
    class TestesValidacao {

        @Test
        @DisplayName("Deve lançar exceção quando código for inválido")
        void deveLancarExcecaoQuandoCodigoInvalido() {
            AutorDTO dto = AutorDTO.builder().nome("John Doe").build();

            assertThatThrownBy(() -> atualizarAutorUseCase.execute(null, dto))
                    .isInstanceOf(InvalidAuthorDataException.class)
                    .hasMessage("Código do autor inválido");

            assertThatThrownBy(() -> atualizarAutorUseCase.execute(0, dto))
                    .isInstanceOf(InvalidAuthorDataException.class)
                    .hasMessage("Código do autor inválido");
        }
    }
}