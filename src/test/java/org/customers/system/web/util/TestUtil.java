package org.customers.system.web.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.customers.system.web.config.json.date.DateDeserializer;
import org.customers.system.web.config.json.date.DateSerializer;

import java.io.IOException;
import java.time.LocalDate;

public class TestUtil {

    public static byte[] toJson(final Object object) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        JavaTimeModule javaTimeModule = new JavaTimeModule();
        javaTimeModule.addSerializer(LocalDate.class, new DateSerializer());
        javaTimeModule.addDeserializer(LocalDate.class, new DateDeserializer());
        mapper.registerModule(javaTimeModule);
        return mapper.writeValueAsBytes(object);
    }

}
