package org.customers.system.web.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.customers.system.web.config.json.date.DateDeserializer;
import org.customers.system.web.config.json.date.DateSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static java.time.format.DateTimeFormatter.ofPattern;

@Configuration
public class ObjectMapperConfiguration {

    public static final DateTimeFormatter DATE_FORMATTER = ofPattern("dd:MM:yyyy");

    @Bean
    @Primary
    public ObjectMapper serializingObjectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
        JavaTimeModule javaTimeModule = new JavaTimeModule();
        javaTimeModule.addSerializer(LocalDate.class, new DateSerializer());
        javaTimeModule.addDeserializer(LocalDate.class, new DateDeserializer());
        objectMapper.registerModule(javaTimeModule);
        return objectMapper;
    }

}
