package com.br.spassu.api.infrastructure.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.servers.Server;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "API Backend - Desafio Técnico Spassu",
                version = "1.0",
                description = "API para gerenciamento de biblioteca",
                contact = @Contact(
                        name = "Tech Challenger",
                        email = "techchallengerspassu@gmail.com"
                )
        )
)
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .components(new Components())
                .info(new io.swagger.v3.oas.models.info.Info()
                        .title("API Backend - Desafio Técnico Spassu")
                        .version("1.0")
                        .description("API para gerenciamento de biblioteca")
                        .contact(new io.swagger.v3.oas.models.info.Contact()
                                .name("Tech Challenger")
                                .email("techchallengerspassu@gmail.com"))
                        .license(new io.swagger.v3.oas.models.info.License()
                                .name("Apache 2.0")
                                .url("http://springdoc.org")));
    }
}
