package com.br.spassu.api.infrastructure.controller;

import com.br.spassu.api.application.dto.AutorDTO;
import com.br.spassu.api.application.usecase.autor.*;
import com.br.spassu.api.domain.exceptions.BusinessException;
import com.br.spassu.api.domain.exceptions.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@SpringBootTest
@AutoConfigureMockMvc
class AutorControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    CriarAutorUseCase criarAutorUseCase;

    @MockBean
    BuscarAutorUseCase buscarAutorUseCase;

    @MockBean
    ListarAutoresUseCase listarAutoresUseCase;

    @MockBean
    AtualizarAutorUseCase atualizarAutorUseCase;

    @MockBean
    DeletarAutorUseCase deletarAutorUseCase;

    @BeforeEach
    void setup() {
    }

    @Test
    @DisplayName("Deve criar um novo autor com sucesso")
    void testCriarAutor() throws Exception {
        AutorDTO autorDTO = AutorDTO.builder()
                .nome("Autor de Teste")
                .build();

        when(criarAutorUseCase.execute(any(AutorDTO.class))).thenReturn(autorDTO);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/autores")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(autorDTO)))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.nome").value("Autor de Teste"));
    }

    @Test
    @DisplayName("Deve buscar um autor por ID com sucesso")
    void testBuscarAutorPorId() throws Exception {
        AutorDTO autorDTO = AutorDTO.builder()
                .codigo(1)
                .nome("Autor de Teste")
                .build();

        when(buscarAutorUseCase.execute(anyInt())).thenReturn(autorDTO);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/autores/1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.codigo").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.nome").value("Autor de Teste"));
    }

    @Test
    @DisplayName("Deve listar todos os autores com sucesso")
    void testListarTodosOsAutores() throws Exception {
        AutorDTO autorDTO1 = AutorDTO.builder()
                .codigo(1)
                .nome("Autor 1")
                .build();

        AutorDTO autorDTO2 = AutorDTO.builder()
                .codigo(2)
                .nome("Autor 2")
                .build();

        List<AutorDTO> autores = Arrays.asList(autorDTO1, autorDTO2);

        when(listarAutoresUseCase.execute()).thenReturn(autores);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/autores"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$").isArray())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].codigo").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].nome").value("Autor 1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].codigo").value(2))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].nome").value("Autor 2"));
    }

    @Test
    @DisplayName("Deve atualizar um autor com sucesso")
    void testAtualizarAutor() throws Exception {
        Integer id = 1;
        AutorDTO autorDTO = AutorDTO.builder()
                .codigo(id)
                .nome("Autor de Teste Atualizado")
                .build();

        when(atualizarAutorUseCase.execute(anyInt(), any(AutorDTO.class))).thenReturn(autorDTO);

        mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/autores/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(autorDTO)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.codigo").value(id))
                .andExpect(MockMvcResultMatchers.jsonPath("$.nome").value("Autor de Teste Atualizado"));
    }

    @Test
    @DisplayName("Deve deletar um autor com sucesso")
    void testDeletarAutor() throws Exception {
        Integer id = 1;
        doNothing().when(deletarAutorUseCase).execute(anyInt());

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/autores/{id}", id))
                .andExpect(MockMvcResultMatchers.status().isNoContent());
    }

    @Test
    @DisplayName("Deve lançar exceção ao criar um autor com dados inválidos")
    void testCriarAutorComDadosInvalidos() throws Exception {
        AutorDTO autorDTO = AutorDTO.builder()
                .nome(null)
                .build();

        when(criarAutorUseCase.execute(any(AutorDTO.class))).thenThrow(new BusinessException("Nome do autor é obrigatório"));

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/autores")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(autorDTO)))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("Erro de validação"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.errors[0]").value("nome: Nome é obrigatório"));
    }

    @Test
    @DisplayName("Deve lançar exceção ao buscar um autor que não existe")
    void testBuscarAutorInexistente() throws Exception {
        when(buscarAutorUseCase.execute(anyInt())).thenThrow(new EntityNotFoundException("Autor com código 1 não encontrado"));

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/autores/1"))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("Autor com código 1 não encontrado"));
    }

    private static String asJsonString(final Object obj) {
        try {
            return new com.fasterxml.jackson.databind.ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
