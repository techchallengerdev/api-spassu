package com.br.spassu.api.infrastructure.controller;

import com.br.spassu.api.application.dto.AssuntoDTO;
import com.br.spassu.api.application.usecase.assunto.AtualizarAssuntoUseCase;
import com.br.spassu.api.application.usecase.assunto.BuscarAssuntoUseCase;
import com.br.spassu.api.application.usecase.assunto.CriarAssuntoUseCase;
import com.br.spassu.api.application.usecase.assunto.DeletarAssuntoUseCase;
import com.br.spassu.api.application.usecase.assunto.ListarAssuntosUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/assuntos")
@RequiredArgsConstructor
@Tag(name = "Assuntos", description = "API de gerenciamento de assuntos")
public class AssuntoController {

    private final CriarAssuntoUseCase criarAssuntoUseCase;
    private final BuscarAssuntoUseCase buscarAssuntoUseCase;
    private final ListarAssuntosUseCase listarAssuntosUseCase;
    private final AtualizarAssuntoUseCase atualizarAssuntoUseCase;
    private final DeletarAssuntoUseCase deletarAssuntoUseCase;

    @PostMapping
    @Operation(summary = "Criar novo assunto")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Assunto criado com sucesso",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = AssuntoDTO.class))),
            @ApiResponse(responseCode = "400", description = "Dados de entrada inválidos",
                    content = @Content(mediaType = "application/json"))
    })
    public ResponseEntity<AssuntoDTO> criar(@Valid @RequestBody AssuntoDTO assuntoDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(criarAssuntoUseCase.execute(assuntoDTO));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar assunto por ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Assunto encontrado",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = AssuntoDTO.class))),
            @ApiResponse(responseCode = "404", description = "Assunto não encontrado",
                    content = @Content(mediaType = "application/json"))
    })
    public ResponseEntity<AssuntoDTO> buscarPorId(@PathVariable Integer id) {
        return ResponseEntity.ok(buscarAssuntoUseCase.execute(id));
    }

    @GetMapping
    @Operation(summary = "Listar todos os assuntos")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de assuntos",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = AssuntoDTO.class))),
            @ApiResponse(responseCode = "500", description = "Erro ao buscar assuntos",
                    content = @Content(mediaType = "application/json"))
    })
    public ResponseEntity<List<AssuntoDTO>> listarTodos() {
        return ResponseEntity.ok(listarAssuntosUseCase.execute());
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar assunto existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Assunto atualizado com sucesso",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = AssuntoDTO.class))),
            @ApiResponse(responseCode = "400", description = "Dados de entrada inválidos",
                    content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "404", description = "Assunto não encontrado",
                    content = @Content(mediaType = "application/json"))
    })
    public ResponseEntity<AssuntoDTO> atualizar(@PathVariable Integer id, @Valid @RequestBody AssuntoDTO assuntoDTO) {
        return ResponseEntity.ok(atualizarAssuntoUseCase.execute(id, assuntoDTO));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deletar assunto")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Assunto deletado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Erro ao deletar o assunto",
                    content = @Content(mediaType = "application/json"))
    })
    public ResponseEntity<Void> deletar(@PathVariable Integer id) {
        try {
            deletarAssuntoUseCase.execute(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
}
