package org.example.logic;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

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
        int nbNeighbours = getNeighbours(i, j);

        if (cell.alive && (nbNeighbours < 2 || nbNeighbours > 3)) {
          deadCells.add(cell);
        }

        if ((cell.alive && (nbNeighbours == 3 || nbNeighbours == 2))
            || (!cell.alive && nbNeighbours == 3)) {
          liveCells.add(cell);
        }
      }
    }

    for (Cell cell : liveCells) cell.setAlive(true);

    for (Cell cell : deadCells) cell.setAlive(false);
  }

  public int getNeighbours(int i, int j) {
    int neighbours = 0;

    for (int k = i - 1; k <= i + 1; k++) {
      for (int l = j - 1; l <= j + 1; l++) {
        if ((k != i || l != j) && k >= 0 && k < width && l >= 0 && l < height) {
          Cell cell = cells[k][l];
          if (cell.alive) {
            neighbours++;
          }
        }
      }
    }

    return neighbours;
  }

  public Cell get(int i, int j) {
    return cells[i][j];
  }
}
