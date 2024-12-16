//package com.br.spassu.api.infrastructure.controller;
//
//import com.br.spassu.api.application.dto.AutorDTO;
//import com.br.spassu.api.application.usecase.autor.*;
//import com.br.spassu.api.domain.exceptions.AuthorNotFoundException;
//import com.br.spassu.api.infrastructure.exception.GlobalExceptionHandler;
//import com.br.spassu.api.infrastructure.response.ResponseWrapper;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Nested;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureWebMvc;
//import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.http.MediaType;
//import org.springframework.test.context.ActiveProfiles;
//import org.springframework.test.context.junit.jupiter.SpringExtension;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.setup.MockMvcBuilders;
//
//import java.util.Arrays;
//import java.util.List;
//
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.ArgumentMatchers.anyInt;
//import static org.mockito.Mockito.when;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
//
//@ExtendWith(SpringExtension.class)
//@WebMvcTest(value = AutorController.class)
//@ActiveProfiles("test")
//@AutoConfigureWebMvc
//class AutorControllerTest {
//
//    @Autowired
//    private MockMvc mockMvc;
//
//    @Autowired
//    private ObjectMapper objectMapper;
//
//    @MockBean
//    private CriarAutorUseCase criarAutorUseCase;
//
//    @MockBean
//    private BuscarAutorUseCase buscarAutorUseCase;
//
//    @MockBean
//    private ListarAutoresUseCase listarAutoresUseCase;
//
//    @MockBean
//    private AtualizarAutorUseCase atualizarAutorUseCase;
//
//    @MockBean
//    private DeletarAutorUseCase deletarAutorUseCase;
//
//    @BeforeEach
//    void setup() {
//        mockMvc = MockMvcBuilders.standaloneSetup(new AutorController(
//                        criarAutorUseCase,
//                        buscarAutorUseCase,
//                        listarAutoresUseCase,
//                        atualizarAutorUseCase,
//                        deletarAutorUseCase))
//                .setControllerAdvice(new GlobalExceptionHandler())
//                .build();
//    }
//
//    @Nested
//    @DisplayName("Listar Autores")
//    class ListarAutores {
//
//        @Test
//        @DisplayName("Deve listar todos autores com sucesso")
//        void deveListarTodosAutoresComSucesso() throws Exception {
//            List<AutorDTO> autores = Arrays.asList(
//                    AutorDTO.builder().codigo(1).nome("Autor 1").build(),
//                    AutorDTO.builder().codigo(2).nome("Autor 2").build()
//            );
//
//            ResponseWrapper<List<AutorDTO>> response = ResponseWrapper.<List<AutorDTO>>builder()
//                    .data(autores)
//                    .message("Lista de autores recuperada com sucesso")
//                    .build();
//
//            when(listarAutoresUseCase.execute()).thenReturn(response);
//
//            mockMvc.perform(get("/api/v1/autores")
//                            .contentType(MediaType.APPLICATION_JSON))
//                    .andExpect(status().isOk())
//                    .andExpect(jsonPath("$.data").isArray())
//                    .andExpect(jsonPath("$.data[0].codigo").value(1))
//                    .andExpect(jsonPath("$.data[0].nome").value("Autor 1"))
//                    .andExpect(jsonPath("$.data[1].codigo").value(2))
//                    .andExpect(jsonPath("$.data[1].nome").value("Autor 2"))
//                    .andExpect(jsonPath("$.message").value("Lista de autores recuperada com sucesso"));
//        }
//    }
//
//    @Nested
//    @DisplayName("Criar Autor")
//    class CriarAutor {
//
//        @Test
//        @DisplayName("Deve criar um novo autor com sucesso")
//        void deveCriarAutorComSucesso() throws Exception {
//            AutorDTO inputDto = AutorDTO.builder()
//                    .nome("Autor de Teste")
//                    .build();
//
//            ResponseWrapper<AutorDTO> response = ResponseWrapper.<AutorDTO>builder()
//                    .data(inputDto)
//                    .message("Autor criado com sucesso")
//                    .build();
//
//            when(criarAutorUseCase.execute(any(AutorDTO.class))).thenReturn(response);
//
//            mockMvc.perform(post("/api/v1/autores")
//                            .contentType(MediaType.APPLICATION_JSON)
//                            .content(objectMapper.writeValueAsString(inputDto)))
//                    .andExpect(status().isCreated())
//                    .andExpect(content().contentType(MediaType.APPLICATION_JSON))
//                    .andExpect(jsonPath("$.data.nome").value("Autor de Teste"))
//                    .andExpect(jsonPath("$.message").value("Autor criado com sucesso"));
//        }
//
//        @Test
//        @DisplayName("Deve retornar erro 400 quando nome não for informado")
//        void deveRetornarErroCampoNomeObrigatorio() throws Exception {
//            AutorDTO inputDto = AutorDTO.builder().build();
//
//            mockMvc.perform(post("/api/v1/autores")
//                            .contentType(MediaType.APPLICATION_JSON)
//                            .content(objectMapper.writeValueAsString(inputDto)))
//                    .andExpect(status().isBadRequest());
//        }
//    }
//
//    @Nested
//    @DisplayName("Buscar Autor")
//    class BuscarAutor {
//
//        @Test
//        @DisplayName("Deve buscar um autor por ID com sucesso")
//        void deveBuscarAutorPorIdComSucesso() throws Exception {
//            Integer id = 1;
//            AutorDTO autorDTO = AutorDTO.builder()
//                    .codigo(id)
//                    .nome("Autor de Teste")
//                    .build();
//
//            ResponseWrapper<AutorDTO> response = ResponseWrapper.<AutorDTO>builder()
//                    .data(autorDTO)
//                    .message("Autor encontrado com sucesso")
//                    .build();
//
//            when(buscarAutorUseCase.execute(id)).thenReturn(response);
//
//            mockMvc.perform(get("/api/v1/autores/{id}", id))
//                    .andExpect(status().isOk())
//                    .andExpect(jsonPath("$.data.codigo").value(id))
//                    .andExpect(jsonPath("$.data.nome").value("Autor de Teste"))
//                    .andExpect(jsonPath("$.message").value("Autor encontrado com sucesso"));
//        }
//
//        @Test
//        @DisplayName("Deve retornar 404 quando autor não for encontrado")
//        void deveRetornar404QuandoAutorNaoEncontrado() throws Exception {
//            when(buscarAutorUseCase.execute(anyInt()))
//                    .thenThrow(new AuthorNotFoundException(1));
//
//            mockMvc.perform(get("/api/v1/autores/1"))
//                    .andExpect(status().isNotFound());
//        }
//    }
//
//    @Nested
//    @DisplayName("Atualizar Autor")
//    class AtualizarAutor {
//
//        @Test
//        @DisplayName("Deve atualizar um autor com sucesso")
//        void deveAtualizarAutorComSucesso() throws Exception {
//            Integer id = 1;
//            AutorDTO autorDTO = AutorDTO.builder()
//                    .codigo(id)
//                    .nome("Autor Atualizado")
//                    .build();
//
//            ResponseWrapper<AutorDTO> response = ResponseWrapper.<AutorDTO>builder()
//                    .data(autorDTO)
//                    .message("Autor atualizado com sucesso")
//                    .build();
//
//            when(atualizarAutorUseCase.execute(anyInt(), any(AutorDTO.class)))
//                    .thenReturn(response);
//
//            mockMvc.perform(put("/api/v1/autores/{id}", id)
//                            .contentType(MediaType.APPLICATION_JSON)
//                            .content(objectMapper.writeValueAsString(autorDTO)))
//                    .andExpect(status().isOk())
//                    .andExpect(jsonPath("$.data.codigo").value(id))
//                    .andExpect(jsonPath("$.data.nome").value("Autor Atualizado"))
//                    .andExpect(jsonPath("$.message").value("Autor atualizado com sucesso"));
//        }
//    }
//
//    @Nested
//    @DisplayName("Deletar Autor")
//    class DeletarAutor {
//
//        @Test
//        @DisplayName("Deve deletar um autor com sucesso")
//        void deveDeletarAutorComSucesso() throws Exception {
//            Integer id = 1;
//            ResponseWrapper<Void> response = ResponseWrapper.<Void>builder()
//                    .message("Autor deletado com sucesso")
//                    .build();
//
//            when(deletarAutorUseCase.execute(id)).thenReturn(response);
//
//            mockMvc.perform(delete("/api/v1/autores/{id}", id))
//                    .andExpect(status().isOk())
//                    .andExpect(jsonPath("$.message").value("Autor deletado com sucesso"))
//                    .andExpect(jsonPath("$.data").doesNotExist());
//        }
//
//        @Test
//        @DisplayName("Deve retornar erro quando autor não existir")
//        void deveRetornarErroQuandoAutorNaoExistir() throws Exception {
//            when(deletarAutorUseCase.execute(anyInt()))
//                    .thenThrow(new AuthorNotFoundException(1));
//
//            mockMvc.perform(delete("/api/v1/autores/1"))
//                    .andExpect(status().isNotFound());
//        }
//    }
//}