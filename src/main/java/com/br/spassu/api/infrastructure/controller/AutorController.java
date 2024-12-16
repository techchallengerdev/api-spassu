package com.br.spassu.api.infrastructure.controller;

import com.br.spassu.api.application.dto.AutorDTO;
import com.br.spassu.api.application.usecase.autor.AtualizarAutorUseCase;
import com.br.spassu.api.application.usecase.autor.BuscarAutorUseCase;
import com.br.spassu.api.application.usecase.autor.CriarAutorUseCase;
import com.br.spassu.api.application.usecase.autor.DeletarAutorUseCase;
import com.br.spassu.api.application.usecase.autor.ListarAutoresUseCase;
import com.br.spassu.api.infrastructure.exception.ApiError;
import com.br.spassu.api.infrastructure.response.ResponseWrapper;
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
import org.springframework.web.bind.annotation.*;

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
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Criar novo autor")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Autor criado com sucesso",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ApiResponse.class))),
            @ApiResponse(responseCode = "400", description = "Dados de entrada inválidos",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ApiError.class))),
            @ApiResponse(responseCode = "422", description = "Erro de validação dos dados",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ApiError.class)))
    })
    public ResponseWrapper<AutorDTO> criar(@Valid @RequestBody AutorDTO autorDTO) {
        return criarAutorUseCase.execute(autorDTO);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar autor por ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Autor encontrado",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ResponseWrapper.class))),
            @ApiResponse(responseCode = "404", description = "Autor não encontrado",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ApiError.class)))
    })
    public ResponseEntity<ResponseWrapper<AutorDTO>> buscarPorId(@PathVariable Integer id) {
        ResponseWrapper<AutorDTO> response = buscarAutorUseCase.execute(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    @Operation(summary = "Listar todos os autores")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de autores recuperada com sucesso",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ResponseWrapper.class))),
            @ApiResponse(responseCode = "500", description = "Erro ao buscar autores",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ApiError.class)))
    })
    public ResponseEntity<ResponseWrapper<List<AutorDTO>>> listarTodos() {
        ResponseWrapper<List<AutorDTO>> response = listarAutoresUseCase.execute();
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar autor existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Autor atualizado com sucesso",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ResponseWrapper.class))),
            @ApiResponse(responseCode = "400", description = "Dados de entrada inválidos",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ApiError.class))),
            @ApiResponse(responseCode = "404", description = "Autor não encontrado",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ApiError.class)))
    })
    public ResponseEntity<ResponseWrapper<AutorDTO>> atualizar(
            @PathVariable Integer id,
            @Valid @RequestBody AutorDTO autorDTO) {
        ResponseWrapper<AutorDTO> response = atualizarAutorUseCase.execute(id, autorDTO);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deletar autor")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Autor deletado com sucesso",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ResponseWrapper.class))),
            @ApiResponse(responseCode = "404", description = "Autor não encontrado",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ApiError.class))),
            @ApiResponse(responseCode = "400", description = "Erro ao deletar o autor",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ApiError.class)))
    })
    public ResponseEntity<ResponseWrapper<Void>> deletar(@PathVariable Integer id) {
        ResponseWrapper<Void> response = deletarAutorUseCase.execute(id);
        return ResponseEntity.ok(response);
    }
}
