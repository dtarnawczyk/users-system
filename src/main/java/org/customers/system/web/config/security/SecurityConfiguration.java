package org.customers.system.web.config.security;

//@Configuration
////@EnableGlobalMethodSecurity(securedEnabled=true)
//@Order(4)
//public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
//
//    private AuthenticationProvider authenticationProvider;
//    private AuthenticationSuccessHandler authenticationSuccessHandler;
//
//    public SecurityConfiguration(AuthenticationProvider authenticationProvider,
//                                 AuthenticationSuccessHandler authenticationSuccessHandler){
//        super();
//        this.authenticationProvider = authenticationProvider;
//        this.authenticationSuccessHandler = authenticationSuccessHandler;
//    }
//
//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        http.authorizeRequests()
//                .antMatchers("/login", "/createForm", "/createCustomer").permitAll()
//                .anyRequest().authenticated()
//                .and()
//                .formLogin()
//                .loginPage("/login")
//                .successHandler(authenticationSuccessHandler)
//                .usernameParameter("login")
//                .permitAll()
//                .and()
//                .logout()
//                .invalidateHttpSession(true)
//                .deleteCookies("JSESSIONID")
//                .and()
//                .sessionManagement()
//                .maximumSessions(1).sessionRegistry(sessionRegistry())
//                .and()
//                .sessionFixation().none();
//    }
//
//    @Override
//    public void configure(final AuthenticationManagerBuilder auth) throws Exception {
//        auth.authenticationProvider(authenticationProvider);
//    }
//
//    @Bean
//    public SessionRegistry sessionRegistry() {
//        return new SessionRegistryImpl();
//    }
//}
