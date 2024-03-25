package com.jcode.ebookpedia.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories(basePackages = "com.jcode.ebookpedia")
public class PersistenceConfig {

}
