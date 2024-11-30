package com.br.spassu.api.infrastructure.controller;

import com.br.spassu.api.application.dto.AssuntoDTO;
import com.br.spassu.api.application.usecase.assunto.AtualizarAssuntoUseCase;
import com.br.spassu.api.application.usecase.assunto.BuscarAssuntoUseCase;
import com.br.spassu.api.application.usecase.assunto.CriarAssuntoUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/assuntos")
@RequiredArgsConstructor
@Tag(name = "Assuntos", description = "API de gerenciamento de assuntos")
public class AssuntoController {

    private final CriarAssuntoUseCase criarAssuntoUseCase;
    private final BuscarAssuntoUseCase buscarAssuntoUseCase;
    private final AtualizarAssuntoUseCase atualizarAssuntoUseCase;

    @PostMapping
    @Operation(summary = "Criar novo assunto")
    public ResponseEntity<AssuntoDTO> criar(@Valid @RequestBody AssuntoDTO assuntoDTO) {
        return new ResponseEntity<>(criarAssuntoUseCase.execute(assuntoDTO), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar assunto por ID")
    public ResponseEntity<AssuntoDTO> buscarPorId(@PathVariable Integer id) {
        return ResponseEntity.ok(buscarAssuntoUseCase.execute(id));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar assunto existente")
    public ResponseEntity<AssuntoDTO> atualizar(@PathVariable Integer id, @Valid @RequestBody AssuntoDTO assuntoDTO) {
        return ResponseEntity.ok(atualizarAssuntoUseCase.execute(id, assuntoDTO));
    }
}
