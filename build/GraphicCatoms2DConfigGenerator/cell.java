import processing.core.*;
import static java.lang.System.out;
import java.lang.Math;
import java.util.Random;

class Cell {
 
 int id;
 int index;
 Coordinate gridCoordinates;
 Coordinate worldCoordinates;
 Parameters parameters;
 float strokeSize;
 
 boolean target;
 boolean initial;
 
 Color initialColor;
 Color targetColor;
 
 Cell(int i, int j, Parameters parameters, Coordinate gridCoordinates) {
   this.gridCoordinates = gridCoordinates;
   this.parameters = parameters;
   worldCoordinates = getWorldCoordinates();
   index = i;
   id = j;
   int x = (int)gridCoordinates.x;
   int y = (int)gridCoordinates.y;
   strokeSize = parameters.getStrokeSize();
   initial = false;
   target = false;
   this.initialColor = Color.gray;
   this.targetColor = Color.gray;
 }
 
 Coordinate getWorldCoordinates() {
    float cellSize = parameters.getCellSize();
    float height = parameters.getScreenHeight();
    float x = gridCoordinates.x;
    float y = gridCoordinates.y;
    double worldX = cellSize/2 + cellSize * (x + 0.5 * ((int)y & 1));
    double worldY = height - (cellSize/2 + cellSize * Math.sqrt(3)/2 * y);
    return new Coordinate((float)worldX,(float)worldY);
 }
 
 void draw(boolean border) {
  if (!isFilled()) {
     draw(255,255,255,border); // white
  } else {
     int r = 0;
     int g = 0;
     int b = 0;
     if (initial) {
       r = initialColor.getR();
       g = initialColor.getG();
       b = initialColor.getB();
     } else if (target) {
       r = targetColor.getR();
       g = targetColor.getG();
       b = targetColor.getB();
     }
     //System.out.println(color.id + ": " + r + " " + g + " " + b);
     draw(r,g,b,border); 
  }
 }
  
  void draw(int r, int g, int b, boolean border) {
   PApplet applet = parameters.getPApplet();
   float cellSize = parameters.getCellSize();
   
   if(!isFilled()) { return; }
     
   applet.strokeWeight(strokeSize);
   applet.stroke(0,0,0);
   float mySize = cellSize - strokeSize/2;
   
   if (border)  {
     float factor = (float) (20.0/100.0);
     if (initial) {
       applet.fill(0,255,0);
     } else {
       applet.fill(255,255,255);
     }
     applet.ellipse(worldCoordinates.x,worldCoordinates.y,mySize,mySize);
     
     ///*
     mySize = mySize- mySize*factor;
     mySize = mySize - strokeSize/2;
     
     if (target) {
       applet.fill(255,0,0);
     } else {
       applet.fill(255,255,255);
     }
     applet.ellipse(worldCoordinates.x,worldCoordinates.y,mySize,mySize);
     mySize = mySize-mySize*factor;
   }
   
   applet.fill(r,g,b);
   mySize = mySize - strokeSize/2;
   applet.ellipse(worldCoordinates.x,worldCoordinates.y,mySize,mySize);
   //applet.strokeWeight(strokeSize/2);
   //applet.stroke(0,255,0);
   //applet.fill(r,g,b);
   
   //applet.ellipse(worldCoordinates.x,worldCoordinates.y,cellSize-strokeSize/2,cellSize-strokeSize/2);
   //applet.strokeWeight(parameters.getStrokeSize());
 }
 
 void setBorderSize(float f) {
  strokeSize = f; 
 }
  
 boolean isFilled() {
    return initial || target;
 }
 
 void setInitialColor(Color color) {
   initialColor = color;  
 }
 
 void setTargetColor(Color color) {
   initialColor = color;  
 }
 
 boolean intersect(float x, float y) {
   float cellSize = parameters.getCellSize();
   return (x>worldCoordinates.x - cellSize/2) && (x<worldCoordinates.x+cellSize/2) &&
      (y>worldCoordinates.y-cellSize/2 )&& (y<worldCoordinates.y+cellSize/2);
 }
 
 float euclideanDistance(Cell c) {
    float x1 = worldCoordinates.x;
    float y1 = worldCoordinates.y;
    
    float x2 = c.worldCoordinates.x;
    float y2 = c.worldCoordinates.y; 
    return (float) Math.sqrt(Math.pow(x1-x2,2) + Math.pow(y1-y2,2)); 
 }
 
 boolean areNeighbors(Cell c) {
   // euclidean distance <= 
   return Math.round(parameters.getCellSize() - euclideanDistance(c)) == 0; // <= width+2;
 }
 
 void setInitial(boolean b) {
   initial = b; 
 }
 
 void setTarget(boolean b) {
   target = b; 
 }
 
 @Override
 public int hashCode() {
   return this.id;
  }
     
 @Override
 public boolean equals(Object obj) {
   Cell c = null;
   if(obj instanceof Cell){
     c = (Cell) obj;
   }
   if(this.id == c.id){
     return true;
   } else {
     return false;
   }     
 }
 
 @Override
 public String toString() {
   return id + "(" + gridCoordinates.x + "," + gridCoordinates.y + ")"; 
 }
 
}