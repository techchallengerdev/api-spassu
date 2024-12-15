package com.br.spassu.api.infrastructure.controller;

import com.br.spassu.api.application.dto.LivroDTO;
import com.br.spassu.api.application.usecase.livro.*;
import com.br.spassu.api.domain.exceptions.EntityNotFoundException;
import com.br.spassu.api.domain.exceptions.InvalidBookDataException;
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

import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@SpringBootTest
@AutoConfigureMockMvc
@DisplayName("Testes do Controller de Livros")
class LivroControllerTest {

    private static final String BASE_URL = "/api/v1/livros";

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

    record LivroTestData(
            String titulo,
            String editora,
            Integer edicao,
            String anoPublicacao,
            List<Integer> autorCodAus,
            List<Integer> assuntoCodAss
    ) {
        static LivroTestData padrao() {
            return new LivroTestData(
                    "O Senhor dos Anéis",
                    "Allen & Unwin",
                    1,
                    "1954",
                    List.of(1, 2),
                    List.of(1, 2)
            );
        }

        LivroDTO toDto(Integer codigo) {
            return LivroDTO.builder()
                    .codigo(codigo)
                    .titulo(titulo)
                    .editora(editora)
                    .edicao(edicao)
                    .anoPublicacao(anoPublicacao)
                    .autorCodAus(autorCodAus)
                    .assuntoCodAss(assuntoCodAss)
                    .build();
        }

        static List<LivroDTO> criarLista() {
            return IntStream.range(0, 2)
                    .mapToObj(i -> padrao().toDto(i + 1))
                    .collect(Collectors.toList());
        }
    }

    @Nested
    @DisplayName("POST /livros - Testes de Criação")
    class CriarLivroTest {

        @Test
        @DisplayName("Deve criar um livro técnico com sucesso")
        void criarLivroTecnico() throws Exception {
            var livroRequest = LivroDTOTestFactory.umLivroBuilder()
                    .codigo(null)
                    .build();

            var livroResponse = LivroDTOTestFactory.umLivroBuilder().build();

            var responseWrapper = ResponseWrapper.<LivroDTO>builder()
                    .message("Livro criado com sucesso")
                    .data(livroResponse)
                    .build();

            when(criarLivroUseCase.execute(any(LivroDTO.class))).thenReturn(responseWrapper);

            performPost(livroRequest)
                    .andExpect(status().isCreated())
                    .andExpect(jsonPath("$.message").value("Livro criado com sucesso"))
                    .andExpect(jsonPath("$.data.codigo").value(1))
                    .andExpect(jsonPath("$.data.titulo").value("Clean Code"))
                    .andExpect(jsonPath("$.data.editora").value("Alta Books"))
                    .andExpect(jsonPath("$.data.autores[0].nome").value("Robert C. Martin"))
                    .andDo(print());
        }

        @Test
        @DisplayName("Deve retornar erro ao criar livro com título em branco")
        void criarLivroSemTitulo() throws Exception {
            var livroInvalido = LivroDTOTestFactory.umLivroSemTitulo().build();

            when(criarLivroUseCase.execute(any(LivroDTO.class)))
                    .thenThrow(new InvalidBookDataException("Título não informado, campo obrigatório"));

            performPost(livroInvalido)
                    .andExpect(status().isUnprocessableEntity())
                    .andExpect(jsonPath("$.type").value("https://api.spassu.com.br/errors/business"))
                    .andExpect(jsonPath("$.title").value("Erro de Regra de Negócio"))
                    .andExpect(jsonPath("$.detail").value("Título não informado, campo obrigatório"))
                    .andDo(print());
        }

        @Test
        @DisplayName("Deve criar vários livros de arquitetura")
        void criarVariosLivros() throws Exception {
            var livros = LivroDTOTestFactory.criarListaLivrosArquitetura();

            for (var livroRequest : livros) {
                var responseWrapper = ResponseWrapper.<LivroDTO>builder()
                        .message("Livro criado com sucesso")
                        .data(livroRequest)
                        .build();

                when(criarLivroUseCase.execute(any(LivroDTO.class))).thenReturn(responseWrapper);

                performPost(livroRequest)
                        .andExpect(status().isCreated())
                        .andExpect(jsonPath("$.message").value("Livro criado com sucesso"))
                        .andExpect(jsonPath("$.data.titulo").exists())
                        .andExpect(jsonPath("$.data.editora").exists())
                        .andDo(print());
            }
        }

    }

    @Nested
    @DisplayName("GET /livros - Testes de Consulta")
    class BuscarLivroTest {

