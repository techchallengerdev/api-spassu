package com.br.spassu.api.infrastructure.controller;

import com.br.spassu.api.application.dto.LivroDTO;
import com.br.spassu.api.application.usecase.livro.*;
import com.br.spassu.api.infrastructure.exception.ApiError;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.Builder;
import lombok.Data;
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

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Criar novo livro")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Livro criado com sucesso",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiResponse.class))),
            @ApiResponse(responseCode = "400", description = "Dados de entrada inválidos",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiError.class))),
            @ApiResponse(responseCode = "422", description = "Erro de validação dos dados",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiError.class)))
    })
    public ResponseWrapper<LivroDTO> criar(@Valid @RequestBody LivroDTO livroDTO) {
        LivroDTO livroCriado = criarLivroUseCase.execute(livroDTO);
        return ResponseWrapper.<LivroDTO>builder()
                .message("Livro criado com sucesso")
                .data(livroCriado)
                .build();
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
    @Operation(summary = "Atualizar livro existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Livro atualizado com sucesso",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiResponse.class))),
            @ApiResponse(responseCode = "400", description = "Dados de entrada inválidos",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiError.class))),
            @ApiResponse(responseCode = "404", description = "Livro não encontrado",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiError.class)))
    })
    public ResponseWrapper<LivroDTO> atualizar(@PathVariable Integer id, @Valid @RequestBody LivroDTO livroDTO) {
        LivroDTO livroAtualizado = atualizarLivroUseCase.execute(id, livroDTO);
        return ResponseWrapper.<LivroDTO>builder()
                .message("Livro atualizado com sucesso")
                .data(livroAtualizado)
                .build();
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

@Data
@Builder
class ResponseWrapper<T> {
    private String message;
    private T data;
}