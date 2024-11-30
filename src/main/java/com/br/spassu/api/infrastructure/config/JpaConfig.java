package com.br.spassu.api.infrastructure.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories(basePackages = "com.br.spassu.api.infrastructure.persistence.repository")
@ComponentScan(basePackages = "com.br.spassu.api.infrastructure.persistence.repository")
public class JpaConfig {
}
