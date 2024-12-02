package com.br.spassu.api.application.mapper;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MapperConfig {

    @Bean
    public LivroMapper livroMapper(AutorMapper autorMapper, AssuntoMapper assuntoMapper) {
        return new LivroMapper(autorMapper, assuntoMapper);
    }

    @Bean
    public AutorMapper autorMapper() {
        return new AutorMapper();
    }

    @Bean
    public AssuntoMapper assuntoMapper() {
        return new AssuntoMapper();
    }
}
