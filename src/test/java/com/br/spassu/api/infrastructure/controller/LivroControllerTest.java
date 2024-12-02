package com.br.spassu.api.infrastructure.controller;

import com.br.spassu.api.application.dto.LivroDTO;
import com.br.spassu.api.application.usecase.livro.*;
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
class LivroControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    CriarLivroUseCase criarLivroUseCase;

    @MockBean
    BuscarLivroUseCase buscarLivroUseCase;

    @MockBean
    ListarLivrosUseCase listarLivrosUseCase;

    @MockBean
    AtualizarLivroUseCase atualizarLivroUseCase;

    @MockBean
    DeletarLivroUseCase deletarLivroUseCase;

    @BeforeEach
    void setup() {
    }

    @Test
    @DisplayName("Deve criar um novo livro com sucesso")
    void testCriarLivro() throws Exception {
        LivroDTO livroDTO = LivroDTO.builder()
                .titulo("Livro de Teste")
                .editora("Editora de Teste")
                .edicao(1)
                .anoPublicacao("2023")
                .autorCodAus(Arrays.asList(1, 2))
                .assuntoCodAss(Arrays.asList(3, 4))
                .build();

        when(criarLivroUseCase.execute(any(LivroDTO.class))).thenReturn(livroDTO);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/livros")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(livroDTO)))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.titulo").value("Livro de Teste"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.editora").value("Editora de Teste"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.edicao").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.anoPublicacao").value("2023"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.autorCodAus").isArray())
                .andExpect(MockMvcResultMatchers.jsonPath("$.autorCodAus[0]").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.autorCodAus[1]").value(2))
                .andExpect(MockMvcResultMatchers.jsonPath("$.assuntoCodAss").isArray())
                .andExpect(MockMvcResultMatchers.jsonPath("$.assuntoCodAss[0]").value(3))
                .andExpect(MockMvcResultMatchers.jsonPath("$.assuntoCodAss[1]").value(4));
    }

    @Test
    @DisplayName("Deve buscar um livro por ID com sucesso")
    void testBuscarLivroPorId() throws Exception {
        LivroDTO livroDTO = LivroDTO.builder()
                .codigo(1)
                .titulo("Livro de Teste")
                .editora("Editora de Teste")
                .edicao(1)
                .anoPublicacao("2023")
                .build();

        when(buscarLivroUseCase.execute(anyInt())).thenReturn(livroDTO);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/livros/1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.codigo").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.titulo").value("Livro de Teste"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.editora").value("Editora de Teste"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.edicao").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.anoPublicacao").value("2023"));
    }

    @Test
    @DisplayName("Deve listar todos os livros com sucesso")
    void testListarTodosOsLivros() throws Exception {
        LivroDTO livroDTO1 = LivroDTO.builder()
                .codigo(1)
                .titulo("Livro de Teste 1")
                .editora("Editora de Teste")
                .edicao(1)
                .anoPublicacao("2023")
                .build();

        LivroDTO livroDTO2 = LivroDTO.builder()
                .codigo(2)
                .titulo("Livro de Teste 2")
                .editora("Editora de Teste")
                .edicao(2)
                .anoPublicacao("2024")
                .build();

        List<LivroDTO> livros = Arrays.asList(livroDTO1, livroDTO2);

        when(listarLivrosUseCase.execute()).thenReturn(livros);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/livros"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$").isArray())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].codigo").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].titulo").value("Livro de Teste 1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].editora").value("Editora de Teste"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].edicao").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].anoPublicacao").value("2023"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].codigo").value(2))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].titulo").value("Livro de Teste 2"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].editora").value("Editora de Teste"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].edicao").value(2))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].anoPublicacao").value("2024"));
    }

    @Test
    @DisplayName("Deve atualizar um livro com sucesso")
    void testAtualizarLivro() throws Exception {
        Integer id = 1;
        LivroDTO livroDTO = LivroDTO.builder()
                .codigo(id)
                .titulo("Livro de Teste Atualizado")
                .editora("Editora de Teste Atualizada")
                .edicao(2)
                .anoPublicacao("2025")
                .autorCodAus(Arrays.asList(1, 2))
                .assuntoCodAss(Arrays.asList(3, 4))
                .build();

        when(atualizarLivroUseCase.execute(anyInt(), any(LivroDTO.class))).thenReturn(livroDTO);

        mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/livros/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(livroDTO)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.codigo").value(id))
                .andExpect(MockMvcResultMatchers.jsonPath("$.titulo").value("Livro de Teste Atualizado"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.editora").value("Editora de Teste Atualizada"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.edicao").value(2))
                .andExpect(MockMvcResultMatchers.jsonPath("$.anoPublicacao").value("2025"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.autorCodAus").isArray())
                .andExpect(MockMvcResultMatchers.jsonPath("$.autorCodAus[0]").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.autorCodAus[1]").value(2))
                .andExpect(MockMvcResultMatchers.jsonPath("$.assuntoCodAss").isArray())
                .andExpect(MockMvcResultMatchers.jsonPath("$.assuntoCodAss[0]").value(3))
                .andExpect(MockMvcResultMatchers.jsonPath("$.assuntoCodAss[1]").value(4));
    }

    @Test
    @DisplayName("Deve deletar um livro com sucesso")
    void testDeletarLivro() throws Exception {
        Integer id = 1;
        doNothing().when(deletarLivroUseCase).execute(anyInt());

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/livros/{id}", id))
                .andExpect(MockMvcResultMatchers.status().isNoContent());
    }

    @Test
    @DisplayName("Deve lançar exceção ao deletar um livro")
    void testDeletarLivroComErro() throws Exception {
        Integer id = 1;
        doThrow(new RuntimeException("Erro na deleção")).when(deletarLivroUseCase).execute(anyInt());

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/livros/{id}", id))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    @DisplayName("Deve lançar exceção ao criar um livro com dados inválidos")
    void testCriarLivroComDadosInvalidos() throws Exception {
        LivroDTO livroDTO = LivroDTO.builder()
                .titulo(null)
                .editora("Editora de Teste")
                .edicao(1)
                .anoPublicacao("2023")
                .autorCodAus(Arrays.asList(1, 2))
                .assuntoCodAss(Arrays.asList(3, 4))
                .build();

        when(criarLivroUseCase.execute(any(LivroDTO.class))).thenThrow(new BusinessException("Título é obrigatório"));

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/livros")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(livroDTO)))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("Erro de validação"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.errors[0]").value("titulo: Título é obrigatório"));
    }

    @Test
    @DisplayName("Deve lançar exceção ao buscar um livro que não existe")
    void testBuscarLivroInexistente() throws Exception {
        when(buscarLivroUseCase.execute(anyInt())).thenThrow(new EntityNotFoundException("Livro com código 1 não encontrado"));

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/livros/1"))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("Livro com código 1 não encontrado"));
    }

    private static String asJsonString(final Object obj) {
        try {
            return new com.fasterxml.jackson.databind.ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
