package org.customers.system.web.config.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.error.OAuth2AccessDeniedHandler;

@Configuration
@EnableResourceServer
@Order(2)
public class ResourceServer extends ResourceServerConfigurerAdapter {

    public static final String RESOURCE_ID = "customers-service";

    @Override
    public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
        resources.resourceId(RESOURCE_ID);
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.
                anonymous().disable()
                .requestMatchers()
                .antMatchers("/api/customer")
                .and().authorizeRequests()
//                .antMatchers("/api/**").access("hasRole('USER')")
                .antMatchers((HttpMethod.GET)).hasAnyRole("USER")
                .antMatchers((HttpMethod.POST)).hasAnyRole("ADMIN")
                .antMatchers((HttpMethod.PUT)).hasAnyRole("ADMIN")
                .antMatchers((HttpMethod.DELETE)).hasAnyRole("ADMIN")
                .and().exceptionHandling().accessDeniedHandler(new OAuth2AccessDeniedHandler());
    }
}
