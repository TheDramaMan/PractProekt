package truelecter.practproekt.entity.serializer;

import java.io.IOException;
import java.util.Set;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import truelecter.practproekt.entity.Discipline;

public class NestedDisciplineSetSerializer extends JsonSerializer<Set<Discipline>> {

    @Override
    public void serialize(Set<Discipline> value, JsonGenerator jgen, SerializerProvider p) throws IOException, JsonProcessingException {
        jgen.writeStartArray();
        for (Discipline s : value) {
            jgen.writeStartObject();
            jgen.writeStringField("name", s.getName());
            jgen.writeNumberField("id", s.getId());
            jgen.writeNumberField("credits", s.getCredits());
            jgen.writeBooleanField("recommended", s.isRecommended());
            jgen.writeEndObject();
        }
        jgen.writeEndArray();
    }

}