        @Test
        @DisplayName("Deve listar todos os livros Java")
        void listarLivrosJava() throws Exception {
            var livros = LivroDTOTestFactory.criarListaLivrosJava();
            when(listarLivrosUseCase.execute()).thenReturn(livros);

            performGet("")
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.data", hasSize(3)))
                    .andExpect(jsonPath("$.data[*].titulo",
                            hasItems("Effective Java", "Java Concurrency in Practice")))
                    .andExpect(jsonPath("$.data[*].editora",
                            hasItem("Manning")));
        }

        @Test
        @DisplayName("Deve buscar livro por ID")
        void buscarLivroPorId() throws Exception {
            var id = 1;
            var livro = LivroDTOTestFactory.umLivroBuilder().build();
            when(buscarLivroUseCase.execute(id)).thenReturn(livro);

            performGet("/{id}", id)
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.data.codigo").value(id))
                    .andExpect(jsonPath("$.data.editora").value("Prentice Hall"));
        }

        @Test
        @DisplayName("Deve listar todos os livros")
        void listarTodosLivros() throws Exception {
            var livros = LivroTestData.criarLista();
            when(listarLivrosUseCase.execute()).thenReturn(livros);

            performGet("")
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.message").value("Lista de livros recuperada com sucesso"))
                    .andExpect(jsonPath("$.data", hasSize(2)));

            verify(listarLivrosUseCase).execute();
        }

        @Test
        @DisplayName("Deve retornar 404 para livro não encontrado")
        void buscarLivroInexistente() throws Exception {
            when(buscarLivroUseCase.execute(999))
                    .thenThrow(new EntityNotFoundException("Livro não encontrado"));

            performGet("/{id}", 999)
                    .andExpect(status().isNotFound())
                    .andExpect(jsonPath("$.type").value("https://api.spassu.com.br/errors/not-found"));
        }
    }

    @Nested
    @DisplayName("PUT /livros - Testes de Atualização")
    class AtualizarLivroTest {

        @Test
        @DisplayName("Deve atualizar um livro técnico com sucesso")
        void atualizarLivroTecnico() throws Exception {
            var livroRequest = LivroDTOTestFactory.umLivroBuilder()
                    .codigo(null)
                    .build();

            var livroResponse = LivroDTOTestFactory.umLivroBuilder().build();

            var responseWrapper = ResponseWrapper.<LivroDTO>builder()
                    .message("Livro atualizado com sucesso")
                    .data(livroResponse)
                    .build();

            when(atualizarLivroUseCase.execute(eq(1), any(LivroDTO.class)))
                    .thenReturn(responseWrapper);

            mockMvc.perform(put("/api/livros/{id}", 1)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(livroRequest)))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.message").value("Livro atualizado com sucesso"))
                    .andExpect(jsonPath("$.data.codigo").value(1))
                    .andExpect(jsonPath("$.data.titulo").value("Clean Code"))
                    .andExpect(jsonPath("$.data.editora").value("Alta Books"))
                    .andDo(print());
        }

