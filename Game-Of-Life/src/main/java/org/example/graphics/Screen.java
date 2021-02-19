package org.example.graphics;

import org.example.logic.Board;

import java.util.Arrays;
import java.util.Random;

public class Screen {
  public static final int DEFAULT_SIZE = 50;
  private final int width;
  private final int height;
  private final Board board;
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
    board = new Board(columnNumber, rowNumber);
    pixels = new int[width * height];
  }

  public void clear() {
    Arrays.fill(pixels, 0);
  }

  public void render() {

//    for (int y = 0; y < height; y++) {
//      for (int x = 0; x < width; x++) {
//        int tileIndex = ;
//        pixels[x + y * width] = tiles[tileIndex];
//      }
//    }
  }
}
