package org.example.graphics;

import org.example.logic.Board;
import org.example.logic.Cell;

import java.awt.*;
import java.util.Arrays;

public class Screen {
  public static final int DEFAULT_SIZE = 10;
  public static final int DEFAULT_DEAD_COLOR = Color.BLACK.getRGB();
  public final Board board;
  private final int width;
  private final int height;
  private final int columnNumber;
  private final int rowNumber;
  private final int rowHeight;
  private final int columnWidth;
  public int[] pixels;

  public Screen(int width, int height) {
    this.width = width;
    this.height = height;
    this.columnNumber = width / DEFAULT_SIZE;
    this.rowNumber = height / DEFAULT_SIZE;
    this.columnWidth = width / columnNumber;
    this.rowHeight = height / rowNumber;
    this.board = new Board(columnNumber, rowNumber);
    pixels = new int[width * height];
  }

  public void regenerateBoard(int generationsNumber) {
    board.regenerateBoard(generationsNumber);
  }

  public void clear() {
    Arrays.fill(pixels, 0);
  }

  public void render() {
    int currentRow = 0;
    int currentColumn = 0;

    for (int y = 0; y < height; y++) {
      currentRow = y / rowHeight;
      for (int x = 0; x < width; x++) {
        currentColumn = x / columnWidth;
        if (currentRow >= rowNumber) currentRow -= 1;
        else if (currentColumn >= columnNumber) currentColumn -= 1;
        Cell cell = board.get(currentColumn, currentRow);
        pixels[x + y * width] = cell.isAlive() ? cell.getColor() : DEFAULT_DEAD_COLOR;
      }
    }
  }
}
