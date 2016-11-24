package truelecter.practproekt.util;

import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by TrueLecter on 21.11.2016.
 */
public class DisciplineXMLInfoGetter {
    private static DisciplineXMLInfoGetter instance = null;
    private Logger logger = Logger.getLogger(getClass());

    private Map<Integer, String> annotations = new HashMap<>();

    public static DisciplineXMLInfoGetter getInstance(){
        if (instance == null){
            instance = new DisciplineXMLInfoGetter();
        }
        return instance;
    }

    public String getAnnotation(int id){
        if (annotations.containsKey(id)){
            return annotations.get(id);
        }
        String anno = "";
        try {
            File fXmlFile = new File(".//disciplines//"+id+"_info.xml");
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder;
            dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(fXmlFile);
            doc.getDocumentElement().normalize();
            NodeList nList = doc.getElementsByTagName("annotation");
            Node nNode = nList.item(0);
            if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                Element eElement = (Element) nNode;
                anno = eElement.getTextContent();
            } else {
                logger.warn("No annotation found (Node type: "+nNode.getNodeType()+")");
            }
            annotations.put(id, anno);
        } catch (ParserConfigurationException | IOException | SAXException e ) {
            logger.trace("Error parsing XML for discipline " + id, e);
        }
        return anno;
    }

}
