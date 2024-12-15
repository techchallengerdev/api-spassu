package com.br.spassu.api.domain.exceptions;

public class BookNotFoundException extends BusinessException {
    public BookNotFoundException(Integer codigo) {
        super(String.format("Livro com código %d não encontrado", codigo));
    }
}
