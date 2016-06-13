import java.util.ArrayList;

class Color {
  int r;
  int g;
  int b;
  
  static Color white = new Color (255,255,255);
  static Color black = new Color (0,0,0);
  static Color red = new Color (255,0,0);
  static Color green = new Color (0,255,0);
  static Color blue = new Color (0,0,255);
  static Color yellow  = new Color (255,255,0);  
  static Color indigo = new Color (75, 0, 130);
  static Color orange = new Color (255,140,0);
  static Color gray = new Color (100,100,100);
  static Color aqua = new Color (18, 189, 185);
  static Color purple = new Color (139, 0, 255);

  Color(int r, int g, int b) {
    this.r = r;
    this.g = g;
    this.b = b;
  }
  
  Color(int c) {
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