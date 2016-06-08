class Coordinate {
  float x;
  float y;
  
  Coordinate(float x, float y) {
    this.x = x;
    this.y = y;
  }
  
  Coordinate getGridCoordinates() {
   float gridX = 0;
   float gridY = 0;
   return new Coordinate(gridX,gridY);
  }
}
