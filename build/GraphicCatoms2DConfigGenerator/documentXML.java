import java.io.File;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
 
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.Text;
import org.xml.sax.SAXException;

// See:
// http://research.jacquet.xyz/teaching/java/xml-dom/
// http://stackoverflow.com/questions/4138754/getting-an-attribute-value-in-xml-element

class DocumentXML {
  
  Document doc;
  
  DocumentXML(String file) {
    doc = null;
    DocumentBuilder docBuilder = null;
    try {
      docBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
    } catch(ParserConfigurationException e) {
      System.err.println("ERROR: DocumentBuilder not created!");
      System.exit(1);
    }
     
    try {
      doc = docBuilder.parse(new File(file));
    } catch(SAXException e) {
      System.err.println("ERROR: in " + file + " parsing");
    } catch (IOException e) {
      System.err.println("ERROR: in/out error while reading " + file);
    }
  }
  
  /*        NodeList nodeList = document.getElementsByTagName("Item");
        for(int x=0,size= nodeList.getLength(); x<size; x++) {
            System.out.println(nodeList.item(x).getAttributes().getNamedItem("name").getNodeValue());
        } */
  
  
}