import java.util.Vector;
import java.util.Iterator;

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
  
  static Color getColor(int r, int g, int b) {
    Color col = new Color(r,g,b);
    Vector<Color> colors = new Vector();
    Iterator it;
    
    colors.add(white);
    colors.add(black);
    colors.add(red);
    colors.add(green);
    colors.add(blue);
    colors.add(yellow);
    colors.add(indigo);
    colors.add(orange);
    colors.add(gray);
    colors.add(aqua);
    colors.add(purple);
    
    it = colors.iterator();
    while(it.hasNext()) {
      Color c = (Color) it.next();
      if (col.equals(c)) {
         return c; 
      }
    }
    return null;
  }
  
  @Override public boolean equals(Object o) {
    if ( this == o )
      return true;
    
    if ( !(o instanceof Color) ) 
      return false;
    
    Color col = (Color) o;
    
    return (col.r == r) && (col.g == g) && (col.b == b); 
  }
}