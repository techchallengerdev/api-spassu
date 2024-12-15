package com.br.spassu.api.infrastructure.controller;

import com.br.spassu.api.application.dto.LivroDTO;
import com.br.spassu.api.application.usecase.livro.*;
import com.br.spassu.api.infrastructure.exception.ApiError;
import com.br.spassu.api.infrastructure.response.ResponseWrapper;
import com.br.spassu.api.infrastructure.validation.ValidJson;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/livros")
@RequiredArgsConstructor
@Tag(name = "Livros", description = "API de gerenciamento de livros")
public class LivroController {

    private final CriarLivroUseCase criarLivroUseCase;
    private final BuscarLivroUseCase buscarLivroUseCase;
    private final ListarLivrosUseCase listarLivrosUseCase;
    private final AtualizarLivroUseCase atualizarLivroUseCase;
    private final DeletarLivroUseCase deletarLivroUseCase;
    private final ObjectMapper objectMapper;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Criar novo livro")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Livro criado com sucesso",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ApiResponse.class))),
            @ApiResponse(responseCode = "400", description = "Dados de entrada inválidos",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ApiError.class))),
            @ApiResponse(responseCode = "422", description = "Erro de validação dos dados",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ApiError.class)))
    })
    public ResponseWrapper<LivroDTO> criar(@Valid @RequestBody LivroDTO livroDTO) {
        return criarLivroUseCase.execute(livroDTO);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar livro por ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Livro encontrado",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiResponse.class))),
            @ApiResponse(responseCode = "404", description = "Livro não encontrado",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiError.class)))
    })
    public ResponseWrapper<LivroDTO> buscarPorId(@PathVariable Integer id) {
        LivroDTO livro = buscarLivroUseCase.execute(id);
        return ResponseWrapper.<LivroDTO>builder()
                .message("Livro encontrado com sucesso")
                .data(livro)
                .build();
    }

    @GetMapping
    @Operation(summary = "Listar todos os livros")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de livros recuperada com sucesso",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiResponse.class))),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiError.class)))
    })
    public ResponseWrapper<List<LivroDTO>> listarTodos() {
        List<LivroDTO> livros = listarLivrosUseCase.execute();
        return ResponseWrapper.<List<LivroDTO>>builder()
                .message("Lista de livros recuperada com sucesso")
                .data(livros)
                .build();
    }

    @PutMapping("/{id}")
    @Operation(
            summary = "Atualizar livro existente",
            description = "Atualiza um livro existente com base no ID informado. Todos os campos serão atualizados."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Livro atualizado com sucesso",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ApiResponse.class),
                            examples = @ExampleObject(value = """
                    {
                      "message": "Livro atualizado com sucesso",
                      "data": {
                        "codigo": 1,
                        "titulo": "Clean Architecture",
                        "editora": "Alta Books",
                        "edicao": 1,
                        "anoPublicacao": "2020",
                        "autorCodAus": [1],
                        "assuntoCodAss": [1],
                        "autores": [{
                          "codigo": 1,
                          "nome": "Robert C. Martin"
                        }],
                        "assuntos": [{
                          "codigo": 1,
                          "descricao": "Arquitetura de Software"
                        }]
                      }
                    }
                    """)
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Dados de entrada inválidos",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ApiError.class),
                            examples = @ExampleObject(value = """
                    {
                      "type": "https://api.spassu.com.br/errors/validation",
                      "title": "Erro de Validação",
                      "detail": "Campos obrigatórios não informados",
                      "status": 400,
                      "timestamp": "2024-12-15T10:30:00Z"
                    }
                    """)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Livro não encontrado",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ApiError.class),
                            examples = @ExampleObject(value = """
                    {
                      "type": "https://api.spassu.com.br/errors/not-found",
                      "title": "Recurso não encontrado",
                      "detail": "Livro com código 1 não encontrado",
                      "status": 404,
                      "timestamp": "2024-12-15T10:30:00Z"
                    }
                    """)
                    )
            )
    })
    public ResponseWrapper<LivroDTO> atualizar(
            @Parameter(description = "ID do livro a ser atualizado", example = "1", required = true)
            @PathVariable Integer id,

            @Parameter(description = "Dados atualizados do livro", required = true)
            @io.swagger.v3.oas.annotations.parameters.RequestBody(content = @Content(examples = @ExampleObject(value = """
                {
                  "titulo": "Clean Architecture",
                  "editora": "Alta Books",
                  "edicao": 1,
                  "anoPublicacao": "2020",
                  "autorCodAus": [1],
                  "assuntoCodAss": [1]
                }
                """)))
            @Valid @RequestBody LivroDTO livroDTO) throws JsonProcessingException {
        return atualizarLivroUseCase.execute(id, livroDTO);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Deletar livro")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Livro deletado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Livro não encontrado",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiError.class)))
    })
    public void deletar(@PathVariable Integer id) {
        deletarLivroUseCase.execute(id);
    }
}

