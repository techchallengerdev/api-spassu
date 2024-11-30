package com.br.spassu.api.infrastructure.controller;

import com.br.spassu.api.application.dto.AutorDTO;
import com.br.spassu.api.application.usecase.autor.AtualizarAutorUseCase;
import com.br.spassu.api.application.usecase.autor.BuscarAutorUseCase;
import com.br.spassu.api.application.usecase.autor.CriarAutorUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/autores")
@RequiredArgsConstructor
@Tag(name = "Autores", description = "API de gerenciamento de autores")
public class AutorController {

    private final CriarAutorUseCase criarAutorUseCase;
    private final BuscarAutorUseCase buscarAutorUseCase;
    private final AtualizarAutorUseCase atualizarAutorUseCase;

    @PostMapping
    @Operation(summary = "Criar novo autor")
    public ResponseEntity<AutorDTO> criar(@Valid @RequestBody AutorDTO autorDTO) {
        return new ResponseEntity<>(criarAutorUseCase.execute(autorDTO), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar autor por ID")
    public ResponseEntity<AutorDTO> buscarPorId(@PathVariable Integer id) {
        return ResponseEntity.ok(buscarAutorUseCase.execute(id));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar autor existente")
    public ResponseEntity<AutorDTO> atualizar(@PathVariable Integer id, @Valid @RequestBody AutorDTO autorDTO) {
        return ResponseEntity.ok(atualizarAutorUseCase.execute(id, autorDTO));
    }
}
