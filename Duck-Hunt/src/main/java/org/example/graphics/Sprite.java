package org.example.graphics;

import org.example.logic.Bullet;

public class Sprite {
  public static Sprite background = new Sprite("src/main/resources/background.jpg");
  public static Sprite shooter = new Sprite("src/main/resources/shooter.png");
  public static Sprite bullet = new Sprite("src/main/resources/bullet.png");

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
    pixels = texture.getPixels(Texture.FORMAT_RGBA);
  }

  public int getWidth() {
    return width;
  }

  public int getHeight() {
    return height;
  }

  public int[] getPixels() {
    return pixels;
  }
}
