package com.br.spassu.api.application.usecase.livro;

import com.br.spassu.api.domain.entity.Livro;
import com.br.spassu.api.domain.exceptions.BusinessException;
import com.br.spassu.api.domain.exceptions.EntityNotFoundException;
import com.br.spassu.api.domain.repository.LivroRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class DeletarLivroUseCase {
    private final LivroRepository livroRepository;

    @Transactional
    public void execute(Integer codigo) {
        validarDadosEntrada(codigo);

        Livro livro = buscarLivro(codigo);
        deletarLivro(livro);
    }

    private void validarDadosEntrada(Integer codigo) {
        if (codigo == null) {
            throw new BusinessException("Código do livro não informado");
        }
        if (codigo <= 0) {
            throw new BusinessException("Código do livro deve ser maior que zero");
        }
    }

    private Livro buscarLivro(Integer codigo) {
        return livroRepository.findByCodigo(codigo)
                .orElseThrow(() -> new EntityNotFoundException(
                        String.format("Livro com código %d não encontrado", codigo)));
    }

    private void deletarLivro(Livro livro) {
        try {
            livroRepository.delete(livro.getCodigo());
        } catch (Exception e) {
            throw new BusinessException(
                    String.format("Erro ao excluir livro com código %d: %s",
                            livro.getCodigo(), e.getMessage()));
        }
    }
}