package com.br.spassu.api.domain.entity;

import com.br.spassu.api.domain.exceptions.BusinessException;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Assunto {
    private Integer codigo;
    private String descricao;
    @Builder.Default
    private List<Livro> livros = new ArrayList<>();

    public void validar() {
        validarDescricao();
        validarLivros();
    }

    private void validarDescricao() {
        if (descricao == null || descricao.trim().isEmpty()) {
            throw new BusinessException("Descrição do assunto é obrigatória");
        }
        if (descricao.length() > 40) {
            throw new BusinessException("Descrição do assunto deve ter no máximo 40 caracteres");
        }
    }

    private void validarLivros() {
        if (livros == null) {
            livros = new ArrayList<>();
        }

        if (livros.size() > 50) {
            throw new BusinessException("Assunto não pode ter mais que 50 livros");
        }
    }

    public void adicionarLivro(Livro livro) {
        if (livro == null) {
            throw new BusinessException("Livro não pode ser nulo");
        }
        if (livros.contains(livro)) {
            throw new BusinessException("Livro já está vinculado ao assunto");
        }

        this.livros.add(livro);
        if (!livro.getAssuntos().contains(this)) {
            livro.getAssuntos().add(this);
        }
    }

    public void removerLivro(Livro livro) {
        if (livro == null) {
            throw new BusinessException("Livro não pode ser nulo");
        }
        if (!livros.contains(livro)) {
            throw new BusinessException("Livro não está vinculado ao assunto");
        }

        this.livros.remove(livro);
        livro.getAssuntos().remove(this);
    }

    public void limparLivros() {
        livros.forEach(livro -> livro.getAssuntos().remove(this));
        livros.clear();
    }
}