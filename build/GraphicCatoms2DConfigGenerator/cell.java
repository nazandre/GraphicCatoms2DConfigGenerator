import processing.core.*;
import static java.lang.System.out;
import java.lang.Math;
import java.util.Random;

class Cell {
 
 int id;
 int index;
 Coordinate gridCoordinates;
 Coordinate worldCoordinates;
 boolean filled;
 Parameters parameters;
 Color color;
 Color borderColor;
 float strokeSize;
 boolean target;
 boolean initial;
 
 Cell(int i, int j, Parameters parameters, Coordinate gridCoordinates) {
   this.gridCoordinates = gridCoordinates;
   this.parameters = parameters;
   this.filled = false;
   this.color = null;
   this.borderColor = null;
   worldCoordinates = getWorldCoordinates();
   index = i;
   id = j;
   int x = (int)gridCoordinates.x;
   int y = (int)gridCoordinates.y;
   strokeSize = parameters.getStrokeSize();
   initial = false;
   target = false;
 }
 
 Coordinate getWorldCoordinates() {
    float cellSize = parameters.getCellSize();
    float height = parameters.getScreenHeight();
    float x = gridCoordinates.x;
    float y = gridCoordinates.y;
    double worldX = cellSize/2 + cellSize * (x - 0.5 * ((int)y & 1));
    double worldY = height - (cellSize/2 + cellSize * Math.sqrt(3)/2 * y);
    return new Coordinate((float)worldX,(float)worldY);
 }
 
 void draw() {
  if (!filled) {
     draw(255,255,255); // white
  } else {
    if (color == null) {
      draw(0,0,0); // black
    } else {
      int r = color.getR();
      int g = color.getG();
      int b = color.getB();
      //System.out.println(color.id + ": " + r + " " + g + " " + b);
      draw(r,g,b); 
    }
  }
 }
  
  void draw(int r, int g, int b) {
   PApplet applet = parameters.getPApplet();
   float cellSize = parameters.getCellSize();
   
   int rt = 0;
   int rg = 0;
   int rb = 0;
   
   if(!isFilled()) { return; }
   
   if (borderColor != null) {
       rt = borderColor.getR();
       rg = borderColor.getG();
       rb = borderColor.getB(); 
   }
   
   applet.strokeWeight(strokeSize);
   applet.stroke(0,0,0);
   
   float mySize = cellSize - strokeSize/2;
   float factor = (float) (20.0/100.0);
   
   applet.fill(255,0,0);
   applet.ellipse(worldCoordinates.x,worldCoordinates.y,mySize,mySize);
   
   ///*
   mySize = mySize- mySize*factor;
   mySize = mySize - strokeSize/2;
   applet.fill(0,255,0);
   applet.ellipse(worldCoordinates.x,worldCoordinates.y,mySize,mySize);
   //*/
   
   ///*
   applet.fill(r,g,b);
   mySize = mySize-mySize*factor;
   mySize = mySize - strokeSize/2;
   applet.ellipse(worldCoordinates.x,worldCoordinates.y,mySize,mySize);
   //*/
   
   //applet.strokeWeight(strokeSize/2);
   //applet.stroke(0,255,0);
   //applet.fill(r,g,b);
   
   //applet.ellipse(worldCoordinates.x,worldCoordinates.y,cellSize-strokeSize/2,cellSize-strokeSize/2);
   //applet.strokeWeight(parameters.getStrokeSize());
 }
 
 void setBorderSize(float f) {
  strokeSize = f; 
 }
 
 void setBorderColor(Color bc) {
   borderColor = bc;  
 }
 
 boolean isFilled() {
    return filled; 
 }
 
 void fill() {
   filled = true;
 }
 
 void fill(Color color) {
  fill();
  setColor(color); 
 }
 
 void unfill() {
   filled = false; 
 }
 
 void setColor(Color color) {
   this.color = color;  
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