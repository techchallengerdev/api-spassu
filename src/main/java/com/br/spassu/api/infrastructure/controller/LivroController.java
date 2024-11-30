package com.br.spassu.api.infrastructure.controller;

import com.br.spassu.api.application.dto.LivroDTO;
import com.br.spassu.api.application.usecase.livro.*;
import io.swagger.v3.oas.annotations.Operation;
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
    public ResponseEntity<LivroDTO> criar(@Valid @RequestBody LivroDTO livroDTO) {
        return new ResponseEntity<>(criarLivroUseCase.execute(livroDTO), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar livro por ID")
    public ResponseEntity<LivroDTO> buscarPorId(@PathVariable Integer id) {
        return ResponseEntity.ok(buscarLivroUseCase.execute(id));
    }

    @GetMapping
    @Operation(summary = "Listar todos os livros")
    public ResponseEntity<List<LivroDTO>> listarTodos() {
        return ResponseEntity.ok(listarLivrosUseCase.execute());
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar livro existente")
    public ResponseEntity<LivroDTO> atualizar(@PathVariable Integer id, @Valid @RequestBody LivroDTO livroDTO) {
        return ResponseEntity.ok(atualizarLivroUseCase.execute(id, livroDTO));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deletar livro")
    public ResponseEntity<Void> deletar(@PathVariable Integer id) {
        deletarLivroUseCase.execute(id);
        return ResponseEntity.noContent().build();
    }
}
