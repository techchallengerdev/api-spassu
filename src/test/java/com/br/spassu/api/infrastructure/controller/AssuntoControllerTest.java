package com.br.spassu.api.infrastructure.controller;

import com.br.spassu.api.application.dto.AssuntoDTO;
import com.br.spassu.api.application.usecase.assunto.*;
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
import static org.mockito.Mockito.*;

@SpringBootTest
@AutoConfigureMockMvc
class AssuntoControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    CriarAssuntoUseCase criarAssuntoUseCase;

    @MockBean
    BuscarAssuntoUseCase buscarAssuntoUseCase;

    @MockBean
    ListarAssuntosUseCase listarAssuntosUseCase;

    @MockBean
    AtualizarAssuntoUseCase atualizarAssuntoUseCase;

    @MockBean
    DeletarAssuntoUseCase deletarAssuntoUseCase;

    @BeforeEach
    void setup() {
    }

    @Test
    @DisplayName("Deve criar um novo assunto com sucesso")
    void testCriarAssunto() throws Exception {
        AssuntoDTO assuntoDTO = AssuntoDTO.builder()
                .descricao("Assunto de Teste")
                .build();

        when(criarAssuntoUseCase.execute(any(AssuntoDTO.class))).thenReturn(assuntoDTO);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/assuntos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(assuntoDTO)))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.descricao").value("Assunto de Teste"));
    }

    @Test
    @DisplayName("Deve buscar um assunto por ID com sucesso")
    void testBuscarAssuntoPorId() throws Exception {
        AssuntoDTO assuntoDTO = AssuntoDTO.builder()
                .codigo(1)
                .descricao("Assunto de Teste")
                .build();

        when(buscarAssuntoUseCase.execute(anyInt())).thenReturn(assuntoDTO);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/assuntos/1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.codigo").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.descricao").value("Assunto de Teste"));
    }

    @Test
    @DisplayName("Deve listar todos os assuntos com sucesso")
    void testListarTodosOsAssuntos() throws Exception {
        AssuntoDTO assuntoDTO1 = AssuntoDTO.builder()
                .codigo(1)
                .descricao("Assunto 1")
                .build();

        AssuntoDTO assuntoDTO2 = AssuntoDTO.builder()
                .codigo(2)
                .descricao("Assunto 2")
                .build();

        List<AssuntoDTO> assuntos = Arrays.asList(assuntoDTO1, assuntoDTO2);

        when(listarAssuntosUseCase.execute()).thenReturn(assuntos);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/assuntos"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$").isArray())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].codigo").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].descricao").value("Assunto 1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].codigo").value(2))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].descricao").value("Assunto 2"));
    }

    @Test
    @DisplayName("Deve atualizar um assunto com sucesso")
    void testAtualizarAssunto() throws Exception {
        Integer id = 1;
        AssuntoDTO assuntoDTO = AssuntoDTO.builder()
                .codigo(id)
                .descricao("Teste Atualizado")
                .build();

        when(atualizarAssuntoUseCase.execute(anyInt(), any(AssuntoDTO.class))).thenReturn(assuntoDTO);

        mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/assuntos/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(assuntoDTO)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.codigo").value(id))
                .andExpect(MockMvcResultMatchers.jsonPath("$.descricao").value("Teste Atualizado"));
    }

    @Test
    @DisplayName("Deve deletar um assunto com sucesso")
    void testDeletarAssunto() throws Exception {
        Integer id = 1;
        doNothing().when(deletarAssuntoUseCase).execute(anyInt());

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/assuntos/{id}", id))
                .andExpect(MockMvcResultMatchers.status().isNoContent());
    }

    @Test
    @DisplayName("Deve lançar exceção ao deletar um assunto")
    void testDeletarAssuntoComErro() throws Exception {
        Integer id = 1;
        doThrow(new RuntimeException("Erro na deleção")).when(deletarAssuntoUseCase).execute(anyInt());

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/assuntos/{id}", id))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    @DisplayName("Deve lançar exceção ao criar um assunto com dados inválidos")
    void testCriarAssuntoComDadosInvalidos() throws Exception {
        AssuntoDTO assuntoDTO = AssuntoDTO.builder()
                .descricao(null)
                .build();

        when(criarAssuntoUseCase.execute(any(AssuntoDTO.class))).thenThrow(new BusinessException("Descrição do assunto é obrigatória"));

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/assuntos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(assuntoDTO)))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("Erro de validação"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.errors[0]").value("descricao: Descrição é obrigatória"));
    }

    @Test
    @DisplayName("Deve lançar exceção ao buscar um assunto que não existe")
    void testBuscarAssuntoInexistente() throws Exception {
        when(buscarAssuntoUseCase.execute(anyInt())).thenThrow(new EntityNotFoundException("Assunto com código 1 não encontrado"));

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/assuntos/1"))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("Assunto com código 1 não encontrado"));
    }

    private static String asJsonString(final Object obj) {
        try {
            return new com.fasterxml.jackson.databind.ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}