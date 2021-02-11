package org.example.first;

import java.util.Random;

public class Forest {
  private final boolean[][] forest;
  private final Coordinates coordinates;

  // Generating forest as a square of n * n.
  // Placing target in one of the cells.
  Forest(int n) {
    forest = new boolean[n][n];
    Random random = new Random();
    int targetPlace = random.nextInt(n * n);
    coordinates = new Coordinates(targetPlace / n, targetPlace % n);
    forest[coordinates.x][coordinates.y] = true;
  }

  public boolean[][] getForest() {
    return forest;
  }

  public Coordinates getCoordinates() {
    return coordinates;
  }
}