        @Test
        @DisplayName("Deve retornar erro ao atualizar livro com título em branco")
        void atualizarLivroSemTitulo() throws Exception {
            var livroInvalido = LivroDTOTestFactory.umLivroSemTitulo().build();

            mockMvc.perform(put("/api/livros/{id}", 1)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(livroInvalido)))
                    .andExpect(status().isUnprocessableEntity())
                    .andExpect(jsonPath("$.type").value("https://api.spassu.com.br/errors/business"))
                    .andExpect(jsonPath("$.detail").value("Título não informado, campo obrigatório"))
                    .andDo(print());
        }

        @Test
        @DisplayName("Deve falhar ao atualizar livro inexistente")
        void atualizarLivroInexistente() throws Exception {
            var id = 999;
            var livro = LivroDTOTestFactory.umLivroBuilder().build();

            when(atualizarLivroUseCase.execute(eq(id), any()))
                    .thenThrow(new EntityNotFoundException("Livro não encontrado"));

            performPut(livro, id)
                    .andExpect(status().isNotFound());
        }

        @Test
        @DisplayName("Deve atualizar vários livros de arquitetura")
        void atualizarVariosLivros() throws Exception {
            var livros = LivroDTOTestFactory.criarListaLivrosArquitetura();

            for (var livroRequest : livros) {
                var responseWrapper = ResponseWrapper.<LivroDTO>builder()
                        .message("Livro atualizado com sucesso")
                        .data(livroRequest)
                        .build();

                when(atualizarLivroUseCase.execute(eq(livroRequest.getCodigo()), any(LivroDTO.class)))
                        .thenReturn(responseWrapper);

                mockMvc.perform(put("/api/livros/{id}", livroRequest.getCodigo())
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(livroRequest)))
                        .andExpect(status().isOk())
                        .andExpect(jsonPath("$.message").value("Livro atualizado com sucesso"))
                        .andExpect(jsonPath("$.data.titulo").value(livroRequest.getTitulo()))
                        .andDo(print());
            }
        }
    }

    @Nested
    @DisplayName("DELETE /livros - Testes de Deleção")
    class DeletarLivroTest {

        @Test
        @DisplayName("Deve deletar um livro com sucesso")
        void deletarLivro() throws Exception {
            var id = 1;
            doNothing().when(deletarLivroUseCase).execute(id);

            performDelete(id)
                    .andExpect(status().isNoContent());

            verify(deletarLivroUseCase).execute(id);
        }

        @Test
        @DisplayName("Deve falhar ao tentar deletar livro inexistente")
        void deletarLivroInexistente() throws Exception {
            var id = 999;
            doThrow(new EntityNotFoundException("Livro não encontrado"))
                    .when(deletarLivroUseCase).execute(id);

            performDelete(id)
                    .andExpect(status().isNotFound())
                    .andExpect(jsonPath("$.type").value("https://api.spassu.com.br/errors/not-found"));
        }
    }

    private ResultActions performPost(Object content) throws Exception {
        return mockMvc.perform(post(BASE_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(content)))
                .andDo(print());
    }

    private ResultActions performGet(String path, Object... urlVariables) throws Exception {
        return mockMvc.perform(get(BASE_URL + path, urlVariables)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print());
    }

    private ResultActions performPut(Object content, Object... urlVariables) throws Exception {
        return mockMvc.perform(put(BASE_URL + "/{id}", urlVariables)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(content)))
                .andDo(print());
    }

    private ResultActions performDelete(Object... urlVariables) throws Exception {
        return mockMvc.perform(delete(BASE_URL + "/{id}", urlVariables))
                .andDo(print());
    }

    static class LivroDTOTestFactory {
        static LivroDTO.LivroDTOBuilder umLivroBuilder() {
            return LivroDTO.builder()
                    .codigo(1)
                    .titulo("Clean Code")
                    .editora("Prentice Hall")
                    .edicao(1)
                    .anoPublicacao("2008")
                    .autorCodAus(Arrays.asList(1, 2))
                    .assuntoCodAss(Arrays.asList(1, 2));
        }

        static LivroDTO.LivroDTOBuilder umLivroComTituloBuilder(String titulo) {
            return umLivroBuilder().titulo(titulo);
        }

        static LivroDTO.LivroDTOBuilder umLivroSemTitulo() {
            return umLivroBuilder()
                    .titulo(null)
                    .editora("Prentice Hall")
                    .edicao(1)
                    .anoPublicacao("2023");
        }

        static LivroDTO.LivroDTOBuilder umLivroComCodigoBuilder(Integer codigo) {
            return umLivroBuilder().codigo(codigo);
        }

        static LivroDTO umLivroCompleto() {
            return umLivroBuilder().build();
        }

        static LivroDTO umLivroAtualizado() {
            return umLivroBuilder()
                    .titulo("Clean Code - Segunda Edição")
                    .edicao(2)
                    .anoPublicacao("2024")
                    .build();
        }

        static List<LivroDTO> criarListaLivros(int quantidade) {
            return IntStream.range(0, quantidade)
                    .mapToObj(i -> umLivroBuilder()
                            .codigo(i + 1)
                            .titulo("Clean Code Volume " + (i + 1))
                            .build())
                    .collect(Collectors.toList());
        }

        static List<LivroDTO> criarListaLivrosTI() {
            return Arrays.asList(
                    umLivroBuilder()
                            .codigo(1)
                            .titulo("Clean Code")
                            .build(),
                    umLivroBuilder()
                            .codigo(2)
                            .titulo("Design Patterns")
                            .editora("Addison-Wesley")
                            .build(),
                    umLivroBuilder()
                            .codigo(3)
                            .titulo("Refactoring")
                            .editora("Addison-Wesley")
                            .build()
            );
        }

        static List<LivroDTO> criarListaLivrosJava() {
            return Arrays.asList(
                    umLivroBuilder()
                            .codigo(1)
                            .titulo("Effective Java")
                            .editora("Addison-Wesley")
                            .build(),
                    umLivroBuilder()
                            .codigo(2)
                            .titulo("Java Concurrency in Practice")
                            .editora("Addison-Wesley")
                            .build(),
                    umLivroBuilder()
                            .codigo(3)
                            .titulo("Modern Java in Action")
                            .editora("Manning")
                            .build()
            );
        }

        static List<LivroDTO> criarListaLivrosArquitetura() {
            return Arrays.asList(
                    umLivroBuilder()
                            .codigo(1)
                            .titulo("Clean Architecture")
                            .editora("Prentice Hall")
                            .build(),
                    umLivroBuilder()
                            .codigo(2)
                            .titulo("Domain-Driven Design")
                            .editora("Addison-Wesley")
                            .build(),
                    umLivroBuilder()
                            .codigo(3)
                            .titulo("Building Microservices")
                            .editora("O'Reilly")
                            .build()
            );
        }
    }


}