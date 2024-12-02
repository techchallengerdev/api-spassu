package com.br.spassu.api.infrastructure.controller;

import com.br.spassu.api.application.dto.LivroDTO;
import com.br.spassu.api.application.usecase.livro.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
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
    @Operation(summary = "Criar novo livro")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Livro criado com sucesso",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = LivroDTO.class))),
            @ApiResponse(responseCode = "400", description = "Dados de entrada inválidos",
                    content = @Content(mediaType = "application/json"))
    })
    public ResponseEntity<LivroDTO> criar(@Valid @RequestBody LivroDTO livroDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(criarLivroUseCase.execute(livroDTO));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar livro por ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Livro encontrado",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = LivroDTO.class))),
            @ApiResponse(responseCode = "404", description = "Livro não encontrado",
                    content = @Content(mediaType = "application/json"))
    })
    public ResponseEntity<LivroDTO> buscarPorId(@PathVariable Integer id) {
        return ResponseEntity.ok(buscarLivroUseCase.execute(id));
    }

    @GetMapping
    @Operation(summary = "Listar todos os livros")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de livros",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = LivroDTO.class))),
            @ApiResponse(responseCode = "500", description = "Erro ao buscar livros",
                    content = @Content(mediaType = "application/json"))
    })
    public ResponseEntity<List<LivroDTO>> listarTodos() {
        return ResponseEntity.ok(listarLivrosUseCase.execute());
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar livro existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Livro atualizado com sucesso",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = LivroDTO.class))),
            @ApiResponse(responseCode = "400", description = "Dados de entrada inválidos",
                    content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "404", description = "Livro não encontrado",
                    content = @Content(mediaType = "application/json"))
    })
    public ResponseEntity<LivroDTO> atualizar(@PathVariable Integer id, @Valid @RequestBody LivroDTO livroDTO) {
        return ResponseEntity.ok(atualizarLivroUseCase.execute(id, livroDTO));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deletar livro")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Livro deletado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Erro ao deletar o livro",
                    content = @Content(mediaType = "application/json"))
    })
    public ResponseEntity<Void> deletar(@PathVariable Integer id) {
        try {
            deletarLivroUseCase.execute(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
}