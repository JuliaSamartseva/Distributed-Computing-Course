package org.example.graphics;

public interface Renderable {
  public int getWidth();
  public int getHeight();
  public int[] getPixels();
  public void render(int x, int y, Screen screen);

}