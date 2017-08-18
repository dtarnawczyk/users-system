package org.customers.system.repository.config;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableAutoConfiguration
@EntityScan(basePackages = "org.customers.system")
@EnableJpaRepositories(basePackages = {"org.customers.system.domain"})
@EnableTransactionManagement
@EnableJpaAuditing(auditorAwareRef = "auditorAware")
public class RepositoryConfig {

    @Bean
    public AuditorAware<String> auditorAware(){
        return new CustomAuditorAware();
    }
}
