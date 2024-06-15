package com.jcode.ebookpedia.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaRepositories(basePackages = "com.jcode.ebookpedia")
@Configuration
public class PersistenceConfig {

}
