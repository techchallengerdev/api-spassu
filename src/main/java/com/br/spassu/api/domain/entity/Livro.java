package com.br.spassu.api.domain.entity;

import com.br.spassu.api.domain.exceptions.BusinessException;
import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Livro {
    private Integer codigo;
    private String titulo;
    private String editora;
    private Integer edicao;
    private String anoPublicacao;
    @Builder.Default
    private List<Autor> autores = new ArrayList<>();
    @Builder.Default
    private List<Assunto> assuntos = new ArrayList<>();

    public void validar() {
        validarTitulo();
        validarEditora();
        validarEdicao();
        validarAnoPublicacao();
        validarAutores();
        validarAssuntos();
    }

    private void validarTitulo() {
        if (titulo == null || titulo.trim().isEmpty()) {
            throw new BusinessException("Título é obrigatório");
        }
        if (titulo.length() > 40) {
            throw new BusinessException("Título deve ter no máximo 40 caracteres");
        }
    }

    private void validarEditora() {
        if (editora != null && editora.length() > 40) {
            throw new BusinessException("Editora deve ter no máximo 40 caracteres");
        }
    }

    private void validarEdicao() {
        if (edicao != null && edicao <= 0) {
            throw new BusinessException("Edição deve ser um número positivo");
        }
    }

    private void validarAnoPublicacao() {
        if (anoPublicacao != null) {
            if (!anoPublicacao.matches("\\d{4}")) {
                throw new BusinessException("Ano de publicação deve conter 4 dígitos");
            }

            int ano = Integer.parseInt(anoPublicacao);
            int anoAtual = LocalDate.now().getYear();

            if (ano > anoAtual) {
                throw new BusinessException("Ano de publicação não pode ser maior que o ano atual");
            }

            if (ano < 1000) {
                throw new BusinessException("Ano de publicação inválido");
            }
        }
    }

    private void validarAutores() {
        if (autores.isEmpty()) {
            throw new BusinessException("Livro deve ter pelo menos um autor");
        }

        if (autores.size() > 10) {
            throw new BusinessException("Livro não pode ter mais que 10 autores");
        }

        if (autores.stream().anyMatch(autor -> autor.getNome() == null || autor.getNome().trim().isEmpty())) {
            throw new BusinessException("Todos os autores devem ter nome");
        }
    }

    private void validarAssuntos() {
        if (assuntos.isEmpty()) {
            throw new BusinessException("Livro deve ter pelo menos um assunto");
        }

        if (assuntos.size() > 5) {
            throw new BusinessException("Livro não pode ter mais que 5 assuntos");
        }

        if (assuntos.stream().anyMatch(assunto -> assunto.getDescricao() == null || assunto.getDescricao().trim().isEmpty())) {
            throw new BusinessException("Todos os assuntos devem ter descrição");
        }
    }

    public void adicionarAutor(Autor autor) {
        if (autor == null) {
            throw new BusinessException("Autor não pode ser nulo");
        }
        if (autores.contains(autor)) {
            throw new BusinessException("Autor já está vinculado ao livro");
        }
        this.autores.add(autor);
        autor.getLivros().add(this);
    }

    public void removerAutor(Autor autor) {
        if (autor == null) {
            throw new BusinessException("Autor não pode ser nulo");
        }
        if (!autores.contains(autor)) {
            throw new BusinessException("Autor não está vinculado ao livro");
        }
        if (autores.size() == 1) {
            throw new BusinessException("Não é possível remover o único autor do livro");
        }
        this.autores.remove(autor);
        autor.getLivros().remove(this);
    }

    public void adicionarAssunto(Assunto assunto) {
        if (assunto == null) {
            throw new BusinessException("Assunto não pode ser nulo");
        }
        if (assuntos.contains(assunto)) {
            throw new BusinessException("Assunto já está vinculado ao livro");
        }
        this.assuntos.add(assunto);
        assunto.getLivros().add(this);
    }

    public void removerAssunto(Assunto assunto) {
        if (assunto == null) {
            throw new BusinessException("Assunto não pode ser nulo");
        }
        if (!assuntos.contains(assunto)) {
            throw new BusinessException("Assunto não está vinculado ao livro");
        }
        if (assuntos.size() == 1) {
            throw new BusinessException("Não é possível remover o único assunto do livro");
        }
        this.assuntos.remove(assunto);
        assunto.getLivros().remove(this);
    }
}