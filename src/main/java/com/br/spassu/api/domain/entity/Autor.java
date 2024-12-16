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
public class Autor {
    private Integer codigo;
    private String nome;
    @Builder.Default
    private List<Livro> livros = new ArrayList<>();

    public void validar() {
        validarNome();
        validarLivros();
    }

    private void validarNome() {
        if (nome == null || nome.trim().isEmpty()) {
            throw new BusinessException("Nome do autor é obrigatório");
        }
        if (nome.length() > 40) {
            throw new BusinessException("Nome do autor deve ter no máximo 40 caracteres");
        }
    }

    private void validarLivros() {
        if (livros == null) {
            livros = new ArrayList<>();
        }

        if (livros.size() > 20) {
            throw new BusinessException("Autor não pode ter mais que 20 livros");
        }
    }

    public void adicionarLivro(Livro livro) {
        if (livro == null) {
            throw new BusinessException("Livro não pode ser nulo");
        }
        if (livros.contains(livro)) {
            throw new BusinessException("Livro já está vinculado ao autor");
        }

        this.livros.add(livro);
        if (!livro.getAutores().contains(this)) {
            livro.getAutores().add(this);
        }
    }

    public void removerLivro(Livro livro) {
        if (livro == null) {
            throw new BusinessException("Livro não pode ser nulo");
        }
        if (!livros.contains(livro)) {
            throw new BusinessException("Livro não está vinculado ao autor");
        }

        this.livros.remove(livro);
        livro.getAutores().remove(this);
    }

    public void limparLivros() {
        livros.forEach(livro -> livro.getAutores().remove(this));
        livros.clear();
    }
}