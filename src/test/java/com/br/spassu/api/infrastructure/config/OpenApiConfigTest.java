package com.br.spassu.api.infrastructure.config;

import io.swagger.v3.oas.models.OpenAPI;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class OpenApiConfigTest {

    @Autowired
    private OpenAPI openAPI;

    @Test
    void testOpenApiConfig() {
        Assertions.assertNotNull(openAPI);
        Assertions.assertEquals("API Backend - Desafio Técnico Spassu", openAPI.getInfo().getTitle());
        Assertions.assertEquals("1.0", openAPI.getInfo().getVersion());
        Assertions.assertEquals("API para gerenciamento de biblioteca", openAPI.getInfo().getDescription());
        Assertions.assertNotNull(openAPI.getInfo().getContact());
        Assertions.assertEquals("Tech Challenger", openAPI.getInfo().getContact().getName());
        Assertions.assertEquals("techchallengerspassu@gmail.com", openAPI.getInfo().getContact().getEmail());
        Assertions.assertNotNull(openAPI.getInfo().getLicense());
        Assertions.assertEquals("Apache 2.0", openAPI.getInfo().getLicense().getName());
        Assertions.assertEquals("http://springdoc.org", openAPI.getInfo().getLicense().getUrl());
    }
}
