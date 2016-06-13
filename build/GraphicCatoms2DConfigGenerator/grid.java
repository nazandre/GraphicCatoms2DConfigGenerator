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
  
  Set<Cell> target;
  Set<Cell> initial;
  Parameters parameters;
  
  boolean displayBorder;
  
  Grid (Parameters parameters) {
    this.parameters = parameters;
    cells = new ArrayList();
    filledCells = new HashSet();
    target = new HashSet();
    initial = new HashSet();
    displayBorder = true;
    
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

  void setTarget(Cell c, boolean b) {
     if (b) {
       target.add(c);
     } else {
       target.remove(c);
     }
     c.setTarget(b);
  }
  
  void setInitial(Cell c, boolean b) {
     if (b) {
       initial.add(c);
     } else {
       initial.remove(c);
     }
     c.setInitial(b);
  }
      
  void draw() {
    for (int i = 0; i < cells.size(); i++) {
     Cell c = cells.get(i);
     //drawNbNeighbors(c);
     c.draw(displayBorder);
     //if (violateConditions(c)) {
     // c.draw(255,0,0);     
     //}
   } 
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
  
  Rectangle getRectangle() {
    Point leftTop = new Point(Integer.MAX_VALUE,Integer.MAX_VALUE);
    Point rightBottom = new Point(0,0);
    Iterator<Cell> it;
    
    float x = 0, y = 0;
    for (int i = 0; i < 2; i++) {
      if (i == 0) {
        it = initial.iterator();
      } else {
        it = target.iterator(); 
      }
      while(it.hasNext()) {
        Cell c = it.next();
        x = Math.min(c.worldCoordinates.x, (float)leftTop.getX());
        y = Math.min(c.worldCoordinates.y, (float)leftTop.getY());
        leftTop.move((int)x,(int)y);
        x = Math.max(c.worldCoordinates.x, (float)rightBottom.getX());
        y = Math.max(c.worldCoordinates.y, (float)rightBottom.getY());
        rightBottom.move((int)x,(int)y);
      }
    }
    x = (float)leftTop.getX() - parameters.getCellSize()/2;
    y = (float)leftTop.getY() - parameters.getCellSize()/2;
    leftTop.move((int)x,(int)y);
    int w = (int)(rightBottom.getX()-leftTop.getX() + parameters.getCellSize()/2);
    int h = (int)(rightBottom.getY()-leftTop.getY() + parameters.getCellSize()/2);
    Dimension d = new Dimension(w,h);
    return new Rectangle(leftTop,d);
   }
   
  Rectangle getGridRectangle() {
    Point leftTop = new Point(Integer.MAX_VALUE,Integer.MAX_VALUE);
    Point rightBottom = new Point(0,0);
    Iterator<Cell> it;
    float x = 0, y = 0;
    
    for (int i = 0; i < 2; i++) {
      if (i == 0) {
        it = initial.iterator();
      } else {
        it = target.iterator(); 
      }
      while(it.hasNext()) {
        Cell c = it.next();
        x = Math.min(c.gridCoordinates.x, (float)leftTop.getX());
        y = Math.min(c.gridCoordinates.y, (float)leftTop.getY());
        leftTop.move((int)x,(int)y);
        x = Math.max(c.gridCoordinates.x, (float)rightBottom.getX());
        y = Math.max(c.gridCoordinates.y, (float)rightBottom.getY());
        rightBottom.move((int)x,(int)y);
      }
    }
    
    x = (float)leftTop.getX();
    y = (float)leftTop.getY();
    leftTop.move((int)x,(int)y);
    int w = (int)(rightBottom.getX()-leftTop.getX());
    int h = (int)(rightBottom.getY()-leftTop.getY());
    Dimension d = new Dimension(w,h);
    return new Rectangle(leftTop,d);
   }
   
   void printSize() {
      System.out.println("Initial: " + initial.size() + " modules!");
      System.out.println("Target: " + target.size() + " modules!"); 
   }
}