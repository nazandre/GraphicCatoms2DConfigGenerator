// coordinate system: pointy topped (horizontal layout), offset even row.
//http://www.redblobgames.com/grids/hexagons/

import java.util.Vector;
import java.util.Enumeration;
import java.awt.Rectangle;
import java.awt.Point;
import processing.core.*;

// 640 * 800
// 1024 * 768
// 1920 * 1080
static int screenWidth = 2100;
static int screenHeight = 1000;
static int cellSize = 40;
static int strokeSize = 2;
static int sizeRandomConfiguration = 300;
static boolean random = false;
static String confToSave = "catoms2d-config.xml";
static String confToLoad = "config.xml";

static int initial = 1;
static int target = 2;
int selectedGrid;
Grid grid;

Parameters parameters;
ColorDictionnary colors;
int selectedColorIndex;

String getAbsolutePath(String file) {
    return sketchPath(file);
}

void settings() {
  size(screenWidth,screenHeight);
  //size(2100,1000);
}

void start() {
  //size(screenWidth,screenHeight);
  //size(2100,1000);
  parameters = new Parameters(this, cellSize, strokeSize);
  colors = new ColorDictionnary(parameters);
  selectedColorIndex = 1;
  // initialize the cells
  grid = new Grid(parameters);
  selectedGrid = initial;
  
  //DocumentXML doc = new DocumentXML(getAbsolutePath("config.xml"));
  
  if (confToLoad != "") {
    DocumentXML.importConfig(grid,getAbsolutePath(confToLoad));
    System.out.println(getAbsolutePath(confToLoad) + " loaded!");
  }
}

void mousePressed() {
 // println("mousse pressed");
 Cell c = grid.getCellWorldCoord(mouseX,mouseY);
 if (c != null) {
   String s = c.id + "(" + c.gridCoordinates.x + "," + c.gridCoordinates.y + ")";
   if (mouseButton == LEFT) {
      grid.fillCell(c, colors.getColor(selectedColorIndex));
      System.out.println(s + " filled in " + "gridname" + "!");
   } else if (mouseButton == RIGHT) {
     grid.unfillCell(c);
     System.out.println(s + " unfilled in " + "gridname" +  "!");
   }
 } else {
   System.out.println("null clicked!"); 
 }
}

void keyPressed() {  
  switch(key) {
    case 'i':
      println("Initial configuration mode");
      selectedGrid = initial;
    break;
    case 't':
      println("Target configuration mode");
      selectedGrid = target;
    break;
    case 's': // save
      DocumentXML.exportConfig(grid,getAbsolutePath(confToSave));
      println("Configuration exported in " + confToSave +  "...");
    break;
   /* case 'l': // (re)-load
      println("Loadding...");
      grid.importConfiguration();
      println("ok!");
    break;*/
    case 'p':
      String filename = "picture.png";
      Rectangle rec = grid.getRectangle();
      PImage img  = get((int)rec.getX(),(int)rec.getY(),(int)rec.getWidth(),(int)rec.getHeight());
      img.save(filename);
      System.out.println("picture saved in " + filename);
    break;
    /* Colors */
    case 'd': // black
      println("Color set to black");
      selectedColorIndex = 1;
    break;  
    case 'w': // white
      println("Color set to white");
      selectedColorIndex = 0;
    break;
    case 'r': // red
      println("Color set to red");
      selectedColorIndex = 2;
    break;
    case 'g': // green
      println("Color set to green");
      selectedColorIndex = 3;
    break;
    case 'b': // blue
      println("Color set to blue");
      selectedColorIndex = 4;
    break;
    case 'o' : // orange
      println("Color set to orange");
      selectedColorIndex = -1;
    case 'y' : // yellow
      println("Color set to orange");
      selectedColorIndex = -1;
    default:
      ;;
  }
}

/*
void drawNbNeighbors(Cell c) {
  if (c.filled) {
     int n = numberOfFilledNeighbors(c);
     switch(n) {
      case 1:
       c.draw(0,0,255); // blue
       break;
      case 2:
       c.draw(0,255,0); // green
       break;
      case 3:
       c.draw(255,0,0); // red
       break;
      case 4:
      c.draw(125,0,0);
       break;
      case 5:
      c.draw(0,125,0);
       break;
      case 6:
      c.draw(0,0,125);
       break;
      default:
       c.draw(0,0,0);  
     }
     //c.draw((n*50)%255,(n*25)%255,(n*75)%255);  
   } else {
     c.draw(255,255,255); 
   }
}

boolean violateConditions(Cell c1) {
 ArrayList<Cell> neighbors = filledNeighbors(c1);
 
 if (c1.filled) {
  return false; 
 }
 
 for (int i = 0; i < neighbors.size(); i++) {
   Cell c2 = neighbors.get(i);
   for (int j = 0; j < neighbors.size(); j++) {
    if (i == j) { continue; }
      Cell c3 = neighbors.get(j);
      if (align(c1,c2,c3)) {
       return true;
      }     
   }
 }
 return false;
}*/

void draw() {
 background(255,255,255);
 grid.draw();
}