package com.br.spassu.api.domain.exceptions;

public class AuthorNotFoundException extends BusinessException {
    public AuthorNotFoundException(Integer authorId) {
        super(String.format("Autor com código %d não encontrado", authorId));
    }
}