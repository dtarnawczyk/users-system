package org.customers.system.web.config.json.date;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.customers.system.web.config.RestConfiguration;
import org.springframework.boot.jackson.JsonComponent;

import java.io.IOException;
import java.time.LocalDate;

@JsonComponent
public class DateSerializer extends JsonSerializer<LocalDate> {

    @Override
    public void serialize(LocalDate localDate, JsonGenerator generator, SerializerProvider provider)
            throws IOException {
        generator.writeString(localDate.format(RestConfiguration.DATE_FORMATTER));
    }
}
