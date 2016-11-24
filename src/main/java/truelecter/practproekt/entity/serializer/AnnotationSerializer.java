package truelecter.practproekt.entity.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.xml.sax.SAXException;
import truelecter.practproekt.entity.Discipline;
import truelecter.practproekt.entity.Student;
import truelecter.practproekt.util.DisciplineXMLInfoGetter;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;

public class AnnotationSerializer extends JsonSerializer<String> {

    @Override
    public void serialize(String s, JsonGenerator jgen, SerializerProvider p) throws IOException{
        DisciplineXMLInfoGetter infoGetter = DisciplineXMLInfoGetter.getInstance();
        Discipline d = (Discipline) jgen.getCurrentValue();
        String annotation = infoGetter.getAnnotation(d.getId());
        jgen.writeString(annotation);
    }

}
