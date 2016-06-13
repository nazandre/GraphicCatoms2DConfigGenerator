import processing.core.*;

class Parameters {
  
  PApplet applet;
  float cellSize;
  float strokeSize;
  
  Parameters (PApplet applet, float cellSize, float strokeSize) {
    this.applet = applet;
    this.cellSize = cellSize;
    this.strokeSize = strokeSize;
  }
  
  PApplet getPApplet() {
    return applet;
  }
  
  float getCellSize() {
     return cellSize; 
  }
  
  float getStrokeSize() {
     return strokeSize; 
  }
  
  float getScreenWidth() {
    return applet.width; 
  }
  
  float getScreenHeight() {
    return applet.height; 
  }
  
  float getGridWidth() {
    return (float) Math.ceil(getScreenWidth()/getCellSize())+1;
  }

  float getGridHeight() {
    return (float) Math.ceil(2*getScreenHeight()/(getCellSize()+Math.sqrt(3)/2*getCellSize()))+1;
  }
}