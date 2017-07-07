package org.customers.system.web.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
@EnableConfigurationProperties(PictureProperties.class)
public class WebConfiguration extends WebMvcConfigurerAdapter {
}
