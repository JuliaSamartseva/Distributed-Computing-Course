package org.example.graphics;

import sun.java2d.pipe.RenderBuffer;

import java.awt.image.BufferedImage;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class Screen {
  private final int width;
  private final int height;
  private int[] pixels, imagePixels;

  private BufferedImage image;
  private List<RenderBuffer> buffer = new LinkedList<RenderBuffer>();

  public Screen(int width, int height) {
    this.width = width;
    this.height = height;
    pixels = new int[width * height];
  }

  public void render(int x, int y, Renderable renderable) {
    for (int yy = 0; yy < renderable.getHeight(); yy++) {
      int yp = yy + y;
      for (int xx = 0; xx < renderable.getWidth(); xx++) {
        int xp = xx + x;
        if (xp < 0 || xp >= width || yp < 0 || yp >= height) continue;
        pixels[xp + yp * width] = renderable.getPixels()[xx + yy * renderable.getWidth()];
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

  public BufferedImage getImage() {
    return image;
  }

}
