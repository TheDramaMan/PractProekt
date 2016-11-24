package truelecter.practproekt.entity.serializer;

import java.io.IOException;
import java.util.Set;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import truelecter.practproekt.entity.Student;

public class NestedStudentSetSerializer extends JsonSerializer<Set<Student>> {

    @Override
    public void serialize(Set<Student> value, JsonGenerator jgen, SerializerProvider p) throws IOException, JsonProcessingException {
        jgen.writeStartArray();
        for (Student s : value) {
            jgen.writeStartObject();
            jgen.writeStringField("name", s.getName());
            jgen.writeStringField("id", s.getId());
            jgen.writeEndObject();
        }
        jgen.writeEndArray();
    }

}
