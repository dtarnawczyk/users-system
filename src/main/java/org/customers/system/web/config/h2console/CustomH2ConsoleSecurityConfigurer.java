package org.customers.system.web.config.h2console;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.h2.H2ConsoleProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@Profile("dev")
@Order(1)
class CustomH2ConsoleSecurityConfigurer extends WebSecurityConfigurerAdapter {

    @Autowired
    private H2ConsoleProperties console;

    @Override
    public void configure(HttpSecurity http) throws Exception {
        String path = this.console.getPath();
        http.antMatcher(path.endsWith("/") ? path + "**" : path + "/**")
                .csrf().disable().headers()
                .frameOptions().sameOrigin()
                .and()
                .authorizeRequests().anyRequest().permitAll();
    }

}
