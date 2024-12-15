package com.br.spassu.api.domain.exceptions;

public class SubjectNotFoundException extends BusinessException {
    public SubjectNotFoundException(Integer subjectId) {
        super(String.format("Assunto com código %d não encontrado", subjectId));
    }
}
