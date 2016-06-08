import processing.core.*;

class Parameters {
  
  PApplet applet;
  String configurationFileName;
  float cellSize;
  float strokeSize;
  
  Parameters (PApplet applet, String configurationFileName, float cellSize, float strokeSize) {
    this.applet = applet;
    this.configurationFileName = configurationFileName;
    this.cellSize = cellSize;
    this.strokeSize = strokeSize;
  }
  
  PApplet getPApplet() {
    return applet;
  }
  
  String getConfigurationFileName() {
    return configurationFileName; 
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
  
  String getAbsolutePath(String file) {
    return applet.sketchPath(file); 
  }
  
  String getConfigurationFilePath() {
    return getAbsolutePath(configurationFileName);
  }
}