package truelecter.practproekt.entity.serializer;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import truelecter.practproekt.entity.Discipline;
import truelecter.practproekt.entity.Student;

public class StudentSerializer extends JsonSerializer<Student> {

    @Override
    public void serialize(Student s, JsonGenerator jgen, SerializerProvider p) throws IOException, JsonProcessingException {
        jgen.writeStartObject();
        jgen.writeStringField("name", s.getName());
        jgen.writeStringField("id", s.getId());
        jgen.writeArrayFieldStart("disciplines");
        int totalCredits = 0;
        for (Discipline d : s.getDisciplines()) {
            jgen.writeStartObject();
            jgen.writeStringField("name", d.getName());
            jgen.writeNumberField("id", d.getId());
            totalCredits += d.getCredits();
            jgen.writeNumberField("credits", d.getCredits());
            jgen.writeBooleanField("recommended", d.isRecommended());
            jgen.writeEndObject();
        }
        jgen.writeEndArray();
        jgen.writeNumberField("totalCredits", totalCredits);
        jgen.writeEndObject();
    }

}
