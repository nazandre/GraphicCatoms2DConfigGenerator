import java.util.*;
import java.io.PrintWriter;
import java.io.FileReader;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.File;

import java.awt.Rectangle;
import java.awt.Point;
import java.awt.Dimension;
//import java.io.BufferReader;
import java.util.Random;

class Grid {
  ArrayList<Cell> cells;
  Set<Cell> filledCells;
  Parameters parameters;
  
  Grid (Parameters parameters) {
    this.parameters = parameters;
    cells = new ArrayList();
    filledCells = new HashSet();
    
    /* Random ids generation */
    int i = 0;
    int gridHeight = (int)parameters.getGridHeight();
    int gridWidth = (int)parameters.getGridWidth();
    int size = gridWidth*gridHeight;
    int ids[] = new int[size];
    for (int j = 0; j < size; j++) {
      ids[j] =  i++;
    }
    Random g = new Random(300); // same each time
    for (i = 0; i < size; i++) { // size random permutations
      int a = 0;
      int b = 0;
      do {
        a = g.nextInt(size);
        b = g.nextInt(size);
      } while (a==b);
      int t = ids[a];
      ids[a] = ids[b];
      ids[b] = t;
    }
    
    /* Cell initialization */
    i = 0;
    for (int y = 0; y < gridHeight; y++) {
      for (int x = 0; x < gridWidth; x++) {
        cells.add (new Cell(i+1,ids[i],parameters, new Coordinate(x,y)));
        i++;
      }
    }
  }
     
  Cell getCell(int i) {
    int index = i - 1;
    if ((index < 0) || (index >= cells.size())) {
      return null;
    }
    return cells.get(index);
  }
  
  Cell getCell(float x, float y) {
    for (int i = 0; i < cells.size(); i++) {
    Cell c = cells.get(i);
    if (c.gridCoordinates.x == x && c.gridCoordinates.y == y) {
      return c; 
    }
  }
  return null;
  }
  
  Cell getCellWorldCoord(float x, float y) {
    for (int i = 0; i < cells.size(); i++) {
      Cell c = cells.get(i);
      if (c.intersect(x,y)) {
        return c; 
      }
   }
  return null;
  }
  
  int size() {
    return filledCells.size();
  }

  void fillCell(Cell c, Color col) {
    fillCell(c);
    c.setColor(col);
  }

  void fillCell(Cell c) {
    c.fill();
    filledCells.add(c);
  }
  
  void unfillCell(Cell c) {
     c.filled = false;  
     filledCells.remove(c);
  }
  
  void click(float x, float y, Color color) {
    for (int i = 0; i < cells.size(); i++) {
      Cell c = cells.get(i);
      if (c.intersect(x,y)) {
        if (c.isFilled()) {
          unfillCell(c); 
        } else {
          fillCell(c,color);
        }
        break;
      }
    }
  }
  
  /*void setCellColor(float x, float y, Color c) {
     
  }*/
  
  void exportConfiguration() {
    exportConfiguration(parameters.getConfigurationFilePath());   
  }
  
  void exportConfiguration(String filePath) {
    PrintWriter output;
    try {
      output = new PrintWriter(filePath);
      Iterator<Cell> it = filledCells.iterator();
      while(it.hasNext()) {
        Cell c = it.next();
            //output.println("fillCell(" + ");"); // Write the coordinate to the file
        output.println(c.gridCoordinates.x + " " + c.gridCoordinates.y);
      }
      output.flush(); // Writes the remaining data to the file
      output.close(); // Finishes the file*/
    } catch (Exception e) {}
  }
  
  void importConfiguration(String f) {
    BufferedReader reader;
    String line;
    
    try {
      reader = new BufferedReader(new FileReader(f));
    } catch (Exception e) {
       System.out.println("invalid file");
       return;
    }
    
    do {
      try {
        line = reader.readLine();
    } catch (IOException e) {
        return;
      }
      
      if (line == null) {
        break; 
      }
      if (line.equals("")) {
        continue; 
      }
      String[] coordinates = line.split(" ");
      float x = Float.parseFloat(coordinates[0]);
      float y = Float.parseFloat(coordinates[1]);
      Cell c = getCell(x,y);
      fillCell(c);
    } while (true);
  }
  
