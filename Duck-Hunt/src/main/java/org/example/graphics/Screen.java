package org.example.graphics;

import java.awt.image.BufferedImage;
import java.util.Arrays;

public class Screen {
  private final int width;
  private final int height;
  private final int[] pixels;

  public Screen(int width, int height) {
    this.width = width;
    this.height = height;
    pixels = new int[width * height];
  }

  public void renderBackground() {
    int[] backgroundPixels = Sprite.background.getPixels();
    for (int y = 0; y < height; y++) {
      for (int x = 0; x < width; x++) {
        pixels[x + y * width] = backgroundPixels[x + y * width];
      }
    }
  }

  public void renderPlayer(int xp, int yp, Sprite sprite) {
    int[] playerPixels = sprite.getPixels();
    for (int y = 0; y < sprite.getHeight(); y++) {
      int ya = y + yp;
      for (int x = 0; x < sprite.getWidth(); x++) {
        int xa = x + xp;
        if (xa < 0 || xa >= width || ya < 0 || ya >= height) continue;
        int pixel = playerPixels[x + y * sprite.getWidth()];
        if (pixel == 0) continue;
        pixels[xa + ya * width] = pixel;
      }
    }
  }

  public void clear() {
    Arrays.fill(pixels, 0);
  }

  public int getWidth() {
    return width;
  }

  public int[] getPixels() {
    return pixels;
  }

  public int getHeight() {
    return height;
  }
}
