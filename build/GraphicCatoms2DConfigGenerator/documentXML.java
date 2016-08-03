import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.FileReader;
import java.io.BufferedReader;

import java.awt.Rectangle;
import java.awt.Point;

import java.util.Iterator;

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
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;
import org.xml.sax.SAXException;

// See:
// http://research.jacquet.xyz/teaching/java/xml-dom/
// http://stackoverflow.com/questions/4138754/getting-an-attribute-value-in-xml-element

class DocumentXML {
  
  // reading http://www.mkyong.com/java/how-to-read-xml-file-in-java-dom-parser/
  static void importConfig(Grid grid, String fileName) {
    Document doc = null;
    DocumentBuilder docBuilder = null;
    
    NodeList nList;
    Node nNode;
    Element eElement;

    try {
      docBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
    } catch(ParserConfigurationException e) {
      System.err.println("ERROR: DocumentBuilder not created!");
      System.exit(1);
    }
     
    try {
      doc = docBuilder.parse(new File(fileName));
      doc.getDocumentElement().normalize();
    } catch(SAXException e) {
      System.err.println("ERROR: in " + fileName + " parsing");
    } catch (IOException e) {
      System.err.println("ERROR: in/out error while reading " + fileName);
    }
    
    //System.out.println("Initial:");
    nList = doc.getElementsByTagName("blockList");
    nNode = nList.item(0);
    eElement = (Element) nNode;
    nList = eElement.getElementsByTagName("block");
    for (int temp = 0; temp < nList.getLength(); temp++) {
        nNode = nList.item(temp);
        if (nNode.getNodeType() == Node.ELEMENT_NODE) {
          eElement = (Element) nNode;
          //System.out.println(eElement.getAttribute("position"));
          String spos = eElement.getAttribute("position");
          String pos[] = spos.split(",");
          int x = Integer.parseInt(pos[0]);
          int y = Integer.parseInt(pos[2]);
          Cell c = grid.getCell(x,y);
          grid.setInitial(c,true);
          String col = eElement.getAttribute("color");
          String colRGB[] = col.split(",");
          int r = Integer.parseInt(colRGB[0]);
          int g = Integer.parseInt(colRGB[1]);
          int b = Integer.parseInt(colRGB[2]);
          Color color = Color.getColor(r,g,b);
          if (color == null) {
            color = new Color(r,g,b);
          }
          c.setInitialColor(color);
        }
    }
    
    nList = doc.getElementsByTagName("targetList");
    nNode = nList.item(0);
    eElement = (Element) nNode;
    nList = eElement.getElementsByTagName("target");
    nNode = nList.item(0);
    eElement = (Element) nNode;
    nList = eElement.getElementsByTagName("cell");
    for (int temp = 0; temp < nList.getLength(); temp++) {
        nNode = nList.item(temp);
        if (nNode.getNodeType() == Node.ELEMENT_NODE) {
          eElement = (Element) nNode;
          //System.out.println(eElement.getAttribute("position"));
          String spos = eElement.getAttribute("position");
          String pos[] = spos.split(",");
          int x = Integer.parseInt(pos[0]);
          int y = Integer.parseInt(pos[2]);          
          Cell c = grid.getCell(x,y);
          grid.setTarget(c,true);
          String col = eElement.getAttribute("color");
          String colRGB[] = col.split(",");
          int r = Integer.parseInt(colRGB[0]);
          int g = Integer.parseInt(colRGB[1]);
          int b = Integer.parseInt(colRGB[2]);
          Color color = Color.getColor(r,g,b);
          if (color == null) {
            color = new Color(r,g,b);
          }
          c.setTargetColor(color);
        }
    }
  }
  
  
  static void exportConfig(Grid grid, String fileName) {
    PrintWriter output;
    Iterator<Cell> it;
    
    grid.printSize();
    if (grid.initial.size() != grid.target.size()) {
       System.out.println("WARNING: initial and target grids have different size!"); 
    }
    Rectangle rec = grid.getGridRectangle();
    rec.add(0,0);
    rec.setSize((int) (rec.getWidth()+2), (int) (rec.getHeight()+5)); // margin
    
    Point targetPoint = new Point((int)rec.getWidth()/2, (int)rec.getHeight()/2);
    int angleAzimut = 0;
    int angleElevation = 25;
    double coef = 115./100.;
    int distance =(int) (Math.max(rec.getWidth(),rec.getHeight())*coef); 
    
    try {
      output = new PrintWriter(fileName);
      
      output.println("<?xml version=\"1.0\" standalone=\"no\" ?>");
      output.println("<world gridSize=\"" + (int)rec.getWidth() + ",1," + (int)rec.getHeight() + "\">");
      output.println("  <camera target=\"" + (int)targetPoint.getX() + "," + (int)targetPoint.getY() + "," + 0 + "\" directionSpherical=\"" + angleAzimut + "," + angleElevation + "," + distance + "\" angle=\"45\"/>");
      output.println("  <spotlight target=\"" + (int)targetPoint.getX() + "," +(int) targetPoint.getY() + "," + 0 + "\" directionSpherical=\"" + angleAzimut + "," + angleElevation + "," + distance + "\" angle=\"30\"/>");
      
      output.println("  <blockList color=\"100,100,100\">");
      it = grid.initial.iterator();
      while(it.hasNext()) {
        Cell c = it.next();
        output.println("    <block" + 
          " position=\"" + (int)c.gridCoordinates.x + ",0," + (int)c.gridCoordinates.y + "\"" +
          " color=\"" + c.initialColor.getR() + "," + c.initialColor.getG() + "," + c.initialColor.getB() + "\"" +
          "/>");
      }
      output.println("  </blockList>");
      
      output.println("  <targetList>");
      output.println("    <target format=\"grid\">");
      it = grid.target.iterator();
      while(it.hasNext()) {
        Cell c = it.next();
        output.println("      <cell" + 
          " position=\"" + (int)c.gridCoordinates.x + ",0," + (int)c.gridCoordinates.y + "\"" +
          " color=\"" + c.targetColor.getR() + "," + c.targetColor.getG() + "," + c.targetColor.getB() + "\"" +
          "/>");
      }
      output.println("    </target>");
      output.println("  </targetList>");
      
      output.println("</world>");

      output.flush(); // Writes the remaining data to the file
      output.close(); // Finishes the file*/
    } catch (Exception e) {}
  }  
}