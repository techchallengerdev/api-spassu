package com.br.spassu.api.infrastructure.controller;

import com.br.spassu.api.application.dto.AutorDTO;
import com.br.spassu.api.application.usecase.autor.AtualizarAutorUseCase;
import com.br.spassu.api.application.usecase.autor.BuscarAutorUseCase;
import com.br.spassu.api.application.usecase.autor.CriarAutorUseCase;
import com.br.spassu.api.application.usecase.autor.DeletarAutorUseCase;
import com.br.spassu.api.application.usecase.autor.ListarAutoresUseCase;
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
@RequestMapping("/api/v1/autores")
@RequiredArgsConstructor
@Tag(name = "Autores", description = "API de gerenciamento de autores")
public class AutorController {

    private final CriarAutorUseCase criarAutorUseCase;
    private final BuscarAutorUseCase buscarAutorUseCase;
    private final ListarAutoresUseCase listarAutoresUseCase;
    private final AtualizarAutorUseCase atualizarAutorUseCase;
    private final DeletarAutorUseCase deletarAutorUseCase;

    @PostMapping
    @Operation(summary = "Criar novo autor")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Autor criado com sucesso",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = AutorDTO.class))),
            @ApiResponse(responseCode = "400", description = "Dados de entrada inválidos",
                    content = @Content(mediaType = "application/json"))
    })
    public ResponseEntity<AutorDTO> criar(@Valid @RequestBody AutorDTO autorDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(criarAutorUseCase.execute(autorDTO));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar autor por ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Autor encontrado",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = AutorDTO.class))),
            @ApiResponse(responseCode = "404", description = "Autor não encontrado",
                    content = @Content(mediaType = "application/json"))
    })
    public ResponseEntity<AutorDTO> buscarPorId(@PathVariable Integer id) {
        return ResponseEntity.ok(buscarAutorUseCase.execute(id));
    }

    @GetMapping
    @Operation(summary = "Listar todos os autores")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de autores",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = AutorDTO.class))),
            @ApiResponse(responseCode = "500", description = "Erro ao buscar autores",
                    content = @Content(mediaType = "application/json"))
    })
    public ResponseEntity<List<AutorDTO>> listarTodos() {
        return ResponseEntity.ok(listarAutoresUseCase.execute());
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar autor existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Autor atualizado com sucesso",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = AutorDTO.class))),
            @ApiResponse(responseCode = "400", description = "Dados de entrada inválidos",
                    content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "404", description = "Autor não encontrado",
                    content = @Content(mediaType = "application/json"))
    })
    public ResponseEntity<AutorDTO> atualizar(@PathVariable Integer id, @Valid @RequestBody AutorDTO autorDTO) {
        return ResponseEntity.ok(atualizarAutorUseCase.execute(id, autorDTO));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deletar autor")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Autor deletado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Erro ao deletar o autor",
                    content = @Content(mediaType = "application/json"))
    })
    public ResponseEntity<Void> deletar(@PathVariable Integer id) {
        try {
            deletarAutorUseCase.execute(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
}
