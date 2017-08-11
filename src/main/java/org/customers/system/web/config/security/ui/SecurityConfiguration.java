package org.customers.system.web.config.security.ui;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

@Configuration
@EnableGlobalMethodSecurity(securedEnabled=true)
@Order(3)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    private AuthenticationProvider authenticationProvider;
    private AuthenticationSuccessHandler authenticationSuccessHandler;

    public SecurityConfiguration(AuthenticationProvider authenticationProvider,
                                 AuthenticationSuccessHandler authenticationSuccessHandler){
        super();
        this.authenticationProvider = authenticationProvider;
        this.authenticationSuccessHandler = authenticationSuccessHandler;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/login", "/createForm", "/createCustomer").permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage("/login")
                .successHandler(authenticationSuccessHandler)
                .usernameParameter("login")
                .permitAll()
                .and()
                .logout()
                .invalidateHttpSession(true)
                .deleteCookies("JSESSIONID")
                .and()
                .sessionManagement()
                .maximumSessions(1).sessionRegistry(sessionRegistry())
                .and()
                .sessionFixation().none();
    }

    @Override
    public void configure(final AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(authenticationProvider);
//        auth.inMemoryAuthentication()
//                .withUser("user").password("password").roles("USER")
//                .and()
//                .withUser("admin").password("password").roles("ADMIN");
    }

    @Bean
    public SessionRegistry sessionRegistry() {
        return new SessionRegistryImpl();
    }
}
