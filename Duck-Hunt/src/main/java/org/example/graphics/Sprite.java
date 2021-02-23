package org.example.graphics;

public class Sprite implements Renderable {
  private int width, height;
  private int[] pixels;

  public Sprite(String fileName) {
    create(Texture.load(fileName));
  }

  public Sprite(Texture texture) {
    create(texture);
  }

  private void create(Texture texture) {
    width = texture.getWidth();
    height = texture.getHeight();
    pixels = texture.getPixels(Texture.FORMAT_RGB);
  }

  @Override
  public int getWidth() {
    return 0;
  }

  @Override
  public int getHeight() {
    return 0;
  }

  @Override
  public int[] getPixels() {
    return new int[0];
  }

  @Override
  public void render(int x, int y, Screen screen) {
    screen.render(x, y, this);
  }
}
