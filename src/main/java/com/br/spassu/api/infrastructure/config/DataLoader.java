package com.br.spassu.api.infrastructure.config;

import com.br.spassu.api.domain.entity.Assunto;
import com.br.spassu.api.domain.entity.Autor;
import com.br.spassu.api.domain.entity.Livro;
import com.br.spassu.api.domain.repository.AssuntoRepository;
import com.br.spassu.api.domain.repository.AutorRepository;
import com.br.spassu.api.domain.repository.LivroRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.util.Arrays;
import java.util.List;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class DataLoader {

    private final LivroRepository livroRepository;
    private final AutorRepository autorRepository;
    private final AssuntoRepository assuntoRepository;

    @Bean
    @Profile("!prod")
    public CommandLineRunner loadData() {
        return args -> {
            log.info("Iniciando carga de dados...");

            // Criando Autores
            Autor robertMartin = Autor.builder()
                    .codigo(1)
                    .nome("Robert C. Martin")
                    .build();

            Autor martinFowler = Autor.builder()
                    .codigo(2)
                    .nome("Martin Fowler")
                    .build();

            Autor ericEvans = Autor.builder()
                    .codigo(3)
                    .nome("Eric Evans")
                    .build();

            List<Autor> autores = Arrays.asList(robertMartin, martinFowler, ericEvans);
            autores.forEach(autor -> {
                log.info("Salvando autor: {}", autor.getNome());
                autorRepository.save(autor);
            });

            // Criando Assuntos
            Assunto cleanCode = Assunto.builder()
                    .codigo(1)
                    .descricao("Clean Code")
                    .build();

            Assunto kubernetes = Assunto.builder()
                    .codigo(2)
                    .descricao("Kubernetes")
                    .build();

            Assunto devops = Assunto.builder()
                    .codigo(3)
                    .descricao("Devops nativo")
                    .build();

            List<Assunto> assuntos = Arrays.asList(cleanCode, kubernetes, devops);
            assuntos.forEach(assunto -> {
                log.info("Salvando assunto: {}", assunto.getDescricao());
                assuntoRepository.save(assunto);
            });

            // Criando Livros
            Livro cleanCodeBook = Livro.builder()
                    .codigo(1)
                    .titulo("Clean Code")
                    .editora("Prentice Hall")
                    .edicao(1)
                    .anoPublicacao("2008")
                    .build();
            cleanCodeBook.adicionarAutor(robertMartin);
            cleanCodeBook.adicionarAssunto(cleanCode);

            Livro refactoring = Livro.builder()
                    .codigo(2)
                    .titulo("Refactoring")
                    .editora("Addison-Wesley")
                    .edicao(2)
                    .anoPublicacao("2018")
                    .build();
            refactoring.adicionarAutor(martinFowler);
            refactoring.adicionarAssunto(cleanCode);
            refactoring.adicionarAssunto(kubernetes);

            Livro dddBook = Livro.builder()
                    .codigo(3)
                    .titulo("Domain-Driven")
                    .editora("Addison-Wesley")
                    .edicao(1)
                    .anoPublicacao("2003")
                    .build();
            dddBook.adicionarAutor(ericEvans);
            dddBook.adicionarAssunto(devops);
            dddBook.adicionarAssunto(kubernetes);

            List<Livro> livros = Arrays.asList(cleanCodeBook, refactoring, dddBook);
            livros.forEach(livro -> {
                log.info("Salvando livro: {}", livro.getTitulo());
                livroRepository.save(livro);
            });

            log.info("Carga de dados concluída com sucesso!");
        };
    }
}