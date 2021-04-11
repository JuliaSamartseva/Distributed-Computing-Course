package parsers;

import com.sun.org.apache.xerces.internal.parsers.XMLParser;
import data.Geometry;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.net.URL;

public class XMLGeometry {
  public void saveToFile(String xmlFilename, Geometry geometry) {
    try {
      JAXBContext jaxbContext = JAXBContext.newInstance(Geometry.class);
      Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
      jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
      File file = new File(xmlFilename);
      jaxbMarshaller.marshal(geometry, file);
    } catch (JAXBException e) {
      e.printStackTrace();
    }
  }

  public static Geometry loadFromFile(String xmlFilename) {
    try {
      ClassLoader classLoader = XMLGeometry.class.getClassLoader();
      URL resource = classLoader.getResource(xmlFilename);
      if (resource == null) {
        throw new IllegalArgumentException("File not found.");
      } else {
        File file = new File(resource.getFile());
        JAXBContext jaxbContext = JAXBContext.newInstance(Geometry.class);
        Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
        return (Geometry) jaxbUnmarshaller.unmarshal(file);
      }
    } catch (JAXBException e) {
      e.printStackTrace();
    }
    return null;
  }
}
