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
static int screenWidth = 1500;
static int screenHeight = 768;
static int cellSize = 40;
static int strokeSize = 2;
static int sizeRandomConfiguration = 300;
static boolean random = false;
static String confToSave = "catoms2d-config.xml";
static String confToLoad = "config.xml";

static int initial = 0;
static int target = 1;
static int selectedGrid;
static String gridName[] = {"initial", "target"};
static Grid grid;
static Parameters parameters;
static Color selectedColor;

String getAbsolutePath(String file) {
    return sketchPath(file);
}

void settings() {
  size(screenWidth,screenHeight);
}

void start() {
  parameters = new Parameters(this, cellSize, strokeSize);
  selectedColor = Color.gray;
  // initialize the cells
  grid = new Grid(parameters);
  selectedGrid = initial;
  
  if (confToLoad != "") {
    DocumentXML.importConfig(grid,getAbsolutePath(confToLoad));
    System.out.println(getAbsolutePath(confToLoad) + " loaded!");
  }
}

void mousePressed() {
 Cell c = grid.getCellWorldCoord(mouseX,mouseY);
 if (c != null) {
   String s = "(" + (int) c.gridCoordinates.x + "," + (int) c.gridCoordinates.y + ")";
   if (mouseButton == LEFT) {
      if (selectedGrid == initial) {
        grid.setInitial(c,true);
        c.setInitialColor(selectedColor);
      } else {
        grid.setTarget(c,true);
        c.setTargetColor(selectedColor);
      }
      System.out.println(s + " filled in " + gridName[selectedGrid] + "!");
   } else if (mouseButton == RIGHT) {
      if (selectedGrid == initial) {
        grid.setInitial(c,false);
      } else {
        grid.setTarget(c,false);
      }
      System.out.println(s + " unfilled in " + gridName[selectedGrid] +  "!");
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
    case 'n': // display size of grids
      grid.printSize();
    break;
    case 'z': // disable border
      grid.displayBorder = !grid.displayBorder;
    break;
    /*case 'l': // (re)-load
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
    case 'c': // gray (classic) 
      selectedColor = Color.gray;
    break;
    case 'd': // black (dark)
      println("Color set to black");
      selectedColor = Color.black;
    break;
    case 'a': // aqua
      println("Color set to aqua");
      selectedColor = Color.aqua;
    break;
    case 'w': // white
      println("Color set to white");
      selectedColor = Color.white;
    break;
    case 'r': // red
      println("Color set to red");
      selectedColor = Color.red;
    break;
    case 'g': // green
      println("Color set to green");
      selectedColor = Color.green;
    break;
    case 'b': // blue
      println("Color set to blue");
      selectedColor = Color.blue;
    break;
    case 'o' : // orange
      println("Color set to orange");
      selectedColor = Color.orange;
    case 'y' : // yellow
      println("Color set to yellow");
      selectedColor = Color.yellow;
    default:
      ;;
  }
}

void draw() {
 background(255,255,255);
 grid.draw();
}