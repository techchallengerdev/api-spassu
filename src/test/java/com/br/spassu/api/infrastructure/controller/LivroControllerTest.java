package com.br.spassu.api.infrastructure.controller;

import com.br.spassu.api.application.dto.LivroDTO;
import com.br.spassu.api.application.usecase.livro.*;
import com.br.spassu.api.domain.exceptions.EntityNotFoundException;
import com.br.spassu.api.infrastructure.response.ResponseWrapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class LivroControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private CriarLivroUseCase criarLivroUseCase;

    @MockBean
    private BuscarLivroUseCase buscarLivroUseCase;

    @MockBean
    private ListarLivrosUseCase listarLivrosUseCase;

    @MockBean
    private AtualizarLivroUseCase atualizarLivroUseCase;

    @MockBean
    private DeletarLivroUseCase deletarLivroUseCase;

    @Nested
    @DisplayName("Testes do endpoint POST /api/v1/livros")
    class CriarLivroTest {

        @Test
        @DisplayName("Deve criar um livro com sucesso")
        void deveCriarLivroComSucesso() throws Exception {
            var livroDTO = LivroDTO.builder()
                    .titulo("Clean Code")
                    .editora("Prentice Hall")
                    .edicao(1)
                    .anoPublicacao("2008")
                    .autorCodAus(List.of(1, 2))
                    .assuntoCodAss(List.of(1, 2))
                    .build();

            var responseWrapper = ResponseWrapper.<LivroDTO>builder()
                    .message("Livro criado com sucesso")
                    .data(livroDTO.toBuilder().codigo(1).build())
                    .build();

            when(criarLivroUseCase.execute(any(LivroDTO.class))).thenReturn(responseWrapper);

            performPost(livroDTO)
                    .andExpect(status().isCreated())
                    .andExpect(jsonPath("$.message").value("Livro criado com sucesso"))
                    .andExpect(jsonPath("$.data.codigo").value(1));
        }
    }

    @Nested
    @DisplayName("Testes do endpoint GET /api/v1/livros/{id}")
    class BuscarLivroTest {

        @Test
        @DisplayName("Deve buscar um livro pelo ID com sucesso")
        void deveBuscarLivroPorIdComSucesso() throws Exception {
            var codigo = 1;

            var livroDTO = LivroDTO.builder()
                    .codigo(codigo)
                    .titulo("Clean Code")
                    .editora("Prentice Hall")
                    .edicao(1)
                    .anoPublicacao("2008")
                    .autorCodAus(List.of(1, 2))
                    .assuntoCodAss(List.of(1, 2))
                    .build();

            var responseWrapper = ResponseWrapper.<LivroDTO>builder()
                    .message("Livro encontrado com sucesso")
                    .data(livroDTO)
                    .build();

            when(buscarLivroUseCase.execute(codigo)).thenReturn(responseWrapper);

            performGet("/api/v1/livros/{id}", codigo)
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.message").value("Livro encontrado com sucesso"))
                    .andExpect(jsonPath("$.data.codigo").value(codigo));
        }

        @Test
        @DisplayName("Deve retornar erro 404 ao buscar livro não existente")
        void deveRetornarErro404AoBuscarLivroNaoExistente() throws Exception {
            var codigo = 999;

            when(buscarLivroUseCase.execute(codigo)).thenThrow(new EntityNotFoundException("Livro não encontrado"));

            performGet("/api/v1/livros/{id}", codigo)
                    .andExpect(status().isNotFound());
        }
    }

    @Nested
    @DisplayName("Testes do endpoint GET /api/v1/livros")
    class ListarLivrosTest {

        @Test
        @DisplayName("Deve listar todos os livros")
        void deveListarTodosOsLivros() throws Exception {
            var livro1 = LivroDTO.builder()
                    .codigo(1)
                    .titulo("Clean Code")
                    .editora("Prentice Hall")
                    .build();

            var livro2 = LivroDTO.builder()
                    .codigo(2)
                    .titulo("Effective Java")
                    .editora("Addison-Wesley")
                    .build();

            var responseWrapper = ResponseWrapper.<List<LivroDTO>>builder()
                    .message("Lista de livros recuperada com sucesso")
                    .data(List.of(livro1, livro2))
                    .build();

            when(listarLivrosUseCase.execute()).thenReturn(responseWrapper);

            performGet("/api/v1/livros")
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.message").value("Lista de livros recuperada com sucesso"))
                    .andExpect(jsonPath("$.data[0].codigo").value(1))
                    .andExpect(jsonPath("$.data[0].titulo").value("Clean Code"))
                    .andExpect(jsonPath("$.data[1].codigo").value(2))
                    .andExpect(jsonPath("$.data[1].titulo").value("Effective Java"));
        }
    }

    @Nested
    @DisplayName("Testes do endpoint PUT /api/v1/livros/{id}")
    class AtualizarLivroTest {

        @Test
        @DisplayName("Deve atualizar um livro com sucesso")
        void deveAtualizarLivroComSucesso() throws Exception {
            var codigo = 1;

            var livroDTO = LivroDTO.builder()
                    .titulo("Clean Code - 2nd Edition")
                    .editora("Prentice Hall")
                    .edicao(2)
                    .anoPublicacao("2020")
                    .autorCodAus(List.of(1))
                    .assuntoCodAss(List.of(1))
                    .build();

            var responseWrapper = ResponseWrapper.<LivroDTO>builder()
                    .message("Livro atualizado com sucesso")
                    .data(livroDTO.toBuilder().codigo(codigo).build())
                    .build();

            when(atualizarLivroUseCase.execute(eq(codigo), any(LivroDTO.class))).thenReturn(responseWrapper);

            performPut(livroDTO, codigo)
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.message").value("Livro atualizado com sucesso"))
                    .andExpect(jsonPath("$.data.codigo").value(codigo));
        }

        @Test
        @DisplayName("Deve retornar erro 404 ao atualizar livro não existente")
        void deveRetornarErro404AoAtualizarLivroNaoExistente() throws Exception {
            var codigo = 999;

            var livroDTO = LivroDTO.builder()
                    .titulo("Clean Code - 2nd Edition")
                    .editora("Prentice Hall")
                    .edicao(2)
                    .anoPublicacao("2020")
                    .autorCodAus(List.of(1))
                    .assuntoCodAss(List.of(1))
                    .build();

            when(atualizarLivroUseCase.execute(eq(codigo), any(LivroDTO.class)))
                    .thenThrow(new EntityNotFoundException("Livro não encontrado"));

            performPut(livroDTO, codigo)
                    .andExpect(status().isNotFound());
        }
    }

    @Nested
    @DisplayName("Testes do endpoint DELETE /api/v1/livros/{id}")
    class DeletarLivroTest {

        @Test
        @DisplayName("Deve deletar um livro com sucesso")
        void deveDeletarLivroComSucesso() throws Exception {
            var codigo = 1;

            doNothing().when(deletarLivroUseCase).execute(codigo);

            performDelete(codigo)
                    .andExpect(status().isNoContent());

            verify(deletarLivroUseCase, times(1)).execute(codigo);
        }

        @Test
        @DisplayName("Deve retornar erro 404 ao deletar livro não existente")
        void deveRetornarErro404AoDeletarLivroNaoExistente() throws Exception {
            var codigo = 999;

            doThrow(new EntityNotFoundException("Livro não encontrado"))
                    .when(deletarLivroUseCase).execute(codigo);

            performDelete(codigo)
                    .andExpect(status().isNotFound());

            verify(deletarLivroUseCase, times(1)).execute(codigo);
        }
    }

    private ResultActions performPost(Object requestBody) throws Exception {
        return mockMvc.perform(post("/api/v1/livros")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(requestBody)));
    }

    private ResultActions performGet(String url, Object... urlVariables) throws Exception {
        return mockMvc.perform(get(url, urlVariables)
                .contentType(MediaType.APPLICATION_JSON));
    }

    private ResultActions performPut(Object requestBody, Object... urlVariables) throws Exception {
        return mockMvc.perform(put("/api/v1/livros/{id}", urlVariables)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(requestBody)));
    }

    private ResultActions performDelete(Object... urlVariables) throws Exception {
        return mockMvc.perform(delete("/api/v1/livros/{id}", urlVariables));
    }
}