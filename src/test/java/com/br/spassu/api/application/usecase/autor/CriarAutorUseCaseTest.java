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

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CriarAutorUseCaseTest {

    @Mock
    private AutorRepository autorRepository;

    @Mock
    private AutorMapper autorMapper;

    @InjectMocks
    private CriarAutorUseCase criarAutorUseCase;

    @Nested
    @DisplayName("Testes de sucesso")
    class TestesComSucesso {

        @Test
        @DisplayName("Deve criar autor com sucesso")
        void deveCriarAutorComSucesso() {

            AutorDTO inputDto = AutorDTO.builder().nome("John Doe").build();
            Autor autorSalvo = Autor.builder().codigo(1).nome("John Doe").build();
            AutorDTO outputDto = AutorDTO.builder().codigo(1).nome("John Doe").build();

            when(autorRepository.save(any(Autor.class))).thenReturn(autorSalvo);
            when(autorMapper.toDto(autorSalvo)).thenReturn(outputDto);

            ResponseWrapper<AutorDTO> response = criarAutorUseCase.execute(inputDto);

            assertThat(response).isNotNull();
            assertThat(response.getMessage()).isEqualTo("Autor criado com sucesso");
            assertThat(response.getData()).isEqualTo(outputDto);
            verify(autorRepository).save(any(Autor.class));
        }

        @Test
        @DisplayName("Deve criar autor removendo espaços em branco do nome")
        void deveCriarAutorRemovendoEspacosEmBranco() {
            AutorDTO inputDto = AutorDTO.builder().nome("  John Doe  ").build();
            Autor autorSalvo = Autor.builder().codigo(1).nome("John Doe").build();
            AutorDTO outputDto = AutorDTO.builder().codigo(1).nome("John Doe").build();

            when(autorRepository.save(any(Autor.class))).thenReturn(autorSalvo);
            when(autorMapper.toDto(autorSalvo)).thenReturn(outputDto);

            ResponseWrapper<AutorDTO> response = criarAutorUseCase.execute(inputDto);

            assertThat(response.getData().getNome()).isEqualTo("John Doe");
        }
    }

    @Nested
    @DisplayName("Testes de validação")
    class TestesValidacao {

        @Test
        @DisplayName("Deve lançar exceção quando DTO for nulo")
        void deveLancarExcecaoQuandoDtoNulo() {
            assertThatThrownBy(() -> criarAutorUseCase.execute(null))
                    .isInstanceOf(InvalidAuthorDataException.class)
                    .hasMessage("Dados do autor não informados");
        }

        @Test
        @DisplayName("Deve lançar exceção quando nome for nulo")
        void deveLancarExcecaoQuandoNomeNulo() {
            AutorDTO dto = AutorDTO.builder().nome(null).build();

            assertThatThrownBy(() -> criarAutorUseCase.execute(dto))
                    .isInstanceOf(InvalidAuthorDataException.class)
                    .hasMessage("Nome não informado, campo obrigatório");
        }

        @Test
        @DisplayName("Deve lançar exceção quando nome for vazio")
        void deveLancarExcecaoQuandoNomeVazio() {
            AutorDTO dto = AutorDTO.builder().nome("").build();

            assertThatThrownBy(() -> criarAutorUseCase.execute(dto))
                    .isInstanceOf(InvalidAuthorDataException.class)
                    .hasMessage("Nome não informado, campo obrigatório");
        }

        @Test
        @DisplayName("Deve lançar exceção quando nome contiver apenas espaços")
        void deveLancarExcecaoQuandoNomeApenasEspacos() {
            AutorDTO dto = AutorDTO.builder().nome("   ").build();

            assertThatThrownBy(() -> criarAutorUseCase.execute(dto))
                    .isInstanceOf(InvalidAuthorDataException.class)
                    .hasMessage("Nome não informado, campo obrigatório");
        }
    }
}