  void importConfiguration() {
    importConfiguration(parameters.getConfigurationFilePath());  
  }
  
  void draw() {
    for (int i = 0; i < cells.size(); i++) {
     Cell c = cells.get(i);
     //drawNbNeighbors(c);
     c.draw();
     //if (violateConditions(c)) {
     // c.draw(255,0,0);     
     //}
   } 
  }
  
  ArrayList<Cell> getNeighbors(Cell c, boolean filled) {
    ArrayList<Cell> neighbors = new ArrayList();
    //System.out.print("Adding: ");
    int j = 0;
    for (int i = 0; i < cells.size(); i++) {
      Cell n = cells.get(i);
      if (n == c) { continue; }
      if ( ((!filled && !n.filled) || (filled && n.filled)) && n.areNeighbors(c)) {
        neighbors.add(n);
        //System.out.print(neighbors.get(j).id + "," );
        j++;
      } 
    }
    //System.out.println("!");
    return neighbors;
  }

  int numberOfNeighbors(Cell c, boolean filled) {
    return getNeighbors(c,filled).size();
  }
  
  boolean align(Cell c1, Cell c2, Cell c3) {
   Coordinate p1 = c1.worldCoordinates; 
   Coordinate p2 = c2.worldCoordinates;
   Coordinate p3 = c3.worldCoordinates; 
 
   // check if the area of the triangle of c1, c2 and c3 is (approx) 0
   // [ Ax * (By - Cy) + Bx * (Cy - Ay) + Cx * (Ay - By) ] / 2
   double area = 1.0/2.0 * (p1.x * (p2.y - p3.y) + p2.x * (p3.y - p1.y) + p3.x * (p1.y - p2.y));
   return Math.round(area) == 0; // >= 0 && area <= 0.001; // area == 0; //
  }

  Cell getRandomNeighbors(Cell c, boolean filled) {
   ArrayList<Cell> neighbors = getNeighbors(c,filled);
   if (neighbors.size() == 0) {
    return null; 
   }
   int n = RandomUtils.generate() % neighbors.size();
   return neighbors.get(n);
  }
  
  void randomConfiguration(int n, boolean blackAndWhite) {
    Cell current = null;
    ArrayList<Cell> fc = new ArrayList();
    // middle lowest-level Cell
    current = getCell(parameters.getGridWidth()/2,0);
    while (filledCells.size() < n) {
      fillCell(current);
      if (!blackAndWhite) {
        Color c = new Color(); // random color
        current.setColor(c);
      }
      fc.add(current);
      do {
        Cell r = fc.get(RandomUtils.generate()%filledCells.size());
        current = getRandomNeighbors(r,false);
      } while (current == null);
   }
  }
  
  Rectangle getRectangle() {
    Point leftTop = new Point(Integer.MAX_VALUE,Integer.MAX_VALUE);
    Point rightBottom = new Point(0,0);
    float x = 0, y = 0;
    Iterator<Cell> it = filledCells.iterator();
    while(it.hasNext()) {
      Cell c = it.next();
      x = Math.min(c.worldCoordinates.x, (float)leftTop.getX());
      y = Math.min(c.worldCoordinates.y, (float)leftTop.getY());
      leftTop.move((int)x,(int)y);
      x = Math.max(c.worldCoordinates.x, (float)rightBottom.getX());
      y = Math.max(c.worldCoordinates.y, (float)rightBottom.getY());
      rightBottom.move((int)x,(int)y);
    }
    x = (float)leftTop.getX() - parameters.getCellSize()/2;
    y = (float)leftTop.getY() - parameters.getCellSize()/2;
    leftTop.move((int)x,(int)y);
    int w = (int)(rightBottom.getX()-leftTop.getX() + parameters.getCellSize()/2);
    int h = (int)(rightBottom.getY()-leftTop.getY() + parameters.getCellSize()/2);
    Dimension d = new Dimension(w,h);
    return new Rectangle(leftTop,d);
   }
}