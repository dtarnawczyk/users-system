package org.customers.system.data.config;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableAutoConfiguration
@EntityScan(basePackages = "org.customers.system.domain")
@EnableJpaRepositories(basePackages = {"org.customers.system.domain"})
@EnableTransactionManagement
public class RepositoryConfig {
}
