import java.lang.Math;

class RandomUtils {
 
  static int generate(int min, int max) {
    return min + (int)(Math.random() * ((max - min) + 1)); 
  }
  
  static int generate() {
    return generate(0,99999999); 
  }
  
}
