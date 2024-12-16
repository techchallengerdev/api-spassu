package com.br.spassu.api.application.usecase.autor;

import com.br.spassu.api.domain.entity.Autor;
import com.br.spassu.api.domain.exceptions.AuthorNotFoundException;
import com.br.spassu.api.domain.exceptions.InvalidAuthorDataException;
import com.br.spassu.api.domain.repository.AutorRepository;
import com.br.spassu.api.infrastructure.response.ResponseWrapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class DeletarAutorUseCase {
    private final AutorRepository autorRepository;

    private static final String SUCESSO_DELETAR_AUTOR = "Autor deletado com sucesso";

    @Transactional
    public ResponseWrapper<Void> execute(Integer codigo) {
        validarCodigo(codigo);
        Autor autor = buscarAutor(codigo);
        deletarAutor(autor);

        return ResponseWrapper.<Void>builder()
                .message(SUCESSO_DELETAR_AUTOR)
                .build();
    }

    private void validarCodigo(Integer codigo) {
        if (codigo == null || codigo <= 0) {
            throw new InvalidAuthorDataException("Código do autor inválido");
        }
    }

    private Autor buscarAutor(Integer codigo) {
        return autorRepository.findByCodigo(codigo)
                .orElseThrow(() -> new AuthorNotFoundException(codigo));
    }

    private void deletarAutor(Autor autor) {
        try {
            autorRepository.delete(autor.getCodigo());
        } catch (Exception e) {
            throw new InvalidAuthorDataException(
                    String.format("Erro ao excluir autor com código %d: %s",
                            autor.getCodigo(), e.getMessage()));
        }
    }
}