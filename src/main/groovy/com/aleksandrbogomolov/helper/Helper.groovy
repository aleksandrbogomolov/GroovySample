package groovy.com.aleksandrbogomolov.helper

import javax.xml.stream.XMLInputFactory
import javax.xml.stream.XMLOutputFactory
import javax.xml.stream.events.Attribute
import javax.xml.transform.OutputKeys
import javax.xml.transform.TransformerFactory
import javax.xml.transform.stream.StreamResult
import javax.xml.transform.stream.StreamSource
import java.util.logging.Logger

class Helper {

  private static final LOGGER = Logger.getLogger(Helper.class.name)

  static Properties readProperties() {
    def properties = new Properties()
    def file = new File("src/main/resources/application.properties")
    properties.load(file.newDataInputStream())
    LOGGER.info("Loaded properties file from source.")
    properties
  }

  static void createXml(List<Integer> rows, String path) {
    def factory = XMLOutputFactory.newInstance()
    def writer = factory.createXMLStreamWriter(new FileWriter(path))
    writer.writeStartDocument("UTF-8", "1.0")
    writer.writeStartElement("entries")
    rows.each {
      writer.writeStartElement("entry")
      writer.writeStartElement("field")
      writer.writeCharacters("" + it)
      writer.writeEndElement()
      writer.writeEndElement()
    }
    writer.writeEndElement()
    writer.writeEndDocument()
    LOGGER.info("Created new file $path, appended ${rows.size()} entry and saved on disk.")
    writer.close()
  }

  static void transformXml(String xsl, String firstPath, String secondPath) {
    def factory = TransformerFactory.newInstance()
    def transformer = factory.newTransformer(new StreamSource(xsl))
    transformer.setOutputProperty(OutputKeys.INDENT, "yes")
    transformer.transform(new StreamSource(firstPath), new StreamResult(secondPath))
    LOGGER.info("Transformed $firstPath to the $secondPath.")
  }

  static int parseXml(String path) {
    def factory = XMLInputFactory.newInstance()
    def reader = factory.createXMLEventReader(new FileReader(path))
    def element
    def result = 0
    while (reader.hasNext()) {
      def event = reader.nextEvent()
      if (event.isStartElement()) {
        element = event.asStartElement()
        if ("entry" == element.getName().getLocalPart()) {
          Iterator<Attribute> iterator = element.getAttributes()
          result += iterator.next().getValue().toInteger()
        }
      }
    }
    LOGGER.info("Parsed $path.")
    result
  }
}
