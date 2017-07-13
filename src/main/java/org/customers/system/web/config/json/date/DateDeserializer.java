package org.customers.system.web.config.json.date;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import org.customers.system.web.config.RestConfiguration;
import org.springframework.boot.jackson.JsonComponent;

import java.io.IOException;
import java.time.LocalDate;

@JsonComponent
public class DateDeserializer extends JsonDeserializer<LocalDate> {

    @Override
    public LocalDate deserialize(JsonParser parser, DeserializationContext context)
            throws IOException {
        return LocalDate.parse(parser.getValueAsString(), RestConfiguration.DATE_FORMATTER);
    }

}
