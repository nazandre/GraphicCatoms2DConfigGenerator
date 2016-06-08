import java.util.ArrayList;

class Color {
  int r;
  int g;
  int b;
  
  int id;

  Color(int r, int g, int b, int id) {
    this.r = r;
    this.g = g;
    this.b = b;
    this.id = id;
  }
  
  Color(int c) {
    id = -1;
    r = (c >> 16) & 0xFF;
    g = (c >> 8) & 0xFF;
    b = c & 0xFF;
    //System.out.println("color: " + c + " => r: " + r + " g: " + g + " b: " + b);
 }
  
  Color() {
   // random
   do {
     this.r  = RandomUtils.generate()%255;
     this.g  = RandomUtils.generate()%255;
     this.b  = RandomUtils.generate()%255;
     this.id =  RandomUtils.generate()%255; // id is not actually used...
   } while ((r == 255) && (g == 255) && (b == 255));
  }
  
  int getR() {
     return r; 
  }
  
  int getG() {
     return g; 
  }
  
  int getB() {
     return b; 
  }
}

class ColorDictionnary {
  ArrayList<Color> colors;
  Parameters parameters;
  
  ColorDictionnary(Parameters parameters) {
    this.parameters = parameters;
    
    colors = new ArrayList();
    
    Color white = new Color (255,255,255,0);
    Color black = new Color (0,0,0,1);
    Color red = new Color (255,0,0,2);
    Color green = new Color (0,255,0,3);
    Color blue = new Color (0,0,255,4);
    
    colors.add(white); // 0
    colors.add(black); // 1
    colors.add(red); // 2
    colors.add(green); // 3
    colors.add(blue); // 4
  }

  Color getColor(int index) {
    if (index >= 0 && index < size()) {
      return colors.get(index); 
    }
    return null;
  }
  
  int size() {
    return colors.size(); 
  }
}
