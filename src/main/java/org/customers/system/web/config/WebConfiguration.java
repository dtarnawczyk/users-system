package org.customers.system.web.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
//import springfox.documentation.swagger2.annotations.EnableSwagger2;
//import springfox.documentation.spi.DocumentationType;
//import springfox.documentation.spring.web.plugins.Docket;

@Configuration
@EnableConfigurationProperties(PictureProperties.class)
//@EnableSwagger2
public class WebConfiguration extends WebMvcConfigurerAdapter {
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

//    @Bean
//    public Docket customerApi(){
//        return new Docket(DocumentationType.SWAGGER_2).select().paths(path -> {
//            assert path != null;
//            return path.startsWith("/api/");
//        }).build();
//    }
}
