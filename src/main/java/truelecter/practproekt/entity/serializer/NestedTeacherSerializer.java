package truelecter.practproekt.entity.serializer;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import truelecter.practproekt.entity.Teacher;

public class NestedTeacherSerializer extends JsonSerializer<Teacher> {
    
    @Override
    public void serialize(Teacher value, JsonGenerator jgen, SerializerProvider p) throws IOException, JsonProcessingException {
        jgen.writeStartObject();
        jgen.writeStringField("name", value.getName());
        jgen.writeEndObject();
    }

}
