package org.customers.system.web.config.security;

//@Configuration
////@EnableGlobalMethodSecurity(securedEnabled=true)
//@Order(2)
//public class ApiSecurityConfiguration extends WebSecurityConfigurerAdapter {
//
//    @Override
//    public void configure(final AuthenticationManagerBuilder auth) throws Exception {
//        auth.inMemoryAuthentication()
//                .withUser("user").password("password").roles("USER")
//                .and()
//                .withUser("admin").password("password").roles("ADMIN");
//    }
//
//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        http.antMatcher("/api/**")
//                .httpBasic()
//                .and()
//                .csrf().disable()
//                .authorizeRequests()
//                .antMatchers((HttpMethod.GET)).hasAnyRole("USER")
//                .antMatchers((HttpMethod.POST)).hasAnyRole("ADMIN")
//                .antMatchers((HttpMethod.PUT)).hasAnyRole("ADMIN")
//                .antMatchers((HttpMethod.DELETE)).hasAnyRole("ADMIN")
//                .anyRequest().authenticated();
//    }
//}
