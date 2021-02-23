package org.example.logic;

import java.util.*;

public class Board {
  private final Cell[][] cells;
  private final int width;
  private final int height;

  public Board(int width, int height) {
    this.width = width;
    this.height = height;
    Random random = new Random();
    cells = new Cell[width][height];
    for (int i = 0; i < width; i++)
      for (int j = 0; j < height; j++) {
        cells[i][j] = new Cell(random.nextBoolean());
      }
  }

  public void generateNext() {
    List<Cell> liveCells = new ArrayList<>();
    List<Cell> deadCells = new ArrayList<>();

    for (int i = 0; i < width; i++) {
      for (int j = 0; j < height; j++) {
        Cell cell = cells[i][j];
        HashMap<Integer, Integer> neighboursNumber = getNeighbours(i, j);
        int allNeighbours = getAllNeighbours(i, j);

        if (cell.isAlive() && (allNeighbours < 2 || allNeighbours > 3)) {
          deadCells.add(cell);
        }

        if ((cell.isAlive() && (allNeighbours == 3 || allNeighbours == 2))
            || (!cell.isAlive() && allNeighbours == 3 && neighboursNumber.size() == 1)) {

          int mostNeighbours = -1;
          int color = -1;
          for (Map.Entry<Integer, Integer> entry : neighboursNumber.entrySet()) {
            if (entry.getValue() > mostNeighbours) {
              mostNeighbours = entry.getValue();
              color = entry.getKey();
            }
          }

          cell.setColor(color);

          liveCells.add(cell);
        }
      }
    }

    for (Cell cell : liveCells) cell.setAlive(true);

    for (Cell cell : deadCells) cell.setAlive(false);
  }

  public HashMap<Integer, Integer> getNeighbours(int i, int j) {
    HashMap<Integer, Integer> neighbours = new HashMap<>();

    for (int k = i - 1; k <= i + 1; k++) {
      for (int l = j - 1; l <= j + 1; l++) {
        if ((k != i || l != j) && k >= 0 && k < width && l >= 0 && l < height) {
          Cell cell = cells[k][l];
          if (cell.isAlive()) {
            neighbours.putIfAbsent(cell.getColor(), 0);
            neighbours.put(cell.getColor(), neighbours.get(cell.getColor()) + 1);
          }
        }
      }
    }

    return neighbours;
  }

  public int getAllNeighbours(int i, int j) {
    int neighbours = 0;
    for (int k = i - 1; k <= i + 1; k++) {
      for (int l = j - 1; l <= j + 1; l++) {
        if ((k != i || l != j) && k >= 0 && k < width && l >= 0 && l < height) {
          Cell cell = cells[k][l];
          if (cell.isAlive()) {
            neighbours++;
          }
        }
      }
    }

    return neighbours;
  }

  public void regenerateBoard(int generationNumber) {
    Random random = new Random();
    int[] colors = new int[generationNumber];
    for (int i = 0; i < colors.length; i++) {
      colors[i] = random.nextInt(0xff00ff);
    }

    for (int i = 0; i < width; i++)
      for (int j = 0; j < height; j++) {
        cells[i][j].setAlive(random.nextBoolean());
        int colorIndex = random.nextInt(colors.length);
        cells[i][j].setColor(colors[colorIndex]);
      }
  }

  public Cell get(int i, int j) {
    try {
      return cells[i][j];
    } catch (ArrayIndexOutOfBoundsException e) {
      throw new ArrayIndexOutOfBoundsException(
          "i = " + i + " maximum width = " + width + " j = " + j + " maximum height = " + height);
    }
  }
}